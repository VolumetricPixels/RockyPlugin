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
package org.spout.legacyapi.packet;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

import org.spout.legacyapi.math.Color;

/**
 * 
 */
public class PacketOutputStream extends OutputStream implements DataOutput {

	private ByteBuffer buffer;

	/**
	 * 
	 */
	public PacketOutputStream() {
		this(256);
	}

	/**
	 * 
	 * @param lenght
	 */
	public PacketOutputStream(int lenght) {
		buffer = ByteBuffer.allocate(lenght).order(ByteOrder.nativeOrder());
	}

	/**
	 * 
	 * @return
	 */
	public ByteBuffer getRawBuffer() {
		return buffer;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeBoolean(boolean v) throws IOException {
		writeByte(v ? 1 : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeByte(int v) throws IOException {
		ensureCapacity(1);
		buffer.put((byte) v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeShort(int v) throws IOException {
		ensureCapacity(2);
		buffer.putShort((short) v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeChar(int v) throws IOException {
		ensureCapacity(2);
		buffer.putChar((char) v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeInt(int v) throws IOException {
		ensureCapacity(4);
		buffer.putInt(v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeLong(long v) throws IOException {
		ensureCapacity(8);
		buffer.putLong(v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeFloat(float v) throws IOException {
		ensureCapacity(4);
		buffer.putFloat(v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeDouble(double v) throws IOException {
		ensureCapacity(8);
		buffer.putDouble(v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeBytes(String s) throws IOException {
		writeShort(s.length());
		for (int i = 0, j = s.length(); i < j; i++)
			writeChar(s.charAt(i));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeChars(String s) throws IOException {
		for (int i = 0, j = s.length(); i < j; i++)
			writeChar(s.charAt(i));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeUTF(String s) throws IOException {
		writeBytes(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(int b) throws IOException {
		writeInt(b);
	}

	/**
	 * 
	 * @param array
	 * @throws IOException
	 */
	public void writeUTFArray(String[] array) throws IOException {
		writeShort(array.length);
		for (String node : array)
			writeUTF(node);
	}

	/**
	 * 
	 * @param color
	 * @throws IOException
	 */
	public void writeColor(Color color) throws IOException {
		writeFloat(color.toFloatBits());
	}
	
	/**
	 * 
	 * @param uuid
	 * @throws IOException
	 */
	public void writeUUID(UUID uuid) throws IOException {
		writeLong(uuid.getLeastSignificantBits());
		writeLong(uuid.getMostSignificantBits());
	}

	/**
	 * 
	 * @param capacity
	 */
	private void ensureCapacity(int capacity) {
		if (buffer.capacity() >= capacity) {
			return;
		}
		ByteBuffer replacement = ByteBuffer.allocate(buffer.capacity() * 2);
		replacement.put(buffer);
		replacement.position(buffer.position());
		buffer = replacement;
	}

}
