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
package org.spout.legacyapi.event.screen;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import org.spout.legacyapi.gui.Screen;
import org.spout.legacyapi.gui.ScreenType;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public abstract class ScreenEvent extends Event implements Cancellable {
	protected final Screen screen;
	protected final SpoutPlayer player;
	protected final ScreenType type;
	protected boolean cancel = false;

	/**
	 * 
	 * @param name
	 * @param player
	 * @param screen
	 * @param type
	 */
	protected ScreenEvent(String name, SpoutPlayer player, Screen screen,
			ScreenType type) {
		this.screen = screen;
		this.player = player;
		this.type = type;
	}

	/**
	 * 
	 * @return
	 */
	public Screen getScreen() {
		return screen;
	}

	/**
	 * 
	 * @return
	 */
	public ScreenType getScreenType() {
		return type;
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
	 */
	public boolean isCancelled() {
		return cancel;
	}

	/**
	 * 
	 */
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}