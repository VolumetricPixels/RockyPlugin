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
package com.volumetricpixels.rockyapi.material;

import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

/**
 * 
 */
public interface MaterialManager extends Listener {	
	/**
	 * 
	 * @return
	 */
	YamlConfiguration getDefaultShape();
	
	/**
	 * 
	 * @return
	 */
	Item[] getItemList();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Item getItem(int id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Block getBlock(int id);
	
	/**
	 * 
	 * @param name
	 * @param clazz
	 */
	void registerType(String name, Class<? extends Material> clazz, Class<?> reference);

	/**
	 * 
	 * @param name
	 */
	void unregisterType(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	Class<? extends Material> getType(String name);

	/**
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	int getRegisteredName(String name, MaterialEnumType type);

	/**
	 * 
	 * @param name
	 * @param type
	 */
	int registerName(String name, MaterialEnumType type);

	/**
	 * 
	 * @param name
	 * @param id
	 * @param type
	 */
	void registerName(String name, int id, MaterialEnumType type);

	/**
	 * 
	 * @return
	 */
	Map<String, Integer> getRegisteredNames(MaterialEnumType type);

	/**
	 * 
	 * @param material
	 */
	void addMaterial(Material material);

	/**
	 * 
	 * @param material
	 */
	void deleteMaterial(Material material);
}
