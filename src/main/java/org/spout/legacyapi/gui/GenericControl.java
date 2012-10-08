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

import org.spout.legacyapi.math.Color;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;

public abstract class GenericControl extends GenericWidget implements Control {
	protected boolean focus = false;
	protected boolean enabled = true;
	protected Color color = new Color(0.878F, 0.878F, 0.878F, 1.0f);
	protected Color disabledColor = new Color(0.625F, 0.625F, 0.625F, 1.0f);

	/**
	 * 
	 */
	public GenericControl() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return super.getVersion() + 3;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		setEnabled(input.readBoolean());
		setColor(input.readColor());
		setDisabledColor(input.readColor());
		setFocus(input.readBoolean());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeBoolean(isEnabled());
		output.writeColor(getColor());
		output.writeColor(getDisabledColor());
		output.writeBoolean(isFocus());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Control setEnabled(boolean enable) {
		if (isEnabled() != enable) {
			enabled = enable;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Control setColor(Color color) {
		if (color != null && !getColor().equals(color)) {
			this.color = color;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getDisabledColor() {
		return disabledColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Control setDisabledColor(Color color) {
		if (color != null && !getDisabledColor().equals(color)) {
			this.disabledColor = color;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFocus() {
		return focus;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Control setFocus(boolean focus) {
		if (isFocus() != focus) {
			this.focus = focus;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Control copy() {
		return ((Control) super.copy()).setEnabled(isEnabled())
				.setColor(getColor()).setDisabledColor(getDisabledColor());
	}
}
