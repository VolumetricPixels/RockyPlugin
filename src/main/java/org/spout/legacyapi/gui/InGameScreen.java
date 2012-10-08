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

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.event.screen.ScreenCloseEvent;
import org.spout.legacyapi.event.screen.ScreenOpenEvent;
import org.spout.legacyapi.packet.protocol.PacketScreenAction;
import org.spout.legacyapi.packet.protocol.PacketWidget;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class InGameScreen extends GenericScreen implements InGameHUD {
	protected HealthBar health;
	protected BubbleBar bubble;
	protected ChatBar chat;
	protected ChatTextBox chatText;
	protected ArmorBar armor;
	protected HungerBar hunger;
	protected ExpBar exp;
	protected PopupScreen activePopup = null;

	/**
	 * 
	 * @param playerId
	 */
	public InGameScreen(int playerId) {
		super(playerId);
		this.health = new HealthBar();
		this.bubble = new BubbleBar();
		this.chat = new ChatBar();
		this.chatText = new ChatTextBox();
		this.armor = new ArmorBar();
		this.hunger = new HungerBar();
		this.exp = new ExpBar();

		Plugin plugin = Bukkit.getServer().getPluginManager()
				.getPlugin("Spout");

		attachWidget(plugin, health).attachWidget(plugin, bubble)
				.attachWidget(plugin, chat).attachWidget(plugin, chatText)
				.attachWidget(plugin, armor).attachWidget(plugin, hunger)
				.attachWidget(plugin, exp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTick() {
		SpoutPlayer player = (SpoutPlayer) SpoutManager
				.getPlayerFromId(playerId);
		if (player != null && player.isSpoutEnabled()) {
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
	public boolean updateWidget(Widget widget) {
		if (widget instanceof HealthBar) {
			health = (HealthBar) widget;
		} else if (widget instanceof BubbleBar) {
			bubble = (BubbleBar) widget;
		} else if (widget instanceof ChatTextBox) {
			chatText = (ChatTextBox) widget;
		} else if (widget instanceof ChatBar) {
			chat = (ChatBar) widget;
		} else if (widget instanceof ArmorBar) {
			armor = (ArmorBar) widget;
		} else if (widget instanceof HungerBar) {
			hunger = (HungerBar) widget;
		} else if (widget instanceof ExpBar) {
			exp = (ExpBar) widget;
		}
		return super.updateWidget(widget);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Screen removeWidget(Widget widget) {
		if (widget instanceof HealthBar) {
			throw new UnsupportedOperationException(
					"Cannot remove the health bar. Use setVisible(false) to hide it instead");
		}
		if (widget instanceof BubbleBar) {
			throw new UnsupportedOperationException(
					"Cannot remove the bubble bar. Use setVisible(false) to hide it instead");
		}
		if (widget instanceof ChatTextBox) {
			throw new UnsupportedOperationException(
					"Cannot remove the chat text box. Use setVisible(false) to hide it instead");
		}
		if (widget instanceof ChatBar) {
			throw new UnsupportedOperationException(
					"Cannot remove the chat bar. Use setVisible(false) to hide it instead");
		}
		if (widget instanceof ArmorBar) {
			throw new UnsupportedOperationException(
					"Cannot remove the armor bar. Use setVisible(false) to hide it instead");
		}
		if (widget instanceof HungerBar) {
			throw new UnsupportedOperationException(
					"Cannot remove the hunger bar. Use setVisible(false) to hide it instead");
		}
		if (widget instanceof ExpBar) {
			throw new UnsupportedOperationException(
					"Cannot remove the exp bar. Use setVisible(false) to hide it instead");
		}
		return super.removeWidget(widget);
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
		SpoutPlayer player = SpoutManager.getPlayerFromId(playerId);
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
	public HealthBar getHealthBar() {
		return health;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BubbleBar getBubbleBar() {
		return bubble;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChatBar getChatBar() {
		return chat;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChatTextBox getChatTextBox() {
		return chatText;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArmorBar getArmorBar() {
		return armor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HungerBar getHungerBar() {
		return hunger;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExpBar getExpBar() {
		return exp;
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
					SpoutManager.getPlayerFromId(playerId), screen,
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
		if (widget instanceof Screen) {
			return false;
		}
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
	 * 
	 * @param widget
	 * @return
	 */
	public static boolean isCustomWidget(Widget widget) {
		return widget instanceof HealthBar || widget instanceof BubbleBar
				|| widget instanceof ChatTextBox || widget instanceof ChatBar
				|| widget instanceof ArmorBar;
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
		health.setVisible(toggle);
		bubble.setVisible(toggle);
		armor.setVisible(toggle);
		hunger.setVisible(toggle);
		exp.setVisible(toggle);
	}
}
