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
package org.spout.legacyapi.player;

import java.util.UUID;

import org.bukkit.entity.Player;

/**
 * 
 */
public interface PlayerManager {

	/**
	 * 
	 * @param player
	 * @return
	 */
	public SpoutPlayer getPlayer(Player player);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpoutPlayer getPlayer(UUID id);

	/**
	 * 
	 * @param entityId
	 * @return
	 */
	public SpoutPlayer getPlayer(int entityId);

	/**
	 * 
	 * @return
	 */
	public SpoutPlayer[] getOnlinePlayers();

	/**
	 * 
	 * @param player
	 * @param version
	 */
	public void setPlayerVersion(SpoutPlayer player, String version);
}
