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

import net.minecraft.server.v1_5_R3.Packet250CustomPayload;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet250CustomPayload}
 */
public class PacketPluginMessage extends
		RockyPacketVanilla<Packet250CustomPayload> {

	/**
	 * Default constructor
	 */
	public PacketPluginMessage() {
	}
	
	/**
	 * Gets the channel of the packet
	 */
	public String getChannel() {
		return packet.tag;
	}

	/**
	 * Gets the lenght of the data
	 */
	public int getLenght() {
		return packet.length;
	}

	/**
	 * Gets the byte of the packet
	 * 
	 * @return
	 */
	public byte[] getData() {
		return packet.data;
	}
}
