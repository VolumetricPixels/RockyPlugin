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
package org.spout.legacyapi.material.generic;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.material.Item;
import org.spout.legacyapi.material.Material;
import org.spout.legacyapi.resource.Texture;

/**
 * 
 */
public class SpoutItem implements Item {

	private String name;
	private int itemID;
	private Plugin plugin;
	private Texture texture;
	private boolean isFuel;
	private boolean isStackable;

	/**
	 * 
	 */
	public SpoutItem() {	
	}
	
	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public SpoutItem(Plugin plugin, String name, Texture texture) {
		if (texture == null) {
			throw new IllegalArgumentException("SpoutItem Texture cannot be null");
		}
		this.plugin = plugin;
		this.name = name;
		this.texture = texture;
		this.itemID = SpoutManager.getMaterialManager().getRegisteredName(
				plugin.getName() + "_" + name);
		SpoutManager.getResourceManager().addToCache(plugin,
				texture.getName());
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
		this.itemID = SpoutManager.getMaterialManager().getRegisteredName(
				plugin.getName() + "_" + name);
		this.isFuel = section.getBoolean("IsFuel", false);
		this.isStackable = section.getBoolean("IsStackable", true);
		//TODO: texture
		return this;
	}

}
