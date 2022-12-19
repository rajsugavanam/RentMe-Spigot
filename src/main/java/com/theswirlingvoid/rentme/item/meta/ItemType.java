package com.theswirlingvoid.rentme.item.meta;

import org.bukkit.ChatColor;

public enum ItemType {

	TOOL(ChatColor.GRAY+""+ChatColor.BOLD);

	private final String displayColor;

	ItemType(String color) {
		this.displayColor = color;
	}

	public String getColorPrefix() {
		return displayColor;
	}
}
