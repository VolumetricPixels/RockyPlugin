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
package org.spout.legacyapi.material.generic;

import org.spout.legacyapi.material.Block;
import org.spout.legacyapi.material.BlockType;

import net.minecraft.server.Material;
import net.minecraft.server.MaterialMapColor;

/**
 * 
 */
public class SpoutMaterialWrapper extends Material {

	protected Block block;
	
	/**
	 * 
	 */
	public SpoutMaterialWrapper(Block block) {
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
