package com.ExpertMode.Modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.ExpertMode.Main;
import com.ExpertMode.Module;

public class PlayerBanner extends Module {

	public PlayerBanner(Main main) {
		super(main);
	}
	
	private final String banReason = "30 Minute Death Cooldown";
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		// Get the player that should be banned
		Player player = event.getEntity();
		
		// The task to ban the player
		BukkitRunnable banTask = new BukkitRunnable() {			
			@Override
			public void run() {
				Bukkit.getBannedPlayers().add(player);
				player.kickPlayer(banReason);
			}
		};
		banTask.runTaskLater(main, 20);
		
		// The task to unban the player
		BukkitRunnable unbanTask = new BukkitRunnable() {			
			@Override
			public void run() {
				Bukkit.getBannedPlayers().remove(player);
			}
		};
		unbanTask.runTaskLater(main, 360);
	}
}
