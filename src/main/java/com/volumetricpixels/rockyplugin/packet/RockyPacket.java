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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.packet.PacketType;
import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public class RockyPacket extends Packet {

	private com.volumetricpixels.rockyapi.packet.Packet packet;
	private boolean isSuccess = false;

	/**
	 * 
	 * @param packet
	 */
	public RockyPacket(com.volumetricpixels.rockyapi.packet.Packet packet) {
		this.packet = packet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int a() {
		return 2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void a(DataInputStream arg0) throws IOException {
		int packetID = arg0.readShort();
		int length = arg0.readShort();

		try {
			packet = PacketType.getPacketFromId(packetID).getClazz()
					.newInstance();
		} catch (Throwable e) {
			RockyManager.printConsole("Failed to identify packet id: ",
					packetID);
		}
		try {
			byte[] data = new byte[length];
			arg0.readFully(data);

			PacketInputStream in = new PacketInputStream(ByteBuffer.wrap(data));
			packet.readData(in);

			isSuccess = true;
		} catch (Throwable ex) {
			RockyManager.printConsole("------------------------");
			RockyManager.printConsole("Unexpected Exception: "
					+ PacketType.getPacketFromId(packetID) + ", " + packetID);
			ex.printStackTrace();
			RockyManager.printConsole("------------------------");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void a(DataOutputStream arg0) throws IOException {
		arg0.writeShort(packet.getType().getId());

		PacketOutputStream out = new PacketOutputStream();
		packet.writeData(out);

		ByteBuffer buffer = out.getRawBuffer();
		buffer.flip();
		arg0.writeShort(buffer.limit());
		arg0.write(buffer.array());
		buffer.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(NetHandler arg0) {
		if (!(arg0 instanceof RockyPacketHandler)) {
			return;
		}
		RockyPlayer player = RockyManager
				.getPlayerFromId(((RockyPacketHandler) arg0).getPlayer()
						.getEntityId());
		if (isSuccess)
			packet.handle(player);
		else
			packet.failure(player);
	}

}
