/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2011-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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

import java.util.Map;

import net.minecraft.server.CraftingManager;
import net.minecraft.server.ItemStack;
import net.minecraft.server.RecipesFurnace;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

/**
 * 
 */
public class RockyRecipeManager {

	/**
	 * 
	 * @param recipe
	 */
	public static void addToCraftingManager(RockyShapedRecipe recipe) {
		String[] shapeList = recipe.getShape();
		Map<Character, Integer> ingredient = recipe.getIngredientMap();
		int lenght = (shapeList.length) + ingredient.size() * 2;
		Object[] data = new Object[lenght];
		int i = 0;

		for (; i < shapeList.length; i++) {
			data[i] = shapeList[i];
		}
		for (Character character : ingredient.keySet()) {
			Integer material = ingredient.get(character);

			data[i++] = character;
			data[i++] = new CraftItemStack(material, 1).getHandle();
		}
		CraftingManager.getInstance().registerShapedRecipe(
				new CraftItemStack(recipe.getResult()).getHandle(), data);
	}

	/**
	 * 
	 * @param recipe
	 */
	public static void addToCraftingManager(RockyShapelessRecipe recipe) {
		Material[] array = recipe.getIngredientList().toArray(new Material[0]);
		Object[] stackArray = new ItemStack[array.length];
		for (int i = 0; i < array.length; i++) {
			stackArray[i] = new CraftItemStack(array[i].getId(), 1).getHandle();
		}
		CraftingManager.getInstance().registerShapelessRecipe(
				new CraftItemStack(recipe.getResult()).getHandle(), stackArray);
	}

	/**
	 * 
	 * @param recipe
	 */
	public static void addToFurnaceManager(RockyFurnaceRecipe recipe) {
		RecipesFurnace.getInstance().registerRecipe(
				recipe.getIngredient(),
				new CraftItemStack(recipe.getResult()).getHandle(),
				recipe.getSpeed());
	}

}
