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

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.material.Food;
import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public class GenericFood extends GenericItem implements Food {

	protected float saturation;
	protected int restoration;
	
	/**
	 * 
	 */
	public GenericFood() {	
	}
	
	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public GenericFood(Plugin plugin, String name, Texture texture) {
		super(plugin, name, texture);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTypeId() {	
		return 1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getSaturation() {
		return saturation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRestoration() {
		return restoration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Food setSaturation(float saturation) {
		this.saturation = saturation;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Food setRestoration(int restoration) {
		this.restoration = restoration;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material load(Plugin plugin, ConfigurationSection section) {
		super.load(plugin, section);

		this.saturation = (float) section.getDouble("Saturation", 1.0f);
		this.restoration = section.getInt("Restoration", 1);
		return this;
	}

}
