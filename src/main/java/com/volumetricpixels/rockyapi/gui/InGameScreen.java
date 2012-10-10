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
package com.volumetricpixels.rockyapi.gui;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.event.screen.ScreenCloseEvent;
import com.volumetricpixels.rockyapi.event.screen.ScreenOpenEvent;
import com.volumetricpixels.rockyapi.packet.protocol.PacketScreenAction;
import com.volumetricpixels.rockyapi.packet.protocol.PacketWidget;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class InGameScreen extends GenericScreen implements InGameHUD {
	protected PopupScreen activePopup = null;

	/**
	 * 
	 * @param playerId
	 */
	public InGameScreen(int playerId) {
		super(playerId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTick() {
		RockyPlayer player = (RockyPlayer) RockyManager
				.getPlayerFromId(playerId);
		if (player != null && player.isModded()) {
			if (getActivePopup() != null) {
				if (getActivePopup().isDirty()) {
					if (!getActivePopup().getType().isServerOnly()) {
						player.sendPacket(new PacketWidget(getActivePopup(),
								getId()));
					}
					getActivePopup().setDirty(false);
				}
				getActivePopup().onTick();
			}
		}
		super.onTick();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InGameScreen attachWidget(Plugin plugin, Widget widget) {
		if (canAttachWidget(widget)) {
			super.attachWidget(plugin, widget);
			return this;
		}
		throw new UnsupportedOperationException("Unsupported widget type");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UUID getId() {
		return new UUID(0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return 240;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 427;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean closePopup() {
		if (getActivePopup() == null) {
			return false;
		}
		RockyPlayer player = RockyManager.getPlayerFromId(playerId);
		ScreenCloseEvent event = new ScreenCloseEvent(player, getActivePopup(),
				ScreenType.CUSTOM_SCREEN);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (event.isCancelled()) {
			return false;
		}
		player.sendPacket(new PacketScreenAction(ScreenAction.Close,
				ScreenType.CUSTOM_SCREEN));
		activePopup = null;
		player.openScreen(ScreenType.GAME_SCREEN, false);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PopupScreen getActivePopup() {
		return activePopup;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean attachPopupScreen(PopupScreen screen) {
		if (getActivePopup() == null) {
			ScreenOpenEvent event = new ScreenOpenEvent(
					RockyManager.getPlayerFromId(playerId), screen,
					ScreenType.CUSTOM_SCREEN);
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (event.isCancelled()) {
				return false;
			}
			activePopup = screen;
			screen.setDirty(true);
			screen.setScreen(screen.getPlugin(), this);
			((GenericPopup) screen).playerId = this.playerId;
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canAttachWidget(Widget widget) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.InGameScreen;
	}

	/**
	 * 
	 */
	public void clearPopup() {
		activePopup = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ScreenType getScreenType() {
		return ScreenType.GAME_SCREEN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toggleSurvivalHUD(boolean toggle) {
	}
}
