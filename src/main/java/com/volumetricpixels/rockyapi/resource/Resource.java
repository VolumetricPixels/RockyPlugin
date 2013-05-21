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
package com.volumetricpixels.rockyapi.resource;

import java.io.IOException;

import com.volumetricpixels.rockyapi.packet.PacketOutputStream;

/**
 * 
 * @param <T>
 */
public interface Resource {

	/**
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 
	 * @return
	 */
	long getRevision();

	/**
	 * 
	 * @param revision
	 */
	void setRevision(long revision);

	/**
	 * 
	 * @return
	 */
	<T> T getData();

	/**
	 * 
	 * @param data
	 */
	<T> void setData(T data);

	/**
	 * 
	 * @param out
	 * @throws IOException
	 */
	void writeToPacket(PacketOutputStream out) throws IOException;
}
