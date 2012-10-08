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
package org.spout.legacy;

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
import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.player.SpoutPlayer;
import org.spout.legacyapi.world.Waypoint;

/**
 * 
 */
public class SpoutConfig {
	private boolean forceClient = false;

	private boolean allowSkyCheat = false;
	private boolean allowClearWaterCheat = false;
	private boolean allowStarsCheat = false;
	private boolean allowWeatherCheat = false;
	private boolean allowTimeCheat = false;
	private boolean allowCoordsCheat = false;
	private boolean allowEntityLabelCheat = false;
	private boolean allowVoidFogCheat = false;
	private boolean authenticateSpoutcraft = true;

	private int authTicks = 200;
	private String kickMessage = "This server requires Spoutcraft! http://get.spout.org";

	private static Map<String, List<Waypoint>> waypoints = new HashMap<String, List<Waypoint>>();

	/**
	 * 
	 * @param file
	 */
	public SpoutConfig() {
		Spout.getInstance().reloadConfig();
		FileConfiguration configuration = Spout.getInstance().getConfig();
		configuration.options().copyDefaults(true);

		forceClient = configuration
				.getBoolean("ForceSinglePlayerClient", false);
		kickMessage = configuration
				.getString("ForceSinglePlayerClientKickMessage");
		authTicks = configuration.getInt("AuthenticateTicks", 200);
		allowSkyCheat = configuration.getBoolean("AllowSkyCheat", false);
		allowClearWaterCheat = configuration.getBoolean("AllowClearWaterCheat",
				false);
		allowStarsCheat = configuration.getBoolean("AllowStarsCheat", false);
		allowWeatherCheat = configuration
				.getBoolean("AllowWeatherCheat", false);
		allowTimeCheat = configuration.getBoolean("AllowTimeCheat", false);
		allowCoordsCheat = configuration.getBoolean("AllowCoordsCheat", false);
		allowEntityLabelCheat = configuration.getBoolean(
				"AllowEntityLabelCheat", false);
		allowVoidFogCheat = configuration
				.getBoolean("AllowVoidFogCheat", false);
		authenticateSpoutcraft = configuration.getBoolean(
				"AuthenticateSpoutcraft", true);

		Spout.getInstance().saveConfig();
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
			SpoutManager.printConsole("Error while loading waypoints: ");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param name
	 * @param location
	 */
	public void addWaypoint(String name, Location location) {
		Spout.getInstance().reloadConfig();
		FileConfiguration configuration = Spout.getInstance().getConfig();
		configuration.set("waypoints."
				+ location.getWorld().getName().toLowerCase() + "." + name
				+ ".x", location.getX());
		configuration.set("waypoints."
				+ location.getWorld().getName().toLowerCase() + "." + name
				+ ".y", location.getY());
		configuration.set("waypoints."
				+ location.getWorld().getName().toLowerCase() + "." + name
				+ ".z", location.getZ());
		Spout.getInstance().saveConfig();
		Waypoint waypoint = new Waypoint(location.getX(), location.getY(),
				location.getZ(), name);

		for (Player p : location.getWorld().getPlayers())
			if (p instanceof SpoutPlayer) {
				((SpoutPlayer) p).addWaypoint(waypoint);
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
	public boolean isAllowSkyCheat() {
		return allowSkyCheat;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllowClearWaterCheat() {
		return allowClearWaterCheat;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllowStarsCheat() {
		return allowStarsCheat;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllowWeatherCheat() {
		return allowWeatherCheat;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllowTimeCheat() {
		return allowTimeCheat;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllowVoidFogCheat() {
		return allowVoidFogCheat;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllowCoordsCheat() {
		return allowCoordsCheat;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllowEntityLabelCheat() {
		return allowEntityLabelCheat;
	}

	/**
	 * 
	 * @return
	 */
	public boolean authenticateSpout() {
		return authenticateSpoutcraft;
	}

}
