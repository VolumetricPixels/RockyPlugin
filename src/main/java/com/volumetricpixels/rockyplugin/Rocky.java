/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2012-2013, VolumetricPixels <http://www.volumetricpixels.com/>
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
import com.volumetricpixels.rockyplugin.packet.RockyPacketManager;
import com.volumetricpixels.rockyplugin.player.RockyPlayerHandler;
import com.volumetricpixels.rockyplugin.player.RockyPlayerManager;
import com.volumetricpixels.rockyplugin.reflection.Reflector;
import com.volumetricpixels.rockyplugin.util.ThreadWarningSystem;

/**
 * 
 */
public class Rocky extends JavaPlugin implements Runnable {
	private static Rocky instance;

	private ThreadWarningSystem threadDeadlock;
	private RockyConfig configuration;
	private Map<String, Integer> playerTimer = new HashMap<String, Integer>();
	private boolean isDisabled = true;
	private BufferedWriter debugWritter;
	private boolean isForgeEnabled = false;
	
	/**
	 * 
	 */
	public Rocky() {
		instance = this;
	}

	/**
	 * 
	 * @param version
	 */
	private void parseVersion(String version) {
		String otherVersion = version.substring(version.indexOf("(MC: ")
				+ "(MC: ".length(), version.length() - 1);
		if (version.contains("MCPC")) {
			Reflector.setReflector("MCPC");
			isForgeEnabled = true;
		} else if (version.contains("Bukkit") || version.contains("Spigot")) {
			Reflector.setReflector("Bukkit");
		}
		RockyManager.printConsole("Using version: %s - %s", version,
				otherVersion);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void onEnable() {
		// Parse and set the reflector of the plugin
		YamlConfiguration config = new YamlConfiguration();
		try {
			config.load(getResource("reflection.yml"));
			Reflector.getValues(config);
		} catch (IOException e1) {
		} catch (InvalidConfigurationException e1) {
			RockyManager.printConsole("Can't read reflection file.");
			setEnabled(false);
			return;
		}
		parseVersion(Bukkit.getVersion());

		// Start the debugger
		try {
			debugWritter = new BufferedWriter(new FileWriter(new File(
					getDataFolder(), "debug.info")));
		} catch (IOException ex) {
		}

		// Register the thread deadlock finder.
		threadDeadlock = new ThreadWarningSystem();
		threadDeadlock.addListener(new RockyDeadlockListener());

		// Create the Manager
		RockyManager.setInstance(new RockyManager(new RockyPlayerManager(),
				new RockyResourceManager(), new RockyPacketManager(),
				new RockyMaterialManager()));

		// Start counting ticks
		Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(this, this, 0, 1);

		// Load the configuration
		configuration = new RockyConfig();

		// For each user online we need to override their values
		Player[] players = Bukkit.getOnlinePlayers();
		for (Player player : players) {
			handlePlayerLogin(player);
		}

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
						itemConfig.getInt("| ItemArray |." + key),
						MaterialEnumType.ITEM);
			}
			list = itemConfig.getStringList("| BlockArray |");
			for (String key : list) {
				RockyManager.getMaterialManager().registerName(key,
						itemConfig.getInt("| BlockArray |." + key),
						MaterialEnumType.BLOCK);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			RockyManager.printConsole("Can't read the configuration file");
		} catch (InvalidConfigurationException e) {
			RockyManager
					.printConsole("The configuration file contains an illegal expresion");
		}

		Bukkit.getServer().getPluginManager()
				.callEvent(new RockyLoadingEvent());
		Bukkit.getServer().getPluginManager()
				.callEvent(new RockyFinishedLoadingEvent());

		isDisabled = false;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void onDisable() {
		isDisabled = true;

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

			// Stop the debug writter
			if (debugWritter != null) {
				debugWritter.close();
			}
		} catch (IOException e) {
			RockyManager.printConsole("Unable to save global item map");
		}

		// Cancel all task registered by the plugin
		getServer().getScheduler().cancelTasks(this);

		// Prevent annoying messages about like "CONFLICT @ X"
		RockyManager.getMaterialManager().clear();
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
	public static final Rocky getInstance() {
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
				if (configuration.isForceClient()) {
					Bukkit.getServer()
							.getPluginManager()
							.callEvent(
									new RockyFailedEvent((RockyPlayer) player));
					RockyManager.printConsole("Kicking " + player.getName()
							+ " for not running Rocky");

					RockyManager.getPlayer(player).getHandle().playerConnection.networkManager
							.a(configuration.getKickMessage(), new Object[0]);
				} else if (player.hasPermission(RockyPermission.FORCE_CLIENT
						.getNode())) {
					Bukkit.getServer()
							.getPluginManager()
							.callEvent(
									new RockyFailedEvent((RockyPlayer) player));
					RockyManager.printConsole("Kicking " + player.getName()
							+ " for not running Rocky when has been forced");

					RockyManager.getPlayer(player).getHandle().playerConnection.networkManager
							.a(configuration.getKickMessage(), new Object[0]);
				}
			} else {
				playerTimer.put(name, tickLeft);
			}
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
		RockyManager.printConsole("%s has been authenticated with Rocky",
				player.getName());
		playerTimer.remove(player.getName());

		player.setBuildVersion(0x300);

		// Send each custom data.
		player.sendPacket(new PacketFileCacheBegin(RockyManager
				.getResourceManager().getResourceList()));

		player.sendPacket(new PacketCustomItem(RockyManager
				.getMaterialManager().getItemList()));
		player.sendPacket(new PacketAchievementList(player));

		// TODO: Send Blocks and Design.

		// Call custom event that tell us that a player has been enabled
		Bukkit.getServer().getPluginManager()
				.callEvent(new RockyEnableEvent(player));
	}

	/**
	 * 
	 * @param player
	 */
	public void handlePlayerLogin(Player player) {
		RockyPlayerHandler.updateBukkitEntry(player);
		RockyPlayerHandler.updateNetworkEntry(player);
		RockyPlayerHandler.sendAuthentication(player);

		Rocky.getInstance().addPlayerToCheckList(player);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isForgeEnabled() {
		return isForgeEnabled;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isDisabled() {
		return isDisabled;
	}

	/**
	 * 
	 * @param text
	 */
	public void writeDebug(String text) {
		if (!configuration.isDebugEnabled()) {
			return;
		}
		try {
			debugWritter.write(text);
		} catch (IOException ex) {
		}
	}

}
