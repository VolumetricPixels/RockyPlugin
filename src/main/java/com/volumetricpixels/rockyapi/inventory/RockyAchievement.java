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
package com.volumetricpixels.rockyapi.inventory;

/**
 * Encapsulate a custom achievement.
 */
public class RockyAchievement {

	private int id;
	private int itemId;
	private String name;
	private String description;
	private RockyAchievement[] depends;

	/**
	 * Default constructor
	 * 
	 * @param id
	 *            the id of the achievement
	 * @param name
	 *            the name of the achievement
	 * @param description
	 *            the description of the achievement
	 * @param itemId
	 *            id of the icon
	 * @param depends
	 *            array of dependencies
	 */
	public RockyAchievement(int id, String name, String description,
			int itemId, RockyAchievement... depends) {
		this.id = id;
		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.depends = depends;
	}

	/**
	 * Gets the id
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the icon id
	 * 
	 * @return the icon id
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * Gets the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the dependencies
	 * 
	 * @return the dependencies
	 */
	public RockyAchievement[] getDependency() {
		return depends;
	}

}
