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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 */
public abstract class AbstractListModel {
	/**
	 * 
	 */
	private HashSet<GenericListView> views = new HashSet<GenericListView>();

	/**
	 * 
	 * @param row
	 * @return
	 */
	public abstract ListWidgetItem getItem(int row);

	/**
	 * 
	 * @return
	 */
	public abstract int getSize();

	/**
	 * 
	 * @param item
	 * @param doubleClick
	 */
	public abstract void onSelected(int item, boolean doubleClick);

	/**
	 * 
	 * @param view
	 */
	public void addView(GenericListView view) {
		views.add(view);
	}

	/**
	 * 
	 * @param view
	 */
	public void removeView(GenericListView view) {
		views.remove(view);
	}

	/**
	 * 
	 */
	public void sizeChanged() {
		for (GenericListView view : views)
			view.sizeChanged();
	}

	/**
	 * 
	 * @return
	 */
	public Set<GenericListView> getViews() {
		return Collections.unmodifiableSet(views);
	}
}
