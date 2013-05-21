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

/**
 * 
 */
public enum MaterialTab {
	/**
	 * 
	 */
	BLOCK(0),
	/**
	 * 
	 */
	DECORATION(1),
	/**
	 * 
	 */
	REDSTONE(2),
	/**
	 * 
	 */
	TRANSPORT(3),
	/**
	 * 
	 */
	MISC(4),
	/**
	 * 
	 */
	FOOD(6),
	/**
	 * 
	 */
	TOOL(7),
	/**
	 * 
	 */
	COMBAT(8),
	/**
	 * 
	 */
	BREWING(9),
	/**
	 * 
	 */
	MATERIAL(10),
	/**
	 * 
	 */
	CUSTOM_ITEM(12);
	
	private int id;
	
	/**
	 * 
	 * @param id
	 */
	private MaterialTab(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
}
