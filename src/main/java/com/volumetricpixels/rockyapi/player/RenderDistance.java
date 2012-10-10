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
package com.volumetricpixels.rockyapi.player;

/**
 * 
 */
public enum RenderDistance {
	/**
	 * 
	 */
	FAR(0),
	/**
	 * 
	 */
	NORMAL(1),
	/**
	 * 
	 */
	SHORT(2),
	/**
	 * 
	 */
	TINY(3);

	private final int value;

	/**
	 * 
	 * @param i
	 */
	private RenderDistance(final int i) {
		value = i;
	}

	/**
	 * 
	 * @return
	 */
	public final int getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static RenderDistance getRenderDistanceFromValue(int value) {
		for (RenderDistance rd : values()) {
			if (rd.getValue() == value) {
				return rd;
			}
		}
		return null;
	}
}
