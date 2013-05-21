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
import net.minecraft.server.v1_5_R3.Packet103SetSlot;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet103SetSlot}
 */
public class PacketSetSlot extends RockyPacketVanilla<Packet103SetSlot> {

	/**
	 * Constructor for sending the packet
	 * 
	 * @param id
	 *            the windows id
	 * @param slot
	 *            the slot
	 * @param stack
	 *            the item stack
	 */
	public PacketSetSlot(int id, int slot, ItemStack stack) {
		packet = new Packet103SetSlot();
		packet.a = id;
		packet.b = slot;
		packet.c = stack;
	}

	/**
	 * Default constructor
	 */
	public PacketSetSlot() {	
	}
	
	/**
	 * Gets the window which is being updated. 0 for player inventory. Note that
	 * all known window types include the player inventory. This packet will
	 * only be sent for the currently opened window while the player is
	 * performing actions, even if it affects the player inventory. After the
	 * window is closed, a number of these packets are sent to update the
	 * player's inventory window (0).
	 */
	public int getWindowId() {
		return packet.a;
	}

	/**
	 * Gets the slot that should be updated
	 */
	public int getSlot() {
		return packet.b;
	}

	/**
	 * Gets the item we're updating
	 */
	public ItemStack getItem() {
		return packet.c;
	}

	/**
	 * Sets the item stack of the packet
	 * 
	 * @param item
	 *            the item stack
	 */
	public void setItem(ItemStack item) {
		packet.c = item;
	}
	
}
