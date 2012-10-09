/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2012-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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
package org.spout.legacy.packet;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.Packet;

import org.spout.legacy.packet.vanilla.SpoutPacketVanilla;
import org.spout.legacyapi.packet.PacketListener;
import org.spout.legacyapi.packet.PacketManager;
import org.spout.legacyapi.packet.PacketVanilla;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class SpoutPacketManager implements PacketManager {

	protected Map<Integer, Class<? extends PacketVanilla>> corePacket = new HashMap<Integer, Class<? extends PacketVanilla>>();
	protected Map<Integer, Class<? extends Packet>> vanillaPacket = new HashMap<Integer, Class<? extends Packet>>();
	protected Map<Integer, List<PacketListener>> listenerList = new HashMap<Integer, List<PacketListener>>();

	/**
	 * 
	 */
	public SpoutPacketManager() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addVanillaPacket(int id, Class<? extends Packet> vanilla,
			Class<? extends PacketVanilla> extended) {
		corePacket.put(id, extended);
		vanillaPacket.put(id, vanilla);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PacketVanilla getInstance(int packetId) {
		if (vanillaPacket.containsKey(packetId)) {
			try {
				Class<? extends Packet> clazz = vanillaPacket.get(packetId);
				Packet packet = clazz.newInstance();

				Class<? extends PacketVanilla> clazz2 = corePacket
						.get(packetId);
				Constructor<? extends PacketVanilla> constructor = clazz2
						.getConstructor();
				SpoutPacketVanilla<Packet> vanilla = (SpoutPacketVanilla<Packet>) constructor
						.newInstance();
				vanilla.setPacket(packet);
			} catch (Throwable ex) {
				throw new RuntimeException(ex);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListenerUncompressedChunk(PacketListener listener) {
		addListener(256, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListener(int packetId, PacketListener listener) {
		List<PacketListener> listListener = listenerList.get(packetId);
		if (listener == null) {
			listListener = new ArrayList<PacketListener>();
		}
		listListener.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeListenerUncompressedChunk(PacketListener listener) {
		return removeListener(256, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeListener(int packetId, PacketListener listener) {
		List<PacketListener> listListener = listenerList.get(packetId);
		if (listListener == null) {
			return false;
		}
		return listenerList.remove(listener) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearAllListeners() {
		for (List<PacketListener> listener : listenerList.values())
			if (listener != null)
				listener.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAllowedToSend(SpoutPlayer player, int packet) {
		List<PacketListener> listenerReference = listenerList.get(packet);
		if (listenerReference != null) {
			PacketVanilla wrapper = getInstance(packet);
			for (PacketListener listener : listenerReference) {
				if (!listener.checkPacket(player, wrapper)) {
					return false;
				}
			}
		}
		return true;
	}

}
