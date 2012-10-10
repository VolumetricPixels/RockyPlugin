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
package com.volumetricpixels.rockyapi.packet.protocol;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import com.volumetricpixels.rockyapi.gui.Widget;
import com.volumetricpixels.rockyapi.packet.Packet;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.packet.PacketType;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

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
	public void handle(RockyPlayer player) {
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void failure(RockyPlayer player) {
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
