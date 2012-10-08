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
package org.spout.legacyapi.gui;

import java.util.HashMap;

/**
 * Types of animation, only one animation is permitted at a time, and note that
 * some types are limited to certain widget types...
 */
public enum WidgetAnim {
	/**
	 * No animation (default).
	 */
	NONE(0),
	/**
	 * Change the X by "value" pixels (any Widget).
	 */
	POS_X(1),
	/**
	 * Change the Y by "value" pixels (any Widget).
	 */
	POS_Y(2),
	/**
	 * Change the Width by "value" pixels (any Widget).
	 */
	WIDTH(3),
	/**
	 * Change the Height by "value" pixels (any Widget).
	 */
	HEIGHT(4),
	/**
	 * Change the Left offset by "value" pixels (Texture only).
	 */
	OFFSET_LEFT(5),
	/**
	 * Change the Top offset by "value" pixels (Texture only).
	 */
	OFFSET_TOP(6);

	private final int id;
	private final static HashMap<Integer, WidgetAnim> lookupId = new HashMap<Integer, WidgetAnim>();

	/**
	 * 
	 * @param id
	 */
	private WidgetAnim(int id) {
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
		for (WidgetAnim t : values()) {
			lookupId.put(t.getId(), t);
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static WidgetAnim getAnimationFromId(int id) {
		return lookupId.get(id);
	}

}
