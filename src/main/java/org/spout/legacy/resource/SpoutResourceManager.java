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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

import org.bukkit.plugin.Plugin;
import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.packet.protocol.PacketFileCacheBegin;
import org.spout.legacyapi.player.SpoutPlayer;
import org.spout.legacyapi.resource.Resource;
import org.spout.legacyapi.resource.ResourceManager;

/**
 * 
 */
public class SpoutResourceManager implements ResourceManager {
	private static final String[] VALID_EXTENSION = { "yml", "png", "ogg",
			"midi", "wav" };
	private Map<String, Map<String, Resource<?>>> resourceCache = new HashMap<String, Map<String, Resource<?>>>();
	private List<Resource<?>> resourceList = new ArrayList<Resource<?>>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCache(Plugin plugin) {
		if (plugin == null) {
			throw new IllegalArgumentException("Plugin may not be null");
		} else if (!resourceCache.containsKey(plugin.getName())) {
			return null;
		}
		return Arrays.asList(resourceCache.get(plugin.getName()).keySet()
				.toArray(new String[0]));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToCache(Plugin plugin, File file) {
		try {
			return addToCache(plugin, new FileInputStream(file), file.getName());
		} catch (FileNotFoundException e) {
			SpoutManager.printConsole(
					"[Error] Trying to add to cache an invalid File! {%s}",
					file.getName());
		} catch (IOException e) {
			SpoutManager.printConsole(e.getLocalizedMessage());
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToCache(Plugin plugin, String fileUrl) {
		try {
			URL url = new URL(fileUrl);
			URLConnection connection = url.openConnection();
			return addToCache(plugin, connection.getInputStream(), fileUrl);
		} catch (MalformedURLException e) {
			SpoutManager.printConsole(
					"[Error] Trying to add to cache an invalid Url! {%s}",
					fileUrl);
		} catch (IOException e) {
			SpoutManager.printConsole(
					"[Error] Cannot connect to remote Url! {%s}", fileUrl);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToCache(Plugin plugin, InputStream input, String fileName)
			throws IOException {
		if (plugin == null) {
			throw new IllegalArgumentException("Plugin may not be null");
		}

		// We always get a valid pointer to a map structure.
		Map<String, Resource<?>> resource;
		if (!resourceCache.containsKey(plugin.getName())) {
			resourceCache.put(plugin.getName(),
					new HashMap<String, Resource<?>>());
		}
		resource = resourceCache.get(plugin.getName());

		// Get the revision from the stream
		long revision = getChecksum(input);
		if (revision == 0) {
			input.close();
			throw new IOException("[Error] Cannot get revision from {"
					+ fileName + "}");
		}

		boolean isLocal = !(fileName.contains("http.") || fileName
				.contains("www."));
		Resource<?> resourceData;
		if (!isLocal)
			resourceData = new Resource<String>(fileName, revision, fileName);
		else
			resourceData = new Resource<byte[]>(fileName, revision, read(input));
		resource.put(fileName, resourceData);
		resourceList.add(resourceData);

		input.close();

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeFromCache(Plugin plugin) {
		if (plugin == null) {
			throw new IllegalArgumentException("Plugin may not be null");
		} else if (!resourceCache.containsKey(plugin.getName())) {
			return;
		}
		resourceCache.get(plugin.getName()).clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeFromCache(Plugin plugin, String resource) {
		if (plugin == null) {
			throw new IllegalArgumentException("Parameters may not be null");
		} else if (!resourceCache.containsKey(plugin.getName())) {
			return;
		}
		resourceList.remove(resourceCache.get(plugin.getName())
				.remove(resource));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canCache(File file) {
		return canCache(file.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canCache(String fileUrl) {
		String extension = fileUrl.substring(fileUrl.lastIndexOf(".") + 1);
		for (String validExtension : VALID_EXTENSION) {
			if (validExtension.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param player
	 */
	public static void sendCustomData(SpoutPlayer player) {
		Resource<?>[] resources = ((SpoutResourceManager) SpoutManager
				.getResourceManager()).resourceList.toArray(new Resource[0]);
		player.sendPacket(new PacketFileCacheBegin(resources));
	}

	/**
	 * 
	 * @param is
	 * @return
	 */
	public static long getChecksum(InputStream is) {
		CheckedInputStream cis = null;
		long checksum = 0;
		try {
			cis = new CheckedInputStream(is, new Adler32());
			byte[] tempBuf = new byte[128];
			while (cis.read(tempBuf) >= 0) {
			}
			checksum = cis.getChecksum().getValue();
		} catch (IOException e) {
			checksum = 0;
		} finally {
			if (cis != null) {
				try {
					cis.close();
				} catch (IOException ioe) {
				}
			}
		}
		return checksum;
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public byte[] read(InputStream ios) throws IOException {
		ByteArrayOutputStream ous = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}
		}
		return ous.toByteArray();
	}
}
