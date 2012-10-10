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

import com.volumetricpixels.rockyapi.event.screen.SliderDragEvent;
import com.volumetricpixels.rockyapi.math.Color;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;

public class GenericSlider extends GenericControl implements Slider {
	protected Label label = new GenericLabel();
	protected float slider = 0.5f;

	public GenericSlider() {
	}

	@Override
	public int getVersion() {
		return super.getVersion() + 2;
	}

	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		setSliderPosition(input.readFloat());
		label.readData(input);
	}

	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeFloat(getSliderPosition());
		label.writeData(output);
	}

	@Override
	public float getSliderPosition() {
		return slider;
	}

	@Override
	public Slider setSliderPosition(float value) {
		float val = Math.max(0f, Math.min(value, 1f));
		if (getSliderPosition() != val) {
			slider = val;
			autoDirty();
		}
		return this;
	}

	@Override
	public WidgetType getType() {
		return WidgetType.Slider;
	}

	@Override
	public Slider copy() {
		return (Slider) ((Slider) super.copy())
				.setSliderPosition(getSliderPosition()).setText(getText())
				.setTextColor(getTextColor()).setAuto(isAuto())
				.setAlign(getAlign()).setScale(getScale())
				.setResize(isResize());
	}

	@Override
	public void onSliderDrag(SliderDragEvent event) {

	}

	@Override
	public String getText() {
		return label.getText();
	}

	@Override
	public Color getTextColor() {
		return label.getTextColor();
	}

	@Override
	public boolean isAuto() {
		return label.isAuto();
	}

	@Override
	public WidgetAnchor getAlign() {
		return label.getAlign();
	}

	@Override
	public Slider setScale(float scale) {
		label.setScale(scale);
		return this;
	}

	@Override
	public float getScale() {
		return label.getScale();
	}

	@Override
	public Slider setText(String text) {
		label.setText(text);
		return this;
	}

	@Override
	public Slider setTextColor(Color color) {
		label.setTextColor(color);
		return this;
	}

	@Override
	public Slider setAuto(boolean auto) {
		label.setAuto(auto);
		return this;
	}

	@Override
	public Slider setAlign(WidgetAnchor align) {
		label.setAlign(align);
		return this;
	}

	@Override
	public boolean isResize() {
		return label.isResize();
	}

	@Override
	public Slider setResize(boolean resize) {
		label.setResize(resize);
		return this;
	}

	@Override
	public Slider doResize() {
		label.doResize();
		return this;
	}

	public Slider setShadow(boolean shadow) {
		label.setShadow(shadow);
		return this;
	}

	public boolean hasShadow() {
		return label.hasShadow();
	}
}
