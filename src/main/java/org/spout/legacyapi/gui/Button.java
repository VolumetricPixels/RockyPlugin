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

import org.spout.legacyapi.event.screen.ButtonClickEvent;
import org.spout.legacyapi.math.Color;

/**
 * The GenericButton class represents a Minecraft button with a label placed on
 * it.
 */
public interface Button extends Control {
	/**
	 * Get's the text that is displayed when the control is disabled
	 * 
	 * @return disabled text
	 */
	public String getDisabledText();

	/**
	 * Sets the text that is displayed when the control is disabled
	 * 
	 * @param text
	 *            to display
	 * @return Button
	 */
	public Button setDisabledText(String text);

	/**
	 * Get's the color of the control while the mouse is hovering over it
	 * 
	 * @return color
	 */
	public Color getHoverColor();

	/**
	 * Sets the color of the control while the mouse is hovering over it
	 * 
	 * @param color
	 * @return Button
	 */
	public Button setHoverColor(Color color);

	/**
	 * Gets the text of the label
	 * 
	 * @return text
	 */
	public String getText();

	/**
	 * Sets the text of the label
	 * 
	 * @param text
	 *            to set
	 * @return label
	 */
	public Button setText(String text);

	/**
	 * Gets the color for the text
	 * 
	 * @return color
	 */
	public Color getTextColor();

	/**
	 * Sets the color for the text
	 * 
	 * @param color
	 *            to set
	 * @return label
	 */
	public Button setTextColor(Color color);

	/**
	 * Determines if text expands to fill width and height
	 * 
	 * @param auto
	 * @return label
	 */
	public Button setAuto(boolean auto);

	/**
	 * True if the text will expand to fill width and height
	 * 
	 * @return
	 */
	public boolean isAuto();

	/**
	 * Does this widget automatically resize with it's contents
	 * 
	 * @return
	 */
	public boolean isResize();

	/**
	 * Tell this widget to resize with it's contents
	 * 
	 * @param resize
	 * @return
	 */
	public Button setResize(boolean resize);

	/**
	 * Actually resize the Label with the current text size
	 * 
	 * @return
	 */
	public Button doResize();

	/**
	 * Get the text alignment
	 * 
	 * @return
	 */
	public WidgetAnchor getAlign();

	/**
	 * Set the text alignment
	 * 
	 * @param pos
	 * @return
	 */
	public Button setAlign(WidgetAnchor pos);

	/**
	 * Set the scale of the text
	 * 
	 * @param scale
	 *            to set
	 */
	public Button setScale(float scale);

	/**
	 * Gets the scale of the text
	 * 
	 * @return scale of text
	 */
	public float getScale();

	/**
	 * Sets whether or not the label has a shadow.
	 * 
	 * @param shadow
	 */
	public Button setShadow(boolean shadow);

	/**
	 * Gets whether or not the label has a shadow.
	 * 
	 * @return
	 */
	public boolean hasShadow();

	/**
	 * Fires when this button is clicked on the screen.
	 * <p/>
	 * If this is not overridden in a subclass then this event will be sent to
	 * the screen listener.
	 * 
	 * @param event
	 */
	public void onButtonClick(ButtonClickEvent event);
}
