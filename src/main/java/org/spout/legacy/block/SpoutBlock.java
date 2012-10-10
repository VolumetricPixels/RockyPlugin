/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2012-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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
package org.spout.legacy.block;

import org.fest.reflect.core.Reflection;

import net.minecraft.server.Block;

/**
 * 
 */
public class SpoutBlock extends Block {

	/**
	 * 
	 * @param id
	 * @param material
	 */
	public SpoutBlock(int id, org.spout.legacyapi.material.Block material) {
		super(id, material.getMaterial());

		Reflection.field("frictionFactor").ofType(float.class).in(this)
				.set(material.getFriction());
		Reflection.field("co").ofType(float.class).in(this)
				.set(material.getHardness());
	}

}