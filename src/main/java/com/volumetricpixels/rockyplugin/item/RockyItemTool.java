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
package com.volumetricpixels.rockyplugin.item;

import java.util.Map;

import net.minecraft.server.v1_5_R3.Block;
import net.minecraft.server.v1_5_R3.EnumToolMaterial;
import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.ItemTool;

import org.fest.reflect.core.Reflection;

import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.material.Tool;

/**
 * 
 */
public class RockyItemTool extends ItemTool {

	private Map<Integer, Float> destroySpeed;

	/**
	 * 
	 * @param material
	 */
	public RockyItemTool(Material material) {
		this((Tool) material);
	}

	/**
	 * 
	 * @param item
	 */
	public RockyItemTool(Tool item) {
		super(item.getId() - 256, 0, EnumToolMaterial.DIAMOND, null);

		destroySpeed = item.getDestroyMap();

		Reflection.field("maxStackSize").ofType(int.class).in(this)
				.set(item.isStackable() ? 64 : 1);
		setMaxDurability(item.getDurability());
		Reflection.field("name").ofType(String.class).in(this)
				.set("name." + item.getName());
		Reflection.field("co").ofType(int.class).in(this)
				.set((int) item.getDamage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getDestroySpeed(ItemStack itemstack, Block block) {
		return (destroySpeed.containsKey(block.id) ? destroySpeed.get(block.id)
				: 1.0f);
	}

}
