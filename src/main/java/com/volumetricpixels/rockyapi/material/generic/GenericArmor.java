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

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.material.Armor;
import com.volumetricpixels.rockyapi.material.ArmorModel;
import com.volumetricpixels.rockyapi.material.ArmorType;
import com.volumetricpixels.rockyapi.material.MaterialTab;
import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.resource.AddonPack;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public class GenericArmor extends GenericItem implements Armor {

	private int durability;
	private int defense;
	private ArmorType type;
	private Texture[] modelTexture;

	/**
	 * 
	 */
	public GenericArmor() {
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public GenericArmor(Plugin plugin, String name, Texture texture,
			Texture frontModel, Texture backModel) {
		super(plugin, name, texture);

		if (frontModel == null || backModel == null) {
			throw new IllegalArgumentException("Invalid model textures");
		}
		this.modelTexture = new Texture[] { frontModel, backModel };

		setDefaultId(298);
		setStackable(false);
		setCreativeTab(MaterialTab.COMBAT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTypeId() {
		return 4;
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
	public Texture getModelTexture(ArmorModel model) {
		return (model == ArmorModel.FRONT_MODEL ? modelTexture[0]
				: modelTexture[1]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material loadPreInitialization(Plugin plugin,
			ConfigurationSection section, AddonPack pack) {
		super.loadPreInitialization(plugin, section, pack);

		this.durability = section.getInt("Durability", 100);
		this.defense = section.getInt("Defense", 1);
		this.type = ArmorType.valueOf(section.getString("SubType"));

		String textureFile = section.getString("Model");
		if (!pack.hasEntry(textureFile + "_1.png")
				|| !pack.hasEntry(textureFile + "_2.png")) {
			throw new IllegalArgumentException("The armor model is missing");
		}

		this.modelTexture = new Texture[2];
		this.modelTexture[0] = new Texture(plugin, textureFile + "_1.png",
				pack.getInputStream(textureFile + "_1.png"));
		this.modelTexture[1] = new Texture(plugin, textureFile + "_2.png",
				pack.getInputStream(textureFile + "_2.png"));
		this.setStackable(false);
		setCreativeTab(MaterialTab.valueOf(section.getString("CreativeTab",
				"COMBAT")));

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToPacket(PacketOutputStream out) throws IOException {
		super.writeToPacket(out);

		out.writeShort(durability);
		out.writeByte(type.ordinal());
		out.writeUTFArray(modelTexture[0].getName(), modelTexture[1].getName());
	}
}
