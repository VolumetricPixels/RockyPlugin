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
package com.volumetricpixels.rockyapi.world;

import java.util.List;

import org.bukkit.generator.ChunkGenerator;

/**
 * 
 */
public interface Biome {
	/**
	 * 
	 * @param decorator
	 */
	void setDecorator(ChunkGenerator decorator);

	/**
	 * 
	 * @param value
	 */
	void setRaining(boolean value);

	/**
	 * 
	 * @return
	 */
	boolean isRaining();

	/**
	 * 
	 * @param value
	 */
	void setSnowing(boolean value);

	/**
	 * 
	 * @return
	 */
	boolean isSnowing();

	/**
	 * 
	 * @param temperature
	 * @param humidity
	 */
	void setTemperatureAndHumidity(float temperature, float humidity);

	/**
	 * 
	 * @return
	 */
	float getTemperature();

	/**
	 * 
	 * @return
	 */
	float getHumidity();

	/**
	 * 
	 * @param minHeight
	 * @param maxHeight
	 */
	void setHeight(float minHeight, float maxHeight);

	/**
	 * 
	 * @return
	 */
	float getMinHeight();

	/**
	 * 
	 */
	float getMaxHeight();

	/**
	 * 
	 * @param clazz
	 * @param probability
	 * @param minGroup
	 * @param maxGroup
	 */
	void addEntityToSpawn(Class<?> clazz, int probability, int minGroup,
			int maxGroup);

	/**
	 * 
	 * @param type
	 * @return
	 */
	List<?> getEntitySpawnList(EntityType type);

	/**
	 * 
	 * @param clazz
	 */
	void removeEntityToSpawn(Class<?> clazz);

}
