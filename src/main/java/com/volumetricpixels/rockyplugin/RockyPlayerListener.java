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

import net.minecraft.server.PlayerManager;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.world.WorldInitEvent;
import org.fest.reflect.core.Reflection;
import org.fest.reflect.field.Invoker;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.event.player.PlayerEnterPlayerArea;
import com.volumetricpixels.rockyapi.packet.protocol.PacketPlayerAppearance;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyplugin.player.RockyPlayerHandler;
import com.volumetricpixels.rockyplugin.player.RockyPlayerServerManager;

/**
 * 
 */
public class RockyPlayerListener implements Listener {

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		RockyPlayerHandler.updateBukkitEntry(player);
		RockyPlayerHandler.updateNetworkEntry(player);
		RockyPlayerHandler.sendAuthentication(player);

		Rocky.getInstance().addPlayerToCheckList(player);
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		if (!event.getReason().equals("You moved too quickly :( (Hacking?)")) {
			return;
		}
		if (player.isOp() || player instanceof RockyPlayer
				&& ((RockyPlayer) player).canFly()) {
			event.setCancelled(true);
		}
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		RockyPlayer splr = RockyManager.getPlayer(event.getPlayer());
		if (event.getCause() == TeleportCause.UNKNOWN && splr.isModded()
				&& splr.isFlying() && splr.getFlySpeed() > 1.0f) {
			event.setCancelled(true);
		}
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		RockyPlayer splr = RockyManager.getPlayer(event.getPlayer());
		splr.updateWaypoints();
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWorldInit(WorldInitEvent event) {
		World world = event.getWorld();

		Invoker<PlayerManager> pm = Reflection.field("manager")
				.ofType(PlayerManager.class)
				.in(((CraftWorld) world).getHandle());
		pm.set(new RockyPlayerServerManager(pm.get()));
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerEnterPlayerArea(PlayerEnterPlayerArea event) {
		if (event.getPlayer().isModded()) {
			event.getPlayer().sendPacket(
					new PacketPlayerAppearance(event.getTriggerPlayer()));
		}
	}

}
