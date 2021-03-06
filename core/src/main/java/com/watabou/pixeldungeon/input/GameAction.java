/*
 * Copyright (c) 2012-2015 Oleg Dolya
 *
 * Copyright (c) 2014 Edu Garcia
 *
 * Copyright (c) 2021 Yi An
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.pixeldungeon.input;

public enum GameAction {
	BACK( null ),

	HERO_INFO("Hero Info"),
	CATALOGUS("Catalogus"),
	JOURNAL("Journal"),

	REST("Wait"),
	SEARCH("Search"),
	CELL_INFO("Examine cell"),
	RESUME("Resume motion"),

	BACKPACK("Backpack"),
	QUICKSLOT("Quickslot"),

	TAG_ATTACK("Attack"),
	TAG_DANGER("Visible Enemies"),

	ZOOM_IN("Zoom In"),
	ZOOM_OUT("Zoom Out"),
	ZOOM_DEFAULT("Default Zoom"),

	MOVE_UP("Move North"), MOVE_DOWN("Move South"), MOVE_LEFT("Move West"), MOVE_RIGHT("Move East"),
	MOVE_TOP_LEFT("Move NW"), MOVE_TOP_RIGHT("Move NE"), MOVE_BOTTOM_LEFT("Move SW"), MOVE_BOTTOM_RIGHT("Move SE"),
	OPERATE("Current Cell"),

	UNKNOWN(null);

	private final String description;

	GameAction(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
