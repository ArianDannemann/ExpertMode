package com.ExpertMode.Modules;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.TimeHelper;

public class ElytraBlocker extends Module {

	public ElytraBlocker(Main main) {
		super(main);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		// Get the player who moved
		Player player = event.getPlayer();
		// Get the players world
		World world = player.getLocation().getWorld();
		
		// Check if the player is gliding while it's night or storming
		if (player.isGliding() &&
				(TimeHelper.getInstance().isNight(world)
				|| world.hasStorm())) {
			
			// Stop the player from gliding
			player.setGliding(false);
		}
	}
}
