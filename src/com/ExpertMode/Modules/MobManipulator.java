package com.ExpertMode.Modules;

import org.bukkit.Bukkit;
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

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.MathHelper;

public class MobManipulator extends Module {

	private final int aggrovateZombiePigmenRange = 30;
	private final double aggrovateZombiePigmenChange = 30;
	
	public MobManipulator(Main main) {
		super(main);
		
		// Start a timer that runs once every minute
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
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
}
