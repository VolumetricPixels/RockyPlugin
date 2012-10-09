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

import java.io.IOException;

import org.spout.legacyapi.event.screen.ButtonClickEvent;
import org.spout.legacyapi.math.Color;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;

public class GenericButton extends GenericControl implements Button {
	protected GenericLabel label = (GenericLabel) new GenericLabel()
			.setAlign(WidgetAnchor.TOP_CENTER);
	protected String disabledText = "";
	protected Color hoverColor = new Color(1, 1, 0.627F, 1.0f);
	protected float scale = 1.0F;

	/**
	 * 
	 */
	public GenericButton() {
	}

	/**
	 * 
	 * @param text
	 */
	public GenericButton(String text) {
		setText(text);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return super.getVersion() + 4;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		label.readData(input);
		setDisabledText(input.readUTF());
		setHoverColor(input.readColor());
		scale = input.readFloat();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		label.writeData(output);
		output.writeUTF(getDisabledText());
		output.writeColor(getHoverColor());
		output.writeFloat(scale);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText() {
		return label.getText();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button setText(String text) {
		label.setText(text);
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getTextColor() {
		return label.getTextColor();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button setTextColor(Color color) {
		label.setTextColor(color);
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDisabledText() {
		return disabledText;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button setDisabledText(String text) {
		if (text != null && !getDisabledText().equals(text)) {
			disabledText = text;
			autoDirty();
		}
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getHoverColor() {
		return hoverColor;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button setHoverColor(Color color) {
		if (color != null && !getHoverColor().equals(color)) {
			this.hoverColor = color;
			autoDirty();
		}
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.Button;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button setAuto(boolean auto) {
		label.setAuto(auto);
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAuto() {
		return label.isAuto();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetAnchor getAlign() {
		return label.getAlign();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button setAlign(WidgetAnchor pos) {
		label.setAlign(pos);
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button copy() {
		return (Button) ((Button) super.copy())
				.setDisabledText(getDisabledText()).setText(getText())
				.setAuto(isAuto()).setTextColor(getTextColor())
				.setHoverColor(getHoverColor()).setAuto(isAuto())
				.setResize(isResize());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onButtonClick(ButtonClickEvent event) {

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResize() {
		return label.isResize();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button setResize(boolean resize) {
		label.setResize(resize);
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button doResize() {
		label.doResize();
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button setScale(float scale) {
		this.scale = scale;
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
	 * 
	 */
	public Button setShadow(boolean shadow) {
		label.setShadow(shadow);
		return this;
	}

	/**
	 * 
	 */
	public boolean hasShadow() {
		return label.hasShadow();
	}
}
