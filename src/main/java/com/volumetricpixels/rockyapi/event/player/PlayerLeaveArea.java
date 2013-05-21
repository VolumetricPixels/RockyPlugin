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
package com.volumetricpixels.rockyapi.event.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * Event that is called when a player stop seeing another player
 */
public class PlayerLeaveArea extends Event {
	private static final HandlerList HANDLER = new HandlerList();
	private final RockyPlayer player;
	private final RockyPlayer trigger;

	/**
	 * Default constructor
	 * 
	 * @param player
	 *            the player viewer
	 * @param trigger
	 *            the player that has left another's player view area
	 */
	public PlayerLeaveArea(RockyPlayer player, RockyPlayer trigger) {
		this.player = player;
		this.trigger = trigger;
	}

	/**
	 * Gets the player associated with this event.
	 * 
	 * @return the player associated with this event
	 */
	public RockyPlayer getPlayer() {
		return player;
	}

	/**
	 * Gets the player that has left another's player view area
	 * 
	 * @return the player that has left another's player view area
	 */
	public RockyPlayer getTriggerPlayer() {
		return trigger;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public HandlerList getHandlers() {
		return HANDLER;
	}

	/**
	 * Gets all the event's handlers
	 * 
	 * @return all the event's handlers
	 */
	public static HandlerList getHandlerList() {
		return HANDLER;
	}
}
