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
package org.spout.legacyapi.resource;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

/**
 * 
 */
public class Texture implements Resource {

	private String name;
	private Plugin plugin;
	private Texture parent;
	private List<Texture> textureList;
	private int xLoc, yLoc, xTopLoc, yTopLoc;

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param width
	 * @param height
	 */
	public Texture(Plugin plugin, String name, int width, int height) {
		this.plugin = plugin;
		this.name = name;
		this.xTopLoc = width;
		this.yTopLoc = height;
		this.textureList = new ArrayList<Texture>();
	}

	/**
	 * 
	 * @param parent
	 * @param xLoc
	 * @param yLoc
	 * @param xTopLoc
	 * @param yTopLoc
	 */
	public Texture(Texture parent, int xLoc, int yLoc, int xTopLoc, int yTopLoc) {
		this.parent = parent;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.xTopLoc = xTopLoc;
		this.yTopLoc = yTopLoc;
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param width
	 * @param height
	 * @param spriteSize
	 */
	public Texture(Plugin plugin, String name, int width, int height,
			int spriteSize) {
		this(plugin, name, width, height);

		for (int y = height; y >= 0; y -= spriteSize)
			for (int x = 0; x < width; x += spriteSize)
				textureList.add(new Texture(this, x, y, x + spriteSize, y
						+ spriteSize));
	}

	/**
	 * 
	 * @return
	 */
	public List<Texture> getTextureList() {
		return textureList;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Texture getTexture(int index) {
		return textureList.get(index);
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public Plugin getPlugin() {
		if (parent != null) {
			return parent.getPlugin();
		}
		return plugin;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		if (parent != null) {
			return parent.getName();
		}
		return name;
	}

	/**
	 * 
	 * @return
	 */
	public Texture getParent() {
		return parent;
	}

	/**
	 * 
	 * @return
	 */
	public int getX() {
		return xLoc;
	}

	/**
	 * 
	 * @return
	 */
	public int getY() {
		return yLoc;
	}

	/**
	 * 
	 * @return
	 */
	public int getWidth() {
		return xTopLoc;
	}

	/**
	 * 
	 * @return
	 */
	public int getHeight() {
		return yTopLoc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceType getType() {
		return ResourceType.TEXTURE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getRevision() {
		return 0;	//TODO
	}

}
