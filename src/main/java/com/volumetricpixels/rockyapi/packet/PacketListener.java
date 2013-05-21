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
package com.volumetricpixels.rockyapi.packet;

import com.volumetricpixels.rockyapi.player.RockyPlayer;

/**
 * 
 */
public interface PacketListener {

	/**
	 * Event that is called when the packet is about to be sent
	 * 
	 * @param player
	 *            The player the packet is sent to
	 * @param packet
	 *            The packet to check
	 * @return false if the packet should be stopped, true otherwise.
	 */
	boolean onPrePacket(RockyPlayer player, PacketVanilla packet);
}
