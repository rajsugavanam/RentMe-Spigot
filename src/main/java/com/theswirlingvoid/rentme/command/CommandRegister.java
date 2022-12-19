package com.theswirlingvoid.rentme.command;

import com.theswirlingvoid.rentme.command.commands.GiveItemCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegister {
	public static void registerCommands(JavaPlugin plugin) {
		plugin.getCommand("giveitem").setExecutor(new GiveItemCommand());
	}
}
