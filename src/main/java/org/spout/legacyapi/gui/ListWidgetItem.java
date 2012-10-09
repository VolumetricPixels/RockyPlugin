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

/**
 * This is a single item for the ListWidget.
 */
public class ListWidgetItem {
	private String title;
	private String text;
	private String iconUrl = "";
	private ListWidget listWidget = null;

	/**
	 * 
	 */
	public ListWidgetItem() {
	}

	/**
	 * 
	 * @param title
	 * @param text
	 */
	public ListWidgetItem(String title, String text) {
		this.title = title;
		this.text = text;
	}

	/**
	 * 
	 * @param title
	 * @param text
	 * @param iconUrl
	 */
	public ListWidgetItem(String title, String text, String iconUrl) {
		this.title = title;
		this.text = text;
		this.iconUrl = iconUrl;
	}

	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 
	 * @return
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * 
	 * @param iconUrl
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * 
	 * @param list
	 */
	public void setListWidget(ListWidget list) {
		if (listWidget != null && list != null && !listWidget.equals(list)) {
			listWidget.removeItem(this);
		}
		listWidget = list;
	}

	/**
	 * 
	 * @return
	 */
	public int getHeight() {
		return 24;
	}
}
