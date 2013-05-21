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

import com.volumetricpixels.rockyapi.math.Vector3f;

import net.minecraft.server.v1_5_R3.Packet20NamedEntitySpawn;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet20NamedEntitySpawn}
 */
public class PacketNamedEntitySpawn extends
		RockyPacketVanilla<Packet20NamedEntitySpawn> {

	/**
	 * Constructor for sending the packet
	 * 
	 * @param id
	 *            the id of the entity
	 * @param name
	 *            the name of the entity
	 * @param position
	 *            the position of the entity
	 * @param yaw
	 *            the yaw of the entity
	 * @param pitch
	 *            the pitch of the entity
	 * @param currentItem
	 *            the current item equiped
	 */
	public PacketNamedEntitySpawn(int id, String name, Vector3f position,
			int yaw, int pitch, int currentItem) {
		packet.a = id;
		packet.b = name;
		packet.c = (int) position.x;
		packet.d = (int) position.y;
		packet.e = (int) position.z;
		packet.f = (byte) yaw;
		packet.g = (byte) pitch;
		packet.h = currentItem;
	}

	/**
	 * Default Constructor
	 */
	public PacketNamedEntitySpawn() {
	}

	/**
	 * Gets the entity id
	 */
	public int getPlayerId() {
		return packet.a;
	}

	/**
	 * Gets the entity name
	 */
	public String getPlayerName() {
		return packet.b;
	}

	/**
	 * Sets the player name
	 * 
	 * @param name
	 *            the new name
	 */
	public void setPlayerName(String name) {
		packet.b = name;
	}

	/**
	 * Gets the position of the entity
	 */
	public Vector3f getPosition() {
		return new Vector3f(packet.c, packet.d, packet.e);
	}

	/**
	 * Gets the yaw
	 */
	public int getYaw() {
		return packet.f;
	}

	/**
	 * Gets the pitch
	 */
	public int getPitch() {
		return packet.g;
	}

	/**
	 * Gets the id of the current item equipped
	 */
	public int getCurrentItem() {
		return packet.h;
	}

	/**
	 * Sets the id of the current item
	 * 
	 * @param id
	 *            the new id of the item
	 */
	public void setCurrentItem(int id) {
		packet.h = id;
	}

}
