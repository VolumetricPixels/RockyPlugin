/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2012-2012, VolumetricPixels <http://www.volumetricpixels.com/>
 * SpoutLegacy is licensed under the GNU Lesser General Public License.
 *
 * SpoutLegacy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutLegacy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spout.legacyapi.packet;

import java.io.IOException;

import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public interface Packet {
	/**
	 * Reads the incoming data from the client.
	 * <p/>
	 * Note: Data should be read in exactly the same order as it was written.
	 * 
	 * @param input
	 *            stream to read data from
	 */
	public void readData(PacketInputStream input) throws IOException;

	/**
	 * Writes the outgoing data to the output stream.
	 * 
	 * @param output
	 *            to write data to
	 */
	public void writeData(PacketOutputStream output) throws IOException;

	/**
	 * Performs any tasks for the packet after data has been successfully read
	 * into the packet.
	 * 
	 * @param player
	 *            for the packet
	 */
	public void handle(SpoutPlayer player);

	/**
	 * Performs any tasks for the packet after the data has NOT been
	 * successfully read into the packet. All values will be at defaults (0,
	 * null, etc) and are unsafe.
	 * <p/>
	 * Failure is run when the packet versions mismatch and data could not be
	 * safely read.
	 * <p/>
	 * It may not be called for all cases of failure.
	 * 
	 * @param player
	 */
	public void failure(SpoutPlayer player);

	/**
	 * Version of the packet this represents. Version numbers should start with
	 * 0. Versions should be incremented any time the member variables or
	 * serialization of the packet changes, to prevent crashing. Mismatched
	 * packet versions are discarded, and {@link #failure(int)} is called.
	 * 
	 * @return version
	 */
	public int getVersion();
	
	/**
	 * 
	 * @return
	 */
	public PacketType getType();
}
