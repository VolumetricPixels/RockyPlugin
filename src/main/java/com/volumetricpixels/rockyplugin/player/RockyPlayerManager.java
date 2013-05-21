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
package com.volumetricpixels.rockyplugin.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_5_R3.Entity;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.inventory.RockyAchievement;
import com.volumetricpixels.rockyapi.player.PlayerManager;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class RockyPlayerManager implements PlayerManager {
	private Map<Integer, RockyAchievement> achievementList = new HashMap<Integer, RockyAchievement>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RockyPlayer getPlayer(Player player) {
		if (player instanceof RockyPlayerHandler) {
			return (RockyPlayerHandler) player;
		} else if (((((CraftPlayer) player).getHandle()).getBukkitEntity()) instanceof RockyPlayerHandler) {
			return (RockyPlayerHandler) ((((CraftPlayer) player).getHandle())
					.getBukkitEntity());
		}
		RockyPlayerHandler.updateBukkitEntry(player);
		return (RockyPlayerHandler) ((((CraftPlayer) player).getHandle())
				.getBukkitEntity());
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
		RockyPlayer[] rockyPlayers = new RockyPlayer[online.length];
		for (int i = 0; i < online.length; i++) {
			rockyPlayers[i] = getPlayer(online[i]);
		}
		return rockyPlayers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayerVersion(RockyPlayer player, String version) {
		RockyManager.printConsole("Authenticated " + player.getName()
				+ " using Rocky " + version);
		int build = 2100;
		if (Character.isDigit(version.charAt(0))) {
			build = Integer.valueOf(version);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Entity getEntity(World world, int entityId) {
		return ((CraftWorld) world).getHandle().getEntity(entityId);
	}

}
