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
package com.volumetricpixels.rockyapi;

import java.util.Map;
import java.util.logging.Logger;

import net.minecraft.server.v1_5_R3.CraftingManager;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.RecipesFurnace;

import org.bukkit.Material;
import org.bukkit.World;

import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import com.volumetricpixels.rockyapi.inventory.RockyFurnaceRecipe;
import com.volumetricpixels.rockyapi.inventory.RockyShapedRecipe;
import com.volumetricpixels.rockyapi.inventory.RockyShapelessRecipe;
import com.volumetricpixels.rockyapi.material.MaterialManager;
import com.volumetricpixels.rockyapi.packet.PacketManager;
import com.volumetricpixels.rockyapi.player.PlayerManager;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyapi.resource.ResourceManager;

/**
 * Encapsulate the plugin static manager
 */
public final class RockyManager {
	private static RockyManager instance = null;

	private PlayerManager playerManager = null;
	private ResourceManager resourceManager = null;
	private PacketManager packetManager = null;
	private MaterialManager materialManager = null;

	/**
	 * Default constructor of the class
	 * 
	 * @param playerManager
	 *            the player manager
	 * @param resourceManager
	 *            the resource manager
	 * @param keyManager
	 *            the key manager
	 * @param packetManager
	 *            the packet manager
	 * @param materialManager
	 *            the material manager
	 */
	public RockyManager(PlayerManager playerManager,
			ResourceManager resourceManager, PacketManager packetManager,
			MaterialManager materialManager) {
		this.playerManager = playerManager;
		this.resourceManager = resourceManager;
		this.packetManager = packetManager;
		this.materialManager = materialManager;
	}

	/**
	 * Sets the unique instance of the plugin
	 * 
	 * @param instance
	 *            the final instance
	 */
	public static void setInstance(RockyManager instance) {
		if (RockyManager.instance != null) {
			throw new IllegalArgumentException("Cannot set instance");
		}
		RockyManager.instance = instance;
	}

	/**
	 * Gets the singleton instance of the rocky plugin
	 * 
	 * @return rocky plugin
	 */
	public static RockyManager getInstance() {
		return instance;
	}

	/**
	 * Gets the material manager of rocky
	 * 
	 * @return the material manager of rocky
	 */
	public static MaterialManager getMaterialManager() {
		return getInstance().materialManager;
	}

	/**
	 * Gets a RockyEntity from the given id, or null if none found
	 * 
	 * @param world
	 * @param entityId
	 * @return RockyEntity
	 */
	public static Entity getEntityFromId(World world, int entityId) {
		return getInstance().playerManager.getEntity(world, entityId);
	}

	/**
	 * Gets a RockyPlayer from the given id, or null if none found
	 * 
	 * @param entityId
	 * @return RockyPlayer
	 */
	public static RockyPlayer getPlayerFromId(int entityId) {
		return getInstance().playerManager.getPlayer(entityId);
	}

	/**
	 * Gets a RockyPlayer from the given bukkit player, will never fail
	 * 
	 * @param player
	 * @return RockyPlayer
	 */
	public static RockyPlayer getPlayer(Player player) {
		return getInstance().playerManager.getPlayer(player);
	}

	/**
	 * Gets the list of online players
	 * 
	 * @return online players
	 */
	public static RockyPlayer[] getOnlinePlayers() {
		return getInstance().playerManager.getOnlinePlayers();
	}

	/**
	 * Gets the player manager
	 * 
	 * @return player manager
	 */
	public static PlayerManager getPlayerManager() {
		return getInstance().playerManager;
	}

	/**
	 * Gets the resource manager
	 * 
	 * @return resource manager
	 */
	public static ResourceManager getResourceManager() {
		return getInstance().resourceManager;
	}

	/**
	 * Gets the packet manager
	 * 
	 * @return packet manager
	 */
	public static PacketManager getPacketManager() {
		return getInstance().packetManager;
	}

	/**
	 * Plays a sound effect for all players
	 * 
	 * @param effect
	 *            to play
	 */
	public static void playGlobalSound(String effect) {
		RockyPlayer[] listPlayer = getOnlinePlayers();
		for (RockyPlayer player : listPlayer) {
			player.playSound(effect);
		}
	}

	/**
	 * 
	 * @param effect
	 * @param distance
	 */
	public static void playGlobalSound(String effect, int distance) {
		RockyPlayer[] listPlayer = getOnlinePlayers();
		for (RockyPlayer player : listPlayer) {
			player.playSound(effect, distance);
		}
	}

	/**
	 * 
	 * @param effect
	 * @param distance
	 * @param volumePercent
	 */
	public static void playGlobalSound(String effect, int distance,
			int volumePercent) {
		RockyPlayer[] listPlayer = getOnlinePlayers();
		for (RockyPlayer player : listPlayer) {
			player.playSound(effect, distance, volumePercent);
		}
	}

	/**
	 * Plays the music for all players
	 * 
	 * @param music
	 *            to play
	 */
	public static void playGlobalMusic(String music) {
		RockyPlayer[] listPlayer = getOnlinePlayers();
		for (RockyPlayer player : listPlayer) {
			player.playMusic(music);
		}
	}

	/**
	 * Plays the music at the given volume percent for all players
	 * 
	 * @param music
	 *            to play
	 * @param volumePercent
	 *            to play at (100 = normal, 200 = double volume, 50 = half
	 *            volume)
	 */
	public static void playGlobalMusic(String music, int volumePercent) {
		RockyPlayer[] listPlayer = getOnlinePlayers();
		for (RockyPlayer player : listPlayer) {
			player.playMusic(music, volumePercent);
		}
	}

	/**
	 * Add a {@see RockyShapedRecipe}'s into the crafting table.
	 * 
	 * @param recipe
	 *            the recipe to add into the table
	 */
	public static void addToCraftingManager(RockyShapedRecipe recipe) {
		String[] shapeList = recipe.getShape();
		Map<Character, Integer> ingredient = recipe.getIngredientMap();
		int lenght = (shapeList.length) + ingredient.size() * 2;
		Object[] data = new Object[lenght];
		int i = 0;

		for (i = 0; i < shapeList.length; i++) {
			data[i] = shapeList[i];
		}
		for (Character character : ingredient.keySet()) {
			Integer material = ingredient.get(character);

			data[i++] = character;
			data[i++] = new ItemStack(material, 1, 0);
		}
		CraftingManager.getInstance().registerShapedRecipe(
				CraftItemStack.asNMSCopy(recipe.getResult()), data);
	}

	/**
	 * Add a {@see RockyShapelessRecipe}'s into the crafting table.
	 * 
	 * @param recipe
	 *            the recipe to add into the table
	 */
	public static void addToCraftingManager(RockyShapelessRecipe recipe) {
		Material[] array = recipe.getIngredientList().toArray(new Material[0]);
		Object[] stackArray = new ItemStack[array.length];
		for (int i = 0; i < array.length; i++) {
			stackArray[i] = new ItemStack(array[i].getId(), 1,
					array[i].getMaxDurability());
		}
		CraftingManager.getInstance().registerShapelessRecipe(
				CraftItemStack.asNMSCopy(recipe.getResult()), stackArray);
	}

	/**
	 * Add a {@see RockyFurnaceRecipe}'s into the crafting table.
	 * 
	 * @param recipe
	 *            the recipe to add into the table
	 */
	public static void addToFurnaceManager(RockyFurnaceRecipe recipe) {
		RecipesFurnace.getInstance().registerRecipe(recipe.getIngredient(),
				CraftItemStack.asNMSCopy(recipe.getResult()),
				(float) recipe.getSpeed());
	}

	/**
	 * Helper function to print into the console as the plugin owner.
	 * 
	 * @param data
	 *            the format of the string
	 * @param type
	 *            the variables for the format
	 */
	public static void printConsole(String data, Object... type) {
		Logger.getLogger("RockyPlugin").info(
				"[Rocky]: " + String.format(data, type));
	}

}
