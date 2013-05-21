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

import org.bukkit.configuration.file.FileConfiguration;

/**
 * 
 */
public class RockyConfig {
	private boolean forceClient;
	private boolean authenticate;
	private int authTicks;
	private String kickMessage;
	private boolean cacheEnabled;
	private boolean restartOnCrash;
	private boolean debugEnabled;
	
	/**
	 * 
	 * @param file
	 */
	public RockyConfig() {
		Rocky.getInstance().reloadConfig();
		FileConfiguration configuration = Rocky.getInstance().getConfig();
		configuration.options().copyDefaults(true);

		forceClient = configuration.getBoolean("ForceClient", false);
		kickMessage = configuration.getString("ForceClientKickMessage",
				"This server requires Rocky");
		authTicks = configuration.getInt("AuthenticateTicks", 200);
		authenticate = configuration.getBoolean("Authenticate", true);
		cacheEnabled = configuration.getBoolean("CacheFeature", true);
		restartOnCrash = configuration.getBoolean("RestartOnCrash", true);
		debugEnabled = configuration.getBoolean("Debug", false);
		
		Rocky.getInstance().saveConfig();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRestartOnCrash() {
		return restartOnCrash;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isForceClient() {
		return forceClient;
	}

	/**
	 * 
	 * @return
	 */
	public String getKickMessage() {
		return kickMessage;
	}

	/**
	 * 
	 * @return
	 */
	public int getAuthenticateTicks() {
		return authTicks;
	}

	/**
	 * 
	 * @return
	 */
	public boolean authenticateRocky() {
		return authenticate;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public boolean isCacheEnabled() {
		return cacheEnabled;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isDebugEnabled() {
		return debugEnabled;
	}
}
