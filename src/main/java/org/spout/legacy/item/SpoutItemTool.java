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
package org.spout.legacy.item;

import org.fest.reflect.core.Reflection;
import org.spout.legacyapi.material.Tool;

import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemTool;

/**
 * 
 */
public class SpoutItemTool extends ItemTool implements SpoutItemType {

	private Tool item;

	/**
	 * 
	 * @param arg0
	 * @param item
	 */
	public SpoutItemTool(int arg0, Tool item) {
		super(arg0 - 256, 0,
				EnumToolMaterial.DIAMOND, null);
		this.item = item;

		Reflection.field("bY").ofType(int.class).in(this)
				.set(item.getDamage());
		Reflection.field("maxStackSize").ofType(int.class).in(this)
				.set(item.isStackable() ? 64 : 1);
		setMaxDurability(item.getDurability());
		Reflection.field("name").ofType(String.class).in(this)
				.set("name." + item.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getDestroySpeed(ItemStack itemstack, Block block) {
		return item.getDestroySpeed(block.id);
	}
}
