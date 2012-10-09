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

import java.util.Map;

import org.bukkit.event.Listener;

/**
 * 
 */
public interface MaterialManager extends Listener {
	/**
	 * 
	 * @param name
	 * @param clazz
	 */
	public void registerType(String name, Class<? extends Material> clazz);

	/**
	 * 
	 * @param name
	 */
	public void unregisterType(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Class<? extends Material> getType(String name);

	/**
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public int getRegisteredName(String name, MaterialType type);

	/**
	 * 
	 * @param name
	 * @param type
	 */
	public int registerName(String name, MaterialType type);

	/**
	 * 
	 * @param name
	 * @param id
	 * @param type
	 */
	public void registerName(String name, int id, MaterialType type);

	/**
	 * 
	 * @return
	 */
	public Map<String, Integer> getRegisteredNames(MaterialType type);

	/**
	 * 
	 * @param material
	 */
	public void addMaterial(Material material);

	/**
	 * 
	 * @param material
	 */
	public void deleteMaterial(Material material);

	/**
	 * 
	 * @param source
	 * @param destination
	 */
	public void replaceMaterial(Material source, Material destination);
}
