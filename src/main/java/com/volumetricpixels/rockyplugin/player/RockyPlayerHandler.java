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
package com.volumetricpixels.rockyplugin.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.server.DedicatedServer;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.INetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetHandler;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet250CustomPayload;
import net.minecraft.server.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.fest.reflect.core.Reflection;
import org.fest.reflect.field.Invoker;

import com.volumetricpixels.rockyapi.gui.GenericOverlayScreen;
import com.volumetricpixels.rockyapi.gui.InGameHUD;
import com.volumetricpixels.rockyapi.gui.InGameScreen;
import com.volumetricpixels.rockyapi.gui.OverlayScreen;
import com.volumetricpixels.rockyapi.gui.Screen;
import com.volumetricpixels.rockyapi.gui.ScreenAction;
import com.volumetricpixels.rockyapi.gui.ScreenType;
import com.volumetricpixels.rockyapi.math.Color;
import com.volumetricpixels.rockyapi.packet.Packet;
import com.volumetricpixels.rockyapi.packet.PacketVanilla;
import com.volumetricpixels.rockyapi.packet.protocol.PacketAccessory;
import com.volumetricpixels.rockyapi.packet.protocol.PacketAlert;
import com.volumetricpixels.rockyapi.packet.protocol.PacketMovementAddon;
import com.volumetricpixels.rockyapi.packet.protocol.PacketPlaySound;
import com.volumetricpixels.rockyapi.packet.protocol.PacketPlayerAppearance;
import com.volumetricpixels.rockyapi.packet.protocol.PacketScreenAction;
import com.volumetricpixels.rockyapi.packet.protocol.PacketSetVelocity;
import com.volumetricpixels.rockyapi.packet.protocol.PacketSkyAddon;
import com.volumetricpixels.rockyapi.packet.protocol.PacketStopMusic;
import com.volumetricpixels.rockyapi.packet.protocol.PacketWaypoint;
import com.volumetricpixels.rockyapi.packet.protocol.PacketWidget;
import com.volumetricpixels.rockyapi.player.AccessoryType;
import com.volumetricpixels.rockyapi.player.RenderDistance;
import com.volumetricpixels.rockyapi.player.RockyPlayer;
import com.volumetricpixels.rockyapi.player.Waypoint;
import com.volumetricpixels.rockyplugin.Rocky;
import com.volumetricpixels.rockyplugin.packet.RockyPacket;
import com.volumetricpixels.rockyplugin.packet.RockyPacketHandler;

/**
 * 
 */
public class RockyPlayerHandler extends CraftPlayer implements RockyPlayer {

	private int build = -1;
	private List<Packet> queuePackets = new LinkedList<Packet>();
	private boolean isAllowedToFly = false;
	private Map<AccessoryType, String> accessories = new HashMap<AccessoryType, String>();
	private InGameScreen mainScreen;
	private ScreenType activeScreen = ScreenType.GAME_SCREEN;
	private GenericOverlayScreen currentScreen;
	private List<Player> observers = new LinkedList<Player>();
	private String skin, cape, title;
	private Map<String, String> titleFor;
	private List<Integer> achievementList = new ArrayList<Integer>();
	private long velocityAdjustment = System.currentTimeMillis();

	/**
	 * Movement Addon variables
	 */
	private float[] movementModifier = new float[] { 1.0f, 1.0f, 1.0f, 1.0f,
			1.0f };
	private boolean needMovementUpdate = false;

	/**
	 * Sky Addon variables
	 */
	private boolean needSkyUpdate = false;
	private int skyCloudHeight = 108, skyStarFrequency = 1500,
			skySunPercent = 100, skyMoonPercent = 100;
	private String skySunUrl = "[R]", skyMoonUrl = "[R]";
	private Color skyColor = Color.White, skyFogColor = Color.White,
			skyCloudColor = Color.White;

	/**
	 * View Distance Addon
	 */
	private RenderDistance minimumDistance = RenderDistance.TINY;
	private RenderDistance maximumDistance = RenderDistance.VERY_FAR;
	private RenderDistance currentDistance = RenderDistance.NORMAL;

	/**
	 * 
	 * @param server
	 * @param entity
	 */
	public RockyPlayerHandler(CraftServer server, EntityPlayer entity) {
		super(server, entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLocale() {
		return Reflection.field("d").ofType(String.class)
				.in(getHandle().getLocale()).get();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public InGameHUD getMainScreen() {
		return mainScreen;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public Screen getCurrentScreen() {
		if (getActiveScreen() == ScreenType.GAME_SCREEN)
			return getMainScreen();
		else
			return currentScreen;

	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public boolean isModded() {
		return (build != -1);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public boolean hasAchievement(int id) {
		return achievementList.contains(id);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void setAchievement(int id, boolean flag) {
		if (flag)
			achievementList.add(id);
		else
			achievementList.remove(id);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public Integer[] getAchievement() {
		return achievementList.toArray(new Integer[0]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RenderDistance getRenderDistance() {
		return currentDistance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRenderDistance(RenderDistance distance) {
		Invoker<PlayerManager> pm = Reflection.field("manager")
				.ofType(PlayerManager.class).in(getHandle().world);
		((RockyPlayerServerManager) pm.get()).removePlayer(getHandle());
		
		if (distance.ordinal() > minimumDistance.ordinal()) {
			currentDistance = minimumDistance;
		} else if (distance.ordinal() < maximumDistance.ordinal()) {
			currentDistance = maximumDistance;
		} else
			currentDistance = distance;

		((RockyPlayerServerManager) pm.get()).addPlayer(getHandle());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RenderDistance getMaximumRenderDistance() {
		return maximumDistance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMaximumRenderDistance(RenderDistance maximum) {
		maximumDistance = maximum;
		if (currentDistance.ordinal() < maximum.ordinal())
			setRenderDistance(maximum);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RenderDistance getMinimumRenderDistance() {
		return minimumDistance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMinimumRenderDistance(RenderDistance minimum) {
		minimumDistance = minimum;
		if (currentDistance.ordinal() > minimum.ordinal())
			setRenderDistance(minimum);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendNotification(String title, String message, Material toRender) {
		sendNotification(title, message, toRender, (short) 0, 20 * 5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendNotification(String title, String message,
			Material toRender, short data, int time) {
		if (!isModded()) {
			return;
		}
		if (toRender == null || toRender == Material.AIR) {
			throw new IllegalArgumentException(
					"The item to render may not be null or air");
		}
		if (ChatColor.stripColor(title).length() > 26 || title.length() > 78) {
			throw new UnsupportedOperationException(
					"Notification titles can not be greater than 26 chars + 26 colors");
		}
		if (ChatColor.stripColor(message).length() > 26
				|| message.length() > 78) {
			throw new UnsupportedOperationException(
					"Notification messages can not be greater than 26 chars + 26 colors");
		}
		sendPacket(new PacketAlert(title, message, toRender.getId(), data, time));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendNotification(String title, String message, ItemStack item,
			int time) {
		sendNotification(title, message, item.getType(), item.getDurability(),
				time);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getGravityMultiplier() {
		return movementModifier[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGravityMultiplier(float multiplier) {
		movementModifier[0] = multiplier;
		needMovementUpdate = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getSwimmingMultiplier() {
		return movementModifier[1];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSwimmingMultiplier(float multiplier) {
		movementModifier[1] = multiplier;
		needMovementUpdate = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getWalkingMultiplier() {
		return movementModifier[2];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setWalkingMultiplier(float multiplier) {
		movementModifier[2] = multiplier;
		needMovementUpdate = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getJumpingMultiplier() {
		return movementModifier[3];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setJumpingMultiplier(float multiplier) {
		movementModifier[3] = multiplier;
		needMovementUpdate = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getAirSpeedMultiplier() {
		return movementModifier[4];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAirSpeedMultiplier(float multiplier) {
		movementModifier[4] = multiplier;
		needMovementUpdate = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetMovement() {
		for (int i = 0; i < movementModifier.length; i++) {
			movementModifier[i] = 1.0f;
		}
		needMovementUpdate = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canFly() {
		return isAllowedToFly
				&& (System.currentTimeMillis() < velocityAdjustment);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCanFly(boolean fly) {
		this.isAllowedToFly = fly;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVelocity(Vector velocity) {
		if (!isModded()) {
			return;
		}
		PlayerVelocityEvent event = new PlayerVelocityEvent(this, velocity);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			sendPacket(new PacketSetVelocity(getEntityId(), event.getVelocity()
					.getX(), event.getVelocity().getY(), event.getVelocity()
					.getZ()));
		}
		double speedX = Math.abs(event.getVelocity().getX()
				* event.getVelocity().getX());
		double speedY = Math.abs(event.getVelocity().getY()
				* event.getVelocity().getY());
		double speedZ = Math.abs(event.getVelocity().getZ()
				* event.getVelocity().getZ());
		double speed = speedX + speedY + speedZ;

		velocityAdjustment = System.currentTimeMillis() + (long) (speed * 5);
		getHandle().velocityChanged = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendPacket(PacketVanilla packet) {
		getHandle().netServerHandler.sendPacket((RockyPacket) packet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendImmediatePacket(PacketVanilla packet) {
		if (getHandle().netServerHandler instanceof RockyPacketHandler)
			getNetServerHandler().sendImmediatePacket((RockyPacket) packet);
		else
			sendPacket(packet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reconnect(String message, String hostname, int port) {
		if (hostname.contains(":")) {
			throw new IllegalArgumentException(
					"Hostnames may not contain the : symbol");
		}
		if (message == null) {
			message = "[Redirect] Please reconnect to";
		} else if (message.contains(":")) {
			throw new IllegalArgumentException(
					"Kick messages may not contain the : symbol");
		}
		if (port == 25565)
			this.kickPlayer(message + " : " + hostname);
		else
			this.kickPlayer(message + " : " + hostname + ":" + port);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reconnect(String message, String hostname) {
		if (hostname.contains(":")) {
			String[] split = hostname.split(":");
			if (split.length != 2) {
				throw new IllegalArgumentException(
						"Improperly formatted hostname: " + hostname);
			}
			try {
				reconnect(message, split[0], Integer.parseInt(split[1]));
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(
						"Unable to parse port number: " + split[1] + " in "
								+ hostname);
			}
		} else
			reconnect(message, hostname, 25565);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reconnect(String hostname, int port) {
		reconnect(null, hostname, port);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reconnect(String hostname) {
		reconnect(null, hostname);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ScreenType getActiveScreen() {
		return activeScreen;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSkin(String url) {
		this.skin = url;
		sendPacketToObservers(new PacketPlayerAppearance(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSkin() {
		return skin;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSkin() {
		setSkin("http://s3.amazonaws.com/MinecraftSkins/" + getName() + ".png");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCape(String url) {
		this.cape = url;
		sendPacketToObservers(new PacketPlayerAppearance(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCape() {
		return cape;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetCape() {
		setCape("http://s3.amazonaws.com/MinecraftCloaks/" + getName() + ".png");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
		sendPacketToObservers(new PacketPlayerAppearance(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTitleFor(RockyPlayer viewingPlayer, String title) {
		if (hasObserver(viewingPlayer)) {
			viewingPlayer.sendPacket(new PacketPlayerAppearance(this));
		}
		titleFor.put(viewingPlayer.getName(), title);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitleFor(RockyPlayer viewingPlayer) {
		return titleFor.get(viewingPlayer.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void hideTitle() {
		setTitle("[Hide]");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void hideTitleFrom(RockyPlayer viewingPlayer) {
		setTitleFor(viewingPlayer, "[Hide]");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetTitle() {
		setTitle(getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetTitleFor(RockyPlayer viewingPlayer) {
		setTitleFor(viewingPlayer, getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void openScreen(ScreenType type) {
		openScreen(type, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void openScreen(ScreenType type, boolean packet) {
		if (type == activeScreen) {
			return;
		}
		activeScreen = type;
		if (packet) {
			sendPacket(new PacketScreenAction(ScreenAction.Open, type));
		}
		if (activeScreen != ScreenType.GAME_SCREEN
				&& activeScreen != ScreenType.CUSTOM_SCREEN) {
			currentScreen = (GenericOverlayScreen) new GenericOverlayScreen(
					getEntityId(), getActiveScreen()).setX(0).setY(0);
			PacketWidget packetW = new PacketWidget(currentScreen,
					currentScreen.getId());
			sendPacket(packetW);
			currentScreen.onTick();
		} else
			currentScreen = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendPacket(Packet packet) {
		if (!isModded()) {
			queuePackets.add(packet);
			return;
		}
		getNetServerHandler().sendPacket(new RockyPacket(packet));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasObserver(Player player) {
		return observers.contains(player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Player> getObservers() {
		return observers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObserver(Player player) {
		observers.add(player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObserver(Player player) {
		observers.remove(player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendPacketToObservers(Packet packet) {
		for (Player player : observers)
			if (player instanceof RockyPlayer)
				((RockyPlayer) player).sendPacket(packet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWaypoint(Waypoint waypoint) {
		sendPacket(new PacketWaypoint(waypoint.getX(), waypoint.getY(),
				waypoint.getZ(), waypoint.getName()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBuildVersion() {
		return build;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getVersionString() {
		return (build != -1 ? "SpoutLegacy v" + build : "Minecraft");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasAccessory(AccessoryType type) {
		return accessories.containsKey(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAccessory(AccessoryType type, String url) {
		sendPacketToObservers(new PacketAccessory(getName(), type, url));
		accessories.put(type, url);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String removeAccessory(AccessoryType type) {
		sendPacketToObservers(new PacketAccessory(getName(), type, ""));
		return accessories.remove(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAccessoryURL(AccessoryType type) {
		return accessories.get(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCloudHeight() {
		return skyCloudHeight;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCloudHeight(int y) {
		this.needSkyUpdate = true;
		this.skyCloudHeight = y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getStarFrequency() {
		return skyStarFrequency;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStarFrequency(int frequency) {
		this.needSkyUpdate = true;
		this.skyStarFrequency = frequency;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSunSizePercent() {
		return skySunPercent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSunSizePercent(int percent) {
		this.needSkyUpdate = true;
		this.skySunPercent = percent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSunTextureUrl() {
		return skySunUrl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSunTextureUrl(String url) {
		this.needSkyUpdate = true;
		this.skySunUrl = url;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMoonSizePercent() {
		return skyMoonPercent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMoonSizePercent(int percent) {
		this.needSkyUpdate = true;
		this.skyMoonPercent = percent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMoonTextureUrl() {
		return skyMoonUrl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMoonTextureUrl(String url) {
		this.skyMoonUrl = url;
		this.needSkyUpdate = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSkyColor(Color skyColor) {
		this.needSkyUpdate = true;
		this.skyColor = skyColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getSkyColor() {
		return skyColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFogColor(Color fogColor) {
		this.needSkyUpdate = true;
		this.skyFogColor = fogColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getFogColor() {
		return skyFogColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCloudColor(Color cloudColor) {
		this.needSkyUpdate = true;
		this.skyCloudColor = cloudColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getCloudColor() {
		return skyCloudColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void playSoundEffect(String effect) {
		playSoundEffect(effect, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void playSoundEffect(String effect, int distance) {
		playSoundEffect(effect, distance, 100);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void playSoundEffect(String effect, int distance, int volumePercent) {
		sendPacket(new PacketPlaySound(false, effect, volumePercent, distance));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void playMusic(String music) {
		playMusic(music, 100);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void playMusic(String music, int volumePercent) {
		sendPacket(new PacketPlaySound(music, volumePercent));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopMusic() {
		stopMusic(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopMusic(boolean resetTimer) {
		stopMusic(resetTimer, 5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopMusic(boolean resetTimer, int fadeOutTime) {
		sendPacket(new PacketStopMusic(resetTimer, fadeOutTime));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBuildVersion(int build) {
		this.build = build;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTick() {
		if (!isModded()) {
			return;
		}
		// Tick the current screen we're at
		mainScreen.onTick();
		Screen currentScreen = getCurrentScreen();
		if (currentScreen != null && currentScreen instanceof OverlayScreen) {
			currentScreen.onTick();
		}

		// Check if the movement addon needs update
		if (needMovementUpdate) {
			needMovementUpdate = false;
			sendPacket(new PacketMovementAddon(this));
		}

		// Check if the sky addon needs update
		if (needSkyUpdate) {
			needSkyUpdate = false;
			sendPacket(new PacketSkyAddon(this));
		}

		// Flush the entire queue packet
		getNetServerHandler().syncFlushPacketQueue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateWaypoints() {
		List<Waypoint> waypoints = Rocky.getInstance().getConfiguration()
				.getWaypoints(getWorld().getName().toLowerCase());
		for (Waypoint p : waypoints)
			addWaypoint(p);
	}

	/**
	 * 
	 * @return
	 */
	public RockyPacketHandler getNetServerHandler() {
		if (!(getHandle().netServerHandler instanceof RockyPacketHandler)) {
			updateNetworkEntry(this);
		}
		return (RockyPacketHandler) getHandle().netServerHandler;
	}

	/**
	 * 
	 * @param player
	 */
	public static void updateBukkitEntry(Player player) {
		EntityPlayer ep = ((CraftPlayer) player).getHandle();
		Reflection
				.field("bukkitEntity")
				.ofType(Entity.class)
				.in(ep)
				.set(new RockyPlayerHandler((CraftServer) Bukkit.getServer(),
						ep));
	}

	/**
	 * 
	 * @param player
	 */
	@SuppressWarnings("unchecked")
	public static void updateNetworkEntry(Player player) {
		CraftPlayer cp = (CraftPlayer) player;

		NetServerHandler oldHandler = cp.getHandle().netServerHandler;
		Location loc = player.getLocation();
		RockyPacketHandler handler = new RockyPacketHandler(
				MinecraftServer.getServer(),
				cp.getHandle().netServerHandler.networkManager, cp.getHandle());
		handler.a(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(),
				loc.getPitch());

		cp.getHandle().netServerHandler = handler;
		INetworkManager nm = handler.networkManager;
		Reflection.field("packetListener").ofType(NetHandler.class).in(nm)
				.set(handler);

		List<NetServerHandler> handleList = (List<NetServerHandler>) Reflection
				.field("d").ofType(List.class)
				.in(((DedicatedServer) MinecraftServer.getServer()).ac()).get();
		handleList.remove(oldHandler);
		handleList.add(handler);

		oldHandler.disconnected = true;
	}

	/**
	 * 
	 * @param player
	 */
	public static void sendAuthentication(Player player) {
		if (Rocky.getInstance().getConfiguration().authenticateSpout()) {
			return;
		}
		Packet250CustomPayload loginPacket = new Packet250CustomPayload();
		loginPacket.tag = "TM|Rocky";
		loginPacket.length = 4;
		loginPacket.data = new byte[] { 0x00, (byte) 0xF0, (byte) 0x0F, 0x0F };
		((RockyPlayerHandler) player).getNetServerHandler()
				.sendImmediatePacket(loginPacket);
	}

}
