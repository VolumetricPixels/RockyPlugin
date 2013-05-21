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
package com.volumetricpixels.rockyplugin.reflection;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Base reflection for any type of version
 */
public class Reflector {
	/**
	 * List of all reflector
	 */
	protected static Map<String, Map<String, String>> reflectorList = new HashMap<String, Map<String, String>>();

	/**
	 * Current Reflector
	 */
	protected static Map<String, String> reflector;

	/**
	 * Gets the reflector values from a yml file
	 * 
	 * @param node
	 *            where to start looking for the files
	 */
	@SuppressWarnings("unchecked")
	public static void getValues(YamlConfiguration node) {
		Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) node
				.getMapList("Reflection");
		for (String key : values.keySet()) {
			Map<String, String> holder = values.get(key);
			reflectorList.put(key, holder);
		}
		return;
	}

	/**
	 * Sets the reflector that the engine will use
	 * 
	 * @param id
	 *            the name of the reflector
	 */
	public static void setReflector(String id) {
		if (reflectorList.containsKey(id) == false) {
			throw new IllegalArgumentException(
					"The reflector is not available.");
		}
		reflector = reflectorList.get(id);
	}

	/**
	 * Gets the field using the current reflection class
	 * 
	 * @param id
	 *            the id of the field
	 * @return the field name
	 */
	public static String getField(String id) {
		if (reflector == null) {
			throw new IllegalArgumentException(
					"Can't get field without setting a reflector.");
		}
		return reflector.get(id);
	}

	/**
	 * Gets the method using the current reflection class
	 * 
	 * @param id
	 *            the id of the method
	 * @return the method name
	 */
	public static String getMethod(String id) {
		if (reflector == null) {
			throw new IllegalArgumentException(
					"Can't get method without setting a reflector.");
		}
		return reflector.get(id);
	}

}
