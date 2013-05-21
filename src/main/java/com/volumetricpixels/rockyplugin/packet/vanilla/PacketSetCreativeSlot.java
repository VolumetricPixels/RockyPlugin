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
import net.minecraft.server.v1_5_R3.Packet107SetCreativeSlot;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet107SetCreativeSlot}
 */
public class PacketSetCreativeSlot extends
		RockyPacketVanilla<Packet107SetCreativeSlot> {

	/**
	 * Gets the inventory slot
	 */
	public int getSlot() {
		return packet.slot;
	}

	/**
	 * Gets the item we're updating
	 */
	public ItemStack getItem() {
		return packet.b;
	}

	/**
	 * Sets the item stack of the packet
	 * 
	 * @param item
	 *            the item stack
	 */
	public void setItem(ItemStack item) {
		packet.b = item;
	}
}
