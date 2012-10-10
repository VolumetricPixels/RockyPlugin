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
package com.volumetricpixels.rockyapi.gui;

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
