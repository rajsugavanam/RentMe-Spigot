package com.theswirlingvoid.rentme.item.configurationwand;

import com.theswirlingvoid.rentme.Rentme;
import com.theswirlingvoid.rentme.item.meta.ItemDisplay;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BuildingWand extends GenericWand implements Listener {
	private static final NamespacedKey MODE_KEY = new NamespacedKey(Rentme.plugin, "mode");
	private BuildingMode mode;
	private WandHud.Building attachedHud;

	public BuildingWand(BuildingMode mode) {
		super("building_wand", "Building Wand");
		this.mode = mode;
	}

	public ItemStack constructItem() {
		ItemStack buildingWand = new ItemDisplay(new ItemStack(Material.GOLDEN_PICKAXE))
			.setDisplayName(
				getBaseDisplayName()+
				ChatColor.DARK_GRAY+" ["+
				mode.getText()+
				ChatColor.DARK_GRAY+"]"
			)
			.addSectionText(
				mode.getDescription()
			)
			.addLoreSection("About")
			.addSectionText(
				"A tool with multiple modes to manage " +
				ItemDisplay.EMPH_COLOR+"building "+ItemDisplay.DESCRIPTION_COLOR +
				"rentals."
			)
			.addLoreSection("Modes")
			.addBullet(BuildingMode.AREA.getText())
			.addBullet(BuildingMode.ACCESS.getText())
			.addBullet(BuildingMode.SIGN.getText())
			.addBullet(BuildingMode.SELECT.getText())
			.addBullet(BuildingMode.SETTINGS.getText())
			.addLoreSection("Controls")
			.addSectionText(
				"Use your bindings for "+
				ItemDisplay.EMPH_COLOR+"Use/Attack "+ItemDisplay.DESCRIPTION_COLOR+
				"while sneaking",
				"to cycle the tool mode "+
				ItemDisplay.EMPH_COLOR+"Up/Down "+ItemDisplay.DESCRIPTION_COLOR+
				"respectively."
			)
			.hideAttributes()
			.getResultingItem();

		buildingWand = withItemData(buildingWand);

		return buildingWand;
	}

	public BuildingMode getMode(ItemStack stack) {
		PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();
		return BuildingMode.valueOf(container.getOrDefault(MODE_KEY, PersistentDataType.STRING, mode.getPlainText()));
	}

	public void setMode(ItemStack stack, BuildingMode mode) {
		this.mode = mode;
		ItemMeta meta = stack.getItemMeta();
		meta.getPersistentDataContainer()
			.set(MODE_KEY, PersistentDataType.STRING, mode.getPlainText());
		stack.setItemMeta(meta);
	}

	protected void onHold(Player player, ItemStack stack) {
		attachedHud = new WandHud.Building(player, stack, this);

		PlayerThreads threads = new PlayerThreads();
		threads.startThreadForPlayer(player, id, () -> {
			attachedHud.displayHudFrame();
		});
	}

	protected void onRelease(Player player) {
		PlayerThreads.stopAllThreadsForPlayer(player, id);
	}

	@Override
	protected void onInteracted(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack stack = event.getItem();
		if (player.isSneaking()) {
			int modeNum = getMode(stack).ordinal();
			if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				modeNum = Math.floorMod(modeNum+1,  BuildingMode.values().length); // mod mode num to prevent out of bounds and cycle it around
				setMode(stack, BuildingMode.values()[modeNum]);
			}
			else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				modeNum = Math.floorMod(modeNum-1,  BuildingMode.values().length); // mod mode num to prevent negatives and cycle it around
				setMode(stack, BuildingMode.values()[modeNum]);
			}
			player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
		}
		attachedHud.setItemStack(stack);
	}

	@Override
	protected void onDrop(PlayerDropItemEvent event) {

	}

	@EventHandler
	protected void interactEvent(PlayerInteractEvent event) {
		handleInteraction(event);
	}

	@EventHandler
	protected void switchEvent(PlayerItemHeldEvent event) {
		handleItemSwitch(event);
	}

	@EventHandler
	protected void invClickEvent(InventoryClickEvent event) {
		handleInvClick(event);
	}

	@EventHandler
	protected void dropEvent(PlayerDropItemEvent event) {
		handleDropEvent(event);
	}

	private ItemStack withItemData(ItemStack stack) {

		ItemMeta data = stack.getItemMeta();

		if (data != null) {

			data.getPersistentDataContainer().set(
					ID_KEY,
					PersistentDataType.STRING,
					id
			);

			data.getPersistentDataContainer().set(
					MODE_KEY,
					PersistentDataType.STRING,
					mode.getPlainText()
			);

			stack.setItemMeta(data);
		}

		return stack;
	}
}
