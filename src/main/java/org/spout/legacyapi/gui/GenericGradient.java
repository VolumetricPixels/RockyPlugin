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

import org.spout.legacyapi.math.Color;
import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;

/**
 * 
 */
public class GenericGradient extends GenericWidget implements Gradient {
	protected Color color1 = new Color(0, 0, 0, 0), color2 = new Color(0, 0, 0,
			0);
	protected Orientation axis = Orientation.VERTICAL;

	/**
	 * 
	 */
	public GenericGradient() {
	}

	/**
	 * 
	 * @param both
	 */
	public GenericGradient(Color both) {
		this.color1 = this.color2 = both;
	}

	/**
	 * 
	 * @param top
	 * @param bottom
	 */
	public GenericGradient(Color top, Color bottom) {
		this.color1 = top;
		this.color2 = bottom;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion() {
		return super.getVersion() + 2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Gradient setTopColor(Color color) {
		if (color != null && !getTopColor().equals(color)) {
			this.color1 = color;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Gradient setBottomColor(Color color) {
		if (color != null && !getBottomColor().equals(color)) {
			this.color2 = color;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Gradient setColor(Color color) {
		setTopColor(color);
		setBottomColor(color);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getTopColor() {
		return this.color1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getBottomColor() {
		return this.color2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.Gradient;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		this.setTopColor(input.readColor());
		this.setBottomColor(input.readColor());
		this.setOrientation(Orientation.getOrientationFromId(input.read()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeColor(getTopColor());
		output.writeColor(getBottomColor());
		output.write(getOrientation().getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Gradient copy() {
		return ((Gradient) super.copy()).setTopColor(getTopColor())
				.setBottomColor(getBottomColor());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Gradient setOrientation(Orientation axis) {
		if (getOrientation() != axis) {
			this.axis = axis;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Orientation getOrientation() {
		return axis;
	}
}