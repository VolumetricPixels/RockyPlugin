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

import net.minecraft.server.v1_5_R3.Packet56MapChunkBulk;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet56MapChunkBulk}
 */
public class PacketBulkChunkData extends
		RockyPacketVanilla<Packet56MapChunkBulk> {

	/**
	 * Gets chunk X Coordinate (*16 to get true X)
	 */
	public int[] getChunkX() {
		return Reflection.field("c").ofType(int[].class).in(packet).get();
	}

	/**
	 * Gets chunk Z Coordinate (*16 to get true Z)
	 */
	public int[] getChunkZ() {
		return Reflection.field("d").ofType(int[].class).in(packet).get();
	}

	/**
	 * Gets if the world send light to the client
	 */
	public boolean isSkyLight() {
		return Reflection.field("h").ofType(boolean.class).in(packet).get();
	}

	/**
	 * Bitmask with 1 for every 16x16x16 section which data follows in the
	 * compressed data.
	 */
	public int[] getPrimaryMap() {
		return Reflection.field("a").ofType(int[].class).in(packet).get();
	}

	/**
	 * Same as {@see #getPrimaryMap()}, but this is used exclusively for the
	 * 'add' portion of the payload
	 */
	public int[] getExtraMap() {
		return Reflection.field("b").ofType(int[].class).in(packet).get();
	}

	/**
	 * Gets the uncompressed data of the chunk to send.
	 */
	public byte[][] getData() {
		return Reflection.field("inflatedBuffers").ofType(byte[][].class)
				.in(packet).get();
	}

}
