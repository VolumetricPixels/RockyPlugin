/*
 * This file is part of SpoutPlugin.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutPlugin is licensed under the GNU Lesser General Public License.
 *
 * SpoutPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spout.legacyapi.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;

public class GenericRadioButton extends GenericButton implements RadioButton {
	boolean selected = false;
	int group = 0;

	public GenericRadioButton() {
	}

	public GenericRadioButton(String text) {
		super(text);
	}

	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		selected = input.readBoolean();
		group = input.readInt();
	}

	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeBoolean(isSelected());
		output.writeInt(getGroup());
	}

	@Override
	public WidgetType getType() {
		return WidgetType.RadioButton;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public RadioButton setSelected(boolean selected) {
		if (selected) {
			for (RadioButton b : getRadiosInGroup()) {
				b.setSelected(false);
			}
		}
		this.selected = selected;
		return this;
	}

	@Override
	public int getGroup() {
		return group;
	}

	@Override
	public RadioButton setGroup(int group) {
		this.group = group;
		return this;
	}

	public List<RadioButton> getRadiosInGroup() {
		List<RadioButton> ret = new ArrayList<RadioButton>();
		for (Widget w : getScreen().getAttachedWidgets()) {
			if (w instanceof RadioButton) {
				if (((RadioButton) w).getGroup() == group) {
					ret.add((RadioButton) w);
				}
			}
		}
		return ret;
	}
}
