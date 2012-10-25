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
package com.volumetricpixels.rockyapi.material;

import org.bukkit.inventory.ItemStack;

import com.volumetricpixels.rockyapi.block.design.BlockDesign;

/**
 * 
 */
public interface Block extends Material {
	String DEFAULT_SHAPE = "cube.shape";
	
	/**
	 * 
	 * @return
	 */
	net.minecraft.server.Material getMaterial();
	/**
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 
	 * @return
	 */
	String getStepSound();

	/**
	 * 
	 * @param effect
	 * @return
	 */
	Block setStepSound(String effect);

	/**
	 * 
	 * @return
	 */
	float getFriction();

	/**
	 * 
	 * @param friction
	 * @return
	 */
	Block setFriction(float friction);

	/**
	 * 
	 * @return
	 */
	float getHardness();

	/**
	 * 
	 * @param hardness
	 * @return
	 */
	Block setHardness(float hardness);

	/**
	 * 
	 * @return
	 */
	boolean isOpaque();

	/**
	 * 
	 * @param isOpaque
	 * @return
	 */
	Block setOpaque(boolean isOpaque);

	/**
	 * 
	 * @return
	 */
	float getLightLevel();

	/**
	 * 
	 * @param level
	 * @return
	 */
	Block setLightLevel(float level);

	/**
	 * 
	 * @return
	 */
	BlockType getType();

	/**
	 * 
	 * @return
	 */
	Item getItemBlock();

	/**
	 * 
	 * @return
	 */
	ItemStack getDropType();

	/**
	 * 
	 * @param item
	 */
	Block setDropType(ItemStack item);

	/**
	 * 
	 * @param design
	 * @return
	 */
	Block setBlockDesign(BlockDesign design);

	/**
	 * 
	 * @param design
	 * @param id
	 * @return
	 */
	Block setBlockDesign(BlockDesign design, int id);

	/**
	 * 
	 * @return
	 */
	BlockDesign getBlockDesign();

	/**
	 * 
	 * @param id
	 * @return
	 */
	BlockDesign getBlockDesign(int id);

	/**
	 * 
	 * @return
	 */
	boolean allowRotation();
}
