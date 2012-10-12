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

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.keyboard.KeyBinding;
import com.volumetricpixels.rockyapi.keyboard.Keyboard;
import com.volumetricpixels.rockyapi.packet.Packet;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.packet.PacketType;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 * @author Administrador
 * 
 */
public class PacketKeyBinding implements Packet {
	private KeyBinding binding;
	private Keyboard key;
	private String id;
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
	public void handle(RockyPlayer player) {
		RockyManager.getKeyBindingManager().summonKey(uniqueId, player, key,
				pressed);
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
		return PacketType.PacketKeyBinding;
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
	public int getScreen() {
		return screen;
	}
}