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
package com.volumetricpixels.rockyapi.player;

import java.util.List;

import net.minecraft.server.EntityPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.volumetricpixels.rockyapi.gui.InGameHUD;
import com.volumetricpixels.rockyapi.gui.Screen;
import com.volumetricpixels.rockyapi.gui.ScreenType;
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
	public EntityPlayer getHandle();
	
	/**
	 * Gets a copy of the in game HUD to attach widget and launch popups from
	 * 
	 * @return In game HUD
	 */
	public InGameHUD getMainScreen();

	/**
	 * Gets a copy of the current opened screen of the player. This can be the
	 * InGameHUD, but also other screen types. Check the screentype with
	 * Screen.getType();
	 * 
	 * @return the currently open screen
	 * @see Screen.getType()
	 */
	public Screen getCurrentScreen();

	/**
	 * Return's true if the player is using the mod
	 * 
	 * @return if the mod enabled
	 */
	public boolean isModded();

	/**
	 * Gets the render distance that the player views, or null if unknown
	 * 
	 * @return render distance
	 */
	public RenderDistance getRenderDistance();

	/**
	 * Sets the render distance that the player views
	 * 
	 * @param distance
	 *            to set
	 */
	public void setRenderDistance(RenderDistance distance);

	/**
	 * Gets the maximum render distance that the player can view, or null if
	 * unknown
	 * 
	 * @return maximum distance
	 */
	public RenderDistance getMaximumRenderDistance();

	/**
	 * Sets the maximum render distance that the player can view
	 * 
	 * @param maximum
	 *            distance
	 */
	public void setMaximumRenderDistance(RenderDistance maximum);

	/**
	 * Gets the minimum render distance that the player can view, or null if
	 * unknown
	 * 
	 * @return minimum distance
	 */
	public RenderDistance getMinimumRenderDistance();

	/**
	 * Sets the minimum render distance that the player can view
	 * 
	 * @param minimum
	 *            distance
	 */
	public void setMinimumRenderDistance(RenderDistance minimum);

	/**
	 * Send's the player a notification (using the existing Achievement Get
	 * window), with the given title, message, and item to render as a graphic
	 * The title and message may not exceed 26 characters in length The item to
	 * render may not be null
	 * 
	 * @param title
	 *            to send
	 * @param message
	 *            to send
	 * @param toRender
	 *            to render
	 */
	public void sendNotification(String title, String message, Material toRender);

	/**
	 * Send's the player a notification (using the existing Achievement Get
	 * window), with the given title, message, and item to render as a graphic
	 * The title and message may not exceed 26 characters in length The item to
	 * render may not be null
	 * 
	 * @param title
	 *            to send
	 * @param message
	 *            to send
	 * @param toRender
	 *            to render
	 * @param data
	 *            for the item to render
	 * @param time
	 *            for the notification to remain in milliseconds
	 */
	public void sendNotification(String title, String message,
			Material toRender, short data, int time);

	/**
	 * Send's the player a notification (using the existing Achievement Get
	 * window), with the given title, message, and item to render as a graphic
	 * The title and message may not exceed 26 characters in length The item to
	 * render may not be null
	 * 
	 * @param title
	 *            to send
	 * @param message
	 *            to send
	 * @param item
	 *            to render
	 * @param time
	 *            for the notification to remain in milliseconds
	 */
	public void sendNotification(String title, String message, ItemStack item,
			int time);

	/**
	 * Gets the gravity multiplier for this player
	 * <p/>
	 * Default gravity modifier is 1
	 * 
	 * @return gravity multiplier
	 */
	public float getGravityMultiplier();

	/**
	 * Modifies the effects of gravity on the player's y axis movement.
	 * <p/>
	 * Ex: setGravityMultiplier(10) will cause players to fall ten times faster
	 * than normal.
	 * <p/>
	 * Warning, large modifiers may trigger fly-hack warnings.
	 * <p/>
	 * Default gravity multiplier is 1
	 * 
	 * @param multiplier
	 *            to set.
	 */
	public void setGravityMultiplier(float multiplier);

	/**
	 * Gets the swimming multiplier for this player
	 * <p/>
	 * Default swimming modifier is 1
	 * 
	 * @return swimming multiplier
	 */
	public float getSwimmingMultiplier();

	/**
	 * Modifies the default swimming speed for this player
	 * <p/>
	 * Ex: setSwimmingMultiplier(10) will cause players to swim ten times faster
	 * than normal.
	 * <p/>
	 * Warning, large modifiers may trigger fly-hack warnings.
	 * <p/>
	 * Default swimming multiplier is 1.
	 * 
	 * @param multiplier
	 *            to set.
	 */
	public void setSwimmingMultiplier(float multiplier);

	/**
	 * Gets the walking multiplier for this player
	 * <p/>
	 * Default walking modifier is 1
	 * 
	 * @return walking multiplier
	 */
	public float getWalkingMultiplier();

	/**
	 * Modifies the default walking speed for this player
	 * <p/>
	 * Ex: setWalkingMultiplier(10) will cause players to walk ten times faster
	 * than normal.
	 * <p/>
	 * Warning, large modifiers may trigger fly-hack warnings.
	 * <p/>
	 * Default walking multiplier is 1.
	 * 
	 * @param multiplier
	 *            to set.
	 */
	public void setWalkingMultiplier(float multiplier);

	/**
	 * Gets the jumping multiplier for this player
	 * <p/>
	 * Default jumping modifier is 1
	 * 
	 * @return jumping multiplier
	 */
	public float getJumpingMultiplier();

	/**
	 * Modifies the default jumping speed for this player
	 * <p/>
	 * Ex: setJumpingMultiplier(10) will cause players to jump ten times higher
	 * than normal.
	 * <p/>
	 * Warning, large modifiers may trigger fly-hack warnings.
	 * <p/>
	 * Default jumping multiplier is 1.
	 * 
	 * @param multiplier
	 *            to set.
	 */
	public void setJumpingMultiplier(float multiplier);

	/**
	 * Gets the air speed multiplier for this player
	 * <p/>
	 * Default air speed modifier is 1
	 * 
	 * @return air speed multiplier
	 */
	public float getAirSpeedMultiplier();

	/**
	 * Modifies the default air speed for this player
	 * <p/>
	 * Ex: setAirSpeedMultiplier(10) will cause players to move horizontally
	 * while in the air ten times faster than normal.
	 * <p/>
	 * Warning, large modifiers may trigger fly-hack warnings.
	 * <p/>
	 * Default air speed multiplier is 1.
	 * 
	 * @param multiplier
	 *            to set.
	 */
	public void setAirSpeedMultiplier(float multiplier);

	/**
	 * Resets all modified movement speeds, including walking, swimming,
	 * gravity, air speed, and jumping modifiers.
	 */
	public void resetMovement();

	/**
	 * Returns either the server wide fly setting, or specific player setting if
	 * a plugin has used setCanFly()
	 * 
	 * @return whether this player can fly.
	 */
	public boolean canFly();

	/**
	 * Overrides the server wide fly setting, allowing this player to fly, or
	 * not to fly.
	 * 
	 * @param fly
	 */
	public void setCanFly(boolean fly);

	/**
	 * Sends a MCPacket to the client
	 * 
	 * @param packet
	 *            to send
	 */
	public void sendPacket(PacketVanilla packet);

	/**
	 * Sends the packet immediately. Packets sent using this method are placed
	 * at the start of the packet queue. If called from within a
	 * PacketListener's canSend method, the packet will be processed immediately
	 * after the current packet is handled.
	 * 
	 * @param packet
	 *            the packet to send
	 */
	public void sendImmediatePacket(PacketVanilla packet);

	/**
	 * Orders the client to reconnect to another server
	 * <p/>
	 * This method is also supported by some server to server teleporting mods.
	 * <p/>
	 * Players without the client mod will be given a kick message instructing
	 * them to join the other server
	 * 
	 * @param message
	 *            the message to include in the kick message for vanilla clients
	 * @param hostname
	 *            the hostname of the other server
	 * @param port
	 *            the port of the other server
	 */
	public void reconnect(String message, String hostname, int port);

	/**
	 * Orders the client to reconnect to another server.
	 * <p/>
	 * This method is also supported by some server to server teleporting mods.
	 * <p/>
	 * Players without the client mod will be given a kick message instructing
	 * them to join the other server
	 * 
	 * @param message
	 *            the message to include in the kick message for vanilla clients
	 * @param hostname
	 *            the hostname of the other server
	 */
	public void reconnect(String message, String hostname);

	/**
	 * Orders the client to reconnect to another server
	 * <p/>
	 * This method is also supported by some server to server teleporting mods.
	 * <p/>
	 * Players without the client mod will be given a kick message instructing
	 * them to join the other server
	 * 
	 * @param hostname
	 *            the hostname of the other server
	 * @param port
	 *            the port of the other server
	 */
	public void reconnect(String hostname, int port);

	/**
	 * Orders the client to reconnect to another server.
	 * <p/>
	 * This method is also supported by some server to server teleporting mods.
	 * <p/>
	 * Players without the client mod will be given a kick message instructing
	 * them to join the other server
	 * 
	 * @param hostname
	 *            the hostname of the other server
	 */
	public void reconnect(String hostname);

	/**
	 * Gets the active screen open for this player
	 * 
	 * @return active screen
	 */
	public ScreenType getActiveScreen();

	/**
	 * Orders the client to open the appriopriate screen type
	 * 
	 * @param type
	 *            of screen to open
	 */
	public void openScreen(ScreenType type);

	/**
	 * Sets the skin of this player
	 * 
	 * @param url
	 *            to set to
	 */
	public void setSkin(String url);

	/**
	 * Gets the skin url that this player is using
	 * 
	 * @return skin
	 */
	public String getSkin();

	/**
	 * Resets the skin to the default
	 */
	public void resetSkin();

	/**
	 * Sets the cape url of this player
	 * 
	 * @param url
	 *            to set to
	 */
	public void setCape(String url);

	/**
	 * Gets the cape that this player is wearing
	 * 
	 * @return cape url
	 */
	public String getCape();

	/**
	 * Resets the cape that this player is wearing
	 */
	public void resetCape();

	/**
	 * Sets the overhead title for the player.
	 * <p/>
	 * Note: '/n' or "/n" in the title will create a new line. You may use as
	 * many lines in a title as you desire.
	 * <p/>
	 * Note: You can color titles with the {@link org.bukkit#ChatColor} colors.
	 * 
	 * @param title
	 *            to set overhead.
	 */
	public void setTitle(String title);

	/**
	 * Sets the overhead title for the player, only visible to the
	 * viewingPlayer.
	 * <p/>
	 * Note: '/n' or "/n" in the title will create a new line. You may use as
	 * many lines in a title as you desire.
	 * <p/>
	 * Note: You can color titles with the {@link org.bukkit#ChatColor} colors.
	 * 
	 * @param viewingPlayer
	 *            that this title is visible to
	 * @param title
	 *            to set overhead.
	 */
	public void setTitleFor(RockyPlayer viewingPlayer, String title);

	/**
	 * Gets the overhead title for the player.
	 * 
	 * @return overhead title
	 */
	public String getTitle();

	/**
	 * Gets the overhead title that is visible to the viewingPlayer
	 * 
	 * @param viewingPlayer
	 *            that this title is visible for
	 * @return overhead title
	 */
	public String getTitleFor(RockyPlayer viewingPlayer);

	/**
	 * Completely hides the title from view of all players.
	 */
	public void hideTitle();

	/**
	 * Completely hides the title from the view of the viewingPlayer
	 * 
	 * @param viewingPlayer
	 *            to hide the title from.
	 */
	public void hideTitleFrom(RockyPlayer viewingPlayer);

	/**
	 * Resets the title back to it's default state.
	 */
	public void resetTitle();

	/**
	 * Resets the title back to it's default state for the viewingPlayer.
	 * 
	 * @param viewingPlayer
	 */
	public void resetTitleFor(RockyPlayer viewingPlayer);

	/**
	 * Internal use only
	 * 
	 * @param type
	 * @param packet
	 */
	public void openScreen(ScreenType type, boolean packet);

	/**
	 * 
	 * @param player
	 * @return
	 */
	public boolean hasObserver(Player player);
	
	/**
	 * 
	 * @return
	 */
	public List<Player> getObservers();
	
	/**
	 * 
	 * @param player
	 */
	public void addObserver(Player player);
	
	/**
	 * 
	 * @param player
	 */
	public void removeObserver(Player player);
	
	/**
	 * 
	 * @param packet
	 */
	public void sendPacketToObservers(Packet packet);
	
	/**
	 * Internal use only
	 * 
	 * @param packet
	 */
	public void sendPacket(Packet packet);

	/**
	 * Internal use only
	 * 
	 * @param packet
	 */
	public void sendDelayedPacket(Packet packet);

	/**
	 * Adds a waypoint to the minimap of the client, with the given loation and
	 * given name. <br/>
	 * <br/>
	 * Note: This waypoint will be cleared when the user logs off or changes
	 * worlds.
	 * 
	 * @param waypoint
	 *           waypoint
	 */
	public void addWaypoint(Waypoint waypoint);

	/**
	 * Gets the Player's SC version as an int.
	 */
	public int getBuildVersion();

	/**
	 * Gets the Player's SC version as a String.
	 */
	public String getVersionString();

	/**
	 * Checks if the player has that accessory type.
	 * 
	 * @param type
	 *            The type to check for.
	 * @return Whether the player has that type of accessory.
	 */
	public boolean hasAccessory(AccessoryType type);

	/**
	 * Adds a new accessory to the player.
	 * 
	 * @param type
	 *            The accessory's type.
	 * @param url
	 *            The accessory's url.
	 */
	public void addAccessory(AccessoryType type, String url);

	/**
	 * Removes an accessory from the player.
	 * 
	 * @param type
	 *            The accessory type.
	 * @return The accessory's url.
	 */
	public String removeAccessory(AccessoryType type);

	/**
	 * Gets the accessory's url
	 * 
	 * @param type
	 *            The accessory.
	 * @return The url.
	 */
	public String getAccessoryURL(AccessoryType type);

	/**
	 * Gets the y-axis height that cloud tops are rendered at for the given
	 * player
	 * 
	 * @return height
	 */
	public int getCloudHeight();

	/**
	 * Sets the y-axis heigh that cloud tops are rendered at for the given
	 * player
	 * 
	 * @param y
	 *            axis level to render the cloud top at
	 */
	public void setCloudHeight(int y);

	/**
	 * Gets the frequency of stars overhead at night. The default frequency is
	 * 1500. Higher frequencies cause more stars, lower, less
	 * 
	 * @param player
	 *            to get the frequency for
	 */
	public int getStarFrequency();

	/**
	 * Sets the frequency of stars overhead at night for the given player
	 * 
	 * @param frequency
	 */
	public void setStarFrequency(int frequency);

	/**
	 * Gets the percent size of the sun, relative to the default size. 100
	 * percent is default size. 200 percent is double size. 50 percent is half
	 * size.
	 * 
	 * @return percent size of the sun
	 */
	public int getSunSizePercent();

	/**
	 * Sets the percent size of the sun, relative to the default size. 100
	 * percent is the default size. 200 percent is double size. 50 percent is
	 * half size.
	 * 
	 * @param percent
	 *            to set
	 */
	public void setSunSizePercent(int percent);

	/**
	 * Gets the custom url of the custom sun texture, or null if no custom
	 * texture is set
	 * 
	 * @return url of the custom texture
	 */
	public String getSunTextureUrl();

	/**
	 * Sets the texture of the sun to the picture in the given format, or if the
	 * url is null, resets the sun to the default texture The texture must be a
	 * square png to render correctly (e.g 32x32, 64x64, etc)
	 * 
	 * @param Url
	 *            of the texture
	 */
	public void setSunTextureUrl(String url);

	/**
	 * Gets the size percent of the moon, relative to the default size. 100
	 * percent is the default size. 200 percent is double size. 50 percent is
	 * half size.
	 * 
	 * @return percent size
	 */
	public int getMoonSizePercent();

	/**
	 * Sets the percent size of the moon, relative to the default size. 100
	 * percent is the default size. 200 percent is double size. 50 percent is
	 * half size.
	 * 
	 * @param percent
	 *            to set
	 */
	public void setMoonSizePercent(int percent);

	/**
	 * Gets the custom url of the custom moon texture, or null if no custom
	 * texture is set
	 * 
	 * @return url of the custom texture
	 */
	public String getMoonTextureUrl();

	/**
	 * Sets the texture of the moon to the picture in the given format, or if
	 * the url is null, resets the moon to the default texture The texture must
	 * be a square png to render correctly (e.g 32x32, 64x64, etc)
	 * 
	 * @param Url
	 *            of the texture
	 */
	public void setMoonTextureUrl(String url);

	/**
	 * Sets the sky color for the player
	 * 
	 * @param skyColor
	 */
	public void setSkyColor(Color skyColor);

	/**
	 * @return the set sky color of given player
	 * @warning the return value can be null!
	 */
	public Color getSkyColor();

	/**
	 * Sets the fog color for the player
	 * 
	 * @param fogColor
	 */
	public void setFogColor(Color fogColor);

	/**
	 * @return the set fog color of given player.
	 * @warning the return value can be null!
	 */
	public Color getFogColor();

	/**
	 * Sets the cloud color for the player
	 * 
	 * @param player
	 * @param cloudColor
	 */
	public void setCloudColor(Color cloudColor);

	/**
	 * @return the set cloud color of the player
	 * @warning the return value can be null!
	 */
	public Color getCloudColor();

	/**
	 * Plays a sound effect for the target player, at the given location
	 * 
	 * @param effect
	 *            to play
	 * @param location
	 *            to play at
	 */
	public void playSoundEffect(String effect);

	/**
	 * Plays a sound effect for the target player, at the given location
	 * 
	 * @param effect
	 *            to play
	 * @param location
	 *            to play at
	 * @param distance
	 *            away it can be heard from (in full blocks) or -1 for any
	 *            distance
	 */
	public void playSoundEffect(String effect, int distance);

	/**
	 * Plays a sound effect for the target player, at the given location, with
	 * the given intensity and given volume The intensity is how far away (in
	 * full blocks) players can be and hear the sound effect at full volume.
	 * 
	 * @param effect
	 *            to play
	 * @param distance
	 *            away it can be heard from (in full blocks) or -1 for any
	 *            distance
	 * @param volumePercent
	 *            to play at (100 = normal, 200 = double volume, 50 = half
	 *            volume)
	 */
	public void playSoundEffect(String effect, int distance,
			int volumePercent);

	/**
	 * Plays the music for the target player
	 * 
	 * @param music
	 *            to play
	 */
	public void playMusic(String music);

	/**
	 * Plays the music for the target player at the given volume
	 * 
	 * @param music
	 *            to play
	 * @param volumePercent
	 *            to play at (100 = normal, 200 = double volume, 50 = half
	 *            volume)
	 */
	public void playMusic(String music, int volumePercent);

	/**
	 * Stops the background music if it is playing for the given player
	 */
	public void stopMusic();

	/**
	 * Stops the background music if it is playing for the given player
	 * 
	 * @param resetTimer
	 *            whether to reset the timer (between 12000-24000 ticks) before
	 *            new music plays
	 */
	public void stopMusic(boolean resetTimer);

	/**
	 * Stops the background music if it is playing for the given player
	 * 
	 * @param resetTimer
	 *            whether to reset the timer (between 12000-24000 ticks) before
	 *            new music plays
	 * @param fadeOutTime
	 *            time in ms for the current audio to fade out for
	 */
	public void stopMusic(boolean resetTimer, int fadeOutTime);

	/**
	 * 
	 * @param build
	 */
	public void setBuildVersion(int build);
	
	/**
	 * 
	 */
	public void onTick();
	
	/**
	 * 
	 */
	public void updateWaypoints();
}
