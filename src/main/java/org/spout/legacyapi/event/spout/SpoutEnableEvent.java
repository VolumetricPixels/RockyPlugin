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
package org.spout.legacyapi.event.spout;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class SpoutEnableEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final SpoutPlayer player;

	/**
	 * 
	 * @param player
	 */
	public SpoutEnableEvent(SpoutPlayer player) {
		this.player = player;
	}

	/**
	 * Returns the player who just had their Spout SinglePlayer Mod enabled
	 * 
	 * @return player
	 */
	public SpoutPlayer getPlayer() {
		return player;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * 
	 * @return
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}
}