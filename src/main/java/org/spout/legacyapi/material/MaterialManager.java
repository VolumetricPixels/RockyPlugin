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
	 */
	public Class<? extends Material> getType(String name);
	
	/**
	 * 
	 */
	public int getRegisteredName(String name);
	/**
	 * 
	 * @param name
	 */
	public int registerName(String name);
	/**
	 * 
	 * @param name
	 * @param id
	 */
	public void registerName(String name, int id);
	/**
	 * 
	 * @return
	 */
	public Map<String, Integer> getRegisteredNames();
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
