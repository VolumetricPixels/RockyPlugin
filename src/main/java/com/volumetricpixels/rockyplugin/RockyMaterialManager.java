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
import java.util.BitSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
import com.volumetricpixels.rockyapi.material.Block;
import com.volumetricpixels.rockyapi.material.Item;
import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.material.MaterialManager;
import com.volumetricpixels.rockyapi.material.MaterialEnumType;
import com.volumetricpixels.rockyapi.material.generic.GenericArmor;
import com.volumetricpixels.rockyapi.material.generic.GenericBlock;
import com.volumetricpixels.rockyapi.material.generic.GenericFood;
import com.volumetricpixels.rockyapi.material.generic.GenericItem;
import com.volumetricpixels.rockyapi.material.generic.GenericTool;
import com.volumetricpixels.rockyapi.material.generic.GenericWeapon;
import com.volumetricpixels.rockyapi.resource.AddonPack;
import com.volumetricpixels.rockyplugin.block.RockyBlock;
import com.volumetricpixels.rockyplugin.item.RockyItem;
import com.volumetricpixels.rockyplugin.item.RockyItemArmor;
import com.volumetricpixels.rockyplugin.item.RockyItemFood;
import com.volumetricpixels.rockyplugin.item.RockyItemSword;
import com.volumetricpixels.rockyplugin.item.RockyItemTool;

/**
 * 
 */
public class RockyMaterialManager implements MaterialManager {
	/**
	 * Where to start the ID getter
	 */
	public final static int DEFAULT_ITEM_PLACEHOLDER_ID = 2300;
	public final static int DEFAULT_BLOCK_PLACEHOLDER_ID = 196;
	public final static int MAX_FREE_ITEM_INDEX = 32000 - DEFAULT_ITEM_PLACEHOLDER_ID;
	public final static int MAX_FREE_BLOCK_INDEX = 4096 - DEFAULT_BLOCK_PLACEHOLDER_ID;

	/**
	 * Folder where all the data is at
	 */
	public final static String MATERIAL_FOLDER = "Package";

	/**
	 * Item list data
	 */
	private Map<Integer, Item> itemList = new HashMap<Integer, Item>();
	private BitSet itemIndexList = new BitSet(MAX_FREE_ITEM_INDEX);
	private Map<String, Integer> itemNameList = new HashMap<String, Integer>();

	/**
	 * Block list data
	 */
	private Map<Integer, Block> blockList = new HashMap<Integer, Block>();
	private BitSet blockIndexList = new BitSet(MAX_FREE_BLOCK_INDEX);
	private Map<String, Integer> blockNameList = new HashMap<String, Integer>();

	/**
	 * The reference map
	 */
	private Map<String, Class<? extends Material>> referenceMaterial = new HashMap<String, Class<? extends Material>>();
	private Map<Class<? extends Material>, Class<?>> referenceClazz = new HashMap<Class<? extends Material>, Class<?>>();

	/**
	 * Our default configuration loader
	 */
	private YamlConfiguration blockDefaultShape;

	/**
	 * Default constructor
	 */
	public RockyMaterialManager() {
		registerType("Armor", GenericArmor.class, RockyItemArmor.class);
		registerType("Food", GenericFood.class, RockyItemFood.class);
		registerType("Sword", GenericWeapon.class, RockyItemSword.class);
		registerType("Tool", GenericTool.class, RockyItemTool.class);
		registerType("Item", GenericItem.class, RockyItem.class);
		registerType("Block", GenericBlock.class, RockyBlock.class);

		blockDefaultShape = YamlConfiguration.loadConfiguration(Rocky
				.getInstance().getResource(MaterialManager.DEFAULT_SHAPE));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public YamlConfiguration getDefaultShape() {
		return blockDefaultShape;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item[] getItemList() {
		return itemList.values().toArray(new Item[0]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item getItem(int id) {
		return itemList.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block getBlock(int id) {
		return blockList.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerType(String name, Class<? extends Material> clazz,
			Class<?> reference) {
		referenceMaterial.put(name, clazz);
		referenceClazz.put(clazz, reference);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unregisterType(String name) {
		referenceClazz.remove(referenceMaterial.remove(name));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends Material> getType(String name) {
		return referenceMaterial.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRegisteredName(String name, MaterialEnumType type) {
		if (type == MaterialEnumType.ITEM && itemNameList.containsKey(name)) {
			return itemNameList.get(name);
		} else if (type == MaterialEnumType.BLOCK
				&& blockNameList.containsKey(name)) {
			return blockNameList.get(name);
		}
		return registerName(name, type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int registerName(String name, MaterialEnumType type) {
		int id = getNextFreeIndex(type);
		registerName(name, id, type);
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerName(String name, int id, MaterialEnumType type) {
		if (type == MaterialEnumType.ITEM) {
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
	public Map<String, Integer> getRegisteredNames(MaterialEnumType type) {
		return (type == MaterialEnumType.ITEM ? itemNameList : blockNameList);
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
		if (material instanceof Item) {
			itemList.put(material.getId(), (Item) material);
			try {
				net.minecraft.server.Item.byId[material.getId()] = (net.minecraft.server.Item) referenceClazz
						.get(material.getClass())
						.getConstructor(Material.class).newInstance(material);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return;
		}
		blockList.put(material.getId(), (Block) material);
		try {
			net.minecraft.server.Block.byId[material.getId()] = (net.minecraft.server.Block) referenceClazz
					.get(material.getClass()).getConstructor(Material.class)
					.newInstance(material);
		} catch (Throwable e) {
			e.printStackTrace();
		}
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
		if (material instanceof Item) {
			itemList.remove(material.getId());
			net.minecraft.server.Item.byId[material.getId()] = null;
			return;
		}
		blockList.remove(material.getId());
		net.minecraft.server.Block.byId[material.getId()] = null;
		deleteMaterial(((Block) material).getItemBlock());
	}

	/**
	 * 
	 * @return
	 */
	private int getNextFreeIndex(MaterialEnumType type) {
		if (type == MaterialEnumType.ITEM) {
			return itemIndexList.nextClearBit(DEFAULT_ITEM_PLACEHOLDER_ID);
		}
		return blockIndexList.nextClearBit(DEFAULT_BLOCK_PLACEHOLDER_ID);
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onLoadingEvent(RockyLoadingEvent event) {
		int itemLoaded = 0, packageLoaded = 0;

		// Find each package within the directory
		File directory = new File(Rocky.getInstance().getDataFolder(),
				MATERIAL_FOLDER);
		if (!directory.exists()) {
			directory.mkdir();
		}

		for (File file : directory.listFiles()) {

			// Check if the package is valid
			if (!file.getName().endsWith(".smp"))
				continue;

			// Load the package
			ZipFile zipFile = null;
			try {
				zipFile = new ZipFile(file);
			} catch (Throwable ex) {
				continue;
			}
			// Get each file within this file
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			// This is our current addon pack
			AddonPack pack = new AddonPack(zipFile);

			// For each element of creature load it
			while (enumeration.hasMoreElements()) {

				// Get the current entry
				ZipEntry zipEntry = enumeration.nextElement();
				if (!zipEntry.getName().endsWith(".yml"))
					continue;

				// Load the configuration of the type
				YamlConfiguration configuration = new YamlConfiguration();
				try {
					configuration.load(zipFile.getInputStream(zipEntry));
				} catch (Throwable ex) {
					ex.printStackTrace();
					continue;
				}

				Class<? extends Material> clazz = referenceMaterial
						.get(configuration.getString("Type"));
				if (clazz == null) {
					RockyManager
							.printConsole(
									"[Warning] trying to load an invalid type of Item {%s}",
									configuration.getString("Type"));
					continue;
				}
				Material material = null;
				try {
					material = clazz.newInstance().loadPreInitialization(
							Rocky.getInstance(), configuration, pack);
				} catch (Throwable e) {
					e.printStackTrace();
					continue;
				}
				addMaterial(material);
				itemLoaded++;
			}
			packageLoaded++;
		}

		RockyManager.printConsole("%d item has been loaded in %d packages.",
				itemLoaded, packageLoaded);
	}

	/**
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onFinishedLoadingEvent(RockyFinishedLoadingEvent event) {
		int recipeLoaded = 0, packageLoaded = 0;

		// Find each package within the directory
		File directory = new File(Rocky.getInstance().getDataFolder(),
				MATERIAL_FOLDER);
		for (File file : directory.listFiles()) {

			// Check if the package is valid
			if (!file.getName().endsWith(".smp"))
				continue;

			// Load the package
			ZipFile zipFile = null;
			try {
				zipFile = new ZipFile(file);
			} catch (Throwable ex) {
				continue;
			}
			// Get each file within this file
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			// This is our current addon pack
			AddonPack pack = new AddonPack(zipFile);

			// For each element of creature load it
			while (enumeration.hasMoreElements()) {

				// Get the current entry
				ZipEntry zipEntry = enumeration.nextElement();
				if (!zipEntry.getName().endsWith(".yml"))
					continue;

				// Load the configuration of the type
				YamlConfiguration configuration = new YamlConfiguration();
				try {
					configuration.load(zipFile.getInputStream(zipEntry));
				} catch (Throwable ex) {
					ex.printStackTrace();
					continue;
				}

				// Load the recipes
				if (configuration.contains("Recipes")) {
					List<Map<?, ?>> recipeSection = (List<Map<?, ?>>) configuration
							.getMapList("Recipes");
					for (Map<?, ?> key : recipeSection) {

						String type = (String) key.get("Type");
						int amount = (key.containsKey("Amount") ? (Integer) key
								.get("Amount") : 1);
						int result = getItemID(configuration.getString("Title"));
						if (type.equals("Furnace")) {
							RockyFurnaceRecipe fRecipe = new RockyFurnaceRecipe(
									getItemID((String) key.get("Ingredient")),
									new ItemStack(result, amount),
									(key.containsKey("Speed") ? (Double) key
											.get("Speed") : 1.0f));
							RockyRecipeManager.addToFurnaceManager(fRecipe);
						} else if (type.equals("Shaped")) {

							Map<String, String> lineMap = (Map<String, String>) key
									.get("Line");
							String lA = (lineMap.containsKey("A") ? (String) lineMap
									.get("A") : "   ");
							String lB = (lineMap.containsKey("B") ? (String) lineMap
									.get("B") : "   ");
							String lC = (lineMap.containsKey("C") ? (String) lineMap
									.get("C") : "   ");

							RockyShapedRecipe wRecipe = new RockyShapedRecipe(
									new ItemStack(result, amount));
							wRecipe.shape(lA, lB, lC);

							Map<String, String> ingredientMap = (Map<String, String>) key
									.get("Ingredient");
							for (String ingredientKey : ingredientMap.keySet()) {
								Object value = ingredientMap.get(ingredientKey);
								if (value instanceof Integer) {
									wRecipe.setIngredient(
											ingredientKey.charAt(0),
											(Integer) value);
								} else if (value instanceof String)
									wRecipe.setIngredient(ingredientKey
											.charAt(0),
											getItemID((String) ingredientMap
													.get(ingredientKey)));
							}
							wRecipe.shape(lA, lB, lC);
							RockyRecipeManager.addToCraftingManager(wRecipe);

						} else if (type.equals("Shapeless")) {
							RockyShapelessRecipe wRecipe = new RockyShapelessRecipe(
									new ItemStack(result, amount));
							List<String> ingredientList = (List<String>) key
									.get("Ingredient");
							for (String ingredientKey : ingredientList) {
								wRecipe.addIngredient(getItemID(ingredientKey));
							}
							RockyRecipeManager.addToCraftingManager(wRecipe);
						}
						recipeLoaded++;
					}
				}
				String title = configuration.getString("Title");

				if (configuration.getString("Type").contains("Block")) {
					blockList.get(getBlockID(title)).loadPostInitialization(
							Rocky.getInstance(), configuration, pack);
				} else {
					itemList.get(getItemID(title)).loadPostInitialization(
							Rocky.getInstance(), configuration, pack);
				}
			}
			packageLoaded++;
		}
		RockyManager.printConsole("%d recipes has been loaded in %d packages.",
				recipeLoaded, packageLoaded);
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
	 * @param data
	 * @return
	 */
	private int getBlockID(String data) {
		if (Character.isDigit(data.charAt(0))) {
			return Integer.valueOf(data);
		}
		return blockNameList.get(data);
	}

}
