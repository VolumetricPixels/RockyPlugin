/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2011-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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
package com.volumetricpixels.rockyapi.packet;

import net.minecraft.server.Packet;

import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public interface PacketManager {
	/**
	 * 
	 * @param id
	 * @param vanilla
	 * @param extended
	 */
	void addVanillaPacket(int id, Class<? extends Packet> vanilla,
			Class<? extends PacketVanilla> extended);

	/**
	 * 
	 * @param player
	 * @param packet
	 * @return
	 */
	boolean isAllowedToSend(RockyPlayer player, int packet);

	/**
	 * Returns a MCPacket instance with the default constructor.
	 * <p/>
	 * An id of 256 will give an uncompressed Map Chunk packet
	 * 
	 * @param packetId
	 *            the id of the desired packet
	 * @return an empty MCPacket of type packetId
	 */
	PacketVanilla getInstance(int packetId);

	/**
	 * adds a packet listener for uncompressed map chunk packets
	 * <p/>
	 * These listeners are NOT called from within the main thread.
	 * 
	 * @param listener
	 *            the listener instance
	 */
	void addListenerUncompressedChunk(PacketListener listener);

	/**
	 * adds a packet listener for packets of the given id
	 * <p/>
	 * These listeners are called from the main server thread
	 * 
	 * @param packetId
	 *            the packet id
	 * @param listener
	 *            the listener instance
	 */
	void addListener(int packetId, PacketListener listener);

	/**
	 * removes a packet listener for uncompressed map chunk packets
	 * 
	 * @param listener
	 *            the listener instance
	 * @return true if listener was removed
	 */
	boolean removeListenerUncompressedChunk(PacketListener listener);

	/**
	 * removes a packet listener for packets of the given id
	 * 
	 * @param listener
	 *            the listener instance
	 * @return true if listener was removed
	 */
	boolean removeListener(int packetId, PacketListener listener);

	/**
	 * removes all packet listeners
	 * 
	 * @param listener
	 *            the listener instance
	 * @return true if listener was removed
	 */
	void clearAllListeners();
}