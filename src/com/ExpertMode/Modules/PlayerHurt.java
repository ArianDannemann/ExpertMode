package com.ExpertMode.Modules;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.LocationHelper;
import com.PluginBase.ParticleEmitter;

/*
 * A blood effect on player damage
 */
public class PlayerHurt extends Module {

	public PlayerHurt(Main main) {
		super(main);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageEvent event) {
		
		// Check if a player got hurt
		if (event.getEntity() instanceof LivingEntity == false) {
			return;
		}
		
		// Get the player
		LivingEntity livingEntity = (LivingEntity) event.getEntity();
		// Get the world in which the event occured
		World world = livingEntity.getWorld();
		// Get the location of the player to play the particles there
		Location location = livingEntity.getLocation();
		
		// Play a particle effect at the location of the player
		ParticleEmitter.getInstance().emitParticles
		(
				world,										// world
				LocationHelper.getInstance().offsetLocation	// location
				(
						location,
						new Vector(0, 1, 0)
				),
				Particle.BLOCK_CRACK,						// particle
				Material.REDSTONE_BLOCK.createBlockData(),	// type
				30,											// amount
				0,											// speed
				new Vector(0.25, 0.5, 0.25)					// spread
		);
	}
}
