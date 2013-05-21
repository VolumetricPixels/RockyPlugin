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

import net.minecraft.server.v1_5_R3.Packet29DestroyEntity;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet29DestroyEntity}
 */
public class PacketDestroyEntity extends
		RockyPacketVanilla<Packet29DestroyEntity> {

	/**
	 * Default constructor
	 */
	public PacketDestroyEntity() {	
	}
	
	/**
	 * Constructor for sending the packet
	 * 
	 * @param ids the array of id
	 */
	public PacketDestroyEntity(int... ids) {
		setEntitiesIds(ids);
	}
	
	/**
	 * Gets all the entities
	 * 
	 * @return all the entities
	 */
	public int[] getEntitiesId() {
		return packet.a;
	}

	/**
	 * Sets the entities id
	 * 
	 * @param ids
	 *            the array of id
	 */
	public void setEntitiesIds(int... ids) {
		packet.a = ids;
	}

}
