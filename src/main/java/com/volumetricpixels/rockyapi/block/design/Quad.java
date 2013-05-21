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

import com.volumetricpixels.rockyapi.math.Color;
import com.volumetricpixels.rockyapi.math.Vector3f;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * Geometry structure to define an object of 4 points.
 */
public class Quad {

	private int index;
	private Texture texture;
	private Vertex[] vertices = new Vertex[4];
	private Vector3f lightSource = new Vector3f();
	private Color color = Color.WHITE;

	/**
	 * Default Constructor
	 */
	public Quad() {
	}

	/**
	 * Creates a new quad with the following vertexes at the specified index
	 * 
	 * @param index
	 *            of the quad
	 * @param texture
	 *            Subtexture to use
	 * @param v1
	 *            first vertex
	 * @param v2
	 *            second vertex
	 * @param v3
	 *            third vertex
	 * @param v4
	 *            fourth vertex
	 */
	public Quad(int index, Texture texture, Vertex v1, Vertex v2, Vertex v3,
			Vertex v4) {
		this(index, texture);

		vertices[0] = v1;
		vertices[1] = v2;
		vertices[2] = v3;
		vertices[3] = v4;
	}

	/**
	 * Creates an empty quad at index based on the SubTexture
	 * 
	 * @param index
	 *            of the quad
	 * @param texture
	 */
	public Quad(int index, Texture texture) {
		this.index = index;
		this.texture = texture;
	}

	/**
	 * Set the normal values of the quad
	 * 
	 * @param lightSource
	 *            vector that contains the vertex
	 * @return this
	 */
	public Quad setLightSource(Vector3f lightSource) {
		this.lightSource = lightSource;
		return this;
	}

	/**
	 * Set the normal values of the quad
	 * 
	 * @param x
	 *            the x vertex
	 * @param y
	 *            the y vertex
	 * @param z
	 *            the z vertex
	 * @return this
	 */
	public Quad setLightSource(float x, float y, float z) {
		this.lightSource.set(x, y, z);
		return this;
	}

	/**
	 * Gets the color of the quad
	 * 
	 * @return the color of the quad
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the current color of the quad
	 * 
	 * @param color
	 *            the new color
	 * @return this
	 */
	public Quad setColor(Color color) {
		this.color = color;
		return this;
	}

	/**
	 * Adds a vertex to the quad with the SubTexture properties of this quad
	 * 
	 * @param index
	 *            of the vertex
	 * @param x
	 *            value of the vertex
	 * @param y
	 *            value of the vertex
	 * @param z
	 *            value of the vertex
	 * @return this
	 */
	public Quad addVertex(int vertex, float x, float y, float z) {
		if (vertex < 0 || vertex > 3) {
			throw new IllegalArgumentException("Invalid vertex index: "
					+ vertex);
		}
		float tX = 0.0f, tY = 0.0f;
		switch (index) {
		case 0:
			tX = texture.getWidth();
			tY = texture.getY();
			break;
		case 1:
			tX = texture.getWidth();
			tY = texture.getHeight();
			break;
		case 2:
			tX = texture.getX();
			tY = texture.getHeight();
			break;
		case 3:
			tX = texture.getX();
			tY = texture.getY();
			break;
		}
		vertices[vertex] = new Vertex(index, x, y, z, tX, tY,
				color.toFloatBits());
		return this;
	}

	/**
	 * Adds a vertex to the quad
	 * 
	 * @param vertex
	 *            to add
	 * @return this
	 */
	public Quad addVertex(Vertex vertex) {
		vertices[vertex.getIndex()] = vertex;

		return this;
	}

	/**
	 * Gets the vertex of the specified index
	 * 
	 * @param index
	 *            of the vertex
	 * @return the vertex
	 */
	public Vertex getVertex(int index) {
		if (index < 0 || index > 3) {
			throw new IllegalArgumentException("Invalid vertex index: " + index);
		}

		return vertices[index];
	}

	/**
	 * Gets the quad index (Face)
	 * 
	 * @return the quad index (Face)
	 */
	public int getIndex() {
		return index;
	}
}
