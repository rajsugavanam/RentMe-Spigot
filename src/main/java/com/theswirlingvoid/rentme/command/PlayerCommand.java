package com.theswirlingvoid.rentme.command;

import org.bukkit.entity.Player;

public abstract class PlayerCommand extends ServerCommand {

	protected Player player;

	protected abstract void handlePlayerCommand();

	@Override
	protected void handleCommand() {
		if (sender instanceof Player) {
			this.player = (Player) sender;
			handlePlayerCommand();
		} else {
			System.out.println("The console cannot use that command!");
		}
	}
}
