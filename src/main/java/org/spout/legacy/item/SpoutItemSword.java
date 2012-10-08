/*
 * This file is part of SpoutPlugin.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutPlugin is licensed under the GNU Lesser General Public License.
 *
 * SpoutPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spout.legacy.item;

import org.fest.reflect.core.Reflection;

import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemSword;

/**
 * 
 */
public class SpoutItemSword extends ItemSword implements SpoutItemType {

	/**
	 * 
	 * @param arg0
	 * @param item
	 */
	public SpoutItemSword(int arg0, org.spout.legacyapi.material.Weapon item) {
		super(arg0 - 256, EnumToolMaterial.DIAMOND);

		Reflection.field("maxStackSize").ofType(int.class).in(this)
				.set(item.isStackable() ? 64 : 1);
		Reflection.field("durability").ofType(int.class).in(this)
				.set((int) item.getDurability());
		Reflection.field("name").ofType(String.class).in(this)
				.set("name." + item.getName());
		Reflection.field("damage").ofType(int.class).in(this)
				.set((int) item.getDamage());
	}

}
