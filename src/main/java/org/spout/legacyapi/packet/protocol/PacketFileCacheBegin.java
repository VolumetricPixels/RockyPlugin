/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2011-2012, VolumetricPixels <http://www.volumetricpixels.com/>
 * SpoutLegacy is licensed under the GNU Lesser General Public License.
 *
 * SpoutLegacy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutLegacy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spout.legacyapi.packet.protocol;

import java.io.IOException;

import org.spout.legacyapi.packet.Packet;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;
import org.spout.legacyapi.packet.PacketType;
import org.spout.legacyapi.player.SpoutPlayer;
import org.spout.legacyapi.resource.Resource;

/**
 * 
 */
public class PacketFileCacheBegin implements Packet {

	protected Resource<?>[] resourceList;
	protected String[] difResources;
	
	/**
	 * 
	 * @param isPreLoading
	 * @param names
	 * @param crcExpected
	 */
	public PacketFileCacheBegin(Resource<?>[] resourceList) {
		this.resourceList = resourceList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		this.difResources = input.readUTFArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		output.writeShort(resourceList.length);
		for (Resource<?> resource : resourceList) {
			output.writeUTF(resource.getName());
			output.writeLong(resource.getRevision());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(SpoutPlayer player) {
		//TODO: Send every file cache and finally file cache finish
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void failure(SpoutPlayer player) {
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
		return PacketType.PacketFileCacheBegin;
	}
}
