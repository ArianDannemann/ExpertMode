package com.ExpertMode.Modules;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public class MobManipulator extends Module {

	private final int 
			magmaSpawnRange = 10,
			aggrovateZombiePigmenRange = 30;
	private final double 
			magmaSpawnChance = 0.1,
			aggrovateZombiePigmenChange = 30;
	
	public MobManipulator(Main main) {
		super(main);
		
		// Start a timer that runs once every minute
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				spawnMagmaSlime();
				aggrovateZombiePigmen();
			}
		};
		bukkitRunnable.runTaskTimer(main, 0, 20 * 60);
	}
	
	public void aggrovateZombiePigmen() {

		// Loop through all the players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			// Check if the possibility hit
			if (!MathHelper.getInstance().hasChanceHit(aggrovateZombiePigmenChange)) {
				return;
			}

			// Loop through nearby mobs
			for (Entity nearbyEntity : onlinePlayer.getNearbyEntities(aggrovateZombiePigmenRange, aggrovateZombiePigmenRange, aggrovateZombiePigmenRange)) {

				// Check if the entity is a zombie pigman
				if (nearbyEntity.getType() == EntityType.PIG_ZOMBIE) {

					// Get the zombie pigman
					PigZombie pigZombie = (PigZombie) nearbyEntity;

					// Make the zombie pigman attack the player
					pigZombie.setTarget(onlinePlayer);
				}
			}
		}
	}
	
	/*
	 * Gives creepers speed effect
	 */
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		
		// Check if a creeper spawned
		if (event.getEntity().getType() != EntityType.CREEPER) {
			return;
		}
		
		// Get the living entity
		LivingEntity livingEntity = (LivingEntity) event.getEntity();
		
		// Create a speed potion effect
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 1000000, 1, false, false);
		
		// Apply the speed potion effect to the entity
		livingEntity.addPotionEffect(speed);
	}
	
	/*
	 * This just gets rid of all potion effects when a creeper explodes
	 */
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		
		// Check if a creeper exploded
		if (event.getEntity() instanceof Creeper == false) {
			return;
		}
		
		// Get the living entity
		LivingEntity livingEntity = (LivingEntity) event.getEntity();
		
		// Remove the speed effect
		livingEntity.removePotionEffect(PotionEffectType.SPEED);
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
