/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
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
package com.volumetricpixels.rockyapi.gui;

import java.util.HashMap;

/**
 * This is used to define the orientation for Scrollable widgets.
 */
public enum Orientation {
	/**
	 * Horizontal axis (left-right)
	 */
	HORIZONTAL(0),
	/**
	 * Vertical axis (top-bottom)
	 */
	VERTICAL(1);

	private final int id;
	private final static HashMap<Integer, Orientation> lookupId = new HashMap<Integer, Orientation>();

	/**
	 * 
	 * @param id
	 */
	private Orientation(int id) {
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
	 */
	static {
		for (Orientation t : values()) {
			lookupId.put(t.getId(), t);
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Orientation getOrientationFromId(int id) {
		return lookupId.get(id);
	}

	/**
	 * 
	 * @return
	 */
	public Orientation getOther() {
		switch (this) {
		case HORIZONTAL:
			return VERTICAL;
		case VERTICAL:
			return HORIZONTAL;
		}
		return null;
	}
}
