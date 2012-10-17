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

import net.minecraft.server.Block;
import net.minecraft.server.Item;

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
import com.volumetricpixels.rockyapi.material.MaterialEnumType;
import com.volumetricpixels.rockyapi.material.RangeWeapon;
import com.volumetricpixels.rockyapi.material.Tool;
import com.volumetricpixels.rockyapi.material.Weapon;
import com.volumetricpixels.rockyapi.material.generic.GenericArmor;
import com.volumetricpixels.rockyapi.material.generic.GenericFood;
import com.volumetricpixels.rockyapi.material.generic.GenericRangeWeapon;
import com.volumetricpixels.rockyapi.material.generic.GenericTool;
import com.volumetricpixels.rockyapi.material.generic.GenericWeapon;
import com.volumetricpixels.rockyapi.resource.AddonPack;
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
	public final static int DEFAULT_ITEM_PLACEHOLDER_ID = 4097;
	public final static int DEFAULT_ITEM_FOR_VANILLA = 318;

	public final static int DEFAULT_BLOCK_PLACEHOLDER_ID = 196;
	public final static int DEFAULT_BLOCK_FOR_VANILLA = 1;

	private final static int MAX_FREE_ITEM_INDEX = 32000 - DEFAULT_ITEM_PLACEHOLDER_ID;
	private final static int MAX_FREE_BLOCK_INDEX = 4096 - DEFAULT_BLOCK_PLACEHOLDER_ID;

	private final static String MATERIAL_FOLDER = "Package";

	private Map<String, Integer> itemNameList = new HashMap<String, Integer>();
	private Map<String, Integer> blockNameList = new HashMap<String, Integer>();

	private Map<Integer, com.volumetricpixels.rockyapi.material.Item> itemList = new HashMap<Integer, com.volumetricpixels.rockyapi.material.Item>();
	private Map<Integer, Material> blockList = new HashMap<Integer, Material>();

	private BitSet itemIndexList = new BitSet(MAX_FREE_ITEM_INDEX);
	private BitSet blockIndexList = new BitSet(MAX_FREE_BLOCK_INDEX);

	private Map<String, Class<? extends Material>> registeredTypes = new HashMap<String, Class<? extends Material>>();

	private YamlConfiguration defaultShape;

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
		registerType(
				"Block",
				com.volumetricpixels.rockyapi.material.generic.GenericBlock.class);

		defaultShape = YamlConfiguration.loadConfiguration(Rocky.getInstance()
				.getResource(MaterialManager.DEFAULT_SHAPE));
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
	private int getNextFreeIndex(MaterialEnumType type) {
		if (type == MaterialEnumType.ITEM) {
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
		} else if (material instanceof com.volumetricpixels.rockyapi.material.Item) {
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
		} else if (itemNameList.containsKey(data)) {
			return itemNameList.get(data);
		}
		return blockNameList.get(data);
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

				Class<? extends Material> clazz = registeredTypes
						.get(configuration.getString("Class"));
				if (clazz == null) {
					RockyManager
							.printConsole(
									"[Warning] trying to load an invalid type of Item {%s}",
									configuration.getString("Class"));
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
						int result = getItemID(configuration.getString("Name"));
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
								wRecipe.setIngredient(ingredientKey.charAt(0),
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
				int id = getItemID(configuration.getString("Name"));
				if (id >= DEFAULT_ITEM_PLACEHOLDER_ID) {
					itemList.get(id).loadPostInitialization(
							Rocky.getInstance(), configuration, pack);
				} else {
					blockList.get(id).loadPostInitialization(
							Rocky.getInstance(), configuration, pack);
				}
			}
			packageLoaded++;
		}
		RockyManager.printConsole("%d recipes has been loaded in %d packages.",
				recipeLoaded, packageLoaded);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.volumetricpixels.rockyapi.material.Item[] getItemList() {
		return itemList.values().toArray(
				new com.volumetricpixels.rockyapi.material.Item[0]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public YamlConfiguration getDefaultShape() {
		return defaultShape;
	}
}
