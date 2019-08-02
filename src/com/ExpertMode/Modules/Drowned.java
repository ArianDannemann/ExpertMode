package com.ExpertMode.Modules;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.ParticleEmitter;
import com.PluginBase.SoundEmitter;

/*
 * Drowned can now pull the player down if they are in the water with him
 */
public class Drowned extends Module {

	public Drowned(Main main) {
		super(main);
	}

	double drownedPullRadius = 5;						// the radius in which drowned can pull down the player
	Vector drownedPullForce = new Vector(0, -0.5, 0);	// how much the drowned can pull down the player
	
	PotionEffect weakness =
			new PotionEffect
			(
					PotionEffectType.WEAKNESS,			// type
					10,									// duration
					0,									// amplifier
					true,								// ambient
					false,								// particles
					false								// icon
			);
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		// Get the player who moved
		Player player = event.getPlayer();
		// Get the players' location
		Location location = player.getLocation();
		// Get the players' world
		World world = location.getWorld();
		// Get the player block type as a lower case string
		String playerLocationBlockType = player.getLocation().getBlock().getType().toString().toLowerCase();
		
		// Check if the player is in water
		if
		(
				!playerLocationBlockType.contains("water")
				&& !playerLocationBlockType.contains("sea")
		) {
			return;
		}
		
		// Loop through all nearby entities
		for (Entity entity : player.getNearbyEntities(this.drownedPullRadius, this.drownedPullRadius, this.drownedPullRadius)) {
			
			// Get the entity block type as a lower case string
			String entityLocationBlockType = entity.getLocation().getBlock().getType().toString().toLowerCase();
			
			/*
			 *  Check if the entity is a drowned
			 *  and if the drowned is under water
			 *  and if the player is going up
			 *  and if the player doesn't have a slow effect
			 */
			if
			(
					entity.getType() == EntityType.DROWNED
					&& (entityLocationBlockType.contains("water")
					|| entityLocationBlockType.contains("sea"))
					&& event.getFrom().getY() < event.getTo().getY()
					&& !player.hasPotionEffect(PotionEffectType.WEAKNESS)
			) {
				
				// Pull the player down
				player.setVelocity(this.drownedPullForce);
				
				// Slow the player down for a short amount of time
				player.addPotionEffect(this.weakness);
				
				// Player some water splash effect at the players' position
				ParticleEmitter.getInstance().emitParticles
				(
						world,							// world
						location,						// location
						Particle.WATER_BUBBLE,			// particle
						10,								// amount
						0,								// speed
						new Vector(0.5, 1, 0.5)			// spread
				);
				ParticleEmitter.getInstance().emitParticles
				(
						world,							// world
						location,						// location
						Particle.WATER_WAKE,			// particle
						10,								// amount
						0,								// speed
						new Vector(0.5, 1, 0.5)			// spread
				);
			
				// Play some sound effect at the players' location
				SoundEmitter.getInstance().emitSound
				(
						world,							// world
						location,						// location
						Sound.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED,// sound
						SoundCategory.HOSTILE,			// sound category
						1,								// volume
						1								// pitch
				);
			}
		}
	}
}
