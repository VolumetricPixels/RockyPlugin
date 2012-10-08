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
package org.spout.legacyapi.material.generic;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.spout.legacyapi.material.Material;
import org.spout.legacyapi.material.Weapon;
import org.spout.legacyapi.material.WeaponType;
import org.spout.legacyapi.resource.Texture;

/**
 * 
 */
public class SpoutWeapon extends SpoutItem implements Weapon {

	private int durability;
	private int damage;
	private int attackSpeed;
	private boolean isBlockAllowed;
	private WeaponType type;

	/**
	 * 
	 */
	public SpoutWeapon() {	
	}
	
	/**
	 * 
	 * @param plugin
	 * @param name
	 * @param texture
	 */
	public SpoutWeapon(Plugin plugin, String name, Texture texture) {
		super(plugin, name, texture);
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
	public Material load(Plugin plugin, ConfigurationSection section) {
		super.load(plugin, section);

		this.durability = section.getInt("Durability", 100);
		this.damage = section.getInt("Damage", 1);
		this.attackSpeed = section.getInt("Speed", 215);
		this.type = WeaponType.valueOf(section.getString("Type"));
		this.isBlockAllowed = (type == WeaponType.RANGE ? false :
			section.getBoolean("IsBlocking", true));
		return this;
	}
}
