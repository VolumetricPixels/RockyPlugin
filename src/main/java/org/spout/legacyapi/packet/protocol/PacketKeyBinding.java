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
import java.util.UUID;

import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.keyboard.KeyBinding;
import org.spout.legacyapi.keyboard.Keyboard;
import org.spout.legacyapi.packet.Packet;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;
import org.spout.legacyapi.packet.PacketType;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 * @author Administrador
 * 
 */
public class PacketKeyBinding implements Packet {
	private KeyBinding binding;
	private Keyboard key;
	private String id;
	private String plugin;
	private boolean pressed;
	private int screen;
	private UUID uniqueId;

	/**
	 * 
	 */
	public PacketKeyBinding() {
	}

	/**
	 * 
	 * @param binding
	 */
	public PacketKeyBinding(KeyBinding binding) {
		this.binding = binding;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		key = Keyboard.getKey(input.readInt());
		pressed = input.readBoolean();
		uniqueId = new UUID(input.readLong(), input.readLong());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		output.writeUTF(binding.getId());
		output.writeUTF(binding.getDescription());
		output.writeUTF(binding.getPlugin().getDescription().getName());
		output.writeInt(binding.getDefaultKey().getKeyCode());
		output.writeLong(binding.getUniqueId().getMostSignificantBits());
		output.writeLong(binding.getUniqueId().getLeastSignificantBits());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(SpoutPlayer player) {
		SpoutManager.getKeyBindingManager().summonKey(uniqueId, player, key,
				pressed);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void failure(SpoutPlayer player) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PacketType getType() {
		return PacketType.PacketKeyBinding;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public String getBindingId() {
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public String getPlugin() {
		return plugin;
	}

	/**
	 * 
	 * @return
	 */
	public int getScreen() {
		return screen;
	}
}