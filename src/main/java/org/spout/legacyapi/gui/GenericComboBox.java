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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;

public class GenericComboBox extends GenericButton implements ComboBox {
	private List<String> items = new ArrayList<String>();
	private boolean open = false;
	private int selection = -1;
	private String format = "%text%: %selected%";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ComboBox setItems(List<String> items) {
		this.items = items;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getItems() {
		return items;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSelectedItem() {
		if (selection >= 0 && selection < items.size()) {
			return items.get(selection);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSelectedRow() {
		return this.selection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ComboBox setSelection(int row) {
		boolean event = row != selection;
		this.selection = row;
		if (event) {
			onSelectionChanged(row, getSelectedItem());
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onSelectionChanged(int i, String text) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isOpen() {
		return open;
	}

	/**
	 * Sets the open status.
	 * 
	 * @param open
	 *            the state
	 * @return the instance
	 */
	public ComboBox setOpen(boolean open) {
		this.open = open;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.ComboBox;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeInt(selection);
		output.writeInt(getItems().size());
		for (String item : getItems()) {
			output.writeUTF(item);
		}
		output.writeUTF(format);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return super.getVersion() + 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFormat() {
		return format;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ComboBox setFormat(String format) {
		this.format = format;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText() {
		if (super.getText() == null || super.getText().isEmpty()) {
			return getSelectedItem();
		} else {
			String text = format.replaceAll("%text%", super.getText())
					.replaceAll("%selected%", getSelectedItem());
			return text;
		}
	}
}
