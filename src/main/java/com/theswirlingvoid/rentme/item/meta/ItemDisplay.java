package com.theswirlingvoid.rentme.item.meta;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemDisplay {

	public static final ChatColor DESCRIPTION_COLOR = ChatColor.GRAY;
	public static final ChatColor EMPH_COLOR = ChatColor.GOLD;
	ItemStack customItem;
	ItemMeta itemMeta;

	public ItemDisplay(ItemStack item) {
		this.customItem = item;
		this.itemMeta = customItem.getItemMeta();
	}

	public ItemDisplay setDisplayName(String name) {
		itemMeta.setDisplayName(name);
		return this;
	}

	public ItemDisplay hideAttributes() {
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		return this;
	}

	public ItemDisplay addLoreSection(String sectionName) {

		addLore(""); // make sure there's a blank line before
		addLore(
			ChatColor.DARK_GRAY+"["+ChatColor.WHITE+sectionName+ChatColor.DARK_GRAY+"]"
		);
		return this;
	}

	public ItemDisplay addSectionText(String... sectionText) {

		for (String line : sectionText) {
			addLore(
				DESCRIPTION_COLOR+line
			);
		}
		return this;
	}

	public ItemDisplay addBullet(String bulletText) {

		addLore(
			DESCRIPTION_COLOR+"• "+bulletText
		);
		return this;
	}

	private void addLore(String loreString) {
		List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
		lore.add(
			loreString
		);
		itemMeta.setLore(lore);
	}

	public ItemStack getResultingItem() {
		customItem.setItemMeta(itemMeta);
		return customItem;
	}

}
