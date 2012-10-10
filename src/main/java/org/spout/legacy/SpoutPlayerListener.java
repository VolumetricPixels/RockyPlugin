/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2012-2012, VolumetricPixels <http://www.volumetricpixels.com/>
 * SpoutLegacy is licensed under the GNU Lesser General Public License.
 *
 * SpoutLegacy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutLegacy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spout.legacy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.spout.legacy.player.SpoutPlayerHandler;
import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.event.player.PlayerEnterPlayerArea;
import org.spout.legacyapi.packet.protocol.PacketPlayerAppearance;
import org.spout.legacyapi.player.SpoutPlayer;

/**
 * 
 */
public class SpoutPlayerListener implements Listener {

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		SpoutPlayerHandler.updateBukkitEntry(player);
		SpoutPlayerHandler.updateNetworkEntry(player);
		SpoutPlayerHandler.sendAuthentication(player);

		Spout.getInstance().addPlayerToCheckList(player);
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Spout.getInstance().handlePlayerQuit(event.getPlayer());
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
		if (player.isOp()) {
			event.setCancelled(true);
		} else if (player instanceof SpoutPlayer) {
			SpoutPlayer spoutPlayer = (SpoutPlayer) player;
			if (spoutPlayer.canFly()) {
				event.setCancelled(true);
			} else if (System.currentTimeMillis() < spoutPlayer
					.getAirSpeedMultiplier()) {
				event.setCancelled(true);
			}
		}
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		SpoutPlayer splr = SpoutManager.getPlayer(event.getPlayer());
		if (event.getCause() == TeleportCause.UNKNOWN && splr.isSpoutEnabled()
				&& splr.isFlying() && splr.getFlySpeed() > 1.0f)
			event.setCancelled(true);
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		SpoutPlayer splr = SpoutManager.getPlayer(event.getPlayer());
		splr.updateWaypoints();
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerEnterPlayerArea(PlayerEnterPlayerArea event) {
		if (event.getPlayer().isSpoutEnabled())
			event.getPlayer().sendPacket(
					new PacketPlayerAppearance(event.getTriggerPlayer()));
	}

}