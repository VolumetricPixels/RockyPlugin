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
package com.volumetricpixels.rockyapi.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.plugin.Plugin;

/**
 * 
 */
public interface ResourceManager {
	/**
	 * 
	 * @return
	 */
	Resource[] getResourceList();

	/**
	 * 
	 * @param resource
	 */
	void addResource(Plugin plugin, Resource resource);

	/**
	 * 
	 * @param name
	 * @return
	 */
	boolean hasResource(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	<T extends Resource> T getResource(String name);

	/**
	 * 
	 * @param plugin
	 * @param file
	 * @return
	 */
	boolean addToCache(Plugin plugin, File file);

	/**
	 * 
	 * @param plugin
	 * @param fileUrl
	 * @return
	 */
	boolean addToCache(Plugin plugin, String fileUrl);

	/**
	 * 
	 * @param plugin
	 * @param input
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	boolean addToCache(Plugin plugin, InputStream input, String fileName)
			throws IOException;

	/**
	 * 
	 * @param plugin
	 */
	void removeFromCache(Plugin plugin);

	/**
	 * 
	 * @param plugin
	 * @param file
	 */
	void removeFromCache(Plugin plugin, String file);

	/**
	 * 
	 * @param file
	 * @return
	 */
	boolean canCache(File file);

	/**
	 * 
	 * @param fileUrl
	 * @return
	 */
	boolean canCache(String fileUrl);
}
