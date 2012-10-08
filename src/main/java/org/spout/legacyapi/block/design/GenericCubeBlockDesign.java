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
public class GenericCubeBlockDesign extends GenericBlockDesign {
	private boolean fullCube = false;

	/**
	 * Creates a basic cuboid custom block model
	 * 
	 * @return true if the cuboid completely covers the block's cube
	 */
	public boolean isFullCube() {
		return fullCube;
	}

	/**
	 * Creates a basic cuboid custom block model
	 * 
	 * @param plugin
	 *            making this block
	 * @param texture
	 *            to use
	 * @param textureId
	 *            [6] Array of faces, give Id's for SubTexture locations
	 * @param xMin
	 *            minimum x coordinate of the cuboid
	 * @param yMin
	 *            minimum y coordinate of the cuboid
	 * @param zMin
	 *            minimum z coordinate of the cuboid
	 * @param xMax
	 *            maximum x coordinate of the cuboid
	 * @param yMax
	 *            maximum y coordinate of the cuboid
	 * @param zMax
	 *            maximum z coordinate of the cuboid
	 *            <p/>
	 *            Array is laid out as follows {bottom, face, face, face, face,
	 *            top}
	 */
	public GenericCubeBlockDesign(Texture texture, int[] textureId, float xMin,
			float yMin, float zMin, float xMax, float yMax, float zMax) {
		if (textureId.length != 6) {
			throw new IllegalArgumentException(
					"Invalid textureId Array length: " + textureId.length
							+ ". Should be 6");
		}

		setBoundingBox(xMin, yMin, zMin, xMax, yMax, zMax);
		setQuadNumber(6);
		setMinBrightness(0.0F).setMaxBrightness(1.0F).setTexture(texture);

		Quad bottom = new Quad(0, texture.getTexture(textureId[0]));
		bottom.addVertex(0, xMin, yMin, zMin);
		bottom.addVertex(1, xMax, yMin, zMin);
		bottom.addVertex(2, xMax, yMin, zMax);
		bottom.addVertex(3, xMin, yMin, zMax);
		bottom.setLightSource(0, -1, 0);

		Quad face1 = new Quad(1, texture.getTexture(textureId[1]));
		face1.addVertex(0, xMin, yMin, zMin);
		face1.addVertex(1, xMin, yMax, zMin);
		face1.addVertex(2, xMax, yMax, zMin);
		face1.addVertex(3, xMax, yMin, zMin);
		face1.setLightSource(0, 0, -1);

		Quad face2 = new Quad(2, texture.getTexture(textureId[2]));
		face2.addVertex(0, xMax, yMin, zMin);
		face2.addVertex(1, xMax, yMax, zMin);
		face2.addVertex(2, xMax, yMax, zMax);
		face2.addVertex(3, xMax, yMin, zMax);
		face2.setLightSource(1, 0, 0);

		Quad face3 = new Quad(3, texture.getTexture(textureId[3]));
		face3.addVertex(0, xMax, yMin, zMax);
		face3.addVertex(1, xMax, yMax, zMax);
		face3.addVertex(2, xMin, yMax, zMax);
		face3.addVertex(3, xMin, yMin, zMax);
		face3.setLightSource(0, 0, 1);

		Quad face4 = new Quad(4, texture.getTexture(textureId[4]));
		face4.addVertex(0, xMin, yMin, zMax);
		face4.addVertex(1, xMin, yMax, zMax);
		face4.addVertex(2, xMin, yMax, zMin);
		face4.addVertex(3, xMin, yMin, zMin);
		face4.setLightSource(-1, 0, 0);

		Quad top = new Quad(5, texture.getTexture(textureId[5]));
		top.addVertex(0, xMin, yMax, zMin);
		top.addVertex(1, xMin, yMax, zMax);
		top.addVertex(2, xMax, yMax, zMax);
		top.addVertex(3, xMax, yMax, zMin);
		top.setLightSource(0, 1, 0);

		setQuad(bottom).setQuad(face1).setQuad(face2).setQuad(face3)
				.setQuad(face4).setQuad(top);

		fullCube = xMin == 0 && yMin == 0 && zMin == 0 && xMax == 1
				&& yMax == 1 && zMax == 1;
	}

	/**
	 * Creates a basic cuboid custom block model with only one texture
	 * 
	 * @param plugin
	 *            making this block
	 * @param texture
	 *            url to use
	 * @param textureId
	 *            to get the SubTexture to use
	 * @param xMin
	 *            minimum x coordinate of the cuboid
	 * @param yMin
	 *            minimum y coordinate of the cuboid
	 * @param zMin
	 *            minimum z coordinate of the cuboid
	 * @param xMax
	 *            maximum x coordinate of the cuboid
	 * @param yMax
	 *            maximum y coordinate of the cuboid
	 * @param zMax
	 *            maximum z coordinate of the cuboid
	 */
	public GenericCubeBlockDesign(Texture texture, int textureId, float xMin,
			float yMin, float zMin, float xMax, float yMax, float zMax) {
		this(texture, getIdMap(textureId), xMin, yMin, zMin, xMax, yMax, zMax);
	}

	/**
	 * 
	 * @param textureId
	 * @return
	 */
	private static int[] getIdMap(int textureId) {
		int[] idMap = new int[6];
		for (int i = 0; i < 6; i++) {
			idMap[i] = textureId;
		}
		return idMap;
	}
}
