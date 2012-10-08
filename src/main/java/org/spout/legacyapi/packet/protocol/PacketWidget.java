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
package org.spout.legacyapi.packet.protocol;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.spout.legacyapi.gui.Widget;
import org.spout.legacyapi.packet.Packet;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;
import org.spout.legacyapi.packet.PacketType;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class PacketWidget implements Packet {
	protected Widget widget;
	protected UUID screen;

	/**
	 * 
	 * @param widget
	 * @param screen
	 */
	public PacketWidget(Widget widget, UUID screen) {
		this.widget = widget;
		this.screen = screen;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		output.writeInt(widget.getType().getId());
		output.writeUUID(screen);
		output.writeUUID(widget.getId());

		PacketOutputStream data = new PacketOutputStream();
		widget.writeData(data);
		ByteBuffer buffer = data.getRawBuffer();
		byte[] widgetData = new byte[buffer.capacity() - buffer.remaining()];
		System.arraycopy(buffer.array(), 0, widgetData, 0, widgetData.length);

		output.writeInt(widgetData.length);
		output.writeShort((short) widget.getVersion());
		output.write(widgetData);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void handle(SpoutPlayer player) {
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void failure(SpoutPlayer player) {
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public PacketType getType() {
		return PacketType.PacketWidget;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public int getVersion() {
		return 2;
	}

}
