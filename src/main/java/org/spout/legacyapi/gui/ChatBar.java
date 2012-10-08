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

import java.io.IOException;
import java.util.UUID;

import org.spout.legacyapi.math.Color;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;

/**
 * The Spout implementation of the default Chat Bar.
 * <p/>
 * This provides extra abilities above the default version.
 */
public class ChatBar extends GenericWidget {
	private int cursorX = 4, cursorY = 240;
	private Color textColor = Color.White;

	/**
	 * 
	 */
	public ChatBar() {
		super();
		setDirty(false);
		setX(2);
		setY(-2);
		setWidth(425);
		setHeight(12);
		setAnchor(WidgetAnchor.BOTTOM_LEFT);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		setCursorX(input.readInt());
		setCursorY(input.readInt());
		setTextColor(input.readColor());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeInt(getCursorX());
		output.writeInt(getCursorY());
		output.writeColor(getTextColor());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.ChatBar;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public UUID getId() {
		return new UUID(0, 2);
	}

	/**
	 * Gets the x position that the cursor starts at when typing chat
	 * 
	 * @return cursor x
	 */
	public int getCursorX() {
		return cursorX;
	}

	/**
	 * Sets the x position that the cursor starts at when typing
	 * 
	 * @param x
	 *            position to set
	 * @return this
	 */
	public ChatBar setCursorX(int x) {
		cursorX = x;
		return this;
	}

	/**
	 * Gets the y position that the cursor starts at when typing chat
	 * 
	 * @return cursor y
	 */
	public int getCursorY() {
		return cursorY;
	}

	/**
	 * Sets the y position that the cursor starts at when typing
	 * 
	 * @param y
	 *            position to set
	 * @return this
	 */
	public ChatBar setCursorY(int y) {
		cursorY = y;
		return this;
	}

	/**
	 * Gets the default color of the text for the chat bar
	 * 
	 * @return default text color
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * Sets the default color of the text for the chat bar
	 * 
	 * @param color
	 *            to set
	 * @return this
	 */
	public ChatBar setTextColor(Color color) {
		textColor = color;
		return this;
	}

	/**
	 * 
	 */
	public void render() {
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public int getVersion() {
		return super.getVersion() + 2;
	}
}
