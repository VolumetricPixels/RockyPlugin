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
package com.volumetricpixels.rockyapi.material.generic;

import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.volumetricpixels.rockyapi.material.Material;
import com.volumetricpixels.rockyapi.material.Weapon;
import com.volumetricpixels.rockyapi.material.WeaponType;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;
import com.volumetricpixels.rockyapi.resource.AddonPack;
import com.volumetricpixels.rockyapi.resource.Texture;

/**
 * 
 */
public class GenericWeapon extends GenericItem implements Weapon {

	protected int durability;
	protected int damage;
	protected int attackSpeed;
	protected boolean isBlockAllowed;
	protected WeaponType type;

	/**
	 * 
	 */
	public GenericWeapon() {
	}

	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public GenericWeapon(Plugin plugin, String name, Texture texture) {
		super(plugin, name, texture);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTypeId() {
		return 3;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDurability() {
		return durability;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon setDurability(int durability) {
		this.durability = durability;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDamage() {
		return damage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon setDamage(int damage) {
		this.damage = damage;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getAttackSpeed() {
		return attackSpeed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon setAttackSpeed(int tickSpeed) {
		this.attackSpeed = tickSpeed;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBlockFlag() {
		return isBlockAllowed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon setBlockFlag(boolean allowBlock) {
		this.isBlockAllowed = allowBlock;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WeaponType getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Material loadPreInitialization(Plugin plugin, ConfigurationSection section,
			AddonPack pack) {
		super.loadPreInitialization(plugin, section, pack);

		this.durability = section.getInt("Durability", 100);
		this.damage = section.getInt("Damage", 1);
		this.attackSpeed = section.getInt("AttackSpeed", 215);
		this.type = WeaponType.valueOf(section.getString("Type", "MELEE"));
		this.isBlockAllowed = (type == WeaponType.RANGE ? false : section
				.getBoolean("IsBlocking", true));
		this.setStackable(false);

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToPacket(PacketOutputStream out) throws IOException {
		super.writeToPacket(out);

		out.writeBoolean(isBlockAllowed);
		out.writeByte(type.ordinal());
		out.writeShort(attackSpeed);
		out.writeShort(durability);
	}
}
