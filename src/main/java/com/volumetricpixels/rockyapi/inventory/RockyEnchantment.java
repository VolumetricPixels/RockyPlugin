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
package com.volumetricpixels.rockyapi.inventory;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.fest.reflect.core.Reflection;

public class RockyEnchantment extends Enchantment {
	/**
	 * Default colored enchantment
	 */
	public final static RockyEnchantment COLORED_ENCHANTMENT = new RockyEnchantment(
			256);

	/**
	 * 
	 * @param id
	 */
	public RockyEnchantment(int id) {
		super(id);

		boolean val = Enchantment.isAcceptingRegistrations();
		Reflection.staticField("acceptingNew").ofType(boolean.class)
				.in(Enchantment.class).set(true);
		Enchantment.registerEnchantment(this);
		Reflection.staticField("acceptingNew").ofType(boolean.class)
				.in(Enchantment.class).set(val);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxLevel() {
		return 256;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getStartLevel() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ALL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean conflictsWith(Enchantment paramEnchantment) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canEnchantItem(ItemStack paramItemStack) {
		return true;
	}
}
