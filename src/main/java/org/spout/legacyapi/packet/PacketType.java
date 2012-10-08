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
package org.spout.legacyapi.packet;

import java.util.HashMap;
import java.util.Map;

import org.spout.legacyapi.packet.protocol.*;

/**
 * 
 */
public enum PacketType {
	/**
	 * {@see PacketVersion}
	 */
	PacketVersion(1, PacketVersion.class),
	/**
	 * {@see PacketFileCacheBegin}
	 */
	PacketFileCacheBegin(2, PacketFileCacheBegin.class),
	/**
	 * {@see PacketFileCache}
	 */
	PacketFileCache(3, PacketFileCache.class),
	/**
	 * {@see PacketFileCacheFinish}
	 */
	PacketFileCacheFinish(4, PacketFileCacheFinish.class),
	/**
	 * {@see PacketAllowAddon}
	 */
	PacketAllowAddon(5, PacketAllowAddon.class),
	/**
	 * {@see PacketMovementAddon}
	 */
	PacketMovementAddon(6, PacketMovementAddon.class),
	/**
	 * {@see PacketSkyAddon}
	 */
	PacketSkyAddon(7, PacketSkyAddon.class),
	/**
	 * {@see PacketAlert}
	 */
	PacketAlert(8, PacketAlert.class),
	/**
	 * {@see PacketKeyBinding}
	 */
	PacketKeyBinding(9, PacketKeyBinding.class),
	/**
	 * {@see PacketKeyEvent}
	 */
	PacketKeyEvent(10, PacketKeyEvent.class),
	/**
	 * {@see PacketWaypoint}
	 */
	PacketWaypoint(11, PacketWaypoint.class),
	
	/**
	 * {@see PacketWidget}
	 */
	PacketWidget(100, PacketWidget.class),
	/**
	 * {@see PacketWidgetRemove}
	 */
	PacketWidgetRemove(101, PacketWidgetRemove.class),
	/**
	 * {@see PacketScreenAction}
	 */
	PacketScreenAction(102, PacketScreenAction.class),
	/**
	 * {@see PacketWidgetComboBox}
	 */
	PacketWidgetComboBox(103, PacketWidgetComboBox.class);

	protected final int id;
	protected final Class<? extends Packet> clazz;
	protected final static Map<Integer, PacketType> packetList = new HashMap<Integer, PacketType>();

	/**
	 * 
	 * @param id
	 * @param clazz
	 */
	private PacketType(int id, Class<? extends Packet> clazz) {
		this.id = id;
		this.clazz = clazz;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 */
	public Class<? extends Packet> getClazz() {
		return clazz;
	}

	/**
	 * 
	 * @param packet
	 */
	public static void registerPacket(PacketType packet) {
		packetList.put(packet.getId(), packet);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static PacketType getPacketFromId(int id) {
		return packetList.get(id);
	}
}
