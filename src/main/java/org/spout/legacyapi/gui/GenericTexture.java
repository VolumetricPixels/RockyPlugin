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

import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;

/**
 * 
 */
public class GenericTexture extends GenericWidget implements Texture {
	
	protected String url = null;
	protected boolean drawAlpha = false;
	protected int top = -1;
	protected int left = -1;

	/**
	 * 
	 */
	public GenericTexture() {
	}

	/**
	 * 
	 * @param url
	 */
	public GenericTexture(String url) {
		this.url = url;
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
	public WidgetType getType() {
		return WidgetType.Texture;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		this.setUrl(input.readUTF());
		this.setDrawAlphaChannel(input.readBoolean());
		setTop(input.readShort());
		setLeft(input.readShort());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeUTF(getUrl());
		output.writeBoolean(isDrawingAlphaChannel());
		output.writeShort((short) top);
		output.writeShort((short) left);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUrl() {
		return url;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture setUrl(String Url) {
		if ((getUrl() != null && !getUrl().equals(Url))
				|| (getUrl() == null && Url != null)) {
			this.url = Url;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture copy() {
		return ((Texture) super.copy()).setUrl(getUrl()).setDrawAlphaChannel(
				isDrawingAlphaChannel());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDrawingAlphaChannel() {
		return drawAlpha;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture setDrawAlphaChannel(boolean draw) {
		if (isDrawingAlphaChannel() != draw) {
			drawAlpha = draw;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture setTop(int top) {
		if (getTop() != top) {
			this.top = top;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTop() {
		return top;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture setLeft(int left) {
		if (getLeft() != left) {
			this.left = left;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLeft() {
		return left;
	}
}
