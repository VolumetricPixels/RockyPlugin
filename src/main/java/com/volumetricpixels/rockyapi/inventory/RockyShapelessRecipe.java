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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * 
 */
public class RockyShapelessRecipe implements Recipe {
	private ItemStack output;
	private List<Integer> ingredients = new ArrayList<Integer>();

	/**
	 * 
	 * @param result
	 */
	public RockyShapelessRecipe(ItemStack result) {
		this.output = result;
	}

	/**
	 * 
	 * @param ingredient
	 * @return
	 */
	public RockyShapelessRecipe addIngredient(int ingredient) {
		return addIngredient(1, ingredient);
	}

	/**
	 * 
	 * @param count
	 * @param ingredient
	 * @return
	 */
	public RockyShapelessRecipe addIngredient(int count, int ingredient) {
		if (ingredients.size() + count > 9) {
			throw new IllegalArgumentException(
					"Shapeless recipes cannot have more than 9 ingredients");
		}
		for(int i = 0; i < count; i++) {
			ingredients.add(ingredient);
		}
		return this;
	}

	/**
	 * 
	 * @param ingredient
	 * @return
	 */
	public RockyShapelessRecipe removeIngredient(int ingredient) {
		this.ingredients.remove(ingredient);
		return this;
	}

	/**
	 * 
	 */
	public ItemStack getResult() {
		return output;
	}

	/**
	 * 
	 * @return
	 */
	public List<Integer> getIngredientList() {
		return ingredients;
	}
}
