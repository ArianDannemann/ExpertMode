package com.ExpertMode.Modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.Chat;

public class PlayerDeath extends Module {

	public PlayerDeath(Main main) {
		super(main);
	}

	private final int cooldownDuration = 36000; // 30 minutes
	
	private List<String> deadPlayers = new ArrayList<>();
	private HashMap<String, Location> respawnedPlayers = new HashMap<>();
	
	@EventHandler
	public void onPlayerDeath(PlayerRespawnEvent event) {
		
		// Get the player that respawned
		Player player = event.getPlayer();
		
		// Get the player name
		String playerName = player.getName();
		
		// Add the player name to the list of dead players
		deadPlayers.add(playerName);
		
		// Get the location where the player respawned
		Location playerRespawnLocation = event.getRespawnLocation();
		
		// Notify the player about what happened
		Chat.getInstance().sendMessageToPlayer
		(
				player,
				ChatColor.RED + "You have died and are on a 30 minute cooldown. "
				+ "You will respawn here once that cooldown expired"
		);
		
		// This runnable will set the player back to survival mode after the death cooldown
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				// Remove the player from the list of dead players
				deadPlayers.remove(playerName);
				
				// Add the player from the list of respawned players
				respawnedPlayers.put(playerName, playerRespawnLocation);
			}
		};
		runnable.runTaskLater(main, cooldownDuration);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		// Get the player that moved
		Player player = event.getPlayer();
		// Get the players name
		String playerName = player.getName();

		// Check if the player is on cooldown
		if (deadPlayers.contains(playerName)) {

			// Set the players game mode to spectacting
			player.setGameMode(GameMode.SPECTATOR);
			
			// Regulate fly speed so spectators don't generate that much chunks
			player.setFlySpeed(0.05f);
		}
		
		// Check if the player should be respawned
		if (respawnedPlayers.containsKey(playerName)) {
			
			// Set the players game mode to survival
			player.setGameMode(GameMode.SURVIVAL);
			
			// Teleport the player back to his respawn location
			player.teleport(respawnedPlayers.get(playerName));
			
			// Remove the player from the respawn list
			respawnedPlayers.remove(playerName);
			
			// Notify the player about respawning
			Chat.getInstance().broadcastMessage(player.getName() + " has respawned");
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		// Get the player that joined
		Player player = event.getPlayer();
		// Get the players name
		String playerName = player.getName();

		// Get if the joining player is dead
		if (!deadPlayers.contains(playerName)) {
			return;
		}
		
		// Notify the player about still being dead
		Chat.getInstance().sendMessageToPlayer(player, ChatColor.RED + "You are dead right now");
		
		// Change the join message
		event.setJoinMessage(ChatColor.YELLOW + playerName + " has joined the game as a spectator");
	}
}
