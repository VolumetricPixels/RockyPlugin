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
package org.spout.legacyapi.gui;

import java.io.IOException;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;

/**
 * 
 */
public class GenericPopup extends GenericScreen implements PopupScreen {
	protected boolean transparent = false;

	/**
	 * 
	 */
	public GenericPopup() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return super.getVersion() + 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		this.setTransparent(input.readBoolean());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeBoolean(isTransparent());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isTransparent() {
		return transparent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PopupScreen setTransparent(boolean value) {
		this.transparent = value;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Widget setScreen(Plugin plugin, Screen screen) {
		if (this.screen != null && screen != null && screen != this.screen) {
			((InGameHUD) this.screen).closePopup();
		}
		return super.setScreen(plugin, screen);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.PopupScreen;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean close() {
		if (getScreen() instanceof InGameScreen) {
			return ((InGameScreen) getScreen()).closePopup();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ScreenType getScreenType() {
		return ScreenType.CUSTOM_SCREEN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleItemOnCursor(ItemStack itemOnCursor) {
	}
}
