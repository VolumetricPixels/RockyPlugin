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
package com.volumetricpixels.rockyplugin.reflection;

/**
 * 
 */
public class ReflectorID {

	public final static String PLAYER_CHUNK_MAP = "PlayerChunkMap";
	public final static String PLAYER_CHUNK_MAP_WORLD = "PlayerChunkMap#World";
	public final static String PLAYER_CHUNK_MAP_DISTANCE = "PlayerChunkMap#Distance";
	public final static String PLAYER_CHUNK_MAP_PLAYERS = "PlayerChunkMap#ManagedPlayers";
	public final static String PLAYER_CHUNK_MAP_LONG_HASH_MAP = "PlayerChunkMap#LongHashMap";
	public final static String PLAYER_CHUNK_MAP_QUEUE = "PlayerChunkMap#Queue";

		
	public final static String PLAYER_CONNECTION  = "PlayerConnection";
	public final static String PLAYER_CONNECTION_LIST = "PlayerConnection#List";
	
	public final static String PLAYER_LOCALE = "Player#Locale";
	
	public final static String PLAYER_PACKET_HANDLER_Y  = "PlayerPacketHandler#Y";
	
	public final static String PACKET_LOCALE_VIEW_B  = "PacketLocaleView#B";
	public final static String PACKET_BULK_CHUNK_PACKET_BUFFER = "PacketBulkChunk#BuildBuffer";
	public final static String PACKET_CHUNK_PACKET_BUFFER = "PacketChunk#Buffer";
	public final static String PACKET_CHUNK_PACKET_SIZE = "PacketChunk#Size";
	public final static String PACKET_CHUNK_PACKET_INFLATED = "PacketChunk#Inflated";
	public final static String PACKET_ENTITY_EQUIPMENT_ITEM = "PacketEntityEquipment#Item";
}
