package com.ExpertMode.Modules;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public class MagmaSlime extends Module {

	private final int magmaSpawnRange = 10;
	private final double magmaSpawnChance = 0.1;
	
	public MagmaSlime(Main main) {
		super(main);
		
		// Start a timer that runs once every minute
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				spawnMagmaSlime();
			}
		};
		bukkitRunnable.runTaskTimer(main, 0, 20 * 60);
	}
	
	public void spawnMagmaSlime() {
		
		// Loop through all the players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			
			// Get all nearby lava blocks
			List<Block> nearbyLavaBlocks =
					LocationHelper.getInstance().findNearbyBlocks(
							onlinePlayer.getLocation(),
							magmaSpawnRange,
							Material.LAVA);
			
			// Loop through all nearby lava blocks
			for (Block nearbyLavaBlock : nearbyLavaBlocks) {
				
				// Check if the 10% (magmaSpawnChance) probability occured
				if (MathHelper.getInstance().hasChanceHit(magmaSpawnChance)) {
					
					// Get the world of the player
					World world = onlinePlayer.getLocation().getWorld();
					// Get the spawnLocation
					Location spawnLocation = 
							LocationHelper.getInstance().offsetLocation(
									nearbyLavaBlock.getLocation(),
									new Vector(0.5, 0.5, 0.5));
					
					// Spawn the magma cube
					world.spawnEntity(spawnLocation, EntityType.MAGMA_CUBE);
				}
			}
		}
	}
}
