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
package com.volumetricpixels.rockyapi.resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyplugin.Rocky;

/**
 * 
 */
public class Texture implements Resource {

	private String name;
	private Plugin plugin;
	private Texture parent;
	private List<Texture> textureList;
	private int xLoc, yLoc, xTopLoc, yTopLoc;
	private Object data;
	private long revision;

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param in
	 */
	public Texture(Plugin plugin, String name, InputStream in) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			return;
		}
		this.plugin = plugin;
		this.name = name;
		this.xTopLoc = image.getWidth();
		this.yTopLoc = image.getHeight();
		this.textureList = new ArrayList<Texture>();
		try {
			this.data = new byte[in.available()];
			in.read((byte[]) data);
		} catch (IOException e) {
			return;
		}
		RockyManager.getResourceManager().addResource(plugin, this);
	}

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
		this.data = name;

		RockyManager.getResourceManager().addResource(plugin, this);
	}

	/**
	 * 
	 * @param parent
	 * @param xLoc
	 * @param yLoc
	 * @param xTopLoc
	 * @param yTopLoc
	 */
	public Texture(String parent, int xLoc, int yLoc, int xTopLoc, int yTopLoc) {
		if (!RockyManager.getResourceManager().hasResource(parent)) {
			registerFullTexture(parent);
		}
		this.parent = RockyManager.getResourceManager().getResource(parent);
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
				textureList.add(new Texture(name, x, y, x + spriteSize, y
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
	 * {@inhericDoc}
	 */
	@Override
	public long getRevision() {
		return revision;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void setRevision(long revision) {
		this.revision = revision;
	}

	/**
	 * {@inhericDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getData() {
		return (T) data;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public <T> void setData(T data) {
		this.data = data;
	}

	/**
	 * 
	 * @param texture
	 */
	private static void registerFullTexture(String texture) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(texture));
		} catch (IOException e) {
			return;
		}
		int width = image.getWidth();
		int height = image.getHeight();

		Texture resource = new Texture(Rocky.getInstance(), texture, width,
				height);
		RockyManager.getResourceManager().addResource(resource.getPlugin(),
				resource);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void writeToPacket(PacketOutputStream out) throws IOException {
		out.writeShort(xLoc);
		out.writeShort(yLoc);
		out.writeShort(xTopLoc);
		out.writeShort(yTopLoc);
	}
}
