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
package com.volumetricpixels.rockyapi.packet;

import java.util.HashMap;
import java.util.Map;

import com.volumetricpixels.rockyapi.packet.protocol.PacketAccessory;
import com.volumetricpixels.rockyapi.packet.protocol.PacketAchievement;
import com.volumetricpixels.rockyapi.packet.protocol.PacketAchievementList;
import com.volumetricpixels.rockyapi.packet.protocol.PacketAlert;
import com.volumetricpixels.rockyapi.packet.protocol.PacketAllowAddon;
import com.volumetricpixels.rockyapi.packet.protocol.PacketCustomItem;
import com.volumetricpixels.rockyapi.packet.protocol.PacketFileCache;
import com.volumetricpixels.rockyapi.packet.protocol.PacketFileCacheBegin;
import com.volumetricpixels.rockyapi.packet.protocol.PacketFileCacheFinish;
import com.volumetricpixels.rockyapi.packet.protocol.PacketKeyBinding;
import com.volumetricpixels.rockyapi.packet.protocol.PacketKeyEvent;
import com.volumetricpixels.rockyapi.packet.protocol.PacketMovementAddon;
import com.volumetricpixels.rockyapi.packet.protocol.PacketPlaySound;
import com.volumetricpixels.rockyapi.packet.protocol.PacketPlayerAppearance;
import com.volumetricpixels.rockyapi.packet.protocol.PacketScreenAction;
import com.volumetricpixels.rockyapi.packet.protocol.PacketSetVelocity;
import com.volumetricpixels.rockyapi.packet.protocol.PacketSkyAddon;
import com.volumetricpixels.rockyapi.packet.protocol.PacketStopMusic;
import com.volumetricpixels.rockyapi.packet.protocol.PacketVersion;
import com.volumetricpixels.rockyapi.packet.protocol.PacketWaypoint;
import com.volumetricpixels.rockyapi.packet.protocol.PacketWidget;
import com.volumetricpixels.rockyapi.packet.protocol.PacketWidgetComboBox;
import com.volumetricpixels.rockyapi.packet.protocol.PacketWidgetRemove;

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
	 * {@see PacketAccessory}
	 */
	PacketAccessory(13, PacketAccessory.class),
	/**
	 * {@see PacketPlayerAppearance}
	 */
	PacketPlayerAppearance(14, PacketPlayerAppearance.class),
	/**
	 * {@see PacketCustomItem}
	 */
	PacketCustomItem(15, PacketCustomItem.class),
	/**
	 * {@see PacketPlaySound}
	 */
	PacketPlaySound(16, PacketPlaySound.class),
	/**
	 * {@see PacketStopMusic}
	 */
	PacketStopMusic(17, PacketStopMusic.class),
	/**
	 * {@see PacketAchievementList}
	 */
	PacketAchievementList(18, PacketAchievementList.class),
	/**
	 * {@see PacketAchievement}
	 */
	PacketAchievement(19, PacketAchievement.class),
	/**
	 * {@see PacketSetVelocity}
	 */
	PacketSetVelocity(20, PacketSetVelocity.class),

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
