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
package com.volumetricpixels.rockyplugin.chunk;

import java.io.IOException;

import org.bukkit.Bukkit;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.packet.PacketVanilla;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyplugin.packet.RockyPacketHandler;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketBulkChunkData;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketChunkData;

/**
 * Encapsulate a worker that does the chunk cache async, by pooling this
 * runnable into a pool thread
 */
public class ChunkCacheWorker implements Runnable {

	private PacketVanilla packet;
	private RockyPacketHandler connection;

	/**
	 * Constructor of a cache worker
	 * 
	 * @param connection
	 *            the connection of the player
	 * @param packet
	 *            the chunk packet data
	 */
	public ChunkCacheWorker(final RockyPacketHandler connection,
			final PacketVanilla packet) {
		this.packet = packet;
		this.connection = connection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		try {
			RockyPlayer player = RockyManager.getPlayer(Bukkit
					.getPlayer(connection.player.getName()));

			if (packet instanceof PacketBulkChunkData) {
				ChunkCacheHandler.handlePacketBulk(player.getName(),
						(PacketBulkChunkData) packet);
			} else {
				ChunkCacheHandler.handlePacket(player.getName(),
						(PacketChunkData) packet);
			}
			connection.sendOfflinePacket(packet.getHandler());
		} catch (IOException ex) {
			RockyManager.printConsole(ex.getLocalizedMessage());
		}
	}

}
