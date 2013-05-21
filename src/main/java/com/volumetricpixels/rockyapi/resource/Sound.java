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

import java.io.IOException;
import java.io.InputStream;

import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;

/**
 * 
 */
public class Sound implements Resource {

	private String name;
	private Plugin plugin;
	private long revision;
	private byte[] data;

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param in
	 */
	public Sound(Plugin plugin, String name, InputStream in) {
		this.plugin = plugin;
		this.name = name;
		try {
			this.data = new byte[in.available()];
			in.read((byte[]) data);
		} catch (IOException e) {
			return;
		}
		RockyManager.getResourceManager().addResource(plugin, this);
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 */
	public Sound(Plugin plugin, String name) {
		this.plugin = plugin;
		this.name = name;
		RockyManager.getResourceManager().addResource(plugin, this);
	}

	/**
	 * 
	 * @return
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public long getRevision() {
		return revision;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void setRevision(long revision) {
		this.revision = revision;
	}

	/**
	 * {@inhericDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public byte[] getData() {
		return data;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public <T> void setData(T data) {
		this.data = (byte[]) data;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void writeToPacket(PacketOutputStream out) throws IOException {
	}

}
