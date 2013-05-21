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
package com.volumetricpixels.rockyplugin.packet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.INetworkManager;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet0KeepAlive;
import net.minecraft.server.v1_5_R3.Packet102WindowClick;
import net.minecraft.server.v1_5_R3.Packet106Transaction;
import net.minecraft.server.v1_5_R3.Packet107SetCreativeSlot;
import net.minecraft.server.v1_5_R3.Packet108ButtonClick;
import net.minecraft.server.v1_5_R3.Packet10Flying;
import net.minecraft.server.v1_5_R3.Packet130UpdateSign;
import net.minecraft.server.v1_5_R3.Packet14BlockDig;
import net.minecraft.server.v1_5_R3.Packet15Place;
import net.minecraft.server.v1_5_R3.Packet16BlockItemSwitch;
import net.minecraft.server.v1_5_R3.Packet18ArmAnimation;
import net.minecraft.server.v1_5_R3.Packet19EntityAction;
import net.minecraft.server.v1_5_R3.Packet202Abilities;
import net.minecraft.server.v1_5_R3.Packet203TabComplete;
import net.minecraft.server.v1_5_R3.Packet204LocaleAndViewDistance;
import net.minecraft.server.v1_5_R3.Packet205ClientCommand;
import net.minecraft.server.v1_5_R3.Packet250CustomPayload;
import net.minecraft.server.v1_5_R3.Packet255KickDisconnect;
import net.minecraft.server.v1_5_R3.Packet3Chat;
import net.minecraft.server.v1_5_R3.Packet7UseEntity;
import net.minecraft.server.v1_5_R3.PlayerConnection;

import org.fest.reflect.core.Reflection;
import org.fest.reflect.exception.ReflectionError;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.packet.protocol.PacketType;
import com.volumetricpixels.rockyapi.player.RenderDistance;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyplugin.Rocky;
import com.volumetricpixels.rockyplugin.chunk.ChunkCacheHandler;
import com.volumetricpixels.rockyplugin.player.RockyPlayerHandler;
import com.volumetricpixels.rockyplugin.reflection.Reflector;
import com.volumetricpixels.rockyplugin.reflection.ReflectorID;

/**
 * 
 */
public class RockyPacketHandler extends PlayerConnection {
	/**
	 * 
	 */
	private static final int QUEUE_PACKET_SIZE = 9437184;

	private LinkedBlockingDeque<Packet> resyncQueue = new LinkedBlockingDeque<Packet>();
	private LinkedBlockingDeque<Packet> offlineQueue = new LinkedBlockingDeque<Packet>();

	private static AtomicLong totalPacketUp = new AtomicLong(0),
			totalPacketDown = new AtomicLong(0);
	private static AtomicLong loggingStart = new AtomicLong(
			System.currentTimeMillis());

	/**
	 * 
	 * @param minecraftserver
	 * @param inetworkmanager
	 * @param entityplayer
	 */
	public RockyPacketHandler(MinecraftServer minecraftserver,
			INetworkManager inetworkmanager, EntityPlayer entityplayer) {
		super(minecraftserver, inetworkmanager, entityplayer);

		// Only change "y" if we are using native craftbukkit, otherwise we
		// could be using SpigotMC Netty network manager.
		try {
			Reflection
					.field(Reflector
							.getField(ReflectorID.PLAYER_PACKET_HANDLER_Y))
					.ofType(int.class)
					.in(networkManager)
					.set(Reflection
							.field(Reflector
									.getField(ReflectorID.PLAYER_PACKET_HANDLER_Y))
							.ofType(int.class).in(networkManager).get()
							- QUEUE_PACKET_SIZE);
		} catch (ReflectionError ex) {
		}
	}

	/**
	 * 
	 * @return
	 */
	public static AtomicLong getLoggingTime() {
		return loggingStart;
	}

	/**
	 * 
	 * @return
	 */
	public static AtomicLong getTotalDownloaded() {
		return totalPacketDown;
	}

	/**
	 * 
	 * @return
	 */
	public static AtomicLong getPacketUploaded() {
		return totalPacketUp;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet14BlockDig packet14blockdig) {
		super.a(packet14blockdig);
		totalPacketDown.addAndGet(packet14blockdig.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet15Place packet15place) {
		super.a(packet15place);
		totalPacketDown.addAndGet(packet15place.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet10Flying packet10flying) {
		super.a(packet10flying);
		totalPacketDown.addAndGet(packet10flying.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void onUnhandledPacket(Packet packet) {
		super.onUnhandledPacket(packet);
		totalPacketDown.addAndGet(packet.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet16BlockItemSwitch packet16blockitemswitch) {
		super.a(packet16blockitemswitch);
		totalPacketDown.addAndGet(packet16blockitemswitch.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet3Chat packet3chat) {
		super.a(packet3chat);
		totalPacketDown.addAndGet(packet3chat.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet18ArmAnimation packet18armanimation) {
		super.a(packet18armanimation);
		totalPacketDown.addAndGet(packet18armanimation.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet19EntityAction packet19entityaction) {
		super.a(packet19entityaction);
		totalPacketDown.addAndGet(packet19entityaction.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet255KickDisconnect packet255kickdisconnect) {
		super.a(packet255kickdisconnect);
		totalPacketDown.addAndGet(packet255kickdisconnect.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet7UseEntity packet7useentity) {
		super.a(packet7useentity);
		totalPacketDown.addAndGet(packet7useentity.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet205ClientCommand packet205clientcommand) {
		super.a(packet205clientcommand);
		totalPacketDown.addAndGet(packet205clientcommand.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet102WindowClick packet102windowclick) {
		super.a(packet102windowclick);
		totalPacketDown.addAndGet(packet102windowclick.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet108ButtonClick packet108buttonclick) {
		super.a(packet108buttonclick);
		totalPacketDown.addAndGet(packet108buttonclick.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet107SetCreativeSlot packet107setcreativeslot) {
		super.a(packet107setcreativeslot);
		totalPacketDown.addAndGet(packet107setcreativeslot.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet106Transaction packet106transaction) {
		super.a(packet106transaction);
		totalPacketDown.addAndGet(packet106transaction.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet130UpdateSign packet130updatesign) {
		super.a(packet130updatesign);
		totalPacketDown.addAndGet(packet130updatesign.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet0KeepAlive packet0keepalive) {
		super.a(packet0keepalive);
		totalPacketDown.addAndGet(packet0keepalive.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet202Abilities packet202abilities) {
		super.a(packet202abilities);
		totalPacketDown.addAndGet(packet202abilities.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet203TabComplete packet203tabcomplete) {
		super.a(packet203tabcomplete);
		totalPacketDown.addAndGet(packet203tabcomplete.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void a(Packet250CustomPayload packet250custompayload) {
		if (packet250custompayload.tag.equals("Rocky")) {
			// Custom Packets Channel
			RockyPlayerHandler player = (RockyPlayerHandler) RockyManager
					.getPlayer(getPlayer());
			if (!player.isModded()) {
				return;
			}
			readMessagePlugin(packet250custompayload.data).handle(player);
		} else if (packet250custompayload.tag.equals("TM|Rocky")) {
			// Authentication Channel
			RockyPlayer player = (RockyPlayer) RockyManager
					.getPlayer(getPlayer());
			Rocky.getInstance().handlePlayerAuthentication(player);
		} else if (packet250custompayload.tag.equals("TM|Cache")) {
			// Cache Channel
			ChunkCacheHandler.handlePacket(player.getName(),
					packet250custompayload);
		} else {
			super.a(packet250custompayload);
		}
		totalPacketDown.addAndGet(packet250custompayload.a());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void a(Packet204LocaleAndViewDistance packet204localeandviewdistance) {
		RenderDistance distance = RenderDistance
				.getRenderDistanceFromValue(256 >> packet204localeandviewdistance
						.f());
		RockyPlayer player = (RockyPlayer) RockyManager.getPlayer(getPlayer());
		player.setRenderDistance(distance);
		Reflection.field(Reflector.getField(ReflectorID.PACKET_LOCALE_VIEW_B))
				.ofType(int.class).in(packet204localeandviewdistance)
				.set(distance.getValue());
		super.a(packet204localeandviewdistance);
		totalPacketDown.addAndGet(packet204localeandviewdistance.a());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void sendPacket(Packet packet) {
		if (packet == null) {
			return;
		}
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
	}

	/**
	 * 
	 * @param packet
	 */
	public void sendOfflinePacket(Packet packet) {
		if (packet == null) {
			return;
		}
		offlineQueue.add(packet);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void d() {
		syncFlushPacketOffileQueue();
		syncFlushPacketQueue();
		super.d();
	}

	/**
	 * 
	 */
	public void syncFlushPacketOffileQueue() {
		while (!offlineQueue.isEmpty()) {
			Packet p = offlineQueue.pollFirst();
			if (p != null) {
				super.sendPacket(p);
			}
		}
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
	 */
	private void syncedSendPacket(Packet packet) {
		RockyPlayer player = (RockyPlayer) RockyManager.getPlayer(getPlayer());
		if (!RockyManager.getPacketManager().isAllowedToSend(player, packet)) {
			return;
		} else {
			totalPacketUp.addAndGet(packet.a());
			super.sendPacket(packet);
		}
	}

	/**
	 * 
	 * @param packet
	 */
	public void sendMessagePlugin(
			com.volumetricpixels.rockyapi.packet.Packet packet) {
		ByteArrayDataOutput bos = ByteStreams.newDataOutput();
		try {
			bos.writeShort(packet.getType().getId());

			PacketOutputStream out = new PacketOutputStream();
			packet.writeData(out);

			ByteBuffer buffer = out.getRawBuffer();
			buffer.flip();
			bos.writeShort(buffer.limit());
			bos.write(buffer.array());
			buffer.clear();
		} catch (IOException ex) {
			return;
		}
		Packet250CustomPayload payload = new Packet250CustomPayload("Rocky",
				bos.toByteArray());
		sendOfflinePacket(payload);
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public com.volumetricpixels.rockyapi.packet.Packet readMessagePlugin(
			byte[] data) {
		com.volumetricpixels.rockyapi.packet.Packet packet = null;
		ByteArrayDataInput bis = ByteStreams.newDataInput(data);

		int packetID = 0;
		int length = 0;

		try {
			packetID = bis.readShort();
			length = bis.readShort();

			packet = PacketType.getPacketFromId(packetID).getClazz()
					.newInstance();
		} catch (InstantiationException e) {
			RockyManager.printConsole("Failed to identify packet id: ",
					packetID);
		} catch (IllegalAccessException e) {
			RockyManager.printConsole("Failed to identify packet id: ",
					packetID);
		}
		try {
			byte[] dataPacket = new byte[length];
			bis.readFully(dataPacket);

			PacketInputStream in = new PacketInputStream(
					ByteBuffer.wrap(dataPacket));
			packet.readData(in);
		} catch (IOException ex) {
			RockyManager.printConsole("------------------------");
			RockyManager.printConsole("Unexpected Exception: "
					+ PacketType.getPacketFromId(packetID) + ", " + packetID);
			RockyManager.printConsole(ex.getMessage());
			RockyManager.printConsole("------------------------");
		}
		return packet;
	}
}
