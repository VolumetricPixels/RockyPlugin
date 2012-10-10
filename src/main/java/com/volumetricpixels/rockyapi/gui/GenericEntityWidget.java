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
package com.volumetricpixels.rockyapi.gui;

import java.io.IOException;

import com.volumetricpixels.rockyapi.packet.PacketInputStream;
import com.volumetricpixels.rockyapi.packet.PacketOutputStream;

/**
 * 
 */
public class GenericEntityWidget extends GenericWidget implements EntityWidget {
	private int entityId = 0;

	/**
	 * 
	 */
	public GenericEntityWidget() {
	}

	/**
	 * 
	 * @param entityId
	 */
	public GenericEntityWidget(int entityId) {
		this.entityId = entityId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.EntityWidget;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityWidget setEntityId(int id) {
		if (entityId != id) {
			entityId = id;
			autoDirty();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getEntityId() {
		return entityId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		entityId = input.readInt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeInt(entityId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityWidget copy() {
		return ((EntityWidget) super.copy()).setEntityId(getEntityId());
	}
}