/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
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
package com.volumetricpixels.rockyplugin;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.event.input.KeyBindingEvent;
import com.volumetricpixels.rockyapi.keyboard.BindingExecutionDelegate;
import com.volumetricpixels.rockyapi.keyboard.KeyBinding;
import com.volumetricpixels.rockyapi.keyboard.KeyBindingManager;
import com.volumetricpixels.rockyapi.keyboard.Keyboard;
import com.volumetricpixels.rockyapi.packet.protocol.PacketKeyBinding;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class RockyKeyBindingManager implements KeyBindingManager {
	private HashMap<UUID, KeyBinding> bindings = new HashMap<UUID, KeyBinding>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBinding(String id, Keyboard defaultKey,
			String description, BindingExecutionDelegate callback, Plugin plugin) {
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
			if (p instanceof RockyPlayer) {
				((RockyPlayer) p).sendPacket(new PacketKeyBinding(binding));
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
	public void summonKey(UUID uniqueId, RockyPlayer player, Keyboard key,
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
	public void sendKeyBinding(RockyPlayer player) {
		if (!player.isModded()) {
			return;
		}
		for (KeyBinding binding : bindings.values()) {
			player.sendPacket(new PacketKeyBinding(binding));
		}
	}

}
