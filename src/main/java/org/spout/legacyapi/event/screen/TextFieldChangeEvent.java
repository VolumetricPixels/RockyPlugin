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
package org.spout.legacyapi.event.screen;

import org.bukkit.event.HandlerList;

import org.spout.legacyapi.gui.Screen;
import org.spout.legacyapi.gui.ScreenType;
import org.spout.legacyapi.gui.TextField;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class TextFieldChangeEvent extends ScreenEvent {
	private static final HandlerList handlers = new HandlerList();
	private final TextField field;
	private final String oldVal;
	private String newVal;

	/**
	 * 
	 * @param player
	 * @param screen
	 * @param field
	 * @param newVal
	 */
	public TextFieldChangeEvent(SpoutPlayer player, Screen screen,
			TextField field, String newVal) {
		super("TextFieldChangeEvent", player, screen, ScreenType.CUSTOM_SCREEN);
		this.field = field;
		this.oldVal = field.getText();
		this.newVal = newVal;
	}

	/**
	 * 
	 * @return
	 */
	public TextField getTextField() {
		return field;
	}

	/**
	 * 
	 * @return
	 */
	public String getOldText() {
		return oldVal;
	}

	/**
	 * 
	 * @return
	 */
	public String getNewText() {
		return newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setNewText(String newVal) {
		if (newVal == null) {
			newVal = "";
		}
		this.newVal = newVal;
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