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
package com.volumetricpixels.rockyplugin.world;

import java.util.List;

import org.bukkit.generator.ChunkGenerator;
import org.fest.reflect.core.Reflection;

import com.volumetricpixels.rockyapi.world.Biome;
import com.volumetricpixels.rockyapi.world.EntityType;

import net.minecraft.server.v1_5_R3.BiomeBase;
import net.minecraft.server.v1_5_R3.BiomeMeta;
import net.minecraft.server.v1_5_R3.EntityAmbient;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntityWaterAnimal;

/**
 * Encapsulate a custom biome registered on the server
 */
public class RockyBiome extends BiomeBase implements Biome {
	
	protected ChunkGenerator decorator;
	
	/**
	 * 
	 * @param id
	 * @param name
	 */
	public RockyBiome(int id, String name) {
		super(id);

		this.y = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDecorator(ChunkGenerator decorator) {
		this.decorator = decorator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRaining(boolean value) {
		Reflection.field("T").ofType(boolean.class).in(this).set(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRaining() {
		return Reflection.field("T").ofType(boolean.class).in(this).get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSnowing(boolean value) {
		Reflection.field("T").ofType(boolean.class).in(this).set(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSnowing() {
		return Reflection.field("T").ofType(boolean.class).in(this).get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTemperatureAndHumidity(float temperature, float humidity) {
		this.temperature = temperature;
		this.humidity = humidity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getTemperature() {
		return temperature;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getHumidity() {
		return humidity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setHeight(float minHeight, float maxHeight) {
		this.D = minHeight;
		this.E = maxHeight;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getMinHeight() {
		return this.D;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getMaxHeight() {
		return this.E;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addEntityToSpawn(Class<?> clazz, int probability, int minGroup,
			int maxGroup) {
		if (clazz.isAssignableFrom(EntityAmbient.class)) {
			M.add(new BiomeMeta(clazz, probability, minGroup, maxGroup));
		} else if (clazz.isAssignableFrom(EntityMonster.class)) {
			J.add(new BiomeMeta(clazz, probability, minGroup, maxGroup));
		} else if (clazz.isAssignableFrom(EntityCreature.class)) {
			K.add(new BiomeMeta(clazz, probability, minGroup, maxGroup));
		} else if (clazz.isAssignableFrom(EntityWaterAnimal.class)) {
			L.add(new BiomeMeta(clazz, probability, minGroup, maxGroup));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BiomeMeta> getEntitySpawnList(EntityType type) {
		switch (type) {
		case CREATURE:
			return (List<BiomeMeta>) K;
		case AMBIENT:
			return (List<BiomeMeta>) M;
		case MONSTER:
			return (List<BiomeMeta>) J;
		case WATER_CREATURE:
			return (List<BiomeMeta>) L;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void removeEntityToSpawn(Class<?> clazz) {
		if (clazz.isAssignableFrom(EntityAmbient.class)) {
			removeEntityFromList(M, clazz);
		} else if (clazz.isAssignableFrom(EntityMonster.class)) {
			removeEntityFromList(J, clazz);
		} else if (clazz.isAssignableFrom(EntityCreature.class)) {
			removeEntityFromList(K, clazz);
		} else if (clazz.isAssignableFrom(EntityWaterAnimal.class)) {
			removeEntityFromList(L, clazz);
		}
	}

	/**
	 * 
	 * @param list
	 * @param clazz
	 * @return
	 */
	private boolean removeEntityFromList(List<BiomeMeta> list, Class<?> clazz) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).b.equals(clazz)) {
				return list.remove(i) != null;
			}
		}
		return false;
	}
}
