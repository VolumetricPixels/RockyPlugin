/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2011-2012, VolumetricPixels <http://www.volumetricpixels.com/>
 * RockyPlugin is licensed under the GNU Lesser General License.
 *
 * RockyPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RockyPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General License for more details.
 *
 * You should have received a copy of the GNU Lesser General License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.volumetricpixels.rockyapi.block.design;

import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public interface BlockDesign {
	/**
	 * Sets the maximum brightness of the block
	 * 
	 * @param maxBrightness
	 *            to set
	 * @return this
	 */
	BlockDesign setMaxBrightness(float maxBrightness);

	/**
	 * Sets the minimum brightness of the block
	 * 
	 * @param minBrightness
	 *            to set
	 * @return this
	 */
	BlockDesign setMinBrightness(float minBrightness);

	/**
	 * Sets the fixed brightness of the block
	 * 
	 * @param brightness
	 *            to set
	 * @return this
	 */
	BlockDesign setBrightness(float brightness);

	/**
	 * Sets the number of render passes of the block
	 * 
	 * @param renderPass
	 *            to set
	 * @return this
	 */
	BlockDesign setRenderOrder(BlockRenderOrder renderPass);

	/**
	 * 
	 * @return
	 */
	BlockRenderOrder getRenderOrder();

	/**
	 * Sets the specified Texture for this BlockDesign
	 * 
	 * @param plugin
	 *            associated with the texture
	 * @param texture
	 *            to set
	 * @return this
	 */
	BlockDesign setTexture(Texture texture);

	/**
	 * Sets the bounding box for this block
	 * 
	 * @param lowX
	 *            of the first corner
	 * @param lowY
	 *            of the first corner
	 * @param lowZ
	 *            of the first corner
	 * @param highX
	 *            of the second corner
	 * @param highY
	 *            of the second corner
	 * @param highZ
	 *            of the second corner
	 * @return this
	 */
	BlockDesign setBoundingBox(float lowX, float lowY, float lowZ,
			float highX, float highY, float highZ);

	/**
	 * 
	 * @return
	 */
	BoundingBox getBoundingBox();
	
	/**
	 * Sets the number of quads or faces for this block
	 * 
	 * @param quads
	 *            to set
	 * @return this
	 */
	BlockDesign setQuadNumber(int quads);

	/**
	 * Sets the specified quad or face
	 * 
	 * @param quad
	 *            to set there
	 * @return this
	 */
	BlockDesign setQuad(Quad quad);

	/**
	 * 
	 * @param index
	 * @return
	 */
	Quad getQuad(int index);

	/**
	 * Gets the Texture associated with this BlockDesign
	 * 
	 * @return the texture
	 */
	Texture getTexture();

	/**
	 * 
	 * @param degrees
	 * @return
	 */
	BlockDesign rotate(int degrees);
}
