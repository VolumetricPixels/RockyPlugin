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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyapi.player.Waypoint;

/**
 * 
 */
public class RockyConfig {
	private boolean forceClient = false;
	private boolean authenticate = true;
	private int authTicks = 200;
	private String kickMessage = "This server requires Rocky!";

	private static Map<String, List<Waypoint>> waypoints = new HashMap<String, List<Waypoint>>();

	/**
	 * 
	 * @param file
	 */
	public RockyConfig() {
		Rocky.getInstance().reloadConfig();
		FileConfiguration configuration = Rocky.getInstance().getConfig();
		configuration.options().copyDefaults(true);

		forceClient = configuration.getBoolean("ForceClient", false);
		kickMessage = configuration.getString("ForceClientKickMessage");
		authTicks = configuration.getInt("AuthenticateTicks", 200);
		authenticate = configuration.getBoolean("Authenticate", true);

		Rocky.getInstance().saveConfig();
	}

	/**
	 * 
	 * @param config
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private void loadWaypoints(FileConfiguration config) {
		try {
			Object o = config.get("waypoints");
			if (o != null) {
				MemorySection mem = (MemorySection) o;
				Map<String, Object> worlds = getMemorySectionMap(mem);
				Iterator<Entry<String, Object>> i = worlds.entrySet()
						.iterator();
				while (i.hasNext()) {
					Entry<String, Object> e = i.next();
					final String world = e.getKey().toLowerCase();
					if (e.getValue() instanceof MemorySection) {
						Map<String, Object> waypoints = getMemorySectionMap((MemorySection) e
								.getValue());
						Iterator<Entry<String, Object>> j = waypoints
								.entrySet().iterator();
						while (j.hasNext()) {
							Entry<String, Object> waypoint = j.next();
							MemorySection values = (MemorySection) waypoint
									.getValue();
							double x = values.getDouble("x");
							double y = values.getDouble("y");
							double z = values.getDouble("z");

							List<Waypoint> existing = (List<Waypoint>) waypoints
									.get(world);
							if (existing == null) {
								existing = new LinkedList<Waypoint>();
								waypoints.put(world, existing);
							}
							existing.add(new Waypoint(x, y, z, waypoint
									.getKey()));
						}
					}
				}
			}
		} catch (Exception e) {
			RockyManager.printConsole("Error while loading waypoints: ");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param name
	 * @param location
	 */
	public void addWaypoint(String name, Location location) {
		Rocky.getInstance().reloadConfig();
		FileConfiguration configuration = Rocky.getInstance().getConfig();
		configuration.set("waypoints."
				+ location.getWorld().getName().toLowerCase() + "." + name
				+ ".x", location.getX());
		configuration.set("waypoints."
				+ location.getWorld().getName().toLowerCase() + "." + name
				+ ".y", location.getY());
		configuration.set("waypoints."
				+ location.getWorld().getName().toLowerCase() + "." + name
				+ ".z", location.getZ());
		Rocky.getInstance().saveConfig();
		Waypoint waypoint = new Waypoint(location.getX(), location.getY(),
				location.getZ(), name);

		for (Player p : location.getWorld().getPlayers())
			if (p instanceof RockyPlayer) {
				((RockyPlayer) p).addWaypoint(waypoint);
			}
		waypoints.get(location.getWorld().getName()).add(waypoint);
	}

	/**
	 * 
	 * @param section
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getMemorySectionMap(MemorySection section) {
		Field f;
		try {
			f = MemorySection.class.getDeclaredField("map");
			f.setAccessible(true);
			return (Map<String, Object>) f.get(section);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param world
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Waypoint> getWaypoints(String world) {
		List<Waypoint> l = waypoints.get(world);
		if (l == null) {
			return Collections.EMPTY_LIST;
		}
		return l;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isForceClient() {
		return forceClient;
	}

	/**
	 * 
	 * @return
	 */
	public String getKickMessage() {
		return kickMessage;
	}

	/**
	 * 
	 * @return
	 */
	public int getAuthenticateTicks() {
		return authTicks;
	}

	/**
	 * 
	 * @return
	 */
	public boolean authenticateSpout() {
		return authenticate;
	}

}
