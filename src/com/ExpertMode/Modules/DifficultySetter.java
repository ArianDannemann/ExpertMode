package com.ExpertMode.Modules;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ExpertMode.Main;
import com.ExpertMode.Module;

public class DifficultySetter extends Module {

	public DifficultySetter(Main main) {
		super(main);
		
		// Loop through all online player
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			
			// Get the players' world
			World world = onlinePlayer.getLocation().getWorld();
			
			// Set the difficulty to hard
			world.setDifficulty(Difficulty.HARD);
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		// Get the player
		Player player = event.getPlayer();
		// Get the world
		World world = player.getLocation().getWorld();
		
		// Set the difficulty to hard
		world.setDifficulty(Difficulty.HARD);
	}
}
