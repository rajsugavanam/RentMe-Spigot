package com.theswirlingvoid.rentme.item.configurationwand;

import com.theswirlingvoid.rentme.item.meta.ItemDisplay;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class WandHud {

//	private static HashMap<Player, BukkitRunnable> activeDisplays = new HashMap<>();
	protected Player player;
	protected ItemStack stack;

	public WandHud(Player player, ItemStack stack) {
		this.player = player;
		this.stack = stack;
	}

	public void setItemStack(ItemStack stack) {
		this.stack = stack;
	}

//	public void startDisplayHud() {
//
//		BukkitRunnable displayRun = new BukkitRunnable() {
//			@Override
//			public void run() {
//				displayHudFrame();
//			}
//		};
//		displayRun.runTaskTimer(Rentme.plugin, 0, 1);
//		activeDisplays.put(player, displayRun);
//	}
//	public static void stopDisplayHud(Player player) {
//		if (activeDisplays.containsKey(player)) {
//			activeDisplays.get(player).cancel();
//			activeDisplays.remove(player);
//		}
//	}

	public abstract void displayHudFrame();

	public static class Building extends WandHud {
		private final BuildingWand item;

		public Building(Player player, ItemStack stack, BuildingWand item) {
			super(player, stack);
			this.item = item;
		}

		private Building updateMode() {
			BuildingMode mode = item.getMode(stack);
			stack.setItemMeta(new BuildingWand(mode).constructItem().getItemMeta());
			return this;
		}

		@Override
		public void displayHudFrame() {
			updateMode();
			displayActionHud();
		}

		private String getModalControls() {
			String modalControlsHud = "";
			return modalControlsHud;
		}

		private String getModeText() {
//			String cycleLeft =
//					ChatColor.DARK_GRAY+"["+
//					ChatColor.GREEN+"◀ ⬇+RMB"+
//					ChatColor.DARK_GRAY+"]";
//			String cycleRight =
//					ChatColor.DARK_GRAY+"["+
//					ChatColor.GREEN+"⬇+LMB ▶"+
//					ChatColor.DARK_GRAY+"]";

			String modeText = ChatColor.DARK_GRAY+"["+item.getMode(stack).getText()+ChatColor.DARK_GRAY+"]";

//			return cycleLeft+" "+modeText+" "+cycleRight;
			return modeText;
		}

		private String getActionText() {
			return getModeText()+" "+getModalControls();
		}

		private void displayActionHud() {
			player.spigot().sendMessage(
				ChatMessageType.ACTION_BAR,
				TextComponent.fromLegacyText(getActionText())
			);
		}
	}
}
