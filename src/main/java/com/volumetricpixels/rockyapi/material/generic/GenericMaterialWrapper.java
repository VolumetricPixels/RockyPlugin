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
package com.volumetricpixels.rockyapi.material.generic;

import com.volumetricpixels.rockyapi.material.Block;
import com.volumetricpixels.rockyapi.material.BlockType;

import net.minecraft.server.v1_5_R3.Material;
import net.minecraft.server.v1_5_R3.MaterialMapColor;

/**
 * 
 */
public class GenericMaterialWrapper extends Material {

	private Block block;

	/**
	 * 
	 */
	public GenericMaterialWrapper(Block block) {
		super(MaterialMapColor.b);

		this.block = block;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLiquid() {
		return block.getType() == BlockType.LIQUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBuildable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean blocksLight() {
		return block.isOpaque();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSolid() {
		return block.getType() == BlockType.SOLID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBurnable() {
		return block.getItemBlock().isAllowToBurn();
	}
}
