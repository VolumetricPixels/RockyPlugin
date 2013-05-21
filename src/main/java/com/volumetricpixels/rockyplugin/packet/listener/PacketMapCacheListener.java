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
package com.volumetricpixels.rockyplugin.packet.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.volumetricpixels.rockyapi.packet.PacketListener;
import com.volumetricpixels.rockyapi.packet.PacketVanilla;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyplugin.Rocky;
import com.volumetricpixels.rockyplugin.chunk.ChunkCacheWorker;
import com.volumetricpixels.rockyplugin.packet.RockyPacketHandler;

/**
 * Encapsulate {@see PacketListener} for the chunk cache system.
 */
public class PacketMapCacheListener implements PacketListener {
	private ExecutorService threadService;

	/**
	 * Default constructor of the listener
	 */
	public PacketMapCacheListener() {
		threadService = Executors.newCachedThreadPool();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onPrePacket(RockyPlayer player, PacketVanilla packet) {
		if (!Rocky.getInstance().getConfiguration().isCacheEnabled()
				|| !player.isModded()) {
			return true;
		}
		threadService.submit(new ChunkCacheWorker((RockyPacketHandler) player
				.getHandle().playerConnection, packet));
		return false;
	}

}
