/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2012-2013, VolumetricPixels <http://www.volumetricpixels.com/>
 * RockyPlugin is licensed under the GNU Lesser General Public License.
 *
 * RockyPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RockyPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.volumetricpixels.rockyplugin.chunk;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.Deflater;

import net.minecraft.server.v1_5_R3.Packet250CustomPayload;

import org.fest.reflect.core.Reflection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketBulkChunkData;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketChunkData;
import com.volumetricpixels.rockyplugin.reflection.Reflector;
import com.volumetricpixels.rockyplugin.reflection.ReflectorID;

/**
 * Handler of the entire cache system
 */
public class ChunkCacheHandler {
	/**
	 * List of every world in the cache
	 */
	protected static ChunkCache cache = new ChunkCache();

	/**
	 * Stats of the chunk cache module
	 */
	protected static AtomicLong statCacheHit = new AtomicLong(0),
			statCacheMiss = new AtomicLong(0), statTotal = new AtomicLong(0);

	/**
	 * 
	 * @return
	 */
	public static long getCacheHit() {
		return statCacheHit.get();
	}

	/**
	 * 
	 * @return
	 */
	public static long getCacheMiss() {
		return statCacheMiss.get();
	}

	/**
	 * Gets the % of the cache hit
	 * 
	 * @return the % of the cache hit
	 */
	public static double getStatPorcentage() {
		if (statTotal.get() == 0) {
			return 0.0D;
		}
		double value = (statCacheHit.get() * 100) / statTotal.get();

		return (value == Double.NaN ? 0.0D : value);
	}

	/**
	 * Gets how many MB has been saved with the cache module
	 * 
	 * @return how many MB has been saved with the cache module
	 */
	public static double getBandwidth() {
		return statCacheHit.get() * ChunkCache.CHUNK_PARTITION_SIZE_MB;
	}

	/**
	 * Handle packet that the player send us for nearby hashes
	 * 
	 * @param player
	 *            the name of the packet
	 * @param packet
	 *            the packet to handle
	 */
	public static void handlePacket(String player, Packet250CustomPayload packet) {
		Set<Long> playerCache = cache.getPlayerCache(player);

		ByteArrayDataInput in = ByteStreams.newDataInput(packet.data);
		int hashLength = in.readShort();
		for (int i = 0; i < hashLength; i++) {
			playerCache.add(in.readLong());
		}
	}

	/**
	 * Handle 0x38 packet for sending bulk chunks to a player
	 * 
	 * @param player
	 *            the name of the player
	 * @param packet
	 *            the packet to handle
	 */
	public static void handlePacketBulk(String player,
			PacketBulkChunkData packet) throws IOException {

		byte[] oldByteData = Reflection
				.field(Reflector
						.getField(ReflectorID.PACKET_BULK_CHUNK_PACKET_BUFFER))
				.ofType(byte[].class).in(packet.getPacket()).get();

		byte[] newByteData = handleCompression(player, oldByteData);

		Reflection
				.field(Reflector
						.getField(ReflectorID.PACKET_BULK_CHUNK_PACKET_BUFFER))
				.ofType(byte[].class).in(packet.getPacket()).set(newByteData);
		packet.getPacket().compress();
	}

	/**
	 * Handle 0x33 packet for sending a single chunk to a player
	 * 
	 * @param player
	 *            the name of the player
	 * @param packet
	 *            the packet to handle
	 */
	public static void handlePacket(String player, PacketChunkData packet)
			throws IOException {
		byte[] oldByteData = packet.getData();
		byte[] newByteData = handleCompression(player, oldByteData);

		Deflater deflater = new Deflater(-1);
		deflater.setInput(newByteData, 0, newByteData.length);
		deflater.finish();
		byte[] buffer = new byte[newByteData.length];
		int size = deflater.deflate(buffer);
		deflater.end();

		Reflection
				.field(Reflector
						.getField(ReflectorID.PACKET_CHUNK_PACKET_BUFFER))
				.ofType(byte[].class).in(packet.getPacket()).set(buffer);
		Reflection
				.field(Reflector.getField(ReflectorID.PACKET_CHUNK_PACKET_SIZE))
				.ofType(int.class).in(packet.getPacket()).set(size);
	}

	/**
	 * 
	 * @param buffer
	 * @throws IOException
	 */
	public static byte[] handleCompression(String playerName, byte[] buffer)
			throws IOException {
		Set<Long> playerCache = cache.getPlayerCache(playerName);

		// Each chunk sended is handled by:
		// - BlockType: Whole byte per block
		// - BlockMetaData: Half byte per block
		// - BlockLight: Half byte per block
		// - SkyLight: Half byte per block (Only of handleLight is TRUE)
		// - AddArray: Half byte per block (Only if extraMask has the bit,
		// support for FORGE)
		// - BiomeArray: Whole byte per XZ coordinate (Only if isContinous is
		// TRUE)
		int chunkLen = buffer.length / ChunkCache.CHUNK_PARTITION_SIZE;
		if ((chunkLen & 0x7FF) != 0) {
			chunkLen++;
		}

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		byte[] chunkData = new byte[ChunkCache.CHUNK_PARTITION_SIZE];

		// Write the magic number
		out.writeInt(ChunkCache.CHUNK_MAGIC_NUMBER);

		// Write the number of section inside the packet
		out.writeShort(chunkLen);

		// For each CHUNK_PARTITION_SIZE block, check the hash of it.
		for (int i = 0; i < chunkLen; i++) {
			int index = i * ChunkCache.CHUNK_PARTITION_SIZE;
			int length = ChunkCache.CHUNK_PARTITION_SIZE;

			if (index + ChunkCache.CHUNK_PARTITION_SIZE > buffer.length) {
				length = buffer.length - index;
			}

			// Calculate the hash of the current block
			System.arraycopy(buffer, index, chunkData, 0x0000, length);
			long hash = ChunkCache.calculateHash(chunkData);

			// Write the hash into the packet
			out.writeLong(hash);

			// Add the hash into the player cache
			boolean isPresent = playerCache.add(hash);

			// Writes the length of the section
			out.writeShort(isPresent ? length : 0);

			// Check for the chunk with the player cache
			if (isPresent) {
				// Writes the data of the section
				out.write(chunkData);
				statCacheMiss.incrementAndGet();
			} else {
				statCacheHit.incrementAndGet();
			}
			statTotal.incrementAndGet();
		}
		return out.toByteArray();
	}

	/**
	 * Removes a player from the cache
	 * 
	 * @param name
	 *            the name of the player
	 */
	public static void handlePlayerRemoval(String name) {
		cache.removePlayerCache(name);
	}
}
