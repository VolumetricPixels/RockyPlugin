/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2011-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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
package org.spout.legacy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.Packet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.fest.reflect.core.Reflection;
import org.spout.legacy.keyboard.SpoutKeyBindingManager;
import org.spout.legacy.packet.PacketCompression;
import org.spout.legacy.packet.SpoutPacket;
import org.spout.legacy.packet.SpoutPacketManager;
import org.spout.legacy.player.SpoutPlayerHandler;
import org.spout.legacy.player.SpoutPlayerManager;
import org.spout.legacy.resource.SpoutResourceManager;
import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.event.spout.SpoutEnableEvent;
import org.spout.legacyapi.event.spout.SpoutFailedEvent;
import org.spout.legacyapi.event.spout.SpoutFinishedLoadingEvent;
import org.spout.legacyapi.event.spout.SpoutLoadingEvent;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class Spout extends JavaPlugin implements Runnable {
	private static Spout instance;

	private SpoutConfig configuration;
	private Map<String, Integer> playerTimer = new HashMap<String, Integer>();
	
	/**
	 * 
	 */
	public Spout() {
		// Create the SpoutManager
		SpoutManager.setInstance(new SpoutManager(new SpoutPlayerManager(),
				new SpoutResourceManager(), new SpoutKeyBindingManager(),
				new SpoutPacketManager(), new SpoutMaterialManager()));

		// Set the instance of this class
		instance = this;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void onEnable() {
		configuration = new SpoutConfig();

		// For each user online we need to override their values
		for (Player player : Bukkit.getOnlinePlayers()) {
			SpoutPlayer sp = SpoutManager.getPlayer(player);
			sp.setPreCachingComplete(true);
			SpoutPlayerHandler.updateBukkitEntry(sp);
			SpoutPlayerHandler.updateNetworkEntry(sp);
			SpoutPlayerHandler.sendAuthentication(sp);
		}

		// Initialize the packet compression thread
		PacketCompression.startThread();

		// Start counting ticks
		Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(this, this, 0, 1);

		// Remove mappings from previous loads Can not remove them on disable
		// because
		// the packets will still be in the send queue
		Packet.l.d(195);
		Reflection.staticField("a").ofType(Map.class).in(Packet.class).get()
				.remove(SpoutPacket.class);
		Reflection
				.staticMethod("a")
				.withParameterTypes(int.class, boolean.class, boolean.class,
						Class.class).in(Packet.class)
				.invoke(195, true, true, SpoutPacket.class);

		// Register our listeners and commands
		Bukkit.getPluginManager().registerEvents(new SpoutEntityListener(),
				this);
		Bukkit.getPluginManager().registerEvents(new SpoutPlayerListener(),
				this);
		Bukkit.getPluginManager().registerEvents(SpoutManager.getMaterialManager(),
				this);
		getCommand("spout").setExecutor(new SpoutCommand());

		// Load the current material registered
		YamlConfiguration itemConfig = new YamlConfiguration();
		try {
			itemConfig.load(new File(getDataFolder(), "itemMap.yml"));
			Map<String, Object> listData = itemConfig.getValues(true);
			for (String data : listData.keySet()) {
				SpoutManager.getMaterialManager().registerName(data,
						itemConfig.getInt(data));
			}

		} catch (Throwable e) {
			SpoutManager.printConsole("Unable to load global item map");
		}

		Bukkit.getServer().getPluginManager()
				.callEvent(new SpoutLoadingEvent());
		Bukkit.getServer().getPluginManager()
				.callEvent(new SpoutFinishedLoadingEvent());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void onDisable() {
		// Disable every player in game
		for (Player player : Bukkit.getOnlinePlayers()) {
			handlePlayerReset(player);
		}

		// Save the current material registered
		YamlConfiguration itemConfig = new YamlConfiguration();
		try {
			Map<String, Integer> itemArray = SpoutManager.getMaterialManager()
					.getRegisteredNames();
			for (String item : itemArray.keySet()) {
				itemConfig.set(item, itemArray.get(item));
			}
			itemConfig.save(new File(getDataFolder(), "itemMap.yml"));
		} catch (Throwable e) {
			SpoutManager.printConsole("Unable to load global item map");
		}

		// Cancel all task registered by the plugin
		getServer().getScheduler().cancelTasks(this);

		// End the compression thread
		PacketCompression.endThread();
	}

	/**
	 * 
	 * @return
	 */
	public SpoutConfig getConfiguration() {
		return configuration;
	}

	/**
	 * 
	 * @return
	 */
	public final static Spout getInstance() {
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
			if (player instanceof SpoutPlayer) {
				((SpoutPlayer) player).onTick();
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
						.hasPermission(SpoutPermission.IGNORE_SPOUT.getNode()))
						|| player.hasPermission(SpoutPermission.FORCE_SPOUT
								.getNode()) && !player.isOp()) {
					Bukkit.getServer()
							.getPluginManager()
							.callEvent(
									new SpoutFailedEvent((SpoutPlayer) player));
					SpoutManager.printConsole("Kicking " + player.getName()
							+ " for not running Spoutcraft");
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
	public void handlePlayerAuthentication(SpoutPlayer player) {
		playerTimer.remove(player.getName());

		// Send each custom data.
		SpoutMaterialManager.sendCustomData(player);
		SpoutPlayerManager.sendCustomData(player);
		SpoutResourceManager.sendCustomData(player);

		// Our key bindings
		SpoutManager.getKeyBindingManager().sendKeyBinding(player);

		// Refresh the permissions
		player.updatePermissions();

		// Call custom event that tell us that a player has been enabled
		Bukkit.getServer().getPluginManager()
				.callEvent(new SpoutEnableEvent(player));

		// Update map waypoints
		player.updateWaypoints();
	}

	/**
	 * 
	 * @param player
	 */
	public void handlePlayerQuit(Player player) {
	}

	/**
	 * 
	 * @param player
	 */
	public void handlePlayerReset(Player player) {

	}

}
