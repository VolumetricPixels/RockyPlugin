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
package com.volumetricpixels.rockyapi.packet.protocol;

import java.io.IOException;

import com.volumetricpixels.rockyapi.packet.Packet;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.packet.PacketType;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class PacketMovementAddon implements Packet {

	private RockyPlayer player;
	
	/**
	 * 
	 * @param player
	 */
	public PacketMovementAddon(RockyPlayer player) {
		this.player = player;
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
		output.writeFloat(player.getGravityMultiplier());
		output.writeFloat(player.getWalkingMultiplier());
		output.writeFloat(player.getSwimmingMultiplier());
		output.writeFloat(player.getJumpingMultiplier());
		output.writeFloat(player.getAirSpeedMultiplier());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(RockyPlayer player) {
		// Handle in client-side
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void failure(RockyPlayer player) {
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
		return PacketType.PacketMovementAddon;
	}
}
