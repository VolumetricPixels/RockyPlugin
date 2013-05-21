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
package com.volumetricpixels.rockyapi.material;

import java.io.IOException;
import java.util.List;

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
	List<String> getLore();

	/**
	 * 
	 * @param description
	 */
	void addLore(String description);

	/**
	 * 
	 * @param index
	 */
	void removeLore(int index);

	/**
	 * 
	 * @return
	 */
	MaterialTab getCreativeTab();

	/**
	 * 
	 * @param tab
	 * @return
	 */
	Item setCreativeTab(MaterialTab tab);

	/**
	 * 
	 * @return
	 */
	int getTypeId();

	/**
	 * 
	 * @param isFuel
	 * @return
	 */
	Item setAllowToBurn(boolean isFuel);

	/**
	 * 
	 * @return
	 */
	boolean isAllowToBurn();

	/**
	 * 
	 */
	Item setStackable(boolean stackable);

	/**
	 * 
	 */
	boolean isStackable();

	/**
	 * 
	 */
	Texture getTexture();

	/**
	 * 
	 * @return
	 */
	boolean isThrowable();

	/**
	 * 
	 * @param isThrowable
	 * @return
	 */
	Item setThrowable(boolean isThrowable);

	/**
	 * 
	 * @param out
	 * @throws IOException
	 */
	void writeToPacket(PacketOutputStream out) throws IOException;
}
