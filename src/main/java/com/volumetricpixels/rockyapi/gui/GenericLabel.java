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

import org.bukkit.ChatColor;

import com.volumetricpixels.rockyapi.math.Color;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;

/**
 * 
 */
public class GenericLabel extends GenericWidget implements Label {
	protected String text = "Your Text Here";
	protected WidgetAnchor align = WidgetAnchor.TOP_LEFT;
	protected Color color = new Color(1F, 1F, 1F, 1F);
	protected boolean auto = true;
	protected boolean resize = false;
	protected int textWidth = -1, textHeight = -1;
	protected float scale = 1.0F;
	protected boolean shadow = true;

	/**
	 * 
	 */
	public GenericLabel() {
	}

	/**
	 * 
	 * @param text
	 */
	public GenericLabel(String text) {
		this.text = text;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.Label;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return super.getVersion() + 6;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		setText(input.readUTF());
		setAlign(WidgetAnchor.getAnchorFromId(input.read()));
		setAuto(input.readBoolean());
		setTextColor(input.readColor());
		setScale(input.readFloat());
		setShadow(input.readBoolean());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeUTF(getText());
		output.write(getAlign().getId());
		output.writeBoolean(isAuto());
		output.writeColor(getTextColor());
		output.writeFloat(getScale());
		output.writeBoolean(hasShadow());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText() {
		return text;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label setText(String text) {
		if (text != null && !getText().equals(text)) {
			this.text = text;
			textHeight = textWidth = -1;
			doResize();
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAuto() {
		return auto;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label setAuto(boolean auto) {
		if (isAuto() != auto) {
			this.auto = auto;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetAnchor getAlign() {
		return align;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label setAlign(WidgetAnchor pos) {
		if (pos != null && !getAlign().equals(pos)) {
			this.align = pos;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getTextColor() {
		return color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label setTextColor(Color color) {
		if (color != null && !getTextColor().equals(color)) {
			this.color = color;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label setScale(float scale) {
		if (this.scale != scale) {
			this.scale = scale;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getScale() {
		return scale;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label copy() {
		return ((Label) super.copy()) //
				.setText(getText()) //
				.setScale(getScale()) //
				.setAuto(isAuto()) //
				.setTextColor(getTextColor()) //
				.setResize(isResize());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResize() {
		return resize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label setResize(boolean resize) {
		this.resize = resize;
		doResize();
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label doResize() {
		if (resize) {
			if (textHeight < 0 || textWidth < 0) {
				textHeight = getStringHeight(text, getScale());
				textWidth = getStringWidth(text, getScale());
			}
			setMinHeight(textHeight);
			setMinWidth(textWidth);
			if (isFixed()) {
				setHeight(textHeight);
				setWidth(textWidth);
			}
		} else {
			textHeight = textWidth = -1;
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Widget setFixed(boolean fixed) {
		super.setFixed(fixed);
		doResize();
		return this;
	}

	/**
	 * Gets the height of the text
	 * 
	 * @param text
	 * @return height in pixels
	 */
	public static int getStringHeight(String text) {
		return getStringHeight(text, 1.0F);
	}

	/**
	 * Gets the height of the text, at the given scale
	 * 
	 * @param text
	 * @param scale
	 *            of the text, 1.0 is default
	 * @return height in pixels
	 */
	public static int getStringHeight(String text, float scale) {
		return (int) (text.split("\n").length * 10 * scale);
	}

	/**
	 * Gets the width of the text
	 * 
	 * @param text
	 * @return width of the text
	 */
	public static int getStringWidth(String text) {
		return getStringWidth(text, 1.0F);
	}

	/**
	 * Gets the width of the text, at the given scale
	 * 
	 * @param text
	 * @param scale
	 *            of the text, 1.0 is default
	 * @return width of the text
	 */
	public static int getStringWidth(String text, float scale) {
		final int[] characterWidths = new int[] { 1, 9, 9, 8, 8, 8, 8, 7, 9, 8,
				9, 9, 8, 9, 9, 9, 8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9,
				9, 4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6, 6, 6, 6, 6,
				6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6,
				4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4,
				6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6,
				6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6,
				6, 6, 6, 4, 6, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
				6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6, 8, 9,
				9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6, 9, 9, 9, 9, 9, 9, 9,
				9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9,
				9, 5, 9, 9, 8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7, 7,
				7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1 };
		final String allowedCharacters = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz {|}~?ÃƒÂ³ÃšÃ�?ÃµÃ“Ã•Ã¾Ã›Ã™ÃžÂ´Â¯Ã½â�?€â�?¼â•�?ÂµÃ£Â¶Ã·â€—Â¹Â¨Â Ã�â–„Â°ÃºÃ�ÃŽÃ¢ÃŸÃ�Â¾Â·Â±Ã�Â¬â•‘â�?�Â«Â¼Â¢â•�Ã­Â½â•—";
		int length = 0;
		for (String line : ChatColor.stripColor(text).split("\n")) {
			int lineLength = 0;
			boolean skip = false;
			for (char ch : line.toCharArray()) {
				if (skip) {
					skip = false;
				} else if (ch == '\u00A7') {
					skip = true;
				} else if (allowedCharacters.indexOf(ch) != -1) {
					lineLength += characterWidths[ch];
				}
			}
			length = Math.max(length, lineLength);
		}
		return (int) (length * scale);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label setShadow(boolean shadow) {
		if (hasShadow() != shadow) {
			this.shadow = shadow;
			autoDirty();
		}
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasShadow() {
		return shadow;
	}
}
