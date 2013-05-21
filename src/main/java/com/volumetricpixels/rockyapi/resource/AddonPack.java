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
package com.volumetricpixels.rockyapi.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 
 */
public class AddonPack {

	private ZipFile file;

	/**
	 * 
	 * @param file
	 */
	public AddonPack(ZipFile file) {
		this.file = file;
	}

	/**
	 * 
	 */
	public void close() {
		try {
			file.close();
		} catch (IOException e) {
		}
	}

	/**
	 * 
	 * @param entry
	 * @return
	 */
	public boolean hasEntry(String entry) {
		return file.getEntry(entry) != null;
	}

	/**
	 * 
	 * @param entry
	 * @return
	 */
	public ZipEntry getEntry(String entry) {
		return file.getEntry(entry);
	}

	/**
	 * 
	 * @param entry
	 * @return
	 */
	public InputStream getInputStream(String entry) {
		try {
			return file.getInputStream(getEntry(entry));
		} catch (IOException e) {
		}
		return null;
	}

}