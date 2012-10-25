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
package com.volumetricpixels.rockyplugin.item;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

import org.bukkit.craftbukkit.block.CraftBlockState;
import org.fest.reflect.core.Reflection;

import com.volumetricpixels.rockyapi.material.Block;
import com.volumetricpixels.rockyapi.material.Material;

/**
 * 
 */
public class RockyItemBlock extends Item implements RockyItemType {

	private int id;
	private String stepSound;

	/**
	 * 
	 * @param material
	 */
	public RockyItemBlock(Material material) {
		this((Block) material);
	}

	/**
	 * 
	 * @param i
	 */
	public RockyItemBlock(Block block) {
		super(block.getId() - 256);

		Reflection.field("name").ofType(String.class).in(this)
				.set("name." + block.getName());

		this.id = block.getId();
		this.stepSound = block.getStepSound();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman,
			World world, int i, int j, int k, int l, float f, float f1, float f2) {
		int clickedX = i, clickedY = j, clickedZ = k;

		if (itemstack.count == 0) {
			return false;
		} else if (!entityhuman.e(i, j, k)) {
			return false;
		} else if (j == 255
				&& net.minecraft.server.Block.byId[this.id].material
						.isBuildable()) {
			return false;
		}

		if (world.mayPlace(this.id, i, j, k, false, l, entityhuman)) {
			CraftBlockState replacedBlockState = CraftBlockState.getBlockState(
					world, i, j, k);

			world.suppressPhysics = true;
			world.setTypeIdAndData(i, j, k, id,
					this.filterData(itemstack.getData()));
			org.bukkit.event.block.BlockPlaceEvent event = org.bukkit.craftbukkit.event.CraftEventFactory
					.callBlockPlaceEvent(world, entityhuman,
							replacedBlockState, clickedX, clickedY, clickedZ);
			id = world.getTypeId(i, j, k);
			int data = world.getData(i, j, k);
			replacedBlockState.update(true);
			world.suppressPhysics = false;

			if (event.isCancelled() || !event.canBuild()) {
				return true;
			}
			if (world.setTypeIdAndData(i, j, k, id, data)) {
				if (world.getTypeId(i, j, k) == id
						&& net.minecraft.server.Block.byId[id] != null) {
					net.minecraft.server.Block.byId[id].postPlace(world, i, j,
							k, l, f, f1, f2);
					net.minecraft.server.Block.byId[id].postPlace(world, i, j,
							k, entityhuman);
				}

				world.makeSound((double) ((float) i + 0.5F),
						(double) ((float) j + 0.5F),
						(double) ((float) k + 0.5F), stepSound,
						(1.0f + 1.0F) / 2.0F, 1.0f * 0.8F);
				--itemstack.count;
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String c(ItemStack itemstack) {
		return net.minecraft.server.Block.byId[this.id].a();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return net.minecraft.server.Block.byId[this.id].a();
	}

}
