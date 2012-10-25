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
import java.util.Collection;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.inventory.RockyAchievement;
import com.volumetricpixels.rockyapi.packet.Packet;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class PacketAchievementList implements Packet {

	private RockyPlayer player;

	/**
	 * 
	 * @param player
	 */
	public PacketAchievementList(RockyPlayer player) {
		this.player = player;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		Collection<RockyAchievement> list = RockyManager.getPlayerManager()
				.getAchievements();

		output.writeShort(list.size());
		for (RockyAchievement achievement : list) {
			output.writeShort(achievement.getId());
			output.writeShort(achievement.getItemId());
			output.writeUTF(achievement.getName());
			output.writeUTF(achievement.getDescription());
			for (RockyAchievement dependency : achievement.getDependency())
				output.writeShort(dependency.getId());
		}

		Integer[] playerAchievementList = player.getAchievement();
		output.writeShort(playerAchievementList.length);
		for (Integer id : playerAchievementList)
			output.writeShort(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(RockyPlayer player) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void failure(RockyPlayer player) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PacketType getType() {
		return PacketType.PacketAchievementList;
	}

}
