package com.theswirlingvoid.rentme.item.configurationwand;

import com.theswirlingvoid.rentme.item.generic.PluginItem;
import com.theswirlingvoid.rentme.item.meta.ItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public abstract class GenericWand extends PluginItem {

	public GenericWand(String id, String name) {
		super(id, name, ItemType.TOOL);
	}

	protected abstract void onHold(Player player, ItemStack stack);
	protected abstract void onRelease(Player player);
	protected abstract void onInteracted(PlayerInteractEvent event);
	protected abstract void onDrop(PlayerDropItemEvent event);

	@EventHandler
	protected abstract void interactEvent(PlayerInteractEvent event);

	@EventHandler
	protected abstract void switchEvent(PlayerItemHeldEvent event);

	@EventHandler
	protected abstract void invClickEvent(InventoryClickEvent event);

	@EventHandler
	protected abstract void dropEvent(PlayerDropItemEvent event);

	public String getBaseDisplayName() {
		return type.getColorPrefix()+name;
	}

	protected boolean itemStackIsWand(ItemStack heldItem) {

		if (heldItem != null) {

			try {
				PersistentDataContainer container = heldItem.getItemMeta().getPersistentDataContainer();
				if (container.has(ID_KEY, PersistentDataType.STRING)) {
					String id = container.get(ID_KEY, PersistentDataType.STRING);
					if (id.equals(id)) {
						return true;
					}
				}
			} catch (Exception ignored) {}
		}
		return false;
	}

	protected void handleItemSwitch(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		ItemStack newItem = player.getInventory().getItem(event.getNewSlot());

		if (itemStackIsWand(newItem)) {
			onHold(player, newItem);
		} else {
			onRelease(player);
		}
	}

	protected void handleInvClick(InventoryClickEvent event) {
		if (event.getAction() != InventoryAction.DROP_ALL_SLOT ||
				event.getAction() != InventoryAction.DROP_ONE_SLOT) {
			Player player = (Player) event.getWhoClicked();
			ItemStack previouslyHeldItem = player.getInventory().getItemInMainHand();
			ItemStack clickedItem = event.getCurrentItem();
			ItemStack cursorItem = event.getCursor();
			int clickedSlot = event.getSlot();

			boolean wasHoldingWand = itemStackIsWand(previouslyHeldItem);
			boolean clickedMainHand = clickedSlot == player.getInventory().getHeldItemSlot();
			boolean clickedWithWand = itemStackIsWand(cursorItem);

			// if the player swapped wands
			if (clickedMainHand && clickedWithWand) {
				onHold(player, cursorItem);
			}
			else if (wasHoldingWand && clickedMainHand) {
				onRelease(player);
			}
		}
	}

	protected void handleDropEvent(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		ItemStack droppedItem = event.getItemDrop().getItemStack();

		if (itemStackIsWand(droppedItem)) {
			event.setCancelled(true);
			onDrop(event);
		}
	}

	protected void handleInteraction(PlayerInteractEvent event) {
		ItemStack stack = event.getItem();
		if (itemStackIsWand(stack)) {
			event.setCancelled(true); // to prevent block breaking
			onInteracted(event);
		}
	}

}
