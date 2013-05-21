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
 * A single point in the 3D space.
 */
public class Vertex {

	private int index;
	private float x, y, z;
	private float tX, tY;
	private float color;

	/**
	 * Default constructor of the vertex
	 * 
	 * @param index
	 *            the index
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param z
	 *            the z coordinate
	 * @param tX
	 *            the texture x coordinate
	 * @param tY
	 *            the texture y coordinate
	 * @param color
	 *            the color ;
	 */
	public Vertex(int index, float x, float y, float z, float tX, float tY,
			float color) {
		this.index = index;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tX = tX;
		this.tY = tY;
		this.color = color;
	}

	/**
	 * Sets the vertex coordinates
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param z
	 *            the z coordinate
	 */
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Gets the index of the vertex
	 * 
	 * @return the index of the vertex
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the X coordinate
	 * 
	 * @return the X coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets the Y coordinate
	 * 
	 * @return the Y coordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * Gets the Z coordinate
	 * 
	 * @return the Z coordinate
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Gets the X texture coordinate
	 * 
	 * @return the X texture coordinate
	 */
	public float getTextureX() {
		return tX;
	}

	/**
	 * Gets the Y texture coordinate
	 * 
	 * @return the Y texture coordinate
	 */
	public float getTextureY() {
		return tY;
	}

	/**
	 * Gets the color
	 * 
	 * @return the color
	 */
	public float getColor() {
		return color;
	}
}
