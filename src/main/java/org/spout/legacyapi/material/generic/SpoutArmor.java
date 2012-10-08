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
package org.spout.legacyapi.material.generic;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.material.Armor;
import org.spout.legacyapi.material.ArmorModel;
import org.spout.legacyapi.material.ArmorType;
import org.spout.legacyapi.material.Material;
import org.spout.legacyapi.resource.Texture;

/**
 * 
 */
public class SpoutArmor extends SpoutItem implements Armor {

	private int durability;
	private int defense;
	private ArmorType type;
	private String modelTextureFile;
	
	/**
	 * 
	 */
	public SpoutArmor() {	
	}
	
	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public SpoutArmor(Plugin plugin, String name, Texture texture,
			String modelTextureFile) {
		super(plugin, name, texture);

		this.modelTextureFile = modelTextureFile;

		SpoutManager.getResourceManager().addToCache(plugin,
				getModelTexture(ArmorModel.FRONT_MODEL));
		SpoutManager.getResourceManager().addToCache(plugin,
				getModelTexture(ArmorModel.BACK_MODEL));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Armor setDurability(int durability) {
		this.durability = durability;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDurability() {
		return durability;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDefense() {
		return defense;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Armor setDefense(int defense) {
		this.defense = defense;
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArmorType getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Armor setType(ArmorType type) {
		this.type = type;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getModelTexture(ArmorModel model) {
		return (model == ArmorModel.FRONT_MODEL ? modelTextureFile + "_1.png"
				: modelTextureFile + "_2.png");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material load(Plugin plugin, ConfigurationSection section) {
		super.load(plugin, section);
		
		this.durability = section.getInt("Durability", 100);
		this.defense = section.getInt("Defense", 1);
		this.type = ArmorType.valueOf(section.getString("Type"));
		this.modelTextureFile = section.getString("Model");

		SpoutManager.getResourceManager().addToCache(plugin,
				getModelTexture(ArmorModel.FRONT_MODEL));
		SpoutManager.getResourceManager().addToCache(plugin,
				getModelTexture(ArmorModel.BACK_MODEL));
		
		return this;
	}
}
