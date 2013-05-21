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
package com.volumetricpixels.rockyapi.material.generic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.material.Item;
import com.volumetricpixels.rockyapi.material.MaterialTab;
import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.material.MaterialEnumType;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.resource.AddonPack;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public class GenericItem implements Item {

	protected String name;
	protected int itemID;
	protected Plugin plugin;
	protected Texture texture;
	protected boolean isFuel;
	protected boolean isStackable;
	protected boolean isThrowable;
	protected MaterialTab creativeTab = MaterialTab.CUSTOM_ITEM;
	protected int defaultId = 318;
	protected List<String> loreArray = new LinkedList<String>();
	
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
		this.plugin = plugin;
		this.name = name;
		this.itemID = RockyManager.getMaterialManager().getRegisteredName(name,
				MaterialEnumType.ITEM);
		RockyManager.getMaterialManager().addMaterial(this);
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
		this.itemID = RockyManager.getMaterialManager().getRegisteredName(name,
				MaterialEnumType.ITEM);
		RockyManager.getMaterialManager().addMaterial(this);
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
	public int getDefaultId() {
		return defaultId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDefaultId(int id) {
		defaultId = id;
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
	public Material loadPreInitialization(Plugin plugin,
			ConfigurationSection section, AddonPack pack) {
		this.plugin = plugin;
		this.name = section.getString("Title", "Undefined");
		this.itemID = RockyManager.getMaterialManager().getRegisteredName(name,
				MaterialEnumType.ITEM);
		this.isFuel = section.getBoolean("BurnTime", false);
		this.isStackable = section.getBoolean("Stackable", true);
		this.isThrowable = section.getBoolean("Throwable", false);
		this.defaultId = section.getInt("DefaultId", defaultId);
	
		List<String> data = section.getStringList("Texture");
		if (data != null) {
			if (data.size() == 1) {
				if (!pack.hasEntry(data.get(0))) {
					throw new IllegalArgumentException(
							"Cannot find texture within package");
				}
				this.texture = new Texture(plugin, data.get(0),
						pack.getInputStream(data.get(0)));
			} else if (data.size() == 3) {
				if (!pack.hasEntry(data.get(0))) {
					throw new IllegalArgumentException(
							"Cannot find texture within package");
				}
				this.texture = new Texture(plugin, data.get(0),
						Integer.valueOf(data.get(1)), Integer.valueOf(data
								.get(2)));
			} else if (data.size() == 5) {
				this.texture = new Texture(data.get(0), Integer.valueOf(data
						.get(1)), Integer.valueOf(data.get(2)),
						Integer.valueOf(data.get(3)), Integer.valueOf(data
								.get(4)));
			}
		}
		loreArray = section.getStringList("Lore");

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material loadPostInitialization(Plugin plugin,
			ConfigurationSection section, AddonPack pack) {
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
		out.writeByte(creativeTab.getId());
		texture.writeToPacket(out);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isThrowable() {
		return isThrowable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item setThrowable(boolean isThrowable) {
		this.isThrowable = isThrowable;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MaterialTab getCreativeTab() {
		return creativeTab;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item setCreativeTab(MaterialTab tab) {
		this.creativeTab = tab;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getLore() {
		return loreArray;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLore(String description) {
		loreArray.add(description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLore(int index) {
		loreArray.remove(index);
	}

}
