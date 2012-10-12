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
package com.volumetricpixels.rockyapi.player;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.volumetricpixels.rockyapi.inventory.RockyAchievement;

/**
 * 
 */
public interface PlayerManager {

	/**
	 * 
	 * @param achievement
	 */
	public void registerAchievement(RockyAchievement achievement);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public RockyAchievement getAchievement(int id);
	
	/**
	 * 
	 * @return
	 */
	public Collection<RockyAchievement> getAchievements();
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public RockyPlayer getPlayer(Player player);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public RockyPlayer getPlayer(UUID id);

	/**
	 * 
	 * @param entityId
	 * @return
	 */
	public RockyPlayer getPlayer(int entityId);

	/**
	 * 
	 * @return
	 */
	public RockyPlayer[] getOnlinePlayers();

	/**
	 * 
	 * @param player
	 * @param version
	 */
	public void setPlayerVersion(RockyPlayer player, String version);
}
