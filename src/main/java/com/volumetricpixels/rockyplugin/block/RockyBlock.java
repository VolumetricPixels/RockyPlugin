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
package com.volumetricpixels.rockyplugin.block;

import net.minecraft.server.v1_5_R3.Block;
import net.minecraft.server.v1_5_R3.StepSound;

import org.fest.reflect.core.Reflection;

import com.volumetricpixels.rockyapi.block.design.BoundingBox;
import com.volumetricpixels.rockyapi.material.Material;

/**
 * 
 */
public class RockyBlock extends Block {

	/**
	 * 
	 * @param material
	 */
	public RockyBlock(Material material) {
		this((com.volumetricpixels.rockyapi.material.Block) material);
	}

	/**
	 * 
	 * @param material
	 */
	public RockyBlock(com.volumetricpixels.rockyapi.material.Block material) {
		super(material.getId(), material.getMaterial());

		Reflection.field("frictionFactor").ofType(float.class).in(this)
				.set(material.getFriction());
		Reflection.field("strength").ofType(float.class).in(this)
				.set(material.getHardness());

		BoundingBox bb = material.getBlockDesign().getBoundingBox();
		Reflection
				.method("a")
				.withParameterTypes(float.class, float.class, float.class,
						float.class, float.class, float.class)
				.in(this)
				.invoke(bb.getX(), bb.getY(), bb.getZ(), bb.getX2(),
						bb.getY2(), bb.getZ2());
		Reflection.method("a").withParameterTypes(float.class).in(this)
				.invoke(material.getLightLevel());
		Reflection.field("stepSound").ofType(StepSound.class).in(this)
				.set(new StepSound(material.getStepSound(), 1.0f, 1.0f));
	}
}
