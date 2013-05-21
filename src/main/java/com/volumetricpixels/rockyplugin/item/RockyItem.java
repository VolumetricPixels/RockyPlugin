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

import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.Item;
import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.World;

import org.fest.reflect.core.Reflection;

import com.volumetricpixels.rockyapi.material.Material;

/**
 * 
 */
public class RockyItem extends Item {

	private boolean isThrowable;

	/**
	 * 
	 * @param material
	 */
	public RockyItem(Material material) {
		this((com.volumetricpixels.rockyapi.material.Item) material);
	}

	/**
	 * 
	 * @param item
	 */
	public RockyItem(com.volumetricpixels.rockyapi.material.Item item) {
		super(item.getId() - 256);

		Reflection.field("maxStackSize").ofType(int.class).in(this)
				.set(item.isStackable() ? 64 : 1);
		Reflection.field("name").ofType(String.class).in(this)
				.set("name." + item.getName());
		isThrowable = item.isThrowable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
		if (!isThrowable) {
			return itemstack;
		}

		if (!entityhuman.abilities.canInstantlyBuild) {
			--itemstack.count;
		}
		
		// TODO: Add the entity when we use the throwable

		return itemstack;
	}
}
