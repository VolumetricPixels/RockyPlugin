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
package com.volumetricpixels.rockyapi.packet;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

import com.volumetricpixels.rockyapi.math.Color;

/**
 * 
 */
public class PacketInputStream extends InputStream implements DataInput {

	private ByteBuffer buffer;

	/**
	 * 
	 * @param buffer
	 */
	public PacketInputStream(ByteBuffer buffer) {
		this.buffer = buffer;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public int read() throws IOException {
		return buffer.get();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void readFully(byte[] b) throws IOException {
		buffer.get(b);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		buffer.get(b, off, len);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public int skipBytes(int n) throws IOException {
		int skippedBytes = 0;
		if (buffer.position() + n > buffer.limit()) {
			skippedBytes = buffer.limit() - buffer.position();
		} else {
			skippedBytes = n;
		}
		buffer.position(buffer.position() + skippedBytes);
		return skippedBytes;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public boolean readBoolean() throws IOException {
		return (buffer.get() != 0);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public byte readByte() throws IOException {
		return buffer.get();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public int readUnsignedByte() throws IOException {
		return (short) (0xFF & (int) readByte());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public short readShort() throws IOException {
		return buffer.getShort();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public int readUnsignedShort() throws IOException {
		return (char) ((0xFF & (int) readByte()) << 8 | (0xFF & (int) readByte()));
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public char readChar() throws IOException {
		return buffer.getChar();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public int readInt() throws IOException {
		return buffer.getInt();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public long readLong() throws IOException {
		return buffer.getLong();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public float readFloat() throws IOException {
		return buffer.getFloat();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public double readDouble() throws IOException {
		return buffer.getDouble();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public String readLine() throws IOException {
		return null;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public String readUTF() throws IOException {
		short lenght = readShort();
		StringBuilder builder = new StringBuilder(lenght);
		for (int i = 0; i < lenght; i++) {
			builder.append(buffer.getChar());
		}
		return builder.toString();
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public UUID readUUID() throws IOException {
		return new UUID(readLong(), readLong());
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public Color readColor() throws IOException {
		return new Color(readFloat());
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String[] readUTFArray() throws IOException {
		String[] array = new String[readShort()];
		for(int i = 0; i < array.length; i++) {
			array[i] = readUTF();
		}
		return array;
	}

}
