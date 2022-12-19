package com.theswirlingvoid.rentme.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Optional;

public class CommandMessageSender {
	private final CommandSender sender;
	private String PREFIX;

	public CommandMessageSender(CommandSender sender) {
		this.sender = sender;
	}

	private void send(String message) {
		sender.sendMessage(message);
	}

	public class Info {

		public Info() {
			PREFIX = ChatColor.BLUE +"ℹ ";
		}

		public void properCommandUsage(@Nullable Integer usageNumber, String commandName, String... args) {

			String argString = "";
			for (String arg : args) {
				argString = argString + "<"+arg+"> ";
			}

			String sendString;
			if (usageNumber != null) {
				sendString = PREFIX+"Command Use #"+usageNumber+": /"+commandName+" "+argString;
			} else {
				sendString = PREFIX+"Command Usage: /"+commandName+" "+argString;;
			}

			send(sendString);
		}

	}

	public class Warning {

		public Warning() {
			PREFIX = ChatColor.YELLOW +"⚠ ";
		}

	}

	public class Error {

		public Error() {
			PREFIX = ChatColor.RED +"❌ ";
		}

		public void badArgument(String argName) {

			send(
				PREFIX+"Bad Argument! ("+argName+")"
			);
		}

	}
}
