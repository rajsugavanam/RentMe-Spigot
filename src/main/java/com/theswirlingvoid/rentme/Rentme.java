package com.theswirlingvoid.rentme;

import com.theswirlingvoid.rentme.command.CommandRegister;
import com.theswirlingvoid.rentme.item.configurationwand.BuildingMode;
import com.theswirlingvoid.rentme.item.configurationwand.BuildingWand;
import com.theswirlingvoid.rentme.item.configurationwand.GenericWand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rentme extends JavaPlugin {
	public static JavaPlugin plugin;
	@Override
	public void onEnable() {
		plugin = this;
		CommandRegister.registerCommands(this);
		registerListeners();
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	public void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(new BuildingWand(BuildingMode.AREA), this);
	}
}
