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
package com.volumetricpixels.rockyplugin;

import java.util.Set;

import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

/**
 * 
 */
public class RockyPermissible implements Permissible {
	protected Permissible perm;

	/**
	 * 
	 * @param permissible
	 */
	public RockyPermissible(Permissible permissible) {
		this.perm = permissible;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isOp() {
		return perm.isOp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOp(boolean value) {
		perm.setOp(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PermissionAttachment addAttachment(Plugin plugin) {
		return perm.addAttachment(plugin);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
		return perm.addAttachment(plugin, ticks);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name,
			boolean value) {
		return perm.addAttachment(plugin, name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name,
			boolean value, int ticks) {
		return perm.addAttachment(plugin, name, value, ticks);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return perm.getEffectivePermissions();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission(String perm) {
		return this.perm.hasPermission(perm);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission(Permission perm) {
		return this.perm.hasPermission(perm);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPermissionSet(String name) {
		return perm.isPermissionSet(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPermissionSet(Permission perm) {
		return this.perm.isPermissionSet(perm);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recalculatePermissions() {
		perm.recalculatePermissions();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAttachment(PermissionAttachment attachment) {
		perm.removeAttachment(attachment);
	}
}
