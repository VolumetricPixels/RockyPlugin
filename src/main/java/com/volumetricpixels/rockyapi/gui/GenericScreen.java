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

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.event.screen.ScreenCloseEvent;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.packet.protocol.PacketWidget;
import com.volumetricpixels.rockyapi.packet.protocol.PacketWidgetRemove;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public abstract class GenericScreen extends GenericWidget implements Screen {
	protected Map<Widget, Plugin> widgets = new ConcurrentHashMap<Widget, Plugin>();
	protected int playerId;
	protected boolean bg = true;

	/**
	 * 
	 */
	public GenericScreen() {
	}

	/**
	 * 
	 * @param playerId
	 */
	public GenericScreen(int playerId) {
		this.playerId = playerId;
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
	public Widget[] getAttachedWidgets() {
		Widget[] list = new Widget[widgets.size()];
		widgets.keySet().toArray(list);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Screen attachWidget(Plugin plugin, Widget widget) {
		if (plugin == null)
			throw new NullPointerException("Plugin can not be null!");
		if (widget == null)
			throw new NullPointerException("Widget can not be null!");
		widgets.put(widget, plugin);
		widget.setPlugin(plugin);
		widget.setDirty(true);
		widget.setScreen(plugin, this);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Screen attachWidgets(Plugin plugin, Widget... widgets) {
		for (Widget widget : widgets) {
			attachWidget(plugin, widget);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Screen removeWidget(Widget widget) {
		RockyPlayer player = RockyManager.getPlayerFromId(playerId);
		if (player != null) {
			if (widgets.containsKey(widget)) {
				widgets.remove(widget);
				if (!widget.getType().isServerOnly()) {
					RockyManager.getPlayerFromId(playerId).sendPacket(
							new PacketWidgetRemove(widget, getId()));
				}
				widget.setScreen(null, screen);
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Screen removeWidgets(Plugin p) {
		if (p != Bukkit.getServer().getPluginManager().getPlugin("Spout")) {
			for (Widget i : getAttachedWidgets()) {
				if (widgets.get(i) != null && widgets.get(i).equals(p)) {
					removeWidget(i);
				}
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsWidget(Widget widget) {
		return containsWidget(widget.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsWidget(UUID id) {
		return getWidget(id) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Widget getWidget(UUID id) {
		for (Widget w : widgets.keySet()) {
			if (w.getId().equals(id)) {
				return w;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateWidget(Widget widget) {
		if (widgets.containsKey(widget)) {
			Plugin plugin = widgets.get(widget);
			widgets.remove(widget);
			widgets.put(widget, plugin);
			widget.setScreen(plugin, screen);
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTick() {
		RockyPlayer player = RockyManager.getPlayerFromId(playerId);
		if (player != null) {
			// Create a copy because onTick may remove the widget
			Set<Widget> widgetCopy = new HashSet<Widget>(widgets.keySet());
			for (Widget widget : widgetCopy) {
				try {
					widget.onTick();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (Widget widget : widgets.keySet()) {
				try {
					widget.onAnimate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (Widget widget : widgets.keySet()) {
				if (widget.isDirty()) {
					if (!widget.hasSize()/* || !widget.hasPosition() */) {
						widget.setX(widget.getX());
						widget.setHeight(widget.getHeight());
					}
					if (!widget.getType().isServerOnly()) {
						player.sendPacket(new PacketWidget(widget, getId()));
					}
					widget.setDirty(false);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Screen setBgVisible(boolean enable) {
		bg = enable;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBgVisible() {
		return bg;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RockyPlayer getPlayer() {
		return RockyManager.getPlayerFromId(playerId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		setBgVisible(input.readBoolean());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeBoolean(isBgVisible());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDirty(boolean dirty) {
		super.setDirty(dirty);
		if (dirty) {
			for (Widget widget : getAttachedWidgets()) {
				widget.setDirty(true);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Widget copy() {
		throw new UnsupportedOperationException(
				"You can not create a copy of a screen");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Widget> getAttachedWidgetsAsSet(boolean recursive) {
		Set<Widget> set = new HashSet<Widget>();
		for (Widget w : widgets.keySet()) {
			set.add(w);
			if (w instanceof Screen && recursive) {
				set.addAll(((Screen) w).getAttachedWidgetsAsSet(true));
			}
		}
		return set;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onScreenClose(ScreenCloseEvent e) {
	}
}
