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
package org.spout.legacyapi.resource;

import org.bukkit.plugin.Plugin;

/**
 *
 */
public class SoundMusic implements Resource {

	private final String name;

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public SoundMusic(Plugin plugin, String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public ResourceType getType() {
		return ResourceType.SOUND;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public long getRevision() {
		return 0; // TODO
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public Plugin getPlugin() {
		return null;	//TODO
	}

}
