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
package com.volumetricpixels.rockyplugin.packet.listener;

import java.util.List;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import net.minecraft.server.v1_5_R3.NBTTagList;
import net.minecraft.server.v1_5_R3.NBTTagString;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.event.player.PlayerEnterArea;
import com.volumetricpixels.rockyapi.event.player.PlayerLeaveArea;
import com.volumetricpixels.rockyapi.material.Item;
import com.volumetricpixels.rockyapi.packet.PacketListener;
import com.volumetricpixels.rockyapi.packet.PacketVanilla;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyplugin.RockyMaterialManager;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketDestroyEntity;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketEntityEquipment;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketEntityMetadata;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketNamedEntitySpawn;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketSetCreativeSlot;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketSetSlot;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketWindowClick;
import com.volumetricpixels.rockyplugin.packet.vanilla.PacketWindowItems;

/**
 * Encapsulate {@see PacketListener} for supporting custom features such as item
 * into a non modded client.
 */
public class PacketVanillaSupportListener implements PacketListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onPrePacket(RockyPlayer player, PacketVanilla packet) {
		int packetId = packet.getId();

		if (packetId == 0x5) {
			handlePacket((PacketEntityEquipment) packet);
		} else if (packetId == 0x1D) {
			handlePacket((PacketDestroyEntity) packet, player);
		} else if (packetId == 0x28) {
			handlePacket((PacketEntityMetadata) packet, player);
		} else if (packetId == 0x66) {
			handlePacket((PacketWindowClick) packet);
		} else if (packetId == 0x67) {
			handlePacket((PacketSetSlot) packet);
		} else if (packetId == 0x6B) {
			handlePacket((PacketSetCreativeSlot) packet);
		} else if (packetId == 0x14) {
			handlePacket((PacketNamedEntitySpawn) packet, player);
		} else if (packetId == 0x68) {
			handlePacket((PacketWindowItems) packet);
		}
		return true;
	}

	/**
	 * Handle a single packet
	 * 
	 * @param packet
	 *            the packet to handler
	 */
	private void handlePacket(PacketEntityEquipment packet) {
		if (packet.getItem() == null
				|| packet.getItem().id < RockyMaterialManager.DEFAULT_ITEM_PLACEHOLDER_ID) {
			return;
		}
		packet.setItem(setVanillaData(packet.getItem(), RockyManager
				.getMaterialManager().getItem(packet.getItem().id)));
	}

	/**
	 * Handle a single packet
	 * 
	 * @param packet
	 *            the packet to handler
	 * @param player
	 *            the player owner of the packet
	 */
	private void handlePacket(PacketEntityMetadata packet, RockyPlayer player) {
	}

	/**
	 * Handle a single packet
	 * 
	 * @param packet
	 *            the packet to handler
	 */
	private void handlePacket(PacketWindowClick packet) {
		if (packet.getItem() == null
				|| packet.getItem().id < RockyMaterialManager.DEFAULT_ITEM_PLACEHOLDER_ID) {
			return;
		}
		packet.setItem(setVanillaData(packet.getItem(), RockyManager
				.getMaterialManager().getItem(packet.getItem().id)));
	}

	/**
	 * Handle a single packet
	 * 
	 * @param packet
	 *            the packet to handler
	 */
	private void handlePacket(PacketSetSlot packet) {
		if (packet.getItem() == null
				|| packet.getItem().id < RockyMaterialManager.DEFAULT_ITEM_PLACEHOLDER_ID) {
			return;
		}
		packet.setItem(setVanillaData(packet.getItem(), RockyManager
				.getMaterialManager().getItem(packet.getItem().id)));
	}

	/**
	 * Handle a single packet
	 * 
	 * @param packet
	 *            the packet to handler
	 */
	private void handlePacket(PacketSetCreativeSlot packet) {
		if (packet.getItem() == null
				|| packet.getItem().id < RockyMaterialManager.DEFAULT_ITEM_PLACEHOLDER_ID) {
			return;
		}
		packet.setItem(setVanillaData(packet.getItem(), RockyManager
				.getMaterialManager().getItem(packet.getItem().id)));
	}

	/**
	 * Handle a single packet
	 * 
	 * @param packet
	 *            the packet to handler
	 */
	private void handlePacket(PacketWindowItems packet) {
		ItemStack[] stacks = ((PacketWindowItems) packet).getItems();
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i] != null
					&& stacks[i].id >= RockyMaterialManager.DEFAULT_ITEM_PLACEHOLDER_ID) {
				stacks[i] = setVanillaData(stacks[i], RockyManager
						.getMaterialManager().getItem(stacks[i].id));
			}
		}
		packet.setItems(stacks);
	}

	/**
	 * Handle a single packet
	 * 
	 * @param packet
	 *            the packet to handler
	 * @param player
	 *            the player owner of the packet
	 */
	private void handlePacket(PacketNamedEntitySpawn packet, RockyPlayer player) {
		if (packet.getCurrentItem() < RockyMaterialManager.DEFAULT_ITEM_PLACEHOLDER_ID) {
			return;
		}
		packet.setCurrentItem(RockyManager.getMaterialManager()
				.getItem(packet.getCurrentItem()).getDefaultId());
		if (player.getTitle().equals("") || player.isModded()) {
			return;
		}
		RockyPlayer otherPlayer = RockyManager.getPlayerFromId(packet.getId());
		if (player != null) {
			Bukkit.getPluginManager().callEvent(
					new PlayerEnterArea(otherPlayer, player));
		}
		String title = otherPlayer.getTitle(player).replace("[Hide]", "");
		packet.setPlayerName(title);
	}

	/**
	 * Handle a single packet
	 * 
	 * @param packet
	 *            the packet to handler
	 * @param player
	 *            the player owner of the packet
	 */
	private void handlePacket(PacketDestroyEntity packet, RockyPlayer player) {
		int[] ids = packet.getEntitiesId();
		for (int id : ids) {
			RockyPlayer otherPlayer = RockyManager.getPlayerFromId(id);
			if (player != null) {
				Bukkit.getPluginManager().callEvent(
						new PlayerLeaveArea(otherPlayer, player));
			}
		}
	}

	/**
	 * Sets the vanilla data of a custom item.
	 * 
	 * @param stack
	 *            The vanilla stack
	 * @param item
	 *            The custom item
	 */
	private ItemStack setVanillaData(ItemStack stack, Item item) {
		ItemStack copy = stack.cloneItemStack();

		copy.id = item.getDefaultId();
		copy.c(item.getName());

		NBTTagCompound tag = copy.tag.getCompound("display");
		NBTTagList list = new NBTTagList();
		List<String> lore = item.getLore();

		if (lore.size() > 0) {
			for (String loreName : lore) {
				list.add(new NBTTagString("", loreName));
			}
			tag.set("Lore", list);
		}
		return copy;
	}
}
