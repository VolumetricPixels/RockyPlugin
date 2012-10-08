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
package org.spout.legacy.resource;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.bukkit.plugin.Plugin;
import org.spout.legacyapi.player.SpoutPlayer;
import org.spout.legacyapi.resource.ResourceManager;

/**
 * 
 */
public class SpoutResourceManager implements ResourceManager {

	@Override
	public List<String> getCache(Plugin plugin) {
		return null;
	}

	@Override
	public boolean addToPreLoginCache(Plugin plugin, File file) {
		return false;
	}

	@Override
	public boolean addToPreLoginCache(Plugin plugin, String fileUrl) {
		return false;
	}

	@Override
	public boolean addToPreLoginCache(Plugin plugin, Collection<File> files) {
		return false;
	}

	@Override
	public boolean addToPreLoginCache(Plugin plugin, List<String> fileUrls) {
		return false;
	}

	@Override
	public boolean addToPreLoginCache(Plugin plugin, InputStream input,
			String fileName) {
		return false;
	}

	@Override
	public boolean addToCache(Plugin plugin, File file) {
		return false;
	}

	@Override
	public boolean addToCache(Plugin plugin, String fileUrl) {
		return false;
	}

	@Override
	public boolean addToCache(Plugin plugin, Collection<File> file) {
		return false;
	}

	@Override
	public boolean addToCache(Plugin plugin, List<String> fileUrls) {
		return false;
	}

	@Override
	public boolean addToCache(Plugin plugin, InputStream input, String fileName) {
		return false;
	}

	@Override
	public void removeFromCache(Plugin plugin, String file) {
	}

	@Override
	public void removeFromCache(Plugin plugin, List<String> file) {
	}

	@Override
	public boolean canCache(File file) {
		return false;
	}

	@Override
	public boolean canCache(String fileUrl) {
		return false;
	}

	/**
	 * 
	 * @param player
	 */
	public static void sendCustomData(SpoutPlayer player) {

	}
}
