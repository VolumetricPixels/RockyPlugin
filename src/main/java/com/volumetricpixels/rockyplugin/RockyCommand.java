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

import java.util.concurrent.atomic.AtomicLong;

import net.minecraft.server.v1_5_R3.Item;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.player.RenderDistance;
import com.volumetricpixels.rockyplugin.chunk.ChunkCacheHandler;
import com.volumetricpixels.rockyplugin.packet.RockyPacketHandler;

/**
 * 
 */
public class RockyCommand implements CommandExecutor {

	private static final String MESSAGE_PREFIX = "[" + ChatColor.DARK_PURPLE
			+ "Rocky" + ChatColor.WHITE + "]: ";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MESSAGE_PREFIX + ChatColor.RED
					+ "       \\   .---.");
			sender.sendMessage(MESSAGE_PREFIX + ChatColor.RED
					+ "           |o_o |");
			sender.sendMessage(MESSAGE_PREFIX + ChatColor.BLUE
					+ "           |:_/  |");
			sender.sendMessage(MESSAGE_PREFIX + ChatColor.BLUE
					+ "         //    \\ \\");
			sender.sendMessage(MESSAGE_PREFIX + ChatColor.GREEN
					+ "        ( |       |  )");
			sender.sendMessage(MESSAGE_PREFIX + ChatColor.GREEN
					+ "       /\"\\      /\"\\");
			sender.sendMessage(MESSAGE_PREFIX + ChatColor.GREEN
					+ "       \\__)==(__/");
			sender.sendMessage(MESSAGE_PREFIX + ChatColor.BLACK
					+ "-----------------------------");
			sender.sendMessage(MESSAGE_PREFIX + "Server version {"
					+ Rocky.getInstance().getDescription().getVersion() + "}");
			sender.sendMessage(MESSAGE_PREFIX + ChatColor.BLACK
					+ "-----------------------------");

			return true;
		}

		String c = args[0];
		if (c.equals("item") && args.length > 1
				&& sender.hasPermission(RockyPermission.COMMAND_ITEM.getNode())) {
			Integer item = Integer.valueOf(args[1]);
			int amount = (args.length > 2 ? Integer.valueOf(args[2]) : 1);
			String name = (args.length > 3 ? args[3] : sender.getName());

			Player player = Bukkit.getPlayerExact(name);
			if (player == null) {
				sender.sendMessage(MESSAGE_PREFIX + ChatColor.RED
						+ "The user must be online.");
				return true;
			} else if (amount > 64 || amount < 1) {
				sender.sendMessage(MESSAGE_PREFIX + ChatColor.RED
						+ "The minimum is 1 and the maximum is 64");
				return true;
			} else if (item < 0 || item > Item.byId.length
					|| Item.byId[item] == null) {
				sender.sendMessage(MESSAGE_PREFIX + ChatColor.RED
						+ "The item doesn't exist");
				return true;
			}
			player.getInventory().addItem(new ItemStack(item, amount));
			return true;
		} else if (c.equals("view-distance")
				&& args.length > 2
				&& sender.hasPermission(RockyPermission.COMMAND_VIEW_DISTANCE
						.getNode())) {
			Player player = Bukkit.getPlayerExact(args[1]);
			String name = args[2];
			if (player == null) {
				sender.sendMessage(MESSAGE_PREFIX + ChatColor.RED
						+ "The user must be online.");
				return true;
			} else if (RenderDistance.valueOf(name.toUpperCase()) == null) {
				sender.sendMessage(MESSAGE_PREFIX + ChatColor.RED
						+ "Valid distance " + ChatColor.GREEN
						+ "[VERY_TINY, TINY, SHORT, NORMAL, FAR, VERY_FAR]");
				return true;
			}
			RockyManager.getPlayer(player).setRenderDistance(
					RenderDistance.valueOf(name.toUpperCase()));
			return true;
		} else if (c.equals("cache-info")
				&& sender.hasPermission(RockyPermission.COMMAND_CACHE_INFO
						.getNode())) {
			sender.sendMessage(ChatColor.BLACK + "-------- " + ChatColor.BLUE
					+ "Network " + ChatColor.BLACK + "--------");
			sender.sendMessage(ChatColor.RED + "Cache %: " + ChatColor.GREEN
					+ ChunkCacheHandler.getCacheHit() + ChatColor.WHITE + "/"
					+ ChatColor.RED + ChunkCacheHandler.getCacheMiss()
					+ ChatColor.GREEN + " ("
					+ ChunkCacheHandler.getStatPorcentage() + "%)");
			sender.sendMessage(ChatColor.RED + "Bandwidth Cached: "
					+ ChatColor.GREEN + ChunkCacheHandler.getBandwidth() + "mb");

			AtomicLong pckUpload = RockyPacketHandler.getPacketUploaded();
			AtomicLong pckDownloaded = RockyPacketHandler.getTotalDownloaded();

			long logTime = System.currentTimeMillis()
					- RockyPacketHandler.getLoggingTime().get();

			if (pckUpload.get() != 0) {
				long kbpsUp = (80000L * pckUpload.get()) / 1024 / logTime;
				sender.sendMessage(ChatColor.RED + "Upstream: "
						+ ChatColor.GREEN + (kbpsUp / 10.0) + ChatColor.RED
						+ " avg kbps (" + ChatColor.GREEN
						+ (pckUpload.get() / 1024) + ChatColor.RED + " kB)");
			}
			if (pckDownloaded.get() != 0) {
				long kbpsDown = (80000L * pckDownloaded.get()) / 1024 / logTime;

				sender.sendMessage(ChatColor.RED + "Downstream: "
						+ ChatColor.GREEN + (kbpsDown / 10.0) + ChatColor.RED
						+ " avg kbps (" + ChatColor.GREEN
						+ (pckDownloaded.get() / 1024) + ChatColor.RED + " kB)");
			}
			sender.sendMessage(ChatColor.BLACK + "-------------------------");
			return true;
		}
		return false;
	}
}
