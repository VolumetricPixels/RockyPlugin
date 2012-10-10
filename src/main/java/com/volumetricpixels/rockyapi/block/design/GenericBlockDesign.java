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

import com.volumetricpixels.rockyapi.math.Vector3f;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public class GenericBlockDesign implements BlockDesign {

	protected Texture texture;
	protected Quad[] quadList;
	protected BoundingBox boundingBox = new BoundingBox();
	protected float maxBrightness = 1.0f;
	protected float minBrightness = 0.0f;
	protected float brightness = 0.5f;
	protected BlockRenderPass renderPass = BlockRenderPass.OPAQUE;

	/**
	 * 
	 */
	public GenericBlockDesign() {
	}

	/**
	 * 
	 * @param clone
	 */
	protected GenericBlockDesign(GenericBlockDesign clone) {
		this.texture = clone.getTexture();
		this.boundingBox.set(boundingBox.lowXBound, boundingBox.lowYBound,
				boundingBox.lowZBound, boundingBox.highXBound,
				boundingBox.highYBound, boundingBox.highZBound);
		this.maxBrightness = clone.maxBrightness;
		this.minBrightness = clone.minBrightness;
		this.renderPass = clone.renderPass;
		this.quadList = new Quad[clone.quadList.length];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign setMaxBrightness(float maxBrightness) {
		this.maxBrightness = maxBrightness;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign setMinBrightness(float minBrightness) {
		this.minBrightness = minBrightness;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign setBrightness(float brightness) {
		this.brightness = brightness;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign setRenderPass(BlockRenderPass renderPass) {
		this.renderPass = renderPass;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockRenderPass getRenderPass() {
		return renderPass;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign setBoundingBox(float lowX, float lowY, float lowZ,
			float highX, float highY, float highZ) {
		boundingBox.set(lowX, lowY, lowZ, highX, highY, highZ);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign setQuadNumber(int quads) {
		quadList = new Quad[quads];
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture getTexture() {
		return texture;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign rotate(int degrees) {
		double angle = Math.toRadians(degrees);
		float[][] rotmatrix = {
				{ (float) Math.cos(angle), 0f, (float) Math.sin(angle) },
				{ 0f, 1f, 0f },
				{ (float) -Math.sin(angle), 0f, (float) Math.cos(angle) } };

		GenericBlockDesign block = new GenericBlockDesign(this);
		for (Quad quad : quadList) {
			Quad tmpQuad = new Quad();
			for (int i = 0; i < 4; i++) {
				Vertex v0 = quad.getVertex(i);

				float x1 = v0.getX() - 0.5f;
				float y1 = v0.getY() - 0.5f;
				float z1 = v0.getZ() - 0.5f;
				float x2 = (x1 * rotmatrix[0][0]) + (y1 * rotmatrix[0][1])
						+ (z1 * rotmatrix[0][2]);
				float y2 = (x1 * rotmatrix[1][0]) + (y1 * rotmatrix[1][1])
						+ (z1 * rotmatrix[1][2]);
				float z2 = (x1 * rotmatrix[2][0]) + (y1 * rotmatrix[2][1])
						+ (z1 * rotmatrix[2][2]);

				tmpQuad.setColor(quad.getColor());
				tmpQuad.addVertex(new Vertex(i, x2, y2, z2, v0.tX, v0.tY,
						v0.color));
			}
			block.setQuad(tmpQuad);
		}
		return block.calculateLightSources();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign setQuad(Quad quad) {
		if (quad.getIndex() < 0 || quad.getIndex() > quadList.length) {
			throw new IllegalArgumentException("Invalid quad index");
		}
		quadList[quad.getIndex()] = quad;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Quad getQuad(int index) {
		if (index < 0 || index > quadList.length) {
			throw new IllegalArgumentException("Invalid quad index");
		}
		return quadList[index];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	/**
	 * 
	 * @return
	 */
	public GenericBlockDesign calculateLightSources() {
		Vector3f normal = new Vector3f();
		for (Quad quad : quadList) {
			Vertex v1 = quad.getVertex(0);
			Vertex v2 = quad.getVertex(1);
			Vertex v3 = quad.getVertex(2);

			float y1 = v1.getY();
			float x1 = v1.getX();
			float z1 = v1.getZ();
			float y2 = v2.getY();
			float x2 = v2.getX();
			float z2 = v2.getZ();
			float y3 = v3.getY();
			float x3 = v3.getX();
			float z3 = v3.getZ();

			normal.x = (((y1 - y2) * (z2 - z3)) - ((z1 - z2) * (y2 - y3)));
			normal.y = (((z1 - z2) * (x2 - x3)) - ((x1 - x2) * (z2 - z3)));
			normal.z = (((x1 - x2) * (y2 - y3)) - ((y1 - y2) * (x2 - x3)));

			Double length = Math.sqrt((normal.x * normal.x)
					+ (normal.y * normal.y) + (normal.z * normal.z));

			quad.setLightSource((int) Math.round(normal.x / length),
					(int) Math.round(normal.y / length),
					(int) Math.round(normal.z / length));
		}
		return this;
	}
}
