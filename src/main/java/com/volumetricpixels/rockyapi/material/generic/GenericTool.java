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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.material.Tool;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.resource.AddonPack;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public class GenericTool extends GenericItem implements Tool {

	private int durability;
	private Map<Integer, Float> blockSpeedMap = new HashMap<Integer, Float>();
	private int damage;

	/**
	 * 
	 */
	public GenericTool() {
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public GenericTool(Plugin plugin, String name, Texture texture) {
		super(plugin, name, texture);

		setStackable(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTypeId() {
		return 2;
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
	public Material loadPreInitialization(Plugin plugin,
			ConfigurationSection section, AddonPack pack) {
		super.loadPreInitialization(plugin, section, pack);

		this.durability = section.getInt("Durability", 100);
		this.damage = section.getInt("Damage", 1);
		setStackable(false);

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material loadPostInitialization(Plugin plugin,
			ConfigurationSection section, AddonPack pack) {
		List<String> blockModifier = section.getStringList("BlockModifier");
		if (blockModifier != null) {
			for (String modifier : blockModifier) {
				String[] split = modifier.split(":");

				setDestroySpeed(Integer.valueOf(split[0]),
						Float.valueOf(split[1]));
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToPacket(PacketOutputStream out) throws IOException {
		super.writeToPacket(out);

		out.writeShort(blockSpeedMap.size());
		for (Entry<Integer, Float> entry : blockSpeedMap.entrySet()) {
			out.writeShort(entry.getKey());
			out.writeFloat(entry.getValue());
		}
		out.writeShort(durability);
	}

}
