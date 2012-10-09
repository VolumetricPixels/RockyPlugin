/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2012-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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
package org.spout.legacyapi.material;

import org.bukkit.inventory.ItemStack;
import org.spout.legacyapi.block.design.BlockDesign;

/**
 * 
 */
public interface Block extends Material {
	/**
	 * 
	 * @return
	 */
	public net.minecraft.server.Material getMaterial();
	/**
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 
	 * @return
	 */
	public String getStepSound();

	/**
	 * 
	 * @param effect
	 * @return
	 */
	public Block setStepSound(String effect);

	/**
	 * 
	 * @return
	 */
	public float getFriction();

	/**
	 * 
	 * @param friction
	 * @return
	 */
	public Block setFriction(float friction);

	/**
	 * 
	 * @return
	 */
	public float getHardness();

	/**
	 * 
	 * @param hardness
	 * @return
	 */
	public Block setHardness(float hardness);

	/**
	 * 
	 * @return
	 */
	public boolean isOpaque();

	/**
	 * 
	 * @param isOpaque
	 * @return
	 */
	public Block setOpaque(boolean isOpaque);

	/**
	 * 
	 * @return
	 */
	public int getLightLevel();

	/**
	 * 
	 * @param level
	 * @return
	 */
	public Block setLightLevel(int level);

	/**
	 * 
	 * @return
	 */
	public BlockType getType();

	/**
	 * 
	 * @return
	 */
	public Item getItemBlock();

	/**
	 * 
	 * @return
	 */
	public ItemStack getDropType();

	/**
	 * 
	 * @param item
	 */
	public Block setDropType(ItemStack item);

	/**
	 * 
	 * @param design
	 * @return
	 */
	public Block setBlockDesign(BlockDesign design);

	/**
	 * 
	 * @param design
	 * @param id
	 * @return
	 */
	public Block setBlockDesign(BlockDesign design, int id);

	/**
	 * 
	 * @return
	 */
	public BlockDesign getBlockDesign();

	/**
	 * 
	 * @param id
	 * @return
	 */
	public BlockDesign getBlockDesign(int id);

	/**
	 * 
	 * @return
	 */
	public boolean allowRotation();
}
