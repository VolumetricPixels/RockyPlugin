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
package org.spout.legacyapi.block.design;

import org.spout.legacyapi.resource.Texture;

/**
 * 
 */
public class GenericBlockDesign implements BlockDesign {

	protected Texture texture;
	protected Quad[] quadList;
	protected float lowXBound;
	protected float lowYBound;
	protected float lowZBound;
	protected float highXBound;
	protected float highYBound;
	protected float highZBound;
	protected float maxBrightness = 1.0f;
	protected float minBrightness = 0.0f;
	protected float brightness = 0.5f;
	protected BlockRenderPass renderPass = BlockRenderPass.OPAQUE;

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
		this.lowXBound = lowX;
		this.lowYBound = lowY;
		this.lowZBound = lowZ;
		this.highXBound = highX;
		this.highYBound = highY;
		this.highZBound = highZ;
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
		return this;	//TODO
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign setQuad(Quad quad) {
		if( quad.getIndex() < 0 || quad.getIndex() > quadList.length) {
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
		if( index < 0 || index > quadList.length) {
			throw new IllegalArgumentException("Invalid quad index");
		}
		return quadList[index];
	}


}
