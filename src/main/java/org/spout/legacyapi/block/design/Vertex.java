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
package org.spout.legacyapi.block.design;

/**
 * 
 */
public class Vertex {

	private int index;
	private float x, y, z;
	private float tX, tY;
	private float color;
	
	/**
	 * 
	 * @param index
	 * @param x
	 * @param y
	 * @param z
	 * @param tX
	 * @param tY
	 * @param color;
	 */
	public Vertex(int index, float x, float y, float z, float tX, float tY, float color) {
		this.index = index;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tX = tX;
		this.tY = tY;
		this.color = color;
	}

	/**
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 
	 * @return
	 */
	public float getX() {
		return x;
	}

	/**
	 * 
	 * @return
	 */
	public float getY() {
		return y;
	}

	/**
	 * 
	 * @return
	 */
	public float getZ() {
		return z;
	}

	/**
	 * 
	 * @return
	 */
	public float getTextureX() {
		return tX;
	}

	/**
	 * 
	 * @return
	 */
	public float getTextureY() {
		return tY;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getColor() {
		return color;
	}
}
