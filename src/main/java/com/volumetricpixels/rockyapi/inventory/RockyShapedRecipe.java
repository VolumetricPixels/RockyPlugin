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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * 
 */
public class RockyShapedRecipe implements Recipe {
	private ItemStack output;
	private String[] rows;
	private Map<Character, Integer> ingredients = new HashMap<Character, Integer>();

	/**
	 * 
	 * @param result
	 */
	public RockyShapedRecipe(ItemStack result) {
		this.output = result;
	}

	/**
	 * 
	 * @param shape
	 * @return
	 */
	public RockyShapedRecipe shape(String... shape) {
		if (shape == null || shape.length > 3 || shape.length < 1) {
			throw new IllegalArgumentException(
					"Crafting recipes should be 1, 2, or 3 rows.");
		}
		for (String row : shape) {
			if (row == null || row.length() > 3 || row.length() < 1) {
				throw new IllegalArgumentException(
						"Crafting rows should be 1, 2, or 3 characters.");
			}
		}
		this.rows = shape;
		Map<Character, Integer> ingredientsTemp = this.ingredients;

		this.ingredients = new HashMap<Character, Integer>();
		for (char key : ingredientsTemp.keySet()) {
			try {
				setIngredient(key, ingredientsTemp.get(key));
			} catch (IllegalArgumentException e) {
			}
		}
		return this;
	}

	/**
	 * 
	 * @param key
	 * @param ingredient
	 * @return
	 */
	public RockyShapedRecipe setIngredient(char key, Integer ingredient) {
		if (!hasKey(key)) {
			throw new IllegalArgumentException("Symbol " + key
					+ " does not appear in the shape.");
		}
		ingredients.put(key, ingredient);
		return this;
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private boolean hasKey(char c) {
		String key = Character.toString(c);

		for (String row : rows) {
			if (row.contains(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public Map<Character, Integer> getIngredientMap() {
		return ingredients;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getShape() {
		return rows;
	}

	/**
	 * 
	 */
	public ItemStack getResult() {
		return output;
	}
}
