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
package org.spout.legacyapi.packet.protocol;

import java.io.IOException;

import org.bukkit.Bukkit;

import org.spout.legacyapi.event.screen.ScreenCloseEvent;
import org.spout.legacyapi.event.screen.ScreenEvent;
import org.spout.legacyapi.event.screen.ScreenOpenEvent;
import org.spout.legacyapi.gui.PopupScreen;
import org.spout.legacyapi.gui.ScreenAction;
import org.spout.legacyapi.gui.ScreenType;
import org.spout.legacyapi.packet.Packet;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;
import org.spout.legacyapi.packet.PacketType;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class PacketScreenAction implements Packet {
	protected byte action = -1;
	protected byte screen = -1;

	/**
	 * 
	 */
	public PacketScreenAction() {
	}

	/**
	 * 
	 * @param action
	 * @param screen
	 */
	public PacketScreenAction(ScreenAction action, ScreenType screen) {
		this.action = (byte) action.getId();
		this.screen = (byte) screen.getCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		action = input.readByte();
		screen = input.readByte();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		output.writeByte(action);
		output.writeByte(screen);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(SpoutPlayer player) {
		ScreenEvent event;
		switch (ScreenAction.getScreenActionFromId(action)) {
		case Force_Close:
		case Close:
			event = new ScreenCloseEvent(player, player.getMainScreen()
					.getActivePopup(), ScreenType.getType(this.screen));
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (event.isCancelled()) {
				handleScreenCloseCancelled(player, (ScreenCloseEvent) event,
						true);
			} else if (ScreenType.getType(this.screen) == ScreenType.CUSTOM_SCREEN) {
				handleScreenClose(player, (ScreenCloseEvent) event, true);
			}
			if (!event.isCancelled()) {
				player.openScreen(ScreenType.GAME_SCREEN, false);
			}
			break;
		case Open:
			event = new ScreenOpenEvent(player, player.getMainScreen()
					.getActivePopup(), ScreenType.getType(this.screen));
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (event.isCancelled()) {
				PopupScreen screen = player.getMainScreen().getActivePopup();
				if (screen != null) {
					screen.setDirty(true);
					player.sendPacket(new PacketWidget(screen, screen.getId()));
				}
			} else {
				player.openScreen(ScreenType.getType(this.screen), false);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void failure(SpoutPlayer player) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PacketType getType() {
		return PacketType.PacketScreenAction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return 2;
	}

	/**
	 * 
	 * @param player
	 * @param e
	 * @param update
	 */
	private void handleScreenCloseCancelled(SpoutPlayer player,
			ScreenCloseEvent e, boolean update) {
		this.action = (byte) ScreenAction.Close.getId();
		PopupScreen screen = player.getMainScreen().getActivePopup();
		if (screen != null) {
			if (update)
				screen.onScreenClose(e);
			if (!e.isCancelled()
					&& ScreenType.getType(this.screen) == ScreenType.CUSTOM_SCREEN) {
				handleScreenClose(player, e, false);
				return;
			}
			screen.setDirty(true);
			player.sendPacket(new PacketWidget(screen, screen.getId()));
		}
	}

	/**
	 * 
	 * @param player
	 * @param e
	 * @param update
	 */
	private void handleScreenClose(SpoutPlayer player, ScreenCloseEvent e,
			boolean update) {
		PopupScreen p = player.getMainScreen().getActivePopup();
		if (update && p != null)
			p.onScreenClose(e);
		if (e.isCancelled()) {
			handleScreenCloseCancelled(player, e, false);
			return;
		}
		player.getMainScreen().closePopup();
		if (player.getItemOnCursor() != null && p != null) {
			p.handleItemOnCursor(player.getItemOnCursor());
			player.setItemOnCursor(null);
		}
	}

}
