package com.theswirlingvoid.rentme.item.configurationwand;

import com.theswirlingvoid.rentme.Rentme;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerThreads {

	private static HashMap<Player, HashMap<String, List<BukkitRunnable>>> categorizedThreads = new HashMap<>();

	public void startThreadForPlayer(Player player, String category, Runnable runnable) {

		BukkitRunnable thread = new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		};
		thread.runTaskTimer(Rentme.plugin, 0, 1);

		HashMap<String, List<BukkitRunnable>> playerThreads = getThreadsFromPlayer(player);

		// update player threads for category
		List<BukkitRunnable> categoryThreads = playerThreads.getOrDefault(category, new ArrayList<>());
		categoryThreads.add(thread);
		playerThreads.put(category, categoryThreads);

		categorizedThreads.put(player, playerThreads);
	}
	public static void stopAllThreadsForPlayer(Player player, String category) {

		HashMap<String, List<BukkitRunnable>> playerThreads = getThreadsFromPlayer(player);

		playerThreads.getOrDefault(category, new ArrayList<>()).forEach(BukkitRunnable::cancel);
		categorizedThreads.remove(player);
	}

	@Nullable
	private static HashMap<String, List<BukkitRunnable>> getThreadsFromPlayer(Player player) {
		return categorizedThreads.getOrDefault(player, new HashMap<>());
	}
}
