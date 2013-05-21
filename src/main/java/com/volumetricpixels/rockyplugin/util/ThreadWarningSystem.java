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
package com.volumetricpixels.rockyplugin.util;

import java.lang.management.*;
import java.util.*;

public class ThreadWarningSystem {

	private final Timer threadCheck = new Timer("Thread Monitor", true);
	private final ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
	private final Collection<Listener> listeners = new ArrayList<Listener>();
	/**
	 * The number of milliseconds between checking for deadlocks. It may be
	 * expensive to check for deadlocks, and it is not critical to know so
	 * quickly.
	 */
	private static final int DEADLOCK_CHECK_PERIOD = 500;
	/**
	 * The number of milliseconds between checking number of threads. Since
	 * threads can be created very quickly, we need to check this frequently.
	 */
	private static final int THREAD_NUMBER_CHECK_PERIOD = 20;
	private static final int MAX_STACK_DEPTH = 30;
	private boolean threadThresholdNotified = false;
	private Set<Long> deadlockedThreads = new HashSet<Long>();

	/**
	 * Monitor only deadlocks.
	 * 
	 * @param ThreadWarningSystem
	 */
	public ThreadWarningSystem() {
		threadCheck.schedule(new TimerTask() {
			public void run() {
				long[] ids = mbean.findMonitorDeadlockedThreads();
				if (ids != null && ids.length > 0) {
					for (Long l : ids) {
						if (!deadlockedThreads.contains(l)) {
							deadlockedThreads.add(l);
							ThreadInfo ti = mbean.getThreadInfo(l,
									MAX_STACK_DEPTH);
							fireDeadlockDetected(ti);
						}
					}
				}
			}
		}, 10, DEADLOCK_CHECK_PERIOD);
	}

	/**
	 * Monitor deadlocks and the number of threads.
	 * 
	 * @param threadThreshold
	 */
	public ThreadWarningSystem(final int threadThreshold) {
		this();
		threadCheck.schedule(new TimerTask() {
			public void run() {
				if (mbean.getThreadCount() > threadThreshold) {
					if (!threadThresholdNotified) {
						fireThresholdExceeded();
						threadThresholdNotified = true;
					}
				} else {
					threadThresholdNotified = false;
				}
			}
		}, 10, THREAD_NUMBER_CHECK_PERIOD);
	}

	/**
	 * 
	 * @param thread
	 */
	private void fireDeadlockDetected(ThreadInfo thread) {
		synchronized (listeners) {
			for (Listener l : listeners) {
				l.deadlockDetected(thread);
			}
		}
	}

	/**
	 * 
	 */
	private void fireThresholdExceeded() {
		ThreadInfo[] allThreads = mbean.getThreadInfo(mbean.getAllThreadIds());
		synchronized (listeners) {
			for (Listener l : listeners) {
				l.thresholdExceeded(allThreads);
			}
		}
	}

	/**
	 * 
	 * @param l
	 * @return
	 */
	public boolean addListener(Listener l) {
		synchronized (listeners) {
			return listeners.add(l);
		}
	}

	/**
	 * 
	 * @param l
	 * @return
	 */
	public boolean removeListener(Listener l) {
		synchronized (listeners) {
			return listeners.remove(l);
		}
	}

	/**
	 * This is called whenever a problem with threads is detected. The two
	 * events are deadlockDetected() and thresholdExceeded().
	 */
	public interface Listener {
		/**
		 * @param deadlockedThread
		 *            The deadlocked thread, with stack trace of limited depth.
		 */
		void deadlockDetected(ThreadInfo deadlockedThread);

		/**
		 * @param allThreads
		 *            All the threads in the JVM, without stack traces.
		 */
		void thresholdExceeded(ThreadInfo[] allThreads);
	}
}
