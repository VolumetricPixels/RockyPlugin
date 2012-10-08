/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2011-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;
import org.spout.legacyapi.packet.PacketType;
import org.spout.legacyapi.player.SpoutPlayer;

import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

/**
 * 
 */
public class SpoutPacket extends Packet {

	private org.spout.legacyapi.packet.Packet packet;
	private boolean isSuccess = false;

	/**
	 * 
	 * @param packet
	 */
	public SpoutPacket(org.spout.legacyapi.packet.Packet packet) {
		this.packet = packet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int a() {
		return 8;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void a(DataInputStream arg0) throws IOException {
		int packetID = arg0.readShort();
		int version = arg0.readShort();
		int length = arg0.readInt();

		if (version > -1) {
			try {
				packet = PacketType.getPacketFromId(packetID).getClazz()
						.newInstance();
			} catch (Throwable e) {
				SpoutManager.printConsole("Failed to identify packet id: ",
						packetID);
			}
		}
		try {
			if (packet == null || packet.getVersion() != version) {
				arg0.skipBytes(length);
			} else {
				byte[] data = new byte[length];
				arg0.readFully(data);

				PacketInputStream in = new PacketInputStream(
						ByteBuffer.wrap(data));
				packet.readData(in);

				isSuccess = true;
			}
		} catch (Throwable ex) {
			SpoutManager.printConsole("------------------------");
			SpoutManager.printConsole("Unexpected Exception: "
					+ PacketType.getPacketFromId(packetID) + ", " + packetID);
			ex.printStackTrace();
			SpoutManager.printConsole("------------------------");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void a(DataOutputStream arg0) throws IOException {
		arg0.writeShort(packet.getType().getId());
		arg0.writeShort(packet.getVersion());

		PacketOutputStream out = new PacketOutputStream();
		packet.writeData(out);

		ByteBuffer buffer = out.getRawBuffer();
		buffer.flip();
		arg0.writeInt(buffer.limit());
		arg0.write(buffer.array());
		buffer.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(NetHandler arg0) {
		if (!(arg0 instanceof SpoutPacketHandler)) {
			return;
		}
		SpoutPlayer player = SpoutManager
				.getPlayerFromId(((SpoutPacketHandler) arg0).getPlayer()
						.getEntityId());
		if (isSuccess)
			packet.handle(player);
		else
			packet.failure(player);
	}

}
