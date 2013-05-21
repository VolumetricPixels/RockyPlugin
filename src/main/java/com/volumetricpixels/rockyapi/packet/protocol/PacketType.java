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
package com.volumetricpixels.rockyapi.packet.protocol;

import java.util.HashMap;
import java.util.Map;

import com.volumetricpixels.rockyapi.packet.Packet;

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
	PacketSetVelocity(20, PacketSetVelocity.class);

	private final int id;
	private final Class<? extends Packet> clazz;
	private static final Map<Integer, PacketType> PACKET_LIST = new HashMap<Integer, PacketType>();

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
		PACKET_LIST.put(packet.getId(), packet);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static PacketType getPacketFromId(int id) {
		return PACKET_LIST.get(id);
	}
}
