package com.theswirlingvoid.rentme.command.commands;

import com.theswirlingvoid.rentme.command.PlayerCommand;
import com.theswirlingvoid.rentme.item.ItemIds;
import com.theswirlingvoid.rentme.item.generic.PluginItem;
import org.bukkit.inventory.ItemStack;

public class GiveItemCommand extends PlayerCommand {

	private static final String SHOW_ITEMS_ARGUMENT = "items";
	protected void handlePlayerCommand() {

		if (args.length == 1) {

			if (args[0].equals(SHOW_ITEMS_ARGUMENT)) {
				sendPlayerIdList();
			}
			else {
				String itemId = args[0];
				PluginItem itemType = new ItemIds().getById(itemId);
				if (itemType == null) {
					msgSender.new Error().badArgument(itemId);
				} else {
					ItemStack finalItem = itemType.constructItem();
					giveItemToPlayer(finalItem);
				}
			}

		} else {
			msgSender.new Info().properCommandUsage(1, command.getName(), "item_id");
			msgSender.new Info().properCommandUsage(2, command.getName()+" "+SHOW_ITEMS_ARGUMENT);
		}

	}

	private void giveItemToPlayer(ItemStack item) {
		player.getWorld().dropItem(player.getLocation(), item);
	}
	private void sendPlayerIdList() {
		player.sendMessage("Item IDs:");
		for (String id : new ItemIds().getAllIds()) {
			player.sendMessage("• " + id);
		}
	}
}
