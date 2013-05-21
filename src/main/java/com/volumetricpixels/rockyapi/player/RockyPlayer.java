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
package com.volumetricpixels.rockyapi.player;

import java.util.List;

import net.minecraft.server.v1_5_R3.EntityPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.volumetricpixels.rockyapi.math.Color;
import com.volumetricpixels.rockyapi.packet.Packet;
import com.volumetricpixels.rockyapi.packet.PacketVanilla;

/**
 * Represents a RockyPlayer, which extends the standard Bukkit Player.
 * RockyPlayer's can be retrieved by casting Bukkit's org.bukkit.entity.Player
 * class
 */
public interface RockyPlayer extends Player {
	/**
	 * 
	 * @return
	 */
	String getLocale();
	
	/**
	 * 
	 * @return
	 */
	EntityPlayer getHandle();

	/**
	 * Return's true if the player is using the mod
	 * 
	 * @return if the mod enabled
	 */
	boolean isModded();

	/**
	 * 
	 * @param name
	 * @return
	 */
	boolean hasAchievement(int id);

	/**
	 * 
	 * @param name
	 * @param flag
	 */
	void setAchievement(int id, boolean flag);

	/**
	 * 
	 * @return
	 */
	Integer[] getAchievement();

	/**
	 * Gets the render distance that the player views, or null if unknown
	 * 
	 * @return render distance
	 */
	RenderDistance getRenderDistance();

	/**
	 * Sets the render distance that the player views
	 * 
	 * @param distance
	 *            to set
	 */
	void setRenderDistance(RenderDistance distance);

	/**
	 * Gets the maximum render distance that the player can view, or null if
	 * unknown
	 * 
	 * @return maximum distance
	 */
	RenderDistance getMaximumRenderDistance();

	/**
	 * Sets the maximum render distance that the player can view
	 * 
	 * @param maximum
	 *            distance
	 */
	void setMaximumRenderDistance(RenderDistance maximum);

	/**
	 * Gets the minimum render distance that the player can view, or null if
	 * unknown
	 * 
	 * @return minimum distance
	 */
	RenderDistance getMinimumRenderDistance();

	/**
	 * Sets the minimum render distance that the player can view
	 * 
	 * @param minimum
	 *            distance
	 */
	void setMinimumRenderDistance(RenderDistance minimum);

	/**
	 * 
	 * @param title
	 * @param message
	 * @param material
	 */
	void sendNotification(String title, String message, Material material);

	/**
	 * 
	 * @param title
	 * @param message
	 * @param material
	 * @param data
	 * @param time
	 */
	void sendNotification(String title, String message,
			Material material,  int time);

	/**
	 * 
	 * @param title
	 * @param message
	 * @param item
	 * @param time
	 */
	void sendNotification(String title, String message, ItemStack item,
			int time);

	/**
	 * Gets the gravity multiplier for this player
	 * <p/>
	 * Default gravity modifier is 1
	 * 
	 * @return gravity multiplier
	 */
	float getGravityMultiplier();

	/**
	 * 
	 * @param multiplier
	 */
	void setGravityMultiplier(float multiplier);

	/**
	 * 
	 * @return
	 */
	float getSwimmingMultiplier();

	/**
	 * 
	 * @param multiplier
	 */
	void setSwimmingMultiplier(float multiplier);

	/**
	 * 
	 * @return
	 */
	float getWalkingMultiplier();

	/**
	 * 
	 * @param multiplier
	 */
	void setWalkingMultiplier(float multiplier);

	/**
	 * 
	 * @return
	 */
	float getJumpingMultiplier();

	/**
	 * 
	 * @param multiplier
	 */
	void setJumpingMultiplier(float multiplier);

	/**
	 * 
	 * @return
	 */
	float getAirSpeedMultiplier();

	/**
	 * 
	 * @param multiplier
	 */
	void setAirSpeedMultiplier(float multiplier);

	/**
	 * Resets all modified movement speeds, including walking, swimming,
	 * gravity, air speed, and jumping modifiers.
	 */
	void resetMovement();

	/**
	 * 
	 * @param packet
	 */
	void sendPacket(PacketVanilla packet);

	/**
	 * 
	 * @param packet
	 */
	void sendImmediatePacket(PacketVanilla packet);

	/**
	 * Sets the skin of this player
	 * 
	 * @param url
	 *            to set to
	 */
	void setSkin(String url);

	/**
	 * Gets the skin url that this player is using
	 * 
	 * @return skin
	 */
	String getSkin();

	/**
	 * Resets the skin to the default
	 */
	void resetSkin();

	/**
	 * Sets the cape url of this player
	 * 
	 * @param url
	 *            to set to
	 */
	void setCape(String url);

	/**
	 * Gets the cape that this player is wearing
	 * 
	 * @return cape url
	 */
	String getCape();

	/**
	 * Resets the cape that this player is wearing
	 */
	void resetCape();

	/**
	 * 
	 * @param title
	 */
	void setTitle(String title);

	/**
	 * 
	 * @param viewingPlayer
	 * @param title
	 */
	void setTitle(RockyPlayer viewingPlayer, String title);

	/**
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * 
	 * @param viewingPlayer
	 * @return
	 */
	String getTitle(RockyPlayer viewingPlayer);

	/**
	 * 
	 */
	void hideTitle();

	/**
	 * 
	 * @param viewingPlayer
	 */
	void hideTitleFrom(RockyPlayer viewingPlayer);

	/**
	 * Resets the title back to it's default state.
	 */
	void resetTitle();

	/**
	 * 
	 * @param viewingPlayer
	 */
	void resetTitleFor(RockyPlayer viewingPlayer);

	/**
	 * 
	 * @param player
	 * @return
	 */
	boolean hasObserver(Player player);

	/**
	 * 
	 * @return
	 */
	List<Player> getObservers();

	/**
	 * 
	 * @param player
	 */
	void addObserver(Player player);

	/**
	 * 
	 * @param player
	 */
	void removeObserver(Player player);

	/**
	 * 
	 * @param packet
	 */
	void sendPacketToObservers(Packet packet);

	/**
	 * 
	 * @param packet
	 */
	void sendPacketToObservers(PacketVanilla packet);

	/**
	 * 
	 * @param packet
	 */
	void sendPacket(Packet packet);

	/**
	 * 
	 * @return
	 */
	int getBuildVersion();

	/**
	 * 
	 * @return
	 */
	String getVersionString();

	/**
	 * 
	 * @param skyColor
	 */
	void setSkyColor(Color skyColor);

	/**
	 * 
	 * @return
	 */
	Color getSkyColor();

	/**
	 * 
	 * @param fogColor
	 */
	void setFogColor(Color fogColor);

	/**
	 * 
	 * @return
	 */
	Color getFogColor();

	/**
	 * 
	 * @param cloudColor
	 */
	void setCloudColor(Color cloudColor);

	/**
	 * 
	 * @return
	 */
	Color getCloudColor();

	/**
	 * 
	 * @param effect
	 */
	void playSound(String effect);

	/**
	 * 
	 * @param effect
	 * @param distance
	 */
	void playSound(String effect, int distance);

	/**
	 * 
	 * @param effect
	 * @param distance
	 * @param volumePercent
	 */
	void playSound(String effect, int distance, int volumePercent);


	/**
	 * 
	 * @param music
	 */
	void playMusic(String music);

	/**
	 * 
	 * @param music
	 * @param volumePercent
	 */
	void playMusic(String music, int volumePercent);

	/**
	 * Stops the background music if it is playing for the given player
	 */
	void stopMusic();

	/**
	 * 
	 * @param resetTimer
	 */
	void stopMusic(boolean resetTimer);


	/**
	 * 
	 * @param resetTimer
	 * @param fadeOutTime
	 */
	void stopMusic(boolean resetTimer, int fadeOutTime);

	/**
	 * 
	 * @param build
	 */
	void setBuildVersion(int build);

	/**
	 * 
	 */
	void onTick();
}
