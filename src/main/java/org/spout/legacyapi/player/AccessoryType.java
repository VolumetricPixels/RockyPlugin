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

package org.spout.legacyapi.player;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public enum AccessoryType {
	/**
	 * 
	 */
	TOPHAT(1),
	/**
	 * 
	 */
	NOTCHHAT(2),
	/**
	 * 
	 */
	BRACELET(3),
	/**
	 * 
	 */
	WINGS(4),
	/**
	 * 
	 */
	EARS(5),
	/**
	 * 
	 */
	SUNGLASSES(6),
	/**
	 * 
	 */
	TAIL(7);

	private final int id;
	private static Map<Integer, AccessoryType> types = new HashMap<Integer, AccessoryType>();

	/**
	 * 
	 */
	static {
		for (AccessoryType type : AccessoryType.values()) {
			types.put(type.getId(), type);
		}
	}

	/**
	 * 
	 * @param id
	 */
	private AccessoryType(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static AccessoryType get(int id) {
		return types.get(id);
	}
}
