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
 * When a Scrollable scrollbar will be shown.
 */
public enum ScrollBarPolicy {
	/**
	 * Shows the scrollbar when getMaximumScrollPosition is greater than 0
	 */
	SHOW_IF_NEEDED(0),
	/**
	 * Never show the scrollbar. However, you'll still be able to scroll with
	 * the scroll wheel or your trackpad or with arrow keys if the widget
	 * implemented that (like the list widget).
	 */
	SHOW_NEVER(1),
	/**
	 * Always show the scrollbar
	 */
	SHOW_ALWAYS(2);
	private final int id;
	private final static HashMap<Integer, ScrollBarPolicy> ids = new HashMap<Integer, ScrollBarPolicy>();

	/**
	 * 
	 * @param id
	 */
	private ScrollBarPolicy(int id) {
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
	public static ScrollBarPolicy getById(int id) {
		return ids.get(id);
	}

	/**
	 * 
	 */
	static {
		for (ScrollBarPolicy s : values()) {
			ids.put(s.id, s);
		}
	}
}