package com.theswirlingvoid.rentme.item.generic;

import com.theswirlingvoid.rentme.Rentme;
import com.theswirlingvoid.rentme.item.meta.ItemType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class PluginItem {

	protected String id;
	protected String name;
	protected ItemType type;
	protected static final NamespacedKey ID_KEY = new NamespacedKey(Rentme.plugin, "id");

	public PluginItem(String id, String name, ItemType type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public String getBaseDisplayName() {
		return type.getColorPrefix()+name;
	}
	public abstract ItemStack constructItem();
}
