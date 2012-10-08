/*
 * This file is part of SpoutPlugin.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutPlugin is licensed under the GNU Lesser General Public License.
 *
 * SpoutPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spout.legacy.packet;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import org.fest.reflect.core.Reflection;
import org.spout.legacy.Spout;
import org.spout.legacy.item.SpoutItem;
import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.player.SpoutPlayer;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.INetworkManager;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet102WindowClick;
import net.minecraft.server.Packet103SetSlot;
import net.minecraft.server.Packet104WindowItems;
import net.minecraft.server.Packet107SetCreativeSlot;
import net.minecraft.server.Packet14BlockDig;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet250CustomPayload;

/**
 * 
 */
public class SpoutPacketHandler extends NetServerHandler {
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
	public SpoutPacketHandler(MinecraftServer minecraftserver,
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
		SpoutPlayer player = (SpoutPlayer) SpoutManager.getPlayer(getPlayer());
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
			SpoutPlayer player = (SpoutPlayer) SpoutManager
					.getPlayer(getPlayer());
			Spout.getInstance().handlePlayerAuthentication(player);
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
			SpoutPlayer player = (SpoutPlayer) SpoutManager
					.getPlayer(getPlayer());
			if (!SpoutManager.getPacketManager().isAllowedToSend(player,
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
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet102WindowClick packet) {
		//if (packet.item != null) {
		//	ItemStack custom = SpoutManager.getPlayer(getPlayer()).getHandle().inventory.items[packet.slot];
		//	if (custom != null & custom.id >= SpoutItem.DEFAULT_PLACEHOLDER_ID) {
		//		packet.item = custom;
		//	}
		//}
		super.a(packet);
	}

	/**
	 * 
	 * @param packet
	 */
	private void checkForInvalidStack(Packet packet) {
		if (SpoutManager.getPlayer(getPlayer()).isSpoutEnabled()) {
			return;
		}
		ItemStack stack = null;

		// Check all packets that use ItemStack
		switch (packet.k()) {
		case 5:
			stack = Reflection.field("c").ofType(ItemStack.class).in(packet)
					.get();
			break;
		case 20:
			if (((Packet20NamedEntitySpawn) packet).h >= SpoutItem.DEFAULT_PLACEHOLDER_ID)
				((Packet20NamedEntitySpawn) packet).h = SpoutItem.DEFAULT_ITEM_FOR_VANILLA;
			break;
		case 103:
			stack = ((Packet103SetSlot) packet).c;
			break;
		case 104:
			ItemStack[] stacks = ((Packet104WindowItems) packet).b;
			for (ItemStack itemStack : stacks)
				if (itemStack != null
						&& itemStack.id >= SpoutItem.DEFAULT_PLACEHOLDER_ID)
					itemStack.id = SpoutItem.DEFAULT_ITEM_FOR_VANILLA;
			break;
		case 107:
			stack = ((Packet107SetCreativeSlot) packet).b;
			break;
		}

		// The stack contain a custom id and we don't have a custom client
		if (stack != null && stack.id >= SpoutItem.DEFAULT_PLACEHOLDER_ID) {
			stack.id = SpoutItem.DEFAULT_ITEM_FOR_VANILLA;
		}
	}
}
