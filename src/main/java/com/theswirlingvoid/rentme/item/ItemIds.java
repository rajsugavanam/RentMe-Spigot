package com.theswirlingvoid.rentme.item;

import com.theswirlingvoid.rentme.item.configurationwand.BuildingMode;
import com.theswirlingvoid.rentme.item.configurationwand.BuildingWand;
import com.theswirlingvoid.rentme.item.generic.PluginItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemIds {
	private static final Map<String, PluginItem> itemIds = new HashMap<>();

	public ItemIds() {
		initializeIfEmpty();
	}

	@Nullable
	public PluginItem getById(String id) {
		return itemIds.getOrDefault(id, null);
	}

	private void initializeIfEmpty() {
		if (itemIds.isEmpty()) {
			itemIds.put("building_wand", new BuildingWand(BuildingMode.AREA));
		}
	}

	public Set<String> getAllIds() {
		return itemIds.keySet();
	}
}
