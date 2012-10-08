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

/**
 * 
 */
public class GenericListView extends GenericListWidget implements ListWidget {
	private AbstractListModel model = null;
	private int selected = -1;

	/**
	 * 
	 * @param model
	 */
	public GenericListView(AbstractListModel model) {
		setModel(model);
	}

	/**
	 * 
	 * @param model
	 */
	public void setModel(AbstractListModel model) {
		if (this.model != null) {
			this.model.removeView(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addView(this);
		}
	}

	/**
	 * 
	 * @return
	 */
	public AbstractListModel getModel() {
		return model;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return model.getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidgetItem[] getItems() {
		ListWidgetItem items[] = new ListWidgetItem[model.getSize()];
		for (int i = 0; i < model.getSize(); i++) {
			items[i] = model.getItem(i);
		}
		return items;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidgetItem getItem(int n) {
		return model.getItem(n);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidget addItem(ListWidgetItem item) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeItem(ListWidgetItem item) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidgetItem getSelectedItem() {
		if (getSelectedRow() < 0 || getSelectedRow() > getSize()) {
			return null;
		}
		return model.getItem(getSelectedRow());
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
		if (n < -1) {
			n = -1;
		}
		if (n >= model.getSize()) {
			n = model.getSize() - 1;
		}
		selected = n;
		if (n != -1) {
			ensureVisible(getItemRect(n));
		}

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidget clearSelection() {
		selected = -1;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSelected(int n) {
		return n == selected;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSelected(ListWidgetItem item) {
		return item == getSelectedItem();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListWidget shiftSelection(int n) {
		if (selected + n < 0) {
			n = 0;
		}
		setSelection(selected + n);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onSelected(int item, boolean doubleClick) {
		model.onSelected(item, doubleClick);
	}
	
	/**
	 * 
	 */
	public void sizeChanged() {
		cachedTotalHeight = -1;
		if (selected + 1 > model.getSize()) {
			selected = model.getSize() - 1;
			setScrollPosition(Orientation.VERTICAL,
					getMaximumScrollPosition(Orientation.VERTICAL));
		}
		autoDirty();
	}
}
