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
package org.spout.legacyapi.packet.protocol;

import java.io.IOException;

import org.bukkit.plugin.Plugin;
import org.spout.legacyapi.packet.Packet;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;
import org.spout.legacyapi.packet.PacketType;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class PacketFileCache<T> implements Packet {

	private String pluginName;
	private boolean isLocal;
	private T file;

	/**
	 * 
	 * @param plugin
	 * @param file
	 */
	public PacketFileCache(Plugin plugin, T file) {
		pluginName = plugin.getName();

		isLocal = !(file instanceof String);
		this.file = file;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		// Handle in client-side
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		output.writeUTF(pluginName);
		output.writeBoolean(isLocal);
		if (isLocal)
			output.writeUTF((String) file);
		else
			output.write((byte[]) file);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(SpoutPlayer player) {
		// Handle in client-side
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void failure(SpoutPlayer player) {
		// Handle in client-side
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return 0;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public PacketType getType() {
		return PacketType.PacketFileCache;
	}
}
