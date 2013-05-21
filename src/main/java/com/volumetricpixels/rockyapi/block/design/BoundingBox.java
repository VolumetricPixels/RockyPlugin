/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2012-2013, VolumetricPixels <http://www.volumetricpixels.com/>
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
package com.volumetricpixels.rockyapi.block.design;

/**
 * Encapsulate a simple bounding box
 */
public class BoundingBox {

	private float lowXBound;
	private float lowYBound;
	private float lowZBound;
	private float highXBound;
	private float highYBound;
	private float highZBound;

	/**
	 * Gets the X of the BoundingBox
	 * 
	 * @return the X of the BoundingBox
	 */
	public float getX() {
		return lowXBound;
	}

	/**
	 * the Y of the BoundingBox
	 * 
	 * @return the Y of the BoundingBox
	 */
	public float getY() {
		return lowYBound;
	}

	/**
	 * the Z of the BoundingBox
	 * 
	 * @return the Z of the BoundingBox
	 */
	public float getZ() {
		return lowZBound;
	}

	/**
	 * the X2 of the BoundingBox
	 * 
	 * @return the X2 of the BoundingBox
	 */
	public float getX2() {
		return highXBound;
	}

	/**
	 * the Y2 of the BoundingBox
	 * 
	 * @return the Y2 of the BoundingBox
	 */
	public float getY2() {
		return highYBound;
	}

	/**
	 * the Z2 of the BoundingBox
	 * 
	 * @return the Z2 of the BoundingBox
	 */
	public float getZ2() {
		return highZBound;
	}

	/**
	 * Set the new values of the box
	 * 
	 * @param lowX
	 *            the 'x' value
	 * @param lowY
	 *            the 'y' value
	 * @param lowZ
	 *            the 'z' value
	 * @param highX
	 *            the 'x2' value
	 * @param highY
	 *            the 'y2' value
	 * @param highZ
	 *            the 'z2' value
	 * @return this instance
	 */
	public BoundingBox set(float lowX, float lowY, float lowZ, float highX,
			float highY, float highZ) {
		this.lowXBound = lowX;
		this.lowYBound = lowY;
		this.lowZBound = lowZ;
		this.highXBound = highX;
		this.highYBound = highY;
		this.highZBound = highZ;
		return this;
	}

	/**
	 * Expand the box
	 * 
	 * @param x
	 *            the x value
	 * @param y
	 *            the y value
	 * @param z
	 *            the z value
	 * @return this instance
	 */
	public BoundingBox expand(float x, float y, float z) {
		this.highXBound += x;
		this.highYBound += y;
		this.highZBound += z;
		return this;
	}

}
