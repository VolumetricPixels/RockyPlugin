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
package com.volumetricpixels.rockyapi.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class RockyEnableEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final RockyPlayer player;

	/**
	 * 
	 * @param player
	 */
	public RockyEnableEvent(RockyPlayer player) {
		this.player = player;
	}

	/**
	 * Returns the player who just had rocky client.
	 * 
	 * @return player
	 */
	public RockyPlayer getPlayer() {
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