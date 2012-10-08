/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2011-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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
package org.spout.legacyapi.packet.protocol;

import java.io.IOException;

import org.spout.legacyapi.packet.Packet;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;
import org.spout.legacyapi.packet.PacketType;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class PacketAllowAddon implements Packet {

	private boolean[] allowList;

	/**
	 * 
	 * @param allowList
	 */
	public PacketAllowAddon(boolean[] allowList) {
		this.allowList = allowList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		// Handle in client-side
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		for (boolean isAllow : allowList)
			output.writeBoolean(isAllow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(SpoutPlayer player) {
		// Handle in client-side
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void failure(SpoutPlayer player) {
		// Handle in client-side
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return 0;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public PacketType getType() {
		return PacketType.PacketAllowAddon;
	}
}
