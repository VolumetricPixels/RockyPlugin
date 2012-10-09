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
package org.spout.legacyapi.inventory;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.fest.reflect.core.Reflection;

/**
 * 
 */
public class SpoutEnchantment extends Enchantment {
	/**
	 * 
	 * @param id
	 */
	public SpoutEnchantment(int id) {
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
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
	public int getMaxLevel() {
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "SpoutEnchantment[" + getId() + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getStartLevel() {
		return 0;
	}

	/**
	 * 
	 * @param enchantment
	 */
	public final static void addEnchantment(Enchantment enchantment) {
		Reflection.staticField("acceptingNew").ofType(Boolean.class)
				.in(Enchantment.class).set(true);
		Enchantment.registerEnchantment(enchantment);
		Reflection.staticField("acceptingNew").ofType(Boolean.class)
				.in(Enchantment.class).set(false);
	}
}
