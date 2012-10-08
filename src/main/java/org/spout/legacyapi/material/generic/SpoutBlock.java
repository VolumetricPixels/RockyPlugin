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
package org.spout.legacyapi.material.generic;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.spout.legacyapi.SpoutManager;
import org.spout.legacyapi.block.design.BlockDesign;
import org.spout.legacyapi.material.Block;
import org.spout.legacyapi.material.BlockType;
import org.spout.legacyapi.material.Item;
import org.spout.legacyapi.material.Material;
import org.spout.legacyapi.material.MaterialType;

/**
 * 
 */
public class SpoutBlock implements Block {

	protected int id;
	protected Plugin plugin;
	protected String name;
	protected String stepSound;
	protected float friction;
	protected float hardness;
	protected boolean isOpaque;
	protected int light;
	protected BlockType type;
	protected Item blockItem;
	protected ItemStack dropStack;
	protected List<BlockDesign> design = new LinkedList<BlockDesign>();
	protected boolean allowRotation;
	protected net.minecraft.server.Material material;
	
	/**
	 * 
	 */
	public SpoutBlock() {
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param design
	 */
	public SpoutBlock(Plugin plugin, String name, BlockDesign design) {
		this(plugin, name, false, design);
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param design
	 */
	public SpoutBlock(Plugin plugin, String name, boolean allowRotation,
			BlockDesign design) {
		this.plugin = plugin;
		this.name = name;
		this.id = SpoutManager.getMaterialManager().getRegisteredName(
				plugin.getName() + "_" + name, MaterialType.BLOCK);
		this.allowRotation = true;
		this.setBlockDesign(design);
		this.blockItem = new SpoutItem(plugin, name);
		this.material = new SpoutMaterialWrapper(this);
		
		SpoutManager.getResourceManager().addToCache(plugin,
				design.getTexture().getName());
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
	public Plugin getPlugin() {
		return plugin;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material load(Plugin plugin, ConfigurationSection section) {
		this.plugin = plugin;
		this.name = section.getName();
		this.id = SpoutManager.getMaterialManager().getRegisteredName(
				plugin.getName() + "_" + name, MaterialType.BLOCK);
		this.allowRotation = section.getBoolean("IsRotated", false);
		this.blockItem = new SpoutItem(plugin, name);
		this.stepSound = section.getString("StepSound", "Default");
		this.friction = (float) section.getDouble("Friction", 0.6f);
		this.hardness = (float) section.getDouble("Hardness", 1.5f);
		this.isOpaque = section.getBoolean("IsOpaque", false);
		this.light = section.getInt("Light", 0);
		this.type = BlockType.valueOf(section.getString("Type"));

		String[] dropType = section.getString("Drop", "-1:-1").split(":");
		if (!dropType[0].equals("-1")) {
			this.dropStack = new ItemStack(SpoutManager.getMaterialManager()
					.getRegisteredName(dropType[0], MaterialType.ITEM),
					Integer.valueOf(dropType[1]));
		}
		// TODO: Get the material
		// TODO: Get the design of the block
		SpoutManager.getResourceManager().addToCache(plugin,
				getBlockDesign().getTexture().getName());

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
		return stepSound;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setStepSound(String effect) {
		this.stepSound = effect;
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
	public int getLightLevel() {
		return light;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Block setLightLevel(int level) {
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
			setBlockDesign(design, 0);
			setBlockDesign(design.rotate(90), 1);
			setBlockDesign(design.rotate(180), 2);
			setBlockDesign(design.rotate(270), 3);
			return this;
		}
		return setBlockDesign(design, 0);
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
	public net.minecraft.server.Material getMaterial() {
		return material;
	}

}
