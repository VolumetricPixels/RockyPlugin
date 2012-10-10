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

import com.volumetricpixels.rockyapi.math.Color;
import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;

public abstract class GenericScrollable extends GenericControl implements
		Scrollable {
	protected ScrollBarPolicy sbpVert;
	protected ScrollBarPolicy sbpHoriz = sbpVert = ScrollBarPolicy.SHOW_IF_NEEDED;
	protected int innerSizeHoriz = 0, innerSizeVert = 0;
	protected int scrollX = 0, scrollY = 0;
	private Color background = new Color(0F, 0F, 0F, 0.6F);

	public GenericScrollable() {
	}

	@Override
	public int getInnerSize(Orientation axis) {
		switch (axis) {
		case HORIZONTAL:
			return innerSizeHoriz;
		case VERTICAL:
			return innerSizeVert;
		}
		return 0;
	}

	@Override
	public int getScrollPosition(Orientation axis) {
		switch (axis) {
		case HORIZONTAL:
			return scrollX;
		case VERTICAL:
			return scrollY;
		}
		return 0;
	}

	@Override
	public void setScrollPosition(Orientation axis, int position) {
		if (position < 0) {
			position = 0;
		}
		if (position > getMaximumScrollPosition(axis)) {
			position = getMaximumScrollPosition(axis);
		}
		switch (axis) {
		case HORIZONTAL:
			scrollX = position;
			break;
		case VERTICAL:
			scrollY = position;
			break;
		}
	}

	@Override
	public void scroll(int x, int y) {
		int currentX, currentY;
		currentX = getScrollPosition(Orientation.HORIZONTAL);
		currentX += x;
		currentY = getScrollPosition(Orientation.VERTICAL);
		currentY += y;
		setScrollPosition(Orientation.HORIZONTAL, currentX);
		setScrollPosition(Orientation.VERTICAL, currentY);
	}

	@Override
	public void ensureVisible(Rectangle rect) {
		int scrollTop = getScrollPosition(Orientation.VERTICAL);
		int scrollLeft = getScrollPosition(Orientation.HORIZONTAL);
		int scrollBottom = scrollTop + getViewportSize(Orientation.VERTICAL)
				- 10;
		int scrollRight = scrollLeft + getViewportSize(Orientation.HORIZONTAL)
				- 10;
		boolean correctHorizontal = false;
		boolean correctVertical = false;
		if (scrollTop <= rect.getTop() && rect.getBottom() <= scrollBottom) {
			// Fits vertical
			if (scrollLeft <= rect.getLeft() && rect.getRight() <= scrollRight) {
				// Fits completely
				// Nothing to do.
				return;
			} else {
				// Doesn't fit horizontal
				correctHorizontal = true;
			}
		} else {
			// Doesn't fit vertical
			correctVertical = true;
			if (scrollLeft <= rect.getLeft() && rect.getRight() <= scrollRight) {
				// But does fit horizontal
			} else {
				// Doesn't fit horizontal too
				correctHorizontal = true;
			}
		}
		if (correctHorizontal) {
			if (scrollRight < rect.getRight()) {
				setScrollPosition(Orientation.HORIZONTAL, rect.getRight()
						- getViewportSize(Orientation.HORIZONTAL) + 10);
			}
			if (scrollLeft > rect.getLeft()) {
				setScrollPosition(Orientation.HORIZONTAL, rect.getLeft());
			}
		}
		if (correctVertical) {
			if (scrollBottom < rect.getBottom()) {
				setScrollPosition(Orientation.VERTICAL, rect.getBottom()
						- getViewportSize(Orientation.VERTICAL) + 10);
			}
			if (scrollTop > rect.getTop()) {
				setScrollPosition(Orientation.VERTICAL, rect.getTop());
			}
		}
	}

	@Override
	public int getMaximumScrollPosition(Orientation axis) {
		return (int) Math.max(0, getInnerSize(axis)
				- (axis == Orientation.HORIZONTAL ? getWidth() : getHeight()));
	}

	@Override
	public boolean needsScrollBar(Orientation axis) {
		ScrollBarPolicy policy = getScrollBarPolicy(axis);
		switch (policy) {
		case SHOW_IF_NEEDED:
			return getMaximumScrollPosition(axis) > 0;
		case SHOW_ALWAYS:
			return true;
		case SHOW_NEVER:
			return false;
		default:
			return true;
		}
	}

	@Override
	public void setScrollBarPolicy(Orientation axis, ScrollBarPolicy policy) {
		switch (axis) {
		case HORIZONTAL:
			sbpHoriz = policy;
			break;
		case VERTICAL:
			sbpVert = policy;
			break;
		}
	}

	@Override
	public ScrollBarPolicy getScrollBarPolicy(Orientation axis) {
		switch (axis) {
		case HORIZONTAL:
			return sbpHoriz;
		case VERTICAL:
			return sbpVert;
		}
		return null;
	}

	protected void setInnerSize(Orientation axis, int size) {
		switch (axis) {
		case HORIZONTAL:
			innerSizeHoriz = size;
			break;
		case VERTICAL:
			innerSizeVert = size;
			break;
		}
	}

	@Override
	public int getViewportSize(Orientation axis) {
		int size = 0;
		size = (int) (axis == Orientation.HORIZONTAL ? getWidth() : getHeight());
		if (needsScrollBar(axis.getOther())) {
			return size - 16;
		}
		return size;
	}

	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		sbpHoriz = ScrollBarPolicy.getById(input.readInt());
		sbpVert = ScrollBarPolicy.getById(input.readInt());
		scrollX = input.readInt();
		scrollY = input.readInt();
		innerSizeHoriz = input.readInt();
		innerSizeVert = input.readInt();
		setBackgroundColor(input.readColor());
	}

	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeInt(sbpHoriz.getId());
		output.writeInt(sbpVert.getId());
		output.writeInt(scrollX);
		output.writeInt(scrollY);
		output.writeInt(innerSizeHoriz);
		output.writeInt(innerSizeVert);
		output.writeColor(getBackgroundColor());
	}

	@Override
	public int getVersion() {
		return super.getVersion() + 1;
	}

	@Override
	public Color getBackgroundColor() {
		return background;
	}

	@Override
	public Scrollable setBackgroundColor(Color color) {
		background = color;
		return this;
	}
}
