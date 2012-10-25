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
package com.volumetricpixels.rockyapi;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.volumetricpixels.rockyapi.keyboard.KeyBindingManager;
import com.volumetricpixels.rockyapi.material.MaterialManager;
import com.volumetricpixels.rockyapi.packet.PacketManager;
import com.volumetricpixels.rockyapi.player.PlayerManager;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyapi.resource.ResourceManager;

/**
 * 
 */
public final class RockyManager {
	private static RockyManager instance = null;

	private PlayerManager playerManager = null;
	private ResourceManager resourceManager = null;
	private KeyBindingManager keyBindingManager = null;
	private PacketManager packetManager = null;
	private MaterialManager materialManager = null;

	/**
	 * 
	 * @param playerManager
	 * @param resourceManager
	 * @param keyManager
	 * @param packetManager
	 * @param materialManager
	 */
	public RockyManager(PlayerManager playerManager,
			ResourceManager resourceManager, KeyBindingManager keyManager,
			PacketManager packetManager, MaterialManager materialManager) {
		this.playerManager = playerManager;
		this.resourceManager = resourceManager;
		this.keyBindingManager = keyManager;
		this.packetManager = packetManager;
		this.materialManager = materialManager;
	}

	/**
	 * 
	 * @param instance
	 */
	public static void setInstance(RockyManager instance) {
		if (RockyManager.instance != null) {
			throw new IllegalArgumentException("Cannot set instance");
		}
		RockyManager.instance = instance;
	}

	/**
	 * Gets the singleton instance of the spout plugin
	 * 
	 * @return spout plugin
	 */
	public static RockyManager getInstance() {
		return instance;
	}

	/**
	 * 
	 * @return
	 */
	public static MaterialManager getMaterialManager() {
		return getInstance().materialManager;
	}

	/**
	 * Gets a SpoutPlayer from the given id, or null if none found
	 * 
	 * @param entityId
	 * @return SpoutPlayer
	 */
	public static RockyPlayer getPlayerFromId(int entityId) {
		return getInstance().playerManager.getPlayer(entityId);
	}

	/**
	 * Gets a SpoutPlayer from the given id, or null if none found
	 * 
	 * @param id
	 * @return SpoutPlayer
	 */
	public static RockyPlayer getPlayerFromId(UUID id) {
		return getInstance().playerManager.getPlayer(id);
	}

	/**
	 * Gets a SpoutPlayer from the given bukkit player, will never fail
	 * 
	 * @param player
	 * @return SpoutPlayer
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
	 * Gets the key binding manager;
	 * 
	 * @return key binding manager
	 */
	public static KeyBindingManager getKeyBindingManager() {
		return getInstance().keyBindingManager;
	}

	/**
	 * Plays a sound effect for all players
	 * 
	 * @param effect
	 *            to play
	 */
	public static void playGlobalSoundEffect(String effect) {
		RockyPlayer[] listPlayer = getOnlinePlayers();
		for (RockyPlayer player : listPlayer) {
			player.playSoundEffect(effect);
		}
	}

	/**
	 * Plays a sound effect for all players, at the given location
	 * 
	 * @param effect
	 *            to play
	 * @param distance
	 *            away it can be heard from (in full blocks) or -1 for any
	 *            distance
	 */
	public static void playGlobalSoundEffect(String effect, int distance) {
		RockyPlayer[] listPlayer = getOnlinePlayers();
		for (RockyPlayer player : listPlayer) {
			player.playSoundEffect(effect, distance);
		}
	}

	/**
	 * Plays a sound effect for all players, at the given location, with the
	 * given intensity and given volume The intensity is how far away (in full
	 * blocks) players can be and hear the sound effect at full volume.
	 * 
	 * @param effect
	 *            to play
	 * @param volumePercent
	 *            to play at (100 = normal, 200 = double volume, 50 = half
	 *            volume)
	 * @param distance
	 *            away it can be heard from (in full blocks) or -1 for any
	 *            distance
	 */
	public static void playGlobalSoundEffect(String effect, int distance,
			int volumePercent) {
		RockyPlayer[] listPlayer = getOnlinePlayers();
		for (RockyPlayer player : listPlayer) {
			player.playSoundEffect(effect, distance, volumePercent);
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
	 */
	public static void printConsole(String data, Object... type) {
		System.out.println("[Rocky]: " + String.format(data, type));
	}

}
