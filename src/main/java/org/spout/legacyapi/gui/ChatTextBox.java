/*
 * This file is part of SpoutLegacy.
 *
 * Copyright (c) 2012-2012, VolumetricPixels <http://www.volumetricpixels.com/>
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
import java.util.UUID;

import org.spout.legacyapi.packet.PacketInputStream;
import org.spout.legacyapi.packet.PacketOutputStream;

/**
 * The Spout implementation of the default Chat Text Box.
 * <p/>
 * This provides extra abilities above the default version.
 */
public class ChatTextBox extends GenericWidget {
	protected int visibleLines = 10;
	protected int visibleChatLines = 20;
	protected int fadeoutTicks = 250;

	/**
	 * 
	 */
	public ChatTextBox() {
		setWidth(getWidth());
		setDirty(false);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public WidgetType getType() {
		return WidgetType.ChatTextBox;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void readData(PacketInputStream input) throws IOException {
		super.readData(input);
		setNumVisibleLines(input.readInt());
		setNumVisibleChatLines(input.readInt());
		setFadeoutTicks(input.readInt());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void writeData(PacketOutputStream output) throws IOException {
		super.writeData(output);
		output.writeInt(getNumVisibleLines());
		output.writeInt(getNumVisibleChatLines());
		output.writeInt(getFadeoutTicks());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public UUID getId() {
		return new UUID(0, 3);
	}

	/**
	 * 
	 */
	public void render() {
	}

	/**
	 * Gets the number of visible lines of chat for the player
	 * 
	 * @return visible chat lines
	 */
	public int getNumVisibleLines() {
		return visibleLines;
	}

	/**
	 * Sets the number of visible lines of chat for the player
	 * 
	 * @param lines
	 *            to view
	 * @return ChatTextBox
	 */
	public ChatTextBox setNumVisibleLines(int lines) {
		visibleLines = lines;
		return this;
	}

	/**
	 * Gets the number of visible lines of chat for the player, when fully
	 * opened
	 * 
	 * @return visible chat lines
	 */
	public int getNumVisibleChatLines() {
		return visibleChatLines;
	}

	/**
	 * Sets the number of visible lines of chat for the player, when fully
	 * opened
	 * 
	 * @param lines
	 *            to view
	 * @return ChatTextBox
	 */
	public ChatTextBox setNumVisibleChatLines(int lines) {
		visibleChatLines = lines;
		return this;
	}

	/**
	 * The number ticks until the text fades out from the main screen
	 * 
	 * @return fadeout ticks
	 */
	public int getFadeoutTicks() {
		return fadeoutTicks;
	}

	/**
	 * Sets the number of ticks until the text fades out from the main screen.
	 * 20 ticks is equivelent to one second.
	 * 
	 * @param ticks
	 *            to set
	 * @return this
	 */
	public ChatTextBox setFadeoutTicks(int ticks) {
		fadeoutTicks = ticks;
		return this;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public int getVersion() {
		return super.getVersion() + 1;
	}
}
