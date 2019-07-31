package com.ExpertMode.Modules;

import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.ParticleEmitter;

/*
 * This class will give phantom invisibility and particle effects
 */
public class Phantom extends Module {
	
	public Phantom(Main main) {
		super(main);
	}
	
	/*
	 * Gives creepers speed effect
	 */
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		
		// Check if a phantom spawned
		if (event.getEntity().getType() != EntityType.PHANTOM) {
			return;
		}
		
		// Get the living entity
		LivingEntity livingEntity = (LivingEntity) event.getEntity();
		
		// Create a speed potion effect
		PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 1, false, false);
		
		// Apply the speed potion effect to the entity
		livingEntity.addPotionEffect(invisibility);
		
		// TODO: think of better particle effect
		ParticleEmitter.getInstance().emitParticlesContinuously
		(
				livingEntity,
				Particle.ENCHANTMENT_TABLE,
				1,
				0,
				new Vector(0.25, 0.25, 0.25),
				main,
				0,
				2,
				1000000
		);
	}
}
