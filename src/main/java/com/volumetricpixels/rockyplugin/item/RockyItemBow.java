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

import org.fest.reflect.core.Reflection;

import com.volumetricpixels.rockyapi.material.RangeWeapon;

import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemBow;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

/**
 * 
 */
public class RockyItemBow extends ItemBow implements RockyItemType {

	private String shootSound;
	private int itemAmmoId;

	/**
	 * 
	 * @param i
	 * @param material
	 */
	public RockyItemBow(int i, RangeWeapon material) {
		super(i - 256);

		setMaxDurability(material.getDurability());
		Reflection.field("name").ofType(String.class).in(this)
				.set("name." + material.getName());

		this.shootSound = material.getShootSound();
		this.itemAmmoId = material.getAmmoId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void a(ItemStack itemstack, World world, EntityHuman entityhuman,
			int i) {
		boolean flag = entityhuman.abilities.canInstantlyBuild;

		if (flag || entityhuman.inventory.e(itemAmmoId)) {
			int j = this.a(itemstack) - i;
			float f = (float) j / 20.0F;

			f = (f * f + f * 2.0F) / 3.0F;
			if ((double) f < 0.1D) {
				return;
			}

			if (f > 1.0F) {
				f = 1.0F;
			}
			// TODO: Change when we add the custom entities
			EntityArrow entityarrow = new EntityArrow(world, entityhuman,
					f * 2.0F);
			if (f == 1.0F) {
				entityarrow.d(true);
			}

			org.bukkit.event.entity.EntityShootBowEvent event = org.bukkit.craftbukkit.event.CraftEventFactory
					.callEntityShootBowEvent(entityhuman, itemstack,
							entityarrow, f);
			if (event.isCancelled()) {
				event.getProjectile().remove();
				return;
			}
			world.addEntity(entityarrow);

			itemstack.damage(1, entityhuman);
			world.makeSound(entityhuman, shootSound, 1.0F,
					1.0F / (d.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			if (flag) {
				entityarrow.fromPlayer = 2;
			} else {
				entityhuman.inventory.d(itemAmmoId);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
		if (entityhuman.abilities.canInstantlyBuild
				|| entityhuman.inventory.e(itemAmmoId)) {
			entityhuman.a(itemstack, this.a(itemstack));
		}
		return itemstack;
	}

}
