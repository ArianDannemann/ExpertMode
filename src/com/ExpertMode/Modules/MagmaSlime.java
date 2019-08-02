package com.ExpertMode.Modules;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

/*
 * This class will spawn magma slimes in lava pools near players
 */
public class MagmaSlime extends Module {

	private final int 
			magmaSlimeSpawnRange = 10,				// range in which we check for lava near the player
			maximumNearbyMagmaSlimes = 5,			// maximum amount of magma slimes near the player
			spawnPeriod = 1200;						// amount of ticks between spawning magma slimes
	private final double magmaSpawnChance = 0.1;	// chance of magma slime spawning for each lava block
	
	public MagmaSlime(Main main) {
		super(main);
		
		// Start a timer that runs once every minute
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				spawnMagmaSlime();
			}
		};
		bukkitRunnable.runTaskTimer(main, 0, this.spawnPeriod);
	}
	
	public int getAmountOfNearbyMagmaSlimes(Player player) {
		
		// Prepare an amount to return later
		int amount = 0;
		
		// Loop through all nearby entities
		for (Entity nearbyEntity : player.getNearbyEntities(this.magmaSlimeSpawnRange * 2, this.magmaSlimeSpawnRange, this.magmaSlimeSpawnRange * 2)) {
			
			// Check if entity is a magma slime
			if (nearbyEntity.getType() == EntityType.MAGMA_CUBE) {
				
				// Increase the amount of nearby magma slimes
				amount++;
			}
		}
		
		// Return the amount of magma slimes near the player
		return amount;
	}
	
	public void spawnMagmaSlime() {
		
		// Loop through all the players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			
			// Get all nearby lava blocks
			List<Block> nearbyLavaBlocks =
					LocationHelper.getInstance().findNearbyBlocks
					(
							onlinePlayer.getLocation(),		// location
							this.magmaSlimeSpawnRange,			// range
							Material.LAVA					// block type
					);

			// Check if there is space for more magma slimes
			if (getAmountOfNearbyMagmaSlimes(onlinePlayer) < this.maximumNearbyMagmaSlimes) {

				// Loop through all nearby lava blocks
				for (Block nearbyLavaBlock : nearbyLavaBlocks) {

					// Check if the 10% (magmaSpawnChance) probability occured
					if (MathHelper.getInstance().hasChanceHit(this.magmaSpawnChance)) {

						// Get the world of the player
						World world = onlinePlayer.getLocation().getWorld();
						// Get the spawnLocation
						Location spawnLocation = 
								LocationHelper.getInstance().offsetLocation
								(
										nearbyLavaBlock.getLocation(),
										new Vector(0.5, 0.5, 0.5)
								);

						// Spawn the magma cube
						world.spawnEntity(spawnLocation, EntityType.MAGMA_CUBE);
					}
				}
			}
		}
	}
}
