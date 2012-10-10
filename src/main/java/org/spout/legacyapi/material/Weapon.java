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

/**
 * 
 */
public interface Weapon extends Item {
	/**
	 * 
	 * @return
	 */
	public int getDurability();

	/**
	 * 
	 * @param durability
	 * @return
	 */
	public Weapon setDurability(int durability);

	/**
	 * 
	 * @return
	 */
	public int getDamage();

	/**
	 * 
	 * @param damage
	 * @return
	 */
	public Weapon setDamage(int damage);

	/**
	 * 
	 * @return
	 */
	public int getAttackSpeed();

	/**
	 * 
	 * @param tickSpeed
	 * @return
	 */
	public Weapon setAttackSpeed(int tickSpeed);

	/**
	 * 
	 * @return
	 */
	public boolean isBlockFlag();

	/**
	 * 
	 * @param allowBlock
	 * @return
	 */
	public Weapon setBlockFlag(boolean allowBlock);
	
	/**
	 * 
	 * @return
	 */
	public WeaponType getType();
}