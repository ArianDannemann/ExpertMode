package com.ExpertMode.Modules;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ExpertMode.Main;
import com.ExpertMode.Module;

/*
 * This class will make creepers much faster
 */
public class Creeper extends Module {
	
	public Creeper(Main main) {
		super(main);
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
		if (event.getEntity().getType() != EntityType.CREEPER) {
			return;
		}
		
		// Get the living entity
		LivingEntity livingEntity = (LivingEntity) event.getEntity();
		
		// Remove the speed effect
		livingEntity.removePotionEffect(PotionEffectType.SPEED);
		
		// Create a poison potion effect
		PotionEffect poison = new PotionEffect(PotionEffectType.POISON, 600, 1, false, false);
		
		// Apply the poison potion effect to the entity
		livingEntity.addPotionEffect(poison);
	}
}
