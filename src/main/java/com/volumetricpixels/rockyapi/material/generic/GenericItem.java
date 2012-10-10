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
package com.volumetricpixels.rockyapi.material.generic;

import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.material.Item;
import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.material.MaterialType;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public class GenericItem implements Item {

	private String name;
	private int itemID;
	private Plugin plugin;
	private Texture texture;
	private boolean isFuel;
	private boolean isStackable;

	/**
	 * 
	 */
	public GenericItem() {
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 */
	public GenericItem(Plugin plugin, String name) {
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public GenericItem(Plugin plugin, String name, Texture texture) {
		if (texture == null) {
			throw new IllegalArgumentException(
					"SpoutItem Texture cannot be null");
		}
		this.plugin = plugin;
		this.name = name;
		this.texture = texture;
		this.itemID = RockyManager.getMaterialManager().getRegisteredName(
				plugin.getName() + "_" + name, MaterialType.ITEM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTypeId() {	
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return itemID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item setAllowToBurn(boolean isFuel) {
		this.isFuel = isFuel;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAllowToBurn() {
		return isFuel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture getTexture() {
		return this.texture;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item setStackable(boolean stackable) {
		this.isStackable = stackable;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isStackable() {
		return isStackable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material load(Plugin plugin, ConfigurationSection section) {
		this.plugin = plugin;
		this.name = section.getString("Name", "Undefined");
		this.itemID = RockyManager.getMaterialManager().getRegisteredName(
				plugin.getName() + "_" + name, MaterialType.ITEM);
		this.isFuel = section.getBoolean("IsFuel", false);
		this.isStackable = section.getBoolean("IsStackable", true);

		List<String> data = section.getStringList("Texture");
		if (data.size() == 1) {
			this.texture = new Texture(plugin, data.get(0));
		} else if (data.size() == 3) {
			this.texture = new Texture(plugin, data.get(0),
					Integer.valueOf(data.get(1)), Integer.valueOf(data.get(2)));
		} else if (data.size() == 5) {
			this.texture = new Texture(data.get(0),
					Integer.valueOf(data.get(1)), Integer.valueOf(data.get(2)),
					Integer.valueOf(data.get(3)), Integer.valueOf(data.get(4)));
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToPacket(PacketOutputStream out) throws IOException {
		out.writeByte(getTypeId());
		out.writeUTF(name);
		out.writeShort(itemID);
		out.writeBoolean(isStackable);
		texture.writeToPacket(out);
	}
	
	
}
