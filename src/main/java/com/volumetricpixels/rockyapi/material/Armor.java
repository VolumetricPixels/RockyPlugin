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

import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public interface Armor extends Item {
	/**
	 * 
	 * @param durability
	 * @return
	 */
	Armor setDurability(int durability);
	
	/**
	 * 
	 * @return
	 */
	int getDurability();
	
	/**
	 * 
	 * @return
	 */
	int getDefense();
	
	/**
	 * 
	 * @param defense
	 * @return
	 */
	Armor setDefense(int defense);
	
	/**
	 * 
	 * @return
	 */
	ArmorType getType();
	
	/**
	 * 
	 * @param type
	 */
	Armor setType(ArmorType type);
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	Texture getModelTexture(ArmorModel model);
}
