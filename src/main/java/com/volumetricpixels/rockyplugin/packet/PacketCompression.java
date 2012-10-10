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
package com.volumetricpixels.rockyplugin.packet;

import java.util.concurrent.LinkedBlockingDeque;

import com.volumetricpixels.rockyapi.packet.CompressiblePacket;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class PacketCompression extends Thread {
	private static final int QUEUE_CAPACITY = 1024 * 10;
	private static PacketCompression instance = null;
	private final LinkedBlockingDeque<QueuedPacket> queue = new LinkedBlockingDeque<QueuedPacket>(
			QUEUE_CAPACITY);

	/**
	 * 
	 */
	private PacketCompression() {
		super("Spout Packet Compression Thread");
	}

	/**
	 * 
	 */
	public static void startThread() {
		instance = new PacketCompression();
		instance.start();
	}

	/**
	 * 
	 */
	public static void endThread() {
		instance.interrupt();
		try {
			instance.join();
		} catch (InterruptedException ie) {
		}
		instance = null;
	}

	/**
	 * 
	 * @return
	 */
	public static PacketCompression getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param packet
	 * @param player
	 */
	public static void add(CompressiblePacket packet, RockyPlayer player) {
		if (instance != null)
			instance.queue.add(new QueuedPacket(player, packet));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				QueuedPacket packet = queue.take();
				packet.packet.compress();
				packet.player.sendPacket(packet.packet);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	/**
	 * 
	 */
	private static class QueuedPacket {
		final CompressiblePacket packet;
		final RockyPlayer player;

		/**
		 * 
		 * @param player
		 * @param packet
		 */
		QueuedPacket(RockyPlayer player, CompressiblePacket packet) {
			this.player = player;
			this.packet = packet;
		}
	}
}
