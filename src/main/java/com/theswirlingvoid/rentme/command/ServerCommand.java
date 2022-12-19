package com.theswirlingvoid.rentme.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class ServerCommand implements CommandExecutor {
	protected CommandMessageSender msgSender;
	protected Command command;
	protected String[] args;
	protected CommandSender sender;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		this.msgSender = new CommandMessageSender(sender);
		this.args = args;
		this.command = command;
		this.sender = sender;

		handleCommand();

		return true;
	}

	protected abstract void handleCommand();
}
