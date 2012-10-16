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

import java.util.List;

public interface ComboBox extends Button {
	/**
	 * 
	 * @param items
	 * @return
	 */
	public ComboBox setItems(List<String> items);

	/**
	 * 
	 * @return
	 */
	public List<String> getItems();

	/**
	 * 
	 * @return
	 */
	public String getSelectedItem();

	/**
	 * 
	 * @return
	 */
	public int getSelectedRow();

	/**
	 * 
	 * @param row
	 * @return
	 */
	public ComboBox setSelection(int row);

	/**
	 * 
	 * @param i
	 * @param text
	 */
	public void onSelectionChanged(int i, String text);

	/**
	 * 
	 * @return
	 */
	public boolean isOpen();

	/**
	 * Sets the format of the text on the button. Default is
	 * "%text%: %selected%"
	 * <p/>
	 * %text% will be replaced with whatever can be obtained by Button.getText()
	 * %selected% will be replaced with the text of the selected item
	 * 
	 * @param format
	 *            the format of the text on the button
	 * @return the instance
	 */
	public ComboBox setFormat(String format);

	/**
	 * 
	 * @return
	 */
	public String getFormat();
}
