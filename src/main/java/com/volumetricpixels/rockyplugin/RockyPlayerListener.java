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
package com.volumetricpixels.rockyplugin;

import net.minecraft.server.v1_5_R3.PlayerChunkMap;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.fest.reflect.core.Reflection;
import org.fest.reflect.field.Invoker;

import com.volumetricpixels.rockyapi.event.player.PlayerEnterArea;
import com.volumetricpixels.rockyapi.packet.protocol.PacketPlayerAppearance;
import com.volumetricpixels.rockyplugin.chunk.ChunkCacheHandler;
import com.volumetricpixels.rockyplugin.player.RockyPlayerServerManager;
import com.volumetricpixels.rockyplugin.reflection.Reflector;
import com.volumetricpixels.rockyplugin.reflection.ReflectorID;

/**
 * 
 */
public class RockyPlayerListener implements Listener {

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Rocky.getInstance().handlePlayerLogin(event.getPlayer());
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		ChunkCacheHandler.handlePlayerRemoval(event.getPlayer().getName());
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWorldInit(WorldInitEvent event) {
		World world = event.getWorld();

		Invoker<PlayerChunkMap> pm = Reflection
				.field(Reflector.getField(ReflectorID.PLAYER_CHUNK_MAP))
				.ofType(PlayerChunkMap.class)
				.in(((CraftWorld) world).getHandle());
		pm.set(new RockyPlayerServerManager(pm.get()));
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerEnterPlayerArea(PlayerEnterArea event) {
		if (event.getPlayer().isModded()) {
			event.getPlayer().sendPacket(
					new PacketPlayerAppearance(event.getTriggerPlayer()));
		}
	}

}
