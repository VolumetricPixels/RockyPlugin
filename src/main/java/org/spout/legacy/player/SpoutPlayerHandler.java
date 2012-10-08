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
package org.spout.legacy.player;

import java.util.List;
import net.minecraft.server.DedicatedServer;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.INetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetHandler;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet250CustomPayload;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.fest.reflect.core.Reflection;
import org.spout.legacy.Spout;
import org.spout.legacy.packet.SpoutPacketHandler;
import org.spout.legacyapi.gui.InGameHUD;
import org.spout.legacyapi.gui.Screen;
import org.spout.legacyapi.gui.ScreenType;
import org.spout.legacyapi.math.Color;
import org.spout.legacyapi.packet.Packet;
import org.spout.legacyapi.packet.PacketVanilla;
import org.spout.legacyapi.player.AccessoryType;
import org.spout.legacyapi.player.RenderDistance;
import org.spout.legacyapi.player.SpoutPlayer;
import org.spout.legacyapi.world.Waypoint;

/**
 * 
 */
public class SpoutPlayerHandler extends CraftPlayer implements SpoutPlayer {

	/**
	 * 
	 * @param server
	 * @param entity
	 */
	public SpoutPlayerHandler(CraftServer server, EntityPlayer entity) {
		super(server, entity);
	}

	@Override
	public InGameHUD getMainScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Screen getCurrentScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSpoutEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RenderDistance getRenderDistance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRenderDistance(RenderDistance distance) {
		// TODO Auto-generated method stub

	}

	@Override
	public RenderDistance getMaximumRenderDistance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMaximumRenderDistance(RenderDistance maximum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetMaximumRenderDistance() {
		// TODO Auto-generated method stub

	}

	@Override
	public RenderDistance getMinimumRenderDistance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetMinimumRenderDistance() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMinimumRenderDistance(RenderDistance minimum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendNotification(String title, String message,
			org.spout.legacyapi.material.Material toRender) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendNotification(String title, String message,
			org.spout.legacyapi.material.Material toRender, short data,
			int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendNotification(String title, String message, ItemStack item,
			int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getGravityMultiplier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setGravityMultiplier(double multiplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getSwimmingMultiplier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSwimmingMultiplier(double multiplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getWalkingMultiplier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWalkingMultiplier(double multiplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getJumpingMultiplier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setJumpingMultiplier(double multiplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getAirSpeedMultiplier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAirSpeedMultiplier(double multiplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetMovement() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canFly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCanFly(boolean fly) {
		// TODO Auto-generated method stub

	}

	@Override
	public Location getLastClickedLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendPacket(PacketVanilla packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPreCachingComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendImmediatePacket(PacketVanilla packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reconnect(String message, String hostname, int port) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reconnect(String message, String hostname) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reconnect(String hostname, int port) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reconnect(String hostname) {
		// TODO Auto-generated method stub

	}

	@Override
	public ScreenType getActiveScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openScreen(ScreenType type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendScreenshotRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSkin(String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSkinFor(SpoutPlayer viewingPlayer, String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSkin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSkin(SpoutPlayer viewingPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetSkin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetSkinFor(SpoutPlayer viewingPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCape(String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCapeFor(SpoutPlayer viewingPlayer, String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCape(SpoutPlayer viewingPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetCape() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetCapeFor(SpoutPlayer viewingPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTitleFor(SpoutPlayer viewingPlayer, String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitleFor(SpoutPlayer viewingPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hideTitle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideTitleFrom(SpoutPlayer viewingPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetTitle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetTitleFor(SpoutPlayer viewingPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetEntitySkin(LivingEntity target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkUrl(String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openScreen(ScreenType type, boolean packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPreCachingComplete(boolean complete) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRenderDistance(RenderDistance currentRender, boolean update) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendPacket(Packet packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendDelayedPacket(Packet packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateKeys(byte[] keys) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePermission(String node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePermissions(String... nodes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePermissions() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean spawnTextEntity(String text, Location location, float scale,
			int duration, Vector movement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addWaypoint(Waypoint waypoint) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBuildVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getVersionString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAccessory(AccessoryType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addAccessory(AccessoryType type, String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public String removeAccessory(AccessoryType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccessoryURL(AccessoryType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCloudHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCloudHeight(int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCloudsVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCloudsVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getStarFrequency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStarFrequency(int frequency) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isStarsVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setStarsVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getSunSizePercent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSunSizePercent(int percent) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSunVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSunVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSunTextureUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSunTextureUrl(String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMoonSizePercent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMoonSizePercent(int percent) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isMoonVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMoonVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMoonTextureUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMoonTextureUrl(String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSkyColor(Color skyColor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getSkyColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFogColor(Color fogColor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getFogColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCloudColor(Color cloudColor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getCloudColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void playSoundEffect(String effect) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSoundEffect(String effect, Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSoundEffect(String effect, Location location, int distance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSoundEffect(String effect, Location location, int distance,
			int volumePercent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playMusic(String music) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playMusic(String music, int volumePercent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopMusic() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopMusic(boolean resetTimer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopMusic(boolean resetTimer, int fadeOutTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBuildVersion(int build) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTick() {
		getNetServerHandler().syncFlushPacketQueue();
	}

	@Override
	public void updateWaypoints() {
	}

	/**
	 * 
	 * @return
	 */
	public SpoutPacketHandler getNetServerHandler() {
		if (!(getHandle().netServerHandler instanceof SpoutPacketHandler)) {
			updateNetworkEntry(this);
		}
		return (SpoutPacketHandler) getHandle().netServerHandler;
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
				.set(new SpoutPlayerHandler((CraftServer) Bukkit.getServer(),
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
		SpoutPacketHandler handler = new SpoutPacketHandler(
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
		if (Spout.getInstance().getConfiguration().authenticateSpout()) {
			return;
		}
		Packet250CustomPayload loginPacket = new Packet250CustomPayload();
		loginPacket.tag = "TM|Spout";
		loginPacket.length = 4;
		loginPacket.data = new byte[] { 0x00, (byte) 0xF0, (byte) 0xFF, 0x0F };
		((SpoutPlayerHandler) player).getNetServerHandler()
				.sendImmediatePacket(loginPacket);
	}
	
}
