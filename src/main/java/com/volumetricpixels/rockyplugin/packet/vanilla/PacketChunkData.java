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
package com.volumetricpixels.rockyplugin.packet.vanilla;

import org.fest.reflect.core.Reflection;

import com.volumetricpixels.rockyplugin.reflection.Reflector;
import com.volumetricpixels.rockyplugin.reflection.ReflectorID;

import net.minecraft.server.v1_5_R3.Packet51MapChunk;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet51MapChunk}
 */
public class PacketChunkData extends RockyPacketVanilla<Packet51MapChunk> {

	/**
	 * Gets chunk X Coordinate (*16 to get true X)
	 */
	public int getChunkX() {
		return packet.a;
	}

	/**
	 * Gets chunk Z Coordinate (*16 to get true Z)
	 */
	public int getChunkZ() {
		return packet.b;
	}

	/**
	 * This is True if the packet represents all sections in this vertical
	 * column, where the primary bit map specifies exactly which sections are
	 * included, and which are air.
	 */
	public boolean isContinuous() {
		return packet.e;
	}

	/**
	 * Bitmask with 1 for every 16x16x16 section which data follows in the
	 * compressed data.
	 */
	public int getPrimaryMap() {
		return packet.c;
	}

	/**
	 * Same as {@see #getPrimaryMap()}, but this is used exclusively for the
	 * 'add' portion of the payload
	 */
	public int getExtraMap() {
		return packet.d;
	}

	/**
	 * Gets the uncompressed data of the chunk to send.
	 */
	public byte[] getData() {
		return Reflection
				.field(Reflector
						.getField(ReflectorID.PACKET_CHUNK_PACKET_INFLATED))
				.ofType(byte[].class).in(packet).get();
	}

}
