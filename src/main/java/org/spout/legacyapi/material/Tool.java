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
package org.spout.legacyapi.material;

import java.util.Map;

/**
 * 
 */
public interface Tool extends Item {
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
	public Tool setDurability(int durability);

	/**
	 * 
	 * @param blockTypeId
	 * @param destroySpeed
	 */
	public Tool setDestroySpeed(int blockTypeId, float destroySpeed);
	
	/**
	 * 
	 * @param blockTypeId
	 * @return
	 */
	public float getDestroySpeed(int blockTypeId);
	
	/**
	 * 
	 * @return
	 */
	public Map<Integer, Float> getDestroyMap();
	
	/**
	 * 
	 * @return
	 */
	public int getDamage();
	
	/**
	 * 
	 * @param damage
	 */
	public Tool setDamage(int damage);
}
