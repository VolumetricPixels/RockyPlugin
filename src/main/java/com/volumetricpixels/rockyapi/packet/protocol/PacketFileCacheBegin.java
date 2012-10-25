/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2011-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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
package com.volumetricpixels.rockyapi.packet.protocol;

import java.io.IOException;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.packet.Packet;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyapi.resource.Resource;

/**
 * 
 */
public class PacketFileCacheBegin implements Packet {

	private Resource[] resourceList;
	private String[] difResources;

	/**
	 * 
	 * @param isPreLoading
	 * @param names
	 * @param crcExpected
	 */
	public PacketFileCacheBegin(Resource... resourceList) {
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
		for (Resource resource : resourceList) {
			output.writeUTF(resource.getName());
			output.writeLong(resource.getRevision());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(RockyPlayer player) {
		for (String name : difResources) {
			// Prevent a NPE hack by a hacked client.
			if (RockyManager.getResourceManager().hasResource(name)) {
				player.sendPacket(new PacketFileCache(RockyManager
						.getResourceManager().getResource(name)));
			}
		}
		player.sendPacket(new PacketFileCacheFinish());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void failure(RockyPlayer player) {
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public PacketType getType() {
		return PacketType.PacketFileCacheBegin;
	}
}
