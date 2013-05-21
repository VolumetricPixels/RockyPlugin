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

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.material.MaterialTab;
import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.material.WeaponRange;
import com.volumetricpixels.rockyapi.material.WeaponType;
import com.volumetricpixels.rockyapi.resource.AddonPack;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public class GenericWeaponRange extends GenericWeapon implements WeaponRange {

	private String shootSound = "random.bow";

	/**
	 * 
	 */
	public GenericWeaponRange() {
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public GenericWeaponRange(Plugin plugin, String name, Texture texture) {
		super(plugin, name, texture);

		setDefaultId(261);
		setStackable(false);
		setCreativeTab(MaterialTab.COMBAT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTypeId() {
		return 5;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getShootSound() {
		return shootSound;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WeaponRange setShootSound(String sound) {
		this.shootSound = sound;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material loadPreInitialization(Plugin plugin,
			ConfigurationSection section, AddonPack pack) {
		super.loadPreInitialization(plugin, section, pack);

		this.shootSound = section.getString("ShootSound", "random.bow");
		this.setBlockFlag(false);
		this.setType(WeaponType.RANGE);
		setCreativeTab(MaterialTab.valueOf(section.getString("CreativeTab",
				"COMBAT")));

		return this;
	}

}
