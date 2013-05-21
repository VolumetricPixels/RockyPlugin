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

import com.volumetricpixels.rockyplugin.reflection.Reflector;
import com.volumetricpixels.rockyplugin.reflection.ReflectorID;

import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.Packet5EntityEquipment;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet5EntityEquipment}
 */
public class PacketEntityEquipment extends
		RockyPacketVanilla<Packet5EntityEquipment> {

	/**
	 * Gets the named entity id
	 */
	public int getEntityId() {
		return packet.a;
	}

	/**
	 * Gets the equipment slot
	 */
	public int getSlot() {
		return packet.b;
	}

	/**
	 * Gets the item that the entity equip
	 */
	public ItemStack getItem() {
		return Reflection
				.field(Reflector
						.getField(ReflectorID.PACKET_ENTITY_EQUIPMENT_ITEM))
				.ofType(ItemStack.class).in(packet).get();
	}

	/**
	 * Sets the item stack of the packet
	 * 
	 * @param item
	 *            the item stack
	 */
	public void setItem(ItemStack item) {
		Reflection
				.field(Reflector
						.getField(ReflectorID.PACKET_ENTITY_EQUIPMENT_ITEM))
				.ofType(ItemStack.class).in(packet).set(item);
	}

}
