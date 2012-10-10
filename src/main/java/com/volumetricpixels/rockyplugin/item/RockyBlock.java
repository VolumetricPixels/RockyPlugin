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
package com.volumetricpixels.rockyplugin.item;

import org.fest.reflect.core.Reflection;

import net.minecraft.server.Block;

/**
 * 
 */
public class RockyBlock extends Block {

	/**
	 * 
	 * @param id
	 * @param material
	 */
	public RockyBlock(int id, com.volumetricpixels.rockyapi.material.Block material) {
		super(id, material.getMaterial());

		Reflection.field("frictionFactor").ofType(float.class).in(this)
				.set(material.getFriction());
		Reflection.field("co").ofType(float.class).in(this)
				.set(material.getHardness());
	}

}
