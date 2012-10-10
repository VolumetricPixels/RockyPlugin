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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;

/**
 * 
 */
public class GenericListWidget extends GenericScrollable implements ListWidget {
	private List<ListWidgetItem> items = new ArrayList<ListWidgetItem>();
	private int selected = -1;
	protected int cachedTotalHeight = -1;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.ListWidget;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidgetItem[] getItems() {
		ListWidgetItem[] sample = {};
		return items.toArray(sample);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidgetItem getItem(int i) {
		if (i == -1) {
			return null;
		}
		ListWidgetItem items[] = getItems();
		if (i >= items.length) {
			return null;
		}
		return items[i];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidget addItem(ListWidgetItem item) {
		items.add(item);
		item.setListWidget(this);
		cachedTotalHeight = -1;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidget addItems(ListWidgetItem... items) {
		for (ListWidgetItem item : items) {
			this.addItem(item);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeItem(ListWidgetItem item) {
		if (items.contains(item)) {
			items.remove(item);
			item.setListWidget(null);
			cachedTotalHeight = -1;
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidgetItem getSelectedItem() {
		return getItem(selected);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSelectedRow() {
		return selected;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidget setSelection(int n) {
		selected = n;
		if (selected < -1) {
			selected = -1;
		}
		if (selected > items.size() - 1) {
			selected = items.size() - 1;
		}

		// Check if selection is visible
		ensureVisible(getItemRect(selected));
		return this;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	protected Rectangle getItemRect(int n) {
		ListWidgetItem item = getItem(n);
		Rectangle result = new Rectangle(0, 0, 0, 0);
		if (item == null) {
			return result;
		}
		result.setX(0);
		result.setY(getItemYOnScreen(n));
		result.setHeight(24);
		result.setWidth(getInnerSize(Orientation.VERTICAL));
		return result;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	protected int getItemYOnScreen(int n) {
		return n * 24;
	}

	/**
	 * 
	 * @return
	 */
	public int getSize() {
		return items.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidget clearSelection() {
		setSelection(-1);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSelected(int n) {
		return selected == n;
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	public ListWidget setScrollPosition(int position) {
		setScrollPosition(Orientation.VERTICAL, position);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public int getScrollPosition() {
		return getScrollPosition(Orientation.VERTICAL);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getInnerSize(Orientation axis) {
		if (axis == Orientation.HORIZONTAL) {
			return getViewportSize(Orientation.HORIZONTAL);
		}
		if (cachedTotalHeight == -1) {
			cachedTotalHeight = getItems().length * 24;
		}
		return cachedTotalHeight + 10;
	}

	/**
	 * 
	 * @return
	 */
	public int getTotalHeight() {
		return getInnerSize(Orientation.VERTICAL);
	}

	/**
	 * 
	 * @return
	 */
	public int getMaxScrollPosition() {
		return getMaximumScrollPosition(Orientation.VERTICAL);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSelected(ListWidgetItem item) {
		if (getSelectedItem() == null) {
			return false;
		}
		return getSelectedItem().equals(item);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidget shiftSelection(int n) {
		if (selected + n < 0) {
			setSelection(0);
		} else {
			setSelection(selected + n);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onSelected(int item, boolean doubleClick) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		items.clear();
		cachedTotalHeight = -1;
		selected = -1;
		autoDirty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		selected = input.readInt();
		int count = input.readInt();
		for (int i = 0; i < count; i++) {
			ListWidgetItem item = new ListWidgetItem(input.readUTF(),
					input.readUTF(), input.readUTF());
			addItem(item);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeInt(selected);
		output.writeInt(getItems().length);
		for (ListWidgetItem item : getItems()) {
			output.writeUTF(item.getTitle());
			output.writeUTF(item.getText());
			output.writeUTF(item.getIconUrl());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return super.getVersion() + 1;
	}
}
