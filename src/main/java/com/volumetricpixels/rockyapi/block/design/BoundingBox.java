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
package com.volumetricpixels.rockyapi.block.design;

/**
 * 
 */
public class BoundingBox {

	private float lowXBound;
	private float lowYBound;
	private float lowZBound;
	private float highXBound;
	private float highYBound;
	private float highZBound;

	/**
	 * 
	 * @return
	 */
	public float getX() {
		return lowXBound;
	}

	/**
	 * 
	 * @return
	 */
	public float getY() {
		return lowYBound;
	}

	/**
	 * 
	 * @return
	 */
	public float getZ() {
		return lowZBound;
	}

	/**
	 * 
	 * @return
	 */
	public float getX2() {
		return highXBound;
	}

	/**
	 * 
	 * @return
	 */
	public float getY2() {
		return highYBound;
	}

	/**
	 * 
	 * @return
	 */
	public float getZ2() {
		return highZBound;
	}

	/**
	 * 
	 * @param lowX
	 * @param lowY
	 * @param lowZ
	 * @param highX
	 * @param highY
	 * @param highZ
	 * @return
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
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void expand(float x, float y, float z) {
		this.highXBound += x;
		this.highYBound += y;
		this.highZBound += z;
	}

}
