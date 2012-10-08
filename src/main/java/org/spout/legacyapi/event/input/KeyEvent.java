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
package org.spout.legacyapi.event.input;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import org.spout.legacyapi.gui.ScreenType;
import org.spout.legacyapi.keyboard.Keyboard;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class KeyEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final SpoutPlayer player;
	private final Keyboard key;
	private final ScreenType screenType;

	/**
	 * 
	 * @param keyPress
	 * @param player
	 * @param screenType
	 * @param isPressed
	 */
	public KeyEvent(int keyPress, SpoutPlayer player, ScreenType screenType,
			boolean isPressed) {
		this.player = player;
		this.key = Keyboard.getKey(keyPress);
		this.screenType = screenType;
	}

	/**
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
	public Keyboard getKey() {
		return key;
	}

	/**
	 * 
	 * @return
	 */
	public ScreenType getScreenType() {
		return screenType;
	}

	/**
	 * {@inheritDoc}
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