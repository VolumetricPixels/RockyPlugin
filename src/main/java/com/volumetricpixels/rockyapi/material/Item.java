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
package com.volumetricpixels.rockyapi.material;

import java.io.IOException;

import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public interface Item extends Material {
	/**
	 * 
	 * @return
	 */
	public int getTypeId();
	
	/**
	 * 
	 * @param isFuel
	 * @return
	 */
	public Item setAllowToBurn(boolean isFuel);

	/**
	 * 
	 * @return
	 */
	public boolean isAllowToBurn();

	/**
	 * 
	 */
	public Item setStackable(boolean stackable);

	/**
	 * 
	 */
	public boolean isStackable();
	
	/**
	 * 
	 */
	public Texture getTexture();
	
	/**
	 * 
	 * @return
	 */
	public boolean isThrowable();
	
	/**
	 * 
	 * @param isThrowable
	 * @return
	 */
	public Item setThrowable(boolean isThrowable);
	
	/**
	 * 
	 * @param out
	 * @throws IOException
	 */
	public void writeToPacket(PacketOutputStream out) throws IOException;
}
