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
package org.spout.legacy.keyboard;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import org.spout.legacyapi.event.input.KeyBindingEvent;
import org.spout.legacyapi.keyboard.BindingExecutionDelegate;
import org.spout.legacyapi.keyboard.KeyBinding;
import org.spout.legacyapi.keyboard.KeyBindingManager;
import org.spout.legacyapi.keyboard.Keyboard;
import org.spout.legacyapi.packet.protocol.PacketKeyBinding;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class SpoutKeyBindingManager implements KeyBindingManager {
	private HashMap<UUID, KeyBinding> bindings = new HashMap<UUID, KeyBinding>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBinding(String id, Keyboard defaultKey,
			String description, BindingExecutionDelegate callback, Plugin plugin)
			throws IllegalArgumentException {
		if (searchBinding(id, plugin) != null) {
			throw new IllegalArgumentException(
					"This binding is already registered: " + id
							+ " for plugin ["
							+ plugin.getDescription().getName() + "]");
		}
		KeyBinding binding = new KeyBinding(id, defaultKey, description,
				plugin, callback);
		bindings.put(binding.getUniqueId(), binding);
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p instanceof SpoutPlayer) {
				((SpoutPlayer) p).sendPacket(new PacketKeyBinding(binding));
			}
		}
	}

	/**
	 * 
	 * @param id
	 * @param plugin
	 * @return
	 */
	private KeyBinding searchBinding(String id, Plugin plugin) {
		for (KeyBinding binding : bindings.values()) {
			if (binding.getId().equals(id)
					&& binding.getPlugin().equals(plugin)) {
				return binding;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void summonKey(UUID uniqueId, SpoutPlayer player, Keyboard key,
			boolean pressed) {
		KeyBinding binding = searchBinding(uniqueId);
		if (binding == null) {
			return;
		}
		String id = binding.getId();
		Plugin plugin = binding.getPlugin();
		if (pressed) {
			try {
				binding.getDelegate().keyPressed(
						new KeyBindingEvent(player, binding));
			} catch (Exception e) {
				System.out
						.println("Could not execute Key Press Delegate of plugin ["
								+ plugin.getDescription().getName()
								+ "] for action [" + id + "]!");
				e.printStackTrace();
			}
		} else {
			try {
				binding.getDelegate().keyReleased(
						new KeyBindingEvent(player, binding));
			} catch (Exception e) {
				System.out
						.println("Could not execute Key Release Delegate of plugin ["
								+ plugin.getDescription().getName()
								+ "] for action [" + id + "]!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param uniqueId
	 * @return
	 */
	private KeyBinding searchBinding(UUID uniqueId) {
		return bindings.get(uniqueId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendKeyBinding(SpoutPlayer player) {
		if (!player.isSpoutEnabled()) {
			return;
		}
		for (KeyBinding binding : bindings.values())
			player.sendPacket(new PacketKeyBinding(binding));
	}

}
