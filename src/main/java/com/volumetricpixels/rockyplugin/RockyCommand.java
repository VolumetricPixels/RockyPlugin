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

import net.minecraft.server.MinecraftServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.player.RockyPlayer;


/**
 * 
 */
public class RockyCommand implements CommandExecutor {
	private String motd_temp = null;
	private int motd_task = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("[Rocky] Server version: "
					+ Rocky.getInstance().getDescription().getVersion());
			return true;
		}

		String c = args[0];
		if (c.equals("version")) {
			sender.sendMessage("[Rocky] Server version: "
					+ Rocky.getInstance().getDescription().getVersion());

			CommandSender target = sender;

			if (args.length > 1) {
				target = Rocky.getInstance().getServer().getPlayer(args[1]);
				if (target == null) {
					sender.sendMessage("[Rocky] Unknown player: "
							+ args[1]);
					return true;
				}
			}

			if (!(target instanceof Player)) {
				sender.sendMessage("[Rocky] Client version: no client");
			}
			if (!(target instanceof RockyPlayer)) {
				sender.sendMessage("[Rocky] Client version: standard client");
			} else {
				RockyPlayer sp = (RockyPlayer) target;
				if (!sp.isModded()) {
					sender.sendMessage("[Rocky] Client version: standard client");
				} else {
					sender.sendMessage("[Rocky] Client version: "
							+ sp.getVersionString());
				}
			}
			return true;
		} else if (!sender.isOp()) {
			sender.sendMessage("[Rocky] This command is Op only");
			return true;
		} else if (c.equals("waypoint")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can add waypoints.");
				return true;
			}
			if (args.length > 1) {
				String name = args[1];
				Rocky.getInstance().getConfiguration()
						.addWaypoint(name, ((Player) sender).getLocation());
				sender.sendMessage("Waypoint [" + name
						+ "] created successfully");
				return true;
			} else {
				sender.sendMessage("You must give a name to the waypoint.");
				return true;
			}
		} else if (c.equals("list")) {
			String message = ChatColor.GREEN
					+ "Players online with Spoutcraft: ";
			for (Player plr : Bukkit.getOnlinePlayers()) {
				RockyPlayer splr = RockyManager.getPlayer(plr);
				if (splr.isModded()) {
					message += ChatColor.YELLOW + splr.getName()
							+ ChatColor.GREEN + ", ";
				}
			}
			message = message.substring(0, message.length() - 2);
			message += ChatColor.GREEN + "!";
			sender.sendMessage(message);
			return true;
		} else if (c.equals("reload")) {
			Rocky.getInstance().onDisable();
			Rocky.getInstance().onLoad();
			return true;
		} else if (c.equals("verify") && args.length > 1) {
			sender.sendMessage("[Rocky] Temporarily setting the MOTD to: "
					+ args[1]);
			sender.sendMessage("[Rocky] It will return to its original setting in ~5 mins");
			if (motd_temp == null) {
				motd_temp = MinecraftServer.getServer().getMotd();
			} else {
				Bukkit.getServer().getScheduler().cancelTask(motd_task);
			}
			MinecraftServer.getServer().setMotd(args[1]);
			motd_task = Bukkit
					.getServer()
					.getScheduler()
					.scheduleSyncDelayedTask(Rocky.getInstance(),
							new Runnable() {
								@Override
								public void run() {
									MinecraftServer.getServer().setMotd(
											motd_temp);
									motd_temp = null;
								}
							}, 20 * 60 * 5);
			return true;
		}

		return false;
	}
}
