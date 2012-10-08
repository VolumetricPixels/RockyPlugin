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
package org.spout.legacyapi.resource;

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
	 * @param resource
	 */
	public void addResource(Plugin plugin, Resource resource);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean hasResource(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	public <T extends Resource> T getResource(String name);

	/**
	 * Adds a file to the cache for clients. This file will be downloaded
	 * immediately for any online players, and upon login of new clients.
	 * <p/>
	 * This is not recommended for larger files, since the extra latency for
	 * large downloads may disrupt the player's experience.
	 * 
	 * @param file
	 *            to pre-cache
	 * @return true if the file was pre-cached
	 */
	public boolean addToCache(Plugin plugin, File file);

	/**
	 * Adds a file to the cache for clients. This file will be downloaded
	 * immediately for any online players, and upon login of new clients.
	 * <p/>
	 * This is not recommended for larger files, since the extra latency for
	 * large downloads may disrupt the player's experience.
	 * 
	 * @param fileUrl
	 *            to pre-cache
	 * @return true if the file was pre-cached
	 */
	public boolean addToCache(Plugin plugin, String fileUrl);

	/**
	 * Sends the contents of the input stream to clients. The contents of the
	 * steam will only be downloaded immediately.
	 * <p/>
	 * This is not recommended for larger files, since the extra latency for
	 * large downloads may disrupt the player's experience.
	 * 
	 * @param plugin
	 *            caching the files.
	 * @param input
	 *            stream containing the bytes to be read
	 * @param file
	 *            name of the resulting file.
	 * @return true if the files were pre-cached
	 */
	public boolean addToCache(Plugin plugin, InputStream input, String fileName)
			throws IOException;

	/**
	 * 
	 * @param plugin
	 */
	public void removeFromCache(Plugin plugin);

	/**
	 * Removes the given filename from the cache, if it exists.
	 * 
	 * @param plugin
	 *            that the file is cached for
	 * @param file
	 *            name to remove
	 */
	public void removeFromCache(Plugin plugin, String file);

	/**
	 * Checks if the file is approved for pre-caching. The file types approved
	 * for pre-caching are as follows: .yml, .png, .ogg, .midi, .wav, .zip
	 * 
	 * @param file
	 *            to check
	 * @return true if the file can be cached on the client
	 */
	public boolean canCache(File file);

	/**
	 * Checks if the fileUrl is approved for pre-caching. The file types
	 * approved for pre-caching are as follows: .txt, .yml, .xml, .png, .jpg,
	 * .ogg, .midi, .wav, .zip
	 * 
	 * @param fileUrl
	 *            to check
	 * @return true if the file can be cached on the client
	 */
	public boolean canCache(String fileUrl);
}
