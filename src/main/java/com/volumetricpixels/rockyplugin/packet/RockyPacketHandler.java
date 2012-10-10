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
package com.volumetricpixels.rockyplugin.packet;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Bukkit;
import org.fest.reflect.core.Reflection;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.event.player.PlayerEnterPlayerArea;
import com.volumetricpixels.rockyapi.event.player.PlayerLeavePlayerArea;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyplugin.Rocky;
import com.volumetricpixels.rockyplugin.RockyMaterialManager;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.INetworkManager;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet103SetSlot;
import net.minecraft.server.Packet104WindowItems;
import net.minecraft.server.Packet107SetCreativeSlot;
import net.minecraft.server.Packet14BlockDig;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet250CustomPayload;
import net.minecraft.server.Packet29DestroyEntity;

/**
 * 
 */
public class RockyPacketHandler extends NetServerHandler {
	/**
	 * 
	 */
	private final static int QUEUE_PACKET_SIZE = 9437184;

	private LinkedBlockingDeque<Packet> resyncQueue = new LinkedBlockingDeque<Packet>();
	private AtomicBoolean processingKick = new AtomicBoolean(false);

	/**
	 * 
	 * @param minecraftserver
	 * @param inetworkmanager
	 * @param entityplayer
	 */
	public RockyPacketHandler(MinecraftServer minecraftserver,
			INetworkManager inetworkmanager, EntityPlayer entityplayer) {
		super(minecraftserver, inetworkmanager, entityplayer);
		Reflection
				.field("y")
				.ofType(double.class)
				.in(this)
				.set(Reflection.field("y").ofType(double.class).in(this).get()
						- QUEUE_PACKET_SIZE);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet14BlockDig packet) {
		RockyPlayer player = (RockyPlayer) RockyManager.getPlayer(getPlayer());
		boolean inAir = false;
		if (player.canFly() && !player.getHandle().onGround) {
			inAir = true;
			player.getHandle().onGround = true;
		}
		super.a(packet);
		if (inAir)
			player.getHandle().onGround = false;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet250CustomPayload packet250custompayload) {
		if (packet250custompayload.tag.equals("TM|Spout")) {
			RockyPlayer player = (RockyPlayer) RockyManager
					.getPlayer(getPlayer());
			Rocky.getInstance().handlePlayerAuthentication(player);
		} else
			super.a(packet250custompayload);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void sendPacket(Packet packet) {
		if (packet == null) {
			return;
		}
		checkForInvalidStack(packet);
		queueOutputPacket(packet);
		checkForPostPacket(packet);
	}

	/**
	 * 
	 * @param packet
	 */
	public void sendImmediatePacket(Packet packet) {
		if (packet == null) {
			return;
		}
		resyncQueue.addFirst(packet);
	}

	/**
	 * 
	 * @param packet
	 */
	public void queueOutputPacket(Packet packet) {
		if (packet == null) {
			return;
		}
		resyncQueue.addLast(packet);
		if (processingKick.get())
			syncFlushPacketQueue();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void d() {
		syncFlushPacketQueue();
		super.d();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void disconnect(String kick) {
		processingKick.set(true);
		super.disconnect(kick);
		if (this.disconnected) {
			syncFlushPacketQueue();
		}
		processingKick.set(false);
	}

	/**
	 * 
	 */
	public void syncFlushPacketQueue() {
		while (!resyncQueue.isEmpty()) {
			Packet p = resyncQueue.pollFirst();
			if (p != null) {
				syncedSendPacket(p);
			}
		}
	}

	/**
	 * 
	 * @param packet
	 * @param packetWrappers
	 */
	private void syncedSendPacket(Packet packet) {
		int packetId = -1;
		try {
			packetId = packet.k();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			RockyPlayer player = (RockyPlayer) RockyManager
					.getPlayer(getPlayer());
			if (!RockyManager.getPacketManager().isAllowedToSend(player,
					packetId)) {
				return;
			} else {
				super.sendPacket(packet);
			}
		} catch (NullPointerException npe) {
			throw new RuntimeException(
					"Null pointer exception thrown when trying to process packet of type "
							+ packet.getClass().getName(), npe);
		}
	}

	/**
	 * 
	 * @param packet
	 */
	private void checkForInvalidStack(Packet packet) {
		if (RockyManager.getPlayer(getPlayer()).isModded()) {
			return;
		}
		ItemStack stack = null;

		// Check all packets that use ItemStack
		switch (packet.k()) {
		case 0x5:
			stack = Reflection.field("c").ofType(ItemStack.class).in(packet)
					.get();
			break;
		case 0x14:
			if (((Packet20NamedEntitySpawn) packet).h >= RockyMaterialManager.DEFAULT_ITEM_PLACEHOLDER_ID)
				((Packet20NamedEntitySpawn) packet).h = RockyMaterialManager.DEFAULT_ITEM_FOR_VANILLA;
			break;
		case 0x67:
			stack = ((Packet103SetSlot) packet).c;
			break;
		case 0x68:
			ItemStack[] stacks = ((Packet104WindowItems) packet).b;
			for (ItemStack itemStack : stacks)
				if (itemStack != null
						&& itemStack.id >= RockyMaterialManager.DEFAULT_ITEM_PLACEHOLDER_ID)
					itemStack.id = RockyMaterialManager.DEFAULT_ITEM_FOR_VANILLA;
			break;
		case 0x6B:
			stack = ((Packet107SetCreativeSlot) packet).b;
			break;
		}

		// The stack contain a custom id and we don't have a custom client
		if (stack != null
				&& stack.id >= RockyMaterialManager.DEFAULT_ITEM_PLACEHOLDER_ID) {
			stack.id = RockyMaterialManager.DEFAULT_ITEM_FOR_VANILLA;
		}
	}

	/**
	 * 
	 * @param packet
	 */
	private void checkForPostPacket(Packet packet) {
		RockyPlayer player = null;
		switch (packet.k()) {
		case 0x14:
			player = RockyManager
					.getPlayerFromId(((Packet20NamedEntitySpawn) packet).a);
			if (player != null) {
				Bukkit.getPluginManager().callEvent(
						new PlayerEnterPlayerArea(player, RockyManager
								.getPlayer(getPlayer())));
			}
			break;
		case 0x1D:
			int[] ids = ((Packet29DestroyEntity) packet).a;
			for (int id : ids) {
				player = RockyManager.getPlayerFromId(id);
				if (player != null)
					Bukkit.getPluginManager().callEvent(
							new PlayerLeavePlayerArea(player, RockyManager
									.getPlayer(getPlayer())));
			}
			break;
		}

	}

}
