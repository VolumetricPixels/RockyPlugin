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
package com.volumetricpixels.rockyplugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.Block;
import net.minecraft.server.Item;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.event.RockyFinishedLoadingEvent;
import com.volumetricpixels.rockyapi.event.RockyLoadingEvent;
import com.volumetricpixels.rockyapi.inventory.RockyFurnaceRecipe;
import com.volumetricpixels.rockyapi.inventory.RockyRecipeManager;
import com.volumetricpixels.rockyapi.inventory.RockyShapedRecipe;
import com.volumetricpixels.rockyapi.inventory.RockyShapelessRecipe;
import com.volumetricpixels.rockyapi.material.Armor;
import com.volumetricpixels.rockyapi.material.Food;
import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.material.MaterialManager;
import com.volumetricpixels.rockyapi.material.MaterialType;
import com.volumetricpixels.rockyapi.material.RangeWeapon;
import com.volumetricpixels.rockyapi.material.Tool;
import com.volumetricpixels.rockyapi.material.Weapon;
import com.volumetricpixels.rockyapi.material.generic.GenericArmor;
import com.volumetricpixels.rockyapi.material.generic.GenericFood;
import com.volumetricpixels.rockyapi.material.generic.GenericRangeWeapon;
import com.volumetricpixels.rockyapi.material.generic.GenericTool;
import com.volumetricpixels.rockyapi.material.generic.GenericWeapon;
import com.volumetricpixels.rockyplugin.block.RockyBlock;
import com.volumetricpixels.rockyplugin.item.RockyItem;
import com.volumetricpixels.rockyplugin.item.RockyItemArmor;
import com.volumetricpixels.rockyplugin.item.RockyItemBow;
import com.volumetricpixels.rockyplugin.item.RockyItemFood;
import com.volumetricpixels.rockyplugin.item.RockyItemSword;
import com.volumetricpixels.rockyplugin.item.RockyItemTool;

/**
 * 
 */
public class RockyMaterialManager implements MaterialManager {
	public final static int DEFAULT_ITEM_PLACEHOLDER_ID = 3000;
	public final static int DEFAULT_ITEM_FOR_VANILLA = 318;

	public final static int DEFAULT_BLOCK_PLACEHOLDER_ID = 196;
	public final static int DEFAULT_BLOCK_FOR_VANILLA = 1;

	private final static int MAX_FREE_ITEM_INDEX = 32000 - DEFAULT_ITEM_PLACEHOLDER_ID;
	private final static int MAX_FREE_BLOCK_INDEX = 4096 - DEFAULT_BLOCK_PLACEHOLDER_ID;

	private final static String RECIPE_FILE = "recipe.yml";
	private final static String MATERIAL_FILE = "material.yml";

	private Map<String, Integer> itemNameList = new HashMap<String, Integer>();
	private Map<String, Integer> blockNameList = new HashMap<String, Integer>();

	private Map<Integer, com.volumetricpixels.rockyapi.material.Item> itemList = new HashMap<Integer, com.volumetricpixels.rockyapi.material.Item>();
	private Map<Integer, Material> blockList = new HashMap<Integer, Material>();

	private BitSet itemIndexList = new BitSet(MAX_FREE_ITEM_INDEX);
	private BitSet blockIndexList = new BitSet(MAX_FREE_BLOCK_INDEX);

	private Map<String, Class<? extends Material>> registeredTypes = new HashMap<String, Class<? extends Material>>();

	/**
	 * 
	 */
	public RockyMaterialManager() {
		registerType("Armor", GenericArmor.class);
		registerType("Food", GenericFood.class);
		registerType("Weapon", GenericWeapon.class);
		registerType("Tool", GenericTool.class);
		registerType("RangeWeapon", GenericRangeWeapon.class);
		registerType(
				"Item",
				com.volumetricpixels.rockyapi.material.generic.GenericItem.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerType(String name, Class<? extends Material> clazz) {
		registeredTypes.put(name, clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	public void unregisterType(String name) {
		registeredTypes.remove(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends Material> getType(String name) {
		return registeredTypes.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRegisteredName(String name, MaterialType type) {
		if (type == MaterialType.ITEM && itemNameList.containsKey(name)) {
			return itemNameList.get(name);
		} else if (type == MaterialType.BLOCK
				&& blockNameList.containsKey(name)) {
			return blockNameList.get(name);
		}
		return registerName(name, type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int registerName(String name, MaterialType type) {
		int id = getNextFreeIndex(type);
		registerName(name, id, type);
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerName(String name, int id, MaterialType type) {
		if (type == MaterialType.ITEM) {
			itemNameList.put(name, id);
			itemIndexList.set(id);
			return;
		}
		blockNameList.put(name, id);
		blockIndexList.set(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Integer> getRegisteredNames(MaterialType type) {
		return (type == MaterialType.ITEM ? itemNameList : blockNameList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMaterial(Material material) {
		if (material == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		} else if (material instanceof com.volumetricpixels.rockyapi.material.Item
				&& itemList.containsKey(material.getId())) {
			throw new IllegalArgumentException(
					"Use replace instead for replacing items");
		} else if (material instanceof com.volumetricpixels.rockyapi.material.Block
				&& blockList.containsKey(material.getId())) {
			throw new IllegalArgumentException(
					"Use replace instead for replacing blocks");
		}
		if (material instanceof com.volumetricpixels.rockyapi.material.Item) {
			itemList.put(material.getId(),
					(com.volumetricpixels.rockyapi.material.Item) material);
			Item.byId[material.getId()] = getVanillaType(material.getId(),
					material);
			return;
		}
		blockList.put(material.getId(), material);
		Block.byId[material.getId()] = new RockyBlock(material.getId(),
				(com.volumetricpixels.rockyapi.material.Block) material);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteMaterial(Material material) {
		if (material == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		} else if (material instanceof com.volumetricpixels.rockyapi.material.Item
				&& !itemList.containsKey(material.getId())) {
			throw new IllegalArgumentException(
					"Trying to remove an invalid Material");
		} else if (material instanceof com.volumetricpixels.rockyapi.material.Block
				&& !blockList.containsKey(material.getId())) {
			throw new IllegalArgumentException(
					"Trying to remove an invalid Material");
		}
		if (material instanceof com.volumetricpixels.rockyapi.material.Item) {
			itemList.remove(material.getId());
			Item.byId[material.getId()] = null;
			return;
		}
		blockList.remove(material.getId());
		Block.byId[material.getId()] = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void replaceMaterial(Material source, Material destination) {
		if (source == null || destination == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		} else if (source instanceof com.volumetricpixels.rockyapi.material.Item
				&& !itemList.containsKey(source.getId())) {
			throw new IllegalArgumentException(
					"Trying to replace an invalid Material");
		} else if (source instanceof com.volumetricpixels.rockyapi.material.Block
				&& !blockList.containsKey(source.getId())) {
			throw new IllegalArgumentException(
					"Trying to replace an invalid Material");
		} else if (!source.getClass().equals(destination.getClass())) {
			throw new IllegalArgumentException(
					"Trying to replace two different things");
		}
		if (source instanceof com.volumetricpixels.rockyapi.material.Item) {
			itemList.put(source.getId(),
					(com.volumetricpixels.rockyapi.material.Item) destination);
			Item.byId[source.getId()] = getVanillaType(source.getId(),
					destination);
			return;
		}
		blockList.put(source.getId(), destination);
		Block.byId[source.getId()] = new RockyBlock(source.getId(),
				(com.volumetricpixels.rockyapi.material.Block) destination);
	}

	/**
	 * 
	 * @return
	 */
	private int getNextFreeIndex(MaterialType type) {
		if (type == MaterialType.ITEM) {
			return itemIndexList.nextClearBit(DEFAULT_ITEM_PLACEHOLDER_ID);
		}
		return blockIndexList.nextClearBit(DEFAULT_BLOCK_PLACEHOLDER_ID);
	}

	/**
	 * 
	 * @param material
	 * @return
	 */
	private Item getVanillaType(int id, Material material) {
		if (material instanceof Armor) {
			return new RockyItemArmor(id, (Armor) material);
		} else if (material instanceof Food) {
			return new RockyItemFood(id, (Food) material);
		} else if (material instanceof Tool) {
			return new RockyItemTool(id, (Tool) material);
		} else if (material instanceof RangeWeapon) {
			return new RockyItemBow(id, (RangeWeapon) material);
		} else if (material instanceof Weapon) {
			return new RockyItemSword(id, (Weapon) material);
		} else if (material instanceof Item) {
			return new RockyItem(id,
					(com.volumetricpixels.rockyapi.material.Item) material);
		} 
		throw new IllegalArgumentException(
				"Trying to lookup for an invalid Material class");
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private int getItemID(String data) {
		if (Character.isDigit(data.charAt(0))) {
			return Integer.valueOf(data);
		}
		return itemNameList.get(data);
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onSpoutLoadingEvent(RockyLoadingEvent event) {
		File file = new File(Rocky.getInstance().getDataFolder(), MATERIAL_FILE);
		YamlConfiguration configuration = new YamlConfiguration();
		try {
			configuration.load(file);
		} catch (FileNotFoundException e) {
			try {
				file.createNewFile();
			} catch (Throwable ex) {
			}
			return;
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		Map<String, Object> values = configuration.getValues(false);
		for (String key : values.keySet()) {
			ConfigurationSection section = configuration
					.getConfigurationSection(key);
			Class<? extends Material> clazz = registeredTypes.get(section
					.get("Class"));
			if (clazz == null) {
				RockyManager
						.printConsole(
								"[Warning] trying to load an invalid type of Item {%s}",
								section.get("Class"));
				continue;
			}
			Material material = null;
			try {
				material = clazz.newInstance().load(Rocky.getInstance(),
						section);
			} catch (Throwable e) {
				e.printStackTrace();
				continue;
			}
			addMaterial(material);
		}
		RockyManager.printConsole("%d item has been loaded from '%s'",
				values.size(), MATERIAL_FILE);
	}

	/**
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onSpoutFinishedLoadingEvent(RockyFinishedLoadingEvent event) {
		File file = new File(Rocky.getInstance().getDataFolder(), RECIPE_FILE);
		YamlConfiguration configuration = new YamlConfiguration();
		try {
			configuration.load(file);
		} catch (FileNotFoundException e) {
			try {
				file.createNewFile();
			} catch (Throwable ex) {
			}
			return;
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		List<? extends Map<String, ?>> recipeList = (List<? extends Map<String, ?>>) configuration
				.getList("Recipes");
		if (recipeList == null) {
			return;
		}
		for(int i = 0; i < recipeList.size(); i++) {
			Map<String, ?> recipe = recipeList.get(i);
			String type = (String) recipe.get("Type");
			int amount = recipe.containsKey("Amount") ? (Integer) recipe
					.get("Amount") : 1;
			int result = getItemID((String) recipe.get("Result"));
			if (type.equals("Furnace")) {
				RockyFurnaceRecipe fRecipe = new RockyFurnaceRecipe(
						getItemID((String) recipe.get("Ingredient")),
						new ItemStack(result, amount),
						Float.valueOf((String)recipe.get("BurnSpeed")));
				RockyRecipeManager.addToFurnaceManager(fRecipe);
			} else if (type.equals("Shaped")) {

				Map<String, String> lineMap = (Map<String, String>) recipe
						.get("Line");
				String lA = (lineMap.containsKey("A") ? (String) lineMap
						.get("A") : "   ");
				String lB = (lineMap.containsKey("B") ? (String) lineMap
						.get("B") : "   ");
				String lC = (lineMap.containsKey("C") ? (String) lineMap
						.get("C") : "   ");
				RockyShapedRecipe wRecipe = new RockyShapedRecipe(
						new ItemStack(result, amount));

				Map<String, String> ingredientMap = (Map<String, String>) recipe
						.get("Ingredient");
				for (String key : ingredientMap.keySet()) {
					wRecipe.setIngredient(key.charAt(0),
							getItemID(ingredientMap.get(key)));
				}
				wRecipe.shape(lA, lB, lC);
				RockyRecipeManager.addToCraftingManager(wRecipe);

			} else if (type.equals("Shapeless")) {
				RockyShapelessRecipe wRecipe = new RockyShapelessRecipe(
						new ItemStack(result, amount));
				List<String> ingredientList = (List<String>) recipe
						.get("Ingredient");
				for (String key : ingredientList) {
					wRecipe.addIngredient(getItemID(key));
				}
				RockyRecipeManager.addToCraftingManager(wRecipe);
			}
		}
		RockyManager.printConsole("%d recipes has been loaded from '%s'",
				recipeList.size(), RECIPE_FILE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.volumetricpixels.rockyapi.material.Item[] getItemList() {
		return itemList.values().toArray(
				new com.volumetricpixels.rockyapi.material.Item[0]);
	}
}
