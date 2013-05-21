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
package com.volumetricpixels.rockyapi.material.generic;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyapi.block.design.BlockDesign;
import com.volumetricpixels.rockyapi.block.design.BlockRenderOrder;
import com.volumetricpixels.rockyapi.block.design.GenericBlockDesign;
import com.volumetricpixels.rockyapi.material.Block;
import com.volumetricpixels.rockyapi.material.BlockType;
import com.volumetricpixels.rockyapi.material.Item;
import com.volumetricpixels.rockyapi.material.MaterialTab;
import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.material.MaterialEnumType;
import com.volumetricpixels.rockyapi.resource.AddonPack;
import com.volumetricpixels.rockyapi.resource.Sound;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public class GenericBlock implements Block {

	private int id;
	private Plugin plugin;
	private String name;
	private Sound stepSound;
	private float friction;
	private float hardness;
	private boolean isOpaque;
	private float light;
	private BlockType type;
	private Item blockItem;
	private ItemStack dropStack;
	private List<BlockDesign> design = new LinkedList<BlockDesign>();
	private boolean allowRotation;
	private net.minecraft.server.v1_5_R3.Material material;
	private int defaultId;
	
	/**
	 * 
	 */
	public GenericBlock() {
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param design
	 */
	public GenericBlock(Plugin plugin, String name, BlockDesign design) {
		this(plugin, name, false, design);
		
		setDefaultId(1);
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param design
	 */
	public GenericBlock(Plugin plugin, String name, boolean allowRotation,
			BlockDesign design) {
		this.plugin = plugin;
		this.name = name;
		this.id = RockyManager.getMaterialManager().getRegisteredName(name,
				MaterialEnumType.BLOCK);
		this.allowRotation = allowRotation;
		this.blockItem = new GenericItem(plugin, name);
		this.material = new GenericMaterialWrapper(this);
		if (allowRotation) {
			this.design.add(1, design.rotate(90));
			this.design.add(2, design.rotate(180));
			this.design.add(3, design.rotate(270));
		}
		this.design.add(0, design);
		setDefaultId(1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDefaultId() {
		return defaultId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDefaultId(int id) {
		defaultId = id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Material loadPreInitialization(Plugin plugin,
			ConfigurationSection section, AddonPack pack) {
		this.plugin = plugin;
		this.name = section.getString("Title", "Undefined");
		this.id = RockyManager.getMaterialManager().getRegisteredName(name,
				MaterialEnumType.BLOCK);
		this.allowRotation = section.getBoolean("Rotation", false);
		this.blockItem = new GenericItem(plugin, name);
		this.blockItem.setCreativeTab(MaterialTab.valueOf(section.getString(
				"CreativeTab", "BLOCK")));
		setStepSound(section.getString("StepSound", "stone"));
		this.friction = (float) section.getDouble("Friction", 0.6f);
		this.hardness = (float) section.getDouble("Hardness", 1.5f);
		this.isOpaque = section.getBoolean("Opaque", false);
		this.light = section.getInt("LightLevel", 0);
		this.type = BlockType.valueOf(section.getString("Type"));
		this.material = new GenericMaterialWrapper(this);
		this.defaultId = section.getInt("DefaultId", defaultId);
		
		// Load the shape of the block
		String textureFile = section.getString("Texture", section.getName()
				+ ".png");
		String shapeFile = section.getString("Shape", section.getName()
				+ ".shape");
		if (!pack.hasEntry(shapeFile)) {
			shapeFile = Block.DEFAULT_SHAPE;
		}
		if (!pack.hasEntry(textureFile)) {
			throw new IllegalArgumentException(textureFile
					+ " cannot be found.");
		}
		Texture texture = null;
		if (RockyManager.getResourceManager().hasResource(textureFile)) {
			texture = (Texture) RockyManager.getResourceManager().getResource(
					textureFile);
		} else {
			texture = new Texture(plugin, textureFile,
					pack.getInputStream(textureFile));
		}
		List<String> coordsElement = (List<String>) section.getList("Coords");

		// Load the block shape
		YamlConfiguration configuration = null;
		if (shapeFile.equals(Block.DEFAULT_SHAPE)) {
			configuration = RockyManager.getMaterialManager().getDefaultShape();
		} else {
			configuration = YamlConfiguration.loadConfiguration(pack
					.getInputStream(shapeFile));
		}
		BlockDesign design = new GenericBlockDesign(configuration, texture,
				coordsElement);
		design.setRenderOrder(section.getBoolean("Transparency", false) ? BlockRenderOrder.OPAQUE
				: BlockRenderOrder.TRANSPARENT);
		setBlockDesign(design);

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material loadPostInitialization(Plugin plugin,
			ConfigurationSection section, AddonPack pack) {
		String[] dropType = section.getString("ItemDrop", "-1:-1").split(":");
		if (!dropType[0].equals("-1")) {
			this.dropStack = new ItemStack(RockyManager.getMaterialManager()
					.getRegisteredName(dropType[0], MaterialEnumType.ITEM),
					Integer.valueOf(dropType[1]));
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStepSound() {
		return stepSound.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setStepSound(String effect) {
		if (RockyManager.getResourceManager().hasResource(effect)) {
			stepSound = (Sound) RockyManager.getResourceManager().getResource(
					effect);
		} else {
			stepSound = new Sound(plugin, effect);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getFriction() {
		return friction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setFriction(float friction) {
		this.friction = friction;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getHardness() {
		return hardness;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setHardness(float hardness) {
		this.hardness = hardness;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isOpaque() {
		return isOpaque;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setOpaque(boolean isOpaque) {
		this.isOpaque = isOpaque;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLightLevel() {
		return light;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setLightLevel(float level) {
		this.light = level;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockType getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item getItemBlock() {
		return blockItem;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemStack getDropType() {
		return dropStack;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setDropType(ItemStack item) {
		this.dropStack = item;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setBlockDesign(BlockDesign design) {
		if (allowRotation) {
			setBlockDesign(design.rotate(90), 1);
			setBlockDesign(design.rotate(180), 2);
			setBlockDesign(design.rotate(270), 3);
		}
		setBlockDesign(design, 0);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign getBlockDesign() {
		return design.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setBlockDesign(BlockDesign design, int id) {
		this.design.add(id, design);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlockDesign getBlockDesign(int id) {
		return design.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean allowRotation() {
		return allowRotation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public net.minecraft.server.v1_5_R3.Material getMaterial() {
		return material;
	}

}
