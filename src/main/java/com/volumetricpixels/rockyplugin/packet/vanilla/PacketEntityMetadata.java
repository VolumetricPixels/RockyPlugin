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

import java.util.List;

import org.fest.reflect.core.Reflection;

import net.minecraft.server.v1_5_R3.Packet40EntityMetadata;
import net.minecraft.server.v1_5_R3.WatchableObject;

/**
 * Encapsulate a {@see RockyPacketVanilla} that implements {@see
 * Packet40EntityMetadata}
 */
public class PacketEntityMetadata extends
		RockyPacketVanilla<Packet40EntityMetadata> {

	/**
	 * Gets the entity id
	 */
	public int getEntityId() {
		return packet.a;
	}

	/**
	 * Gets the data of the entity
	 */
	@SuppressWarnings("unchecked")
	public List<WatchableObject> getMetadata() {
		return (List<WatchableObject>) Reflection.field("b").ofType(List.class)
				.in(packet).get();
	}

	/**
	 * Sets the metadata of the entity
	 * 
	 * @param metadata
	 *            the metadata of the entity
	 */
	public void setMetadata(List<WatchableObject> metadata) {
		Reflection.field("b").ofType(List.class).in(packet).set(metadata);
	}
}
