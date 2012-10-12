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
import java.util.UUID;

import com.volumetricpixels.rockyapi.gui.GenericComboBox;
import com.volumetricpixels.rockyapi.gui.Widget;
import com.volumetricpixels.rockyapi.packet.Packet;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.packet.PacketType;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class PacketWidgetComboBox implements Packet {
	private UUID uuid;
	private boolean open;
	private int selection;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		uuid = input.readUUID();
		open = input.readBoolean();
		selection = input.readInt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(RockyPlayer player) {
		Widget w = null;
		if (player.getCurrentScreen() != null)
			w = player.getCurrentScreen().getWidget(uuid);
		if (w == null)
			w = player.getMainScreen().getWidget(uuid);
		if (w == null && player.getMainScreen().getActivePopup() != null)
			w = player.getMainScreen().getActivePopup().getWidget(uuid);
		if (w == null || !(w instanceof GenericComboBox))
			return;
		GenericComboBox box = (GenericComboBox) w;
		box.setOpen(open);
		box.setSelection(selection);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void failure(RockyPlayer player) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PacketType getType() {
		return PacketType.PacketWidgetComboBox;
	}

}
