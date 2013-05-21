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

import java.lang.management.ThreadInfo;

import org.bukkit.Bukkit;

import com.volumetricpixels.rockyapi.RockyManager;
import com.volumetricpixels.rockyplugin.util.ThreadWarningSystem.Listener;

/**
 * 
 */
public class RockyDeadlockListener implements Listener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deadlockDetected(ThreadInfo deadlockedThread) {
		boolean isRestart = Rocky.getInstance().getConfiguration()
				.isRestartOnCrash();
		RockyManager
				.printConsole(
						"Dumping thread information about deadlock\n--------------------\n%s\n--------------------",
						deadlockedThread.toString());
		if (isRestart == true) {
			Bukkit.reload();
		} else {
			checkForFix(deadlockedThread);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void thresholdExceeded(ThreadInfo[] allThreads) {
		for (ThreadInfo deadlockThread : allThreads) {
			deadlockDetected(deadlockThread);
		}
	}

	/**
	 * 
	 * @param deadlockThread
	 */
	private void checkForFix(ThreadInfo deadlockThread) {

	}
}
