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

import net.minecraft.server.v1_5_R3.Packet23VehicleSpawn;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet23VehicleSpawn}
 */
public class PacketSpawnObject extends RockyPacketVanilla<Packet23VehicleSpawn> {

	/**
	 * Gets the entity id
	 */
	public int getEntityId() {
		return packet.a;
	}

	/**
	 * Gets the type of the entity
	 */
	public int getType() {
		return packet.j;
	}

	/**
	 * Gets the type flag
	 */
	public int getFlag() {
		return packet.k;
	}

	/**
	 * Gets the position of the entity
	 */
	public Vector3f getPosition() {
		return new Vector3f(packet.b, packet.c, packet.d);
	}

	/**
	 * Gets the yaw
	 */
	public int getYaw() {
		return packet.h;
	}

	/**
	 * Gets the pitch
	 */
	public int getPitch() {
		return packet.i;
	}

	/**
	 * Gets the speed of the object
	 */
	public Vector3f getSpeed() {
		if (getFlag() == 0) {
			return new Vector3f(0.0f, 0.0f, 0.0f);
		}
		return new Vector3f(packet.e, packet.f, packet.g);
	}

}
