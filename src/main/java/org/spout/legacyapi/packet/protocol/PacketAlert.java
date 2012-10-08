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

import org.spout.legacyapi.packet.Packet;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;
import org.spout.legacyapi.packet.PacketType;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class PacketAlert implements Packet {

	private String title, message;
	private short id, data, time;

	/**
	 * 
	 * @param title
	 * @param message
	 * @param id
	 */
	public PacketAlert(String title, String message, short id, short data, short time) {
		this.title = title;
		this.message = message;
		this.id = id;
		this.data = data;
		this.time = time;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		// Handle in client-side
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		output.writeUTF(title);
		output.writeUTF(message);
		output.writeShort(id);
		output.writeShort(data);
		output.writeShort(time);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void handle(SpoutPlayer player) {
		// Handle in client-side
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void failure(SpoutPlayer player) {
		// Handle in client-side
	}

	/**
	 * {@inhericDoc}
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
		return PacketType.PacketAlert;
	}

}
