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

import org.bukkit.inventory.ItemStack;

/**
 * 
 */
public class RockyFurnaceRecipe {
	private int ingredient;
	private ItemStack result;
	private double speed;

	/**
	 * 
	 * @param ingredient
	 * @param result
	 */
	public RockyFurnaceRecipe(int ingredient, ItemStack result, double speed) {
		this.ingredient = ingredient;
		this.result = result;
		this.speed = speed;
	}

	/**
	 * 
	 * @return
	 */
	public int getIngredient() {
		return ingredient;
	}

	/**
	 * 
	 * @return
	 */
	public ItemStack getResult() {
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public double getSpeed() {
		return speed;
	}

}