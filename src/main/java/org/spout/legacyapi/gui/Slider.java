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

import org.spout.legacyapi.event.screen.SliderDragEvent;
import org.spout.legacyapi.math.Color;

/**
 * The GenericSlider is a bar with which a user can set a value.
 * <p/>
 * The value is a float between 0f to 1f representing how far from the left the
 * slider is.
 */
public interface Slider extends Control {
	/**
	 * Gets the slider position (between 0.0f and 1.0f)
	 * 
	 * @return slider position
	 */
	public float getSliderPosition();

	/**
	 * Sets the slider position. Values below 0.0f are rounded to 0, and values
	 * above 1.0f are rounded to 1
	 * 
	 * @param value
	 *            to set
	 * @return slider
	 */
	public Slider setSliderPosition(float value);

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
	public Slider setText(String text);

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
	public Slider setTextColor(Color color);

	/**
	 * Determines if text expands to fill width and height
	 * 
	 * @param auto
	 * @return label
	 */
	public Slider setAuto(boolean auto);

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
	public Slider setResize(boolean resize);

	/**
	 * Actually resize the Label with the current text size
	 * 
	 * @return
	 */
	public Slider doResize();

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
	public Slider setAlign(WidgetAnchor pos);

	/**
	 * Set the scale of the text
	 * 
	 * @param scale
	 *            to set
	 */
	public Slider setScale(float scale);

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
	public Slider setShadow(boolean shadow);

	/**
	 * Gets whether or not the label has a shadow.
	 * 
	 * @return
	 */
	public boolean hasShadow();
	
	/**
	 * Fires when this slider is dragged on the screen.
	 * <p/>
	 * This event is also sent to the screen listener, afterwards.
	 * 
	 * @param event
	 */
	public void onSliderDrag(SliderDragEvent event);
}
