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
package org.spout.legacyapi.event.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class PlayerLeavePlayerArea extends Event {
	private final static HandlerList handlers = new HandlerList();
	private final SpoutPlayer player;
	private final SpoutPlayer trigger;

	/**
	 * 
	 * @param player
	 * @param trigger
	 */
	public PlayerLeavePlayerArea(SpoutPlayer player, SpoutPlayer trigger) {
		this.player = player;
		this.trigger = trigger;
	}

	/**
	 * Gets the player associated with this event.
	 * 
	 * @return
	 */
	public SpoutPlayer getPlayer() {
		return player;
	}

	/**
	 * 
	 * @return
	 */
	public SpoutPlayer getTriggerPlayer() {
		return trigger;
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
