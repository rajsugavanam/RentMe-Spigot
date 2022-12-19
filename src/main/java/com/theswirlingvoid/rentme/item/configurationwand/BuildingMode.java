package com.theswirlingvoid.rentme.item.configurationwand;

import org.bukkit.ChatColor;

public enum BuildingMode {

		AREA(ChatColor.GOLD, "AREA",
			"Modify included blocks and regions."
		),
		ACCESS(ChatColor.RED, "ACCESS",
				"Change permissions relating to",
						"what renters are able to",
						"to use, open, break, or place."
		),
		SIGN(ChatColor.AQUA, "SIGN",
				"Edit the building or room listing(s)",
						"and hover text shown to everyone."
		),

		SELECT(ChatColor.LIGHT_PURPLE, "SELECT",
				"Change the building you are configuring."
		),

		SETTINGS(ChatColor.GRAY, "SETTINGS",
				"Edit the general settings for the building."
		);


		private final String modeText;
		private final String[] modeDescription;
		private final ChatColor color;

		BuildingMode(ChatColor color, String modeText, String... modeDescription) {
			this.color = color;
			this.modeText = modeText;
			this.modeDescription = modeDescription;
		}

		public String getText() {
			return color+modeText;
		}

		public String getPlainText() {
			return modeText;
		}

		public String[] getDescription() {
			return modeDescription;
		}

		public String getTextWithAdditions(String prefix, String suffix) {
			return color+prefix+modeText+suffix;
		}
}
