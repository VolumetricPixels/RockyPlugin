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

import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.Packet104WindowItems;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet104WindowItems}
 */
public class PacketWindowItems extends RockyPacketVanilla<Packet104WindowItems> {

	/**
	 * Gets the id of window which items are being sent for. 0 for player
	 * inventory
	 */
	public int getWindowId() {
		return packet.a;
	}

	/**
	 * Gets the array of items we're updating
	 */
	public ItemStack[] getItems() {
		return packet.b;
	}

	/**
	 * Sets the items of the packet
	 * 
	 * @param items
	 *            the items of the packet
	 */
	public void setItems(ItemStack[] items) {
		packet.b = items;
	}

}
