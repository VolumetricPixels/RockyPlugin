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
package com.volumetricpixels.rockyplugin.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.inventory.RockyAchievement;
import com.volumetricpixels.rockyapi.player.PlayerManager;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class RockyPlayerManager implements PlayerManager {
	protected Map<Integer, RockyAchievement> achievementList = new HashMap<Integer, RockyAchievement>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RockyPlayer getPlayer(Player player) {
		if (player instanceof RockyPlayer) {
			return (RockyPlayer) player;
		}
		return (RockyPlayer) ((((CraftPlayer) player).getHandle())
				.getBukkitEntity());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RockyPlayer getPlayer(UUID id) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getUniqueId().equals(id)) {
				return getPlayer(player);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RockyPlayer getPlayer(int entityId) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getEntityId() == entityId) {
				return getPlayer(player);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RockyPlayer[] getOnlinePlayers() {
		Player[] online = Bukkit.getServer().getOnlinePlayers();
		RockyPlayer[] spoutPlayers = new RockyPlayer[online.length];
		for (int i = 0; i < online.length; i++) {
			spoutPlayers[i] = getPlayer(online[i]);
		}
		return spoutPlayers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayerVersion(RockyPlayer player, String version) {
		RockyManager.printConsole("Authenticated " + player.getName()
				+ " using Rocky " + version);
		int build = 1700;
		try {
			build = Integer.parseInt(version);
		} catch (Throwable ex) {
		}
		player.setBuildVersion(build);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerAchievement(RockyAchievement achievement) {
		achievementList.put(achievement.getId(), achievement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RockyAchievement getAchievement(int id) {
		return achievementList.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<RockyAchievement> getAchievements() {
		return achievementList.values();
	}

}
