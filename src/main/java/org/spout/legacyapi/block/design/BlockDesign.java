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
package org.spout.legacyapi.block.design;

import org.spout.legacyapi.resource.Texture;

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
	public BlockDesign setMaxBrightness(float maxBrightness);

	/**
	 * Sets the minimum brightness of the block
	 * 
	 * @param minBrightness
	 *            to set
	 * @return this
	 */
	public BlockDesign setMinBrightness(float minBrightness);

	/**
	 * Sets the fixed brightness of the block
	 * 
	 * @param brightness
	 *            to set
	 * @return this
	 */
	public BlockDesign setBrightness(float brightness);

	/**
	 * Sets the number of render passes of the block
	 * 
	 * @param renderPass
	 *            to set
	 * @return this
	 */
	public BlockDesign setRenderPass(BlockRenderPass renderPass);

	/**
	 * 
	 * @return
	 */
	public BlockRenderPass getRenderPass();

	/**
	 * Sets the specified Texture for this BlockDesign
	 * 
	 * @param plugin
	 *            associated with the texture
	 * @param texture
	 *            to set
	 * @return this
	 */
	public BlockDesign setTexture(Texture texture);

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
	public BlockDesign setBoundingBox(float lowX, float lowY, float lowZ,
			float highX, float highY, float highZ);

	/**
	 * Sets the number of quads or faces for this block
	 * 
	 * @param quads
	 *            to set
	 * @return this
	 */
	public BlockDesign setQuadNumber(int quads);

	/**
	 * Sets the specified quad or face
	 * 
	 * @param quad
	 *            to set there
	 * @return this
	 */
	public BlockDesign setQuad(Quad quad);

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Quad getQuad(int index);

	/**
	 * Gets the Texture associated with this BlockDesign
	 * 
	 * @return the texture
	 */
	public Texture getTexture();

	/**
	 * 
	 * @param degrees
	 * @return
	 */
	public BlockDesign rotate(int degrees);
}
