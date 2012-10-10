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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.resource.Resource;
import com.volumetricpixels.rockyapi.resource.ResourceManager;

/**
 * 
 */
public class RockyResourceManager implements ResourceManager {
	private static final String[] VALID_EXTENSION = { "yml", "png", "ogg",
			"midi", "wav" };
	protected Map<Plugin, Map<String, Resource>> cacheList = new HashMap<Plugin, Map<String, Resource>>();
	protected Map<String, Resource> resourceList = new HashMap<String, Resource>();
	protected ExecutorService service;
	
	/**
	 * 
	 */
	public RockyResourceManager() {
		service = Executors.newCachedThreadPool();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addResource(Plugin plugin, Resource resource) {
		resourceList.put(resource.getName(), resource);
		addToCache(plugin, resource.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasResource(String name) {
		return resourceList.containsKey(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Resource> T getResource(String name) {
		return (T) resourceList.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToCache(Plugin plugin, File file) {
		if (!canCache(file)) {
			return false;
		}
		try {
			return addToCache(plugin, new FileInputStream(file), file.getName());
		} catch (FileNotFoundException e) {
			RockyManager.printConsole(
					"[Error] Trying to add to cache an invalid File! {%s}",
					file.getName());
		} catch (IOException e) {
			RockyManager.printConsole(e.getLocalizedMessage());
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToCache(Plugin plugin, String fileUrl) {
		if (!canCache(fileUrl)) {
			return false;
		}
		boolean isLocal = !(fileUrl.contains("http.") || fileUrl
				.contains("www."));
		if (isLocal) {
			return addToCache(plugin, new File(fileUrl));
		}
		try {
			URL url = new URL(fileUrl);
			URLConnection connection = url.openConnection();
			return addToCache(plugin, connection.getInputStream(), fileUrl);
		} catch (MalformedURLException e) {
			RockyManager.printConsole(
					"[Error] Trying to add to cache an invalid Url! {%s}",
					fileUrl);
		} catch (IOException e) {
			RockyManager.printConsole(
					"[Error] Cannot connect to remote Url! {%s}", fileUrl);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToCache(Plugin plugin, final InputStream input,
			final String fileName) throws IOException {
		if (plugin == null) {
			throw new IllegalArgumentException("Plugin may not be null");
		}

		// We always get a valid pointer to a map structure.
		final Map<String, Resource> resource;
		final Resource resourceData = resourceList.get(fileName);
		if (!cacheList.containsKey(plugin)) {
			cacheList.put(plugin,
					new HashMap<String, Resource>());
		}
		resource = cacheList.get(plugin);
		
		// Create our worker
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// Get the revision from the stream
				long revision = getChecksum(input);
				if (revision == 0) {
					try {
						input.close();
					} catch (IOException e) {
					}
					RockyManager
							.printConsole("[Error] Cannot get revision from {"
									+ fileName + "}");
					return;
				}

				boolean isLocal = !(fileName.contains("http.") || fileName
						.contains("www."));

				if (!isLocal)
					resourceData.setData(fileName);
				else
					try {
						resourceData.setData(read(input));
					} catch (IOException e) {
						RockyManager.printConsole("[Error] Cannot read file {"
								+ fileName + "}");
						return;
					} finally {
						try {
							input.close();
						} catch (IOException e) {
						}
					}
				resource.put(fileName, resourceData);
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		};
		service.submit(runnable);
		
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeFromCache(Plugin plugin) {
		if (plugin == null) {
			throw new IllegalArgumentException("Plugin may not be null");
		} else if (!cacheList.containsKey(plugin)) {
			return;
		}
		cacheList.get(plugin.getName()).clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeFromCache(Plugin plugin, String file) {
		if (plugin == null) {
			throw new IllegalArgumentException("Parameters may not be null");
		} else if (!cacheList.containsKey(plugin)) {
			return;
		}
		resourceList.remove(cacheList.get(plugin).remove(file));
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
