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
package com.volumetricpixels.rockyplugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.Packet;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.fest.reflect.core.Reflection;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.event.RockyEnableEvent;
import com.volumetricpixels.rockyapi.event.RockyFailedEvent;
import com.volumetricpixels.rockyapi.event.RockyFinishedLoadingEvent;
import com.volumetricpixels.rockyapi.event.RockyLoadingEvent;
import com.volumetricpixels.rockyapi.material.MaterialEnumType;
import com.volumetricpixels.rockyapi.packet.protocol.PacketAchievementList;
import com.volumetricpixels.rockyapi.packet.protocol.PacketCustomItem;
import com.volumetricpixels.rockyapi.packet.protocol.PacketFileCacheBegin;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyapi.resource.Resource;
import com.volumetricpixels.rockyplugin.packet.RockyPacket;
import com.volumetricpixels.rockyplugin.packet.RockyPacketManager;
import com.volumetricpixels.rockyplugin.player.RockyPlayerHandler;
import com.volumetricpixels.rockyplugin.player.RockyPlayerManager;

/**
 * 
 */
public class Rocky extends JavaPlugin implements Runnable {
	private static Rocky instance;

	private RockyConfig configuration;
	private Map<String, Integer> playerTimer = new HashMap<String, Integer>();

	/**
	 * 
	 */
	public Rocky() {
		instance = this;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void onEnable() {
		// Create the Manager
		RockyManager.setInstance(new RockyManager(new RockyPlayerManager(),
				new RockyResourceManager(), new RockyKeyBindingManager(),
				new RockyPacketManager(), new RockyMaterialManager()));

		// Load the configuration
		configuration = new RockyConfig();

		// For each user online we need to override their values
		for (Player player : Bukkit.getOnlinePlayers()) {
			RockyPlayer sp = RockyManager.getPlayer(player);
			RockyPlayerHandler.updateBukkitEntry(sp);
			RockyPlayerHandler.updateNetworkEntry(sp);
			RockyPlayerHandler.sendAuthentication(sp);
		}

		// Start counting ticks
		Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(this, this, 0, 1);

		// Remove mappings from previous loads Can not remove them on disable
		// because
		// the packets will still be in the send queue
		Packet.l.d(195);
		Reflection.staticField("a").ofType(Map.class).in(Packet.class).get()
				.remove(RockyPacket.class);
		Reflection
				.staticMethod("a")
				.withParameterTypes(int.class, boolean.class, boolean.class,
						Class.class).in(Packet.class)
				.invoke(195, true, true, RockyPacket.class);

		// Register our listeners and commands
		Bukkit.getPluginManager().registerEvents(new RockyPlayerListener(),
				this);
		Bukkit.getPluginManager().registerEvents(
				RockyManager.getMaterialManager(), this);
		getCommand("rocky").setExecutor(new RockyCommand());

		// Load the current material registered
		YamlConfiguration itemConfig = new YamlConfiguration();
		try {
			itemConfig.load(new File(getDataFolder(), "map.yml"));

			List<String> list = itemConfig.getStringList("| ItemArray |");
			for (String key : list) {
				RockyManager.getMaterialManager().registerName(key,
						itemConfig.getInt("| ItemArray |." + key), MaterialEnumType.ITEM);
			}
			list = itemConfig.getStringList("| BlockArray |");
			for (String key : list) {
				RockyManager.getMaterialManager().registerName(key,
						itemConfig.getInt("| BlockArray |." + key), MaterialEnumType.BLOCK);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		Bukkit.getServer().getPluginManager()
				.callEvent(new RockyLoadingEvent());
		Bukkit.getServer().getPluginManager()
				.callEvent(new RockyFinishedLoadingEvent());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void onDisable() {
		// Save the current material registered
		YamlConfiguration itemConfig = new YamlConfiguration();
		try {
			itemConfig.createSection(
					"| ItemArray |",
					RockyManager.getMaterialManager().getRegisteredNames(
							MaterialEnumType.ITEM));
			itemConfig.createSection(
					"| BlockArray |",
					RockyManager.getMaterialManager().getRegisteredNames(
							MaterialEnumType.BLOCK));
			itemConfig.save(new File(getDataFolder(), "map.yml"));
		} catch (Throwable e) {
			RockyManager.printConsole("Unable to save global item map");
		}

		// Cancel all task registered by the plugin
		getServer().getScheduler().cancelTasks(this);
	}

	/**
	 * 
	 * @return
	 */
	public RockyConfig getConfiguration() {
		return configuration;
	}

	/**
	 * 
	 * @return
	 */
	public final static Rocky getInstance() {
		return instance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		Player[] online = Bukkit.getOnlinePlayers();
		for (Player player : online) {
			// Tick the player if is already authenticated
			if (player instanceof RockyPlayer) {
				((RockyPlayer) player).onTick();
			}
			// Check if the player isn't authenticated yet
			String name = player.getName();
			if (!playerTimer.containsKey(name)) {
				continue;
			}
			int tickLeft = playerTimer.get(name) - 1;
			// Remove the player and kick it
			if (tickLeft == 0) {
				playerTimer.remove(name);
				if ((configuration.isForceClient() && !player
						.hasPermission(RockyPermission.FORCE_CLIENT.getNode()))) {
					Bukkit.getServer()
							.getPluginManager()
							.callEvent(
									new RockyFailedEvent((RockyPlayer) player));
					RockyManager.printConsole("Kicking " + player.getName()
							+ " for not running Rocky");
					player.kickPlayer(configuration.getKickMessage());
				}
			} else
				playerTimer.put(name, tickLeft);
		}
	}

	/**
	 * 
	 * @param player
	 */
	public void addPlayerToCheckList(Player player) {
		playerTimer.put(player.getName(), configuration.getAuthenticateTicks());
	}

	/**
	 * 
	 * @param player
	 */
	public void handlePlayerAuthentication(RockyPlayer player) {
		playerTimer.remove(player.getName());

		// Send each custom data.
		Resource[] resources = ((RockyResourceManager) RockyManager
				.getResourceManager()).resourceList.values().toArray(
				new Resource[0]);
		player.sendPacket(new PacketFileCacheBegin(resources));

		player.sendPacket(new PacketCustomItem(RockyManager
				.getMaterialManager().getItemList()));
		player.sendPacket(new PacketAchievementList(player));

		// TODO: Send Blocks and Design.

		// Our key bindings
		RockyManager.getKeyBindingManager().sendKeyBinding(player);

		// Call custom event that tell us that a player has been enabled
		Bukkit.getServer().getPluginManager()
				.callEvent(new RockyEnableEvent(player));

		// Update map waypoints
		player.updateWaypoints();
	}

}
