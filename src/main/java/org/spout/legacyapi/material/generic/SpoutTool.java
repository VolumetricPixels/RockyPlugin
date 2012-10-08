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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.spout.legacyapi.material.Material;
import org.spout.legacyapi.material.Tool;
import org.spout.legacyapi.resource.Texture;

/**
 * 
 */
public class SpoutTool extends SpoutItem implements Tool {

	private int durability;
	private Map<Integer, Float> blockSpeedMap = new HashMap<Integer, Float>();
	private int damage;

	/**
	 * 
	 */
	public SpoutTool() {
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public SpoutTool(Plugin plugin, String name, Texture texture) {
		super(plugin, name, texture);

		setStackable(false);
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
	public Tool setDurability(int durability) {
		this.durability = durability;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tool setDestroySpeed(int blockTypeId, float destroySpeed) {
		blockSpeedMap.put(blockTypeId, destroySpeed);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getDestroySpeed(int blockTypeId) {
		return (blockSpeedMap.containsKey(blockTypeId) ? blockSpeedMap
				.get(blockTypeId) : 1.0f);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Float> getDestroyMap() {
		return blockSpeedMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDamage() {
		return damage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tool setDamage(int damage) {
		this.damage = damage;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material load(Plugin plugin, ConfigurationSection section) {
		super.load(plugin, section);

		this.durability = section.getInt("Durability", 100);
		this.damage = section.getInt("Damage", 1);

		List<String> blockModifier = section.getStringList("BlockModifier");
		for (String modifier : blockModifier) {
			String[] split = modifier.split(":");

			setDestroySpeed(Integer.valueOf(split[0]), Float.valueOf(split[1]));
		}

		setStackable(false);
		return this;
	}

}
