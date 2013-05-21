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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Sets;

/**
 * Entry per world for a chunk cache system.
 */
public class ChunkCache {
	/**
	 * The max height in a world
	 */
	public final static int MAX_SIZE = 16 * 5;
	/**
	 * How much per Y does we partition the chunk
	 */
	public final static int CHUNK_PARTITION_SIZE = 2048;
	/**
	 * The magic number
	 */
	public final static int CHUNK_MAGIC_NUMBER = 0x89ABCDEF;
	/**
	 * 
	 */
	public final static double CHUNK_PARTITION_SIZE_MB = 0.001953125;

	/**
	 * Using table lookup Reference
	 */
	private final static long[] CRC64_TABLE;

	/**
	 * The list of chunk a player hash has
	 */
	private Map<String, Set<Long>> playerCache = new HashMap<String, Set<Long>>();

	/**
	 * Initialize the CRC64 lookup table.
	 */
	static {
		CRC64_TABLE = new long[0x100];
		for (int i = 0; i < 0x100; i++) {
			long crc = i;
			for (int j = 0; j < 8; j++) {
				if ((crc & 1) == 1) {
					crc = (crc >>> 1) ^ 0x42F0E1EBA9EA3693L;
				} else {
					crc = (crc >>> 1);
				}
			}
			CRC64_TABLE[i] = crc;
		}
	}

	/**
	 * Gets the current player cache, if the player and the chunk doesn't have
	 * an entry, then creates a new entry for each one of them
	 * 
	 * @param player
	 *            the name of the player
	 * @return the chunk cache structure
	 */
	public synchronized Set<Long> getPlayerCache(String player) {
		if (!playerCache.containsKey(player)) {
			playerCache.put(player,
					Sets.newSetFromMap(new ConcurrentHashMap<Long, Boolean>()));
		}
		return playerCache.get(player);
	}

	/**
	 * Removes a player cache from the memory
	 * 
	 * @param player the name of the player
	 */
	public synchronized void removePlayerCache(String player) {
		if (!playerCache.containsKey(player)) {
			return;
		}
		playerCache.remove(player);
	}
	
	/**
	 * Calculate the hash of a byte array
	 * 
	 * @param buffer
	 *            the buffer that contains the bytes
	 * @return the crc calculation
	 */
	public static long calculateHash(final byte[] buffer) {
		long checksum = 0x0000000000000000;
		for (int i = 0; i < buffer.length; i++) {
			final int lookupidx = ((int) checksum ^ buffer[i]) & 0xff;
			checksum = (checksum >>> 8) ^ CRC64_TABLE[lookupidx];
		}
		return checksum;
	}

}
