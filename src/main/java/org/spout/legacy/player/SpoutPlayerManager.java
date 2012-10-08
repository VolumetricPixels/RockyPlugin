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
/*
 * This file is part of SpoutPlugin.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutPlugin is licensed under the GNU Lesser General Public License.
 *
 * SpoutPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spout.legacy.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.event.spout.SpoutBuildSetEvent;
import org.spout.legacyapi.player.PlayerManager;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class SpoutPlayerManager implements PlayerManager {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SpoutPlayer getPlayer(Player player) {
		if (player instanceof SpoutPlayer) {
			return (SpoutPlayer) player;
		}
		return (SpoutPlayer) ((((CraftPlayer) player).getHandle())
				.getBukkitEntity());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SpoutPlayer getPlayer(UUID id) {
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
	public SpoutPlayer getPlayer(int entityId) {
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
	public SpoutPlayer[] getOnlinePlayers() {
		Player[] online = Bukkit.getServer().getOnlinePlayers();
		SpoutPlayer[] spoutPlayers = new SpoutPlayer[online.length];
		for (int i = 0; i < online.length; i++) {
			spoutPlayers[i] = getPlayer(online[i]);
		}
		return spoutPlayers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayerVersion(SpoutPlayer player, String version) {
		SpoutManager.printConsole("Authenticated " + player.getName()
				+ " using SpoutLegacyClient " + version);
		int build = 1700;
		try {
			build = Integer.parseInt(version);
		} catch (Throwable ex) {
		}
		player.setBuildVersion(build);
		Bukkit.getPluginManager().callEvent(
				new SpoutBuildSetEvent(player, build));
	}

	/**
	 * 
	 * @param player
	 */
	public static void sendCustomData(SpoutPlayer player) {
		
	}
}
