package com.ExpertMode.Modules;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.ParticleEmitter;

/*
 * Drowned can now pull the player down if they are in the water with him
 */
public class Squid extends Module {

	public Squid(Main main) {
		super(main);
	}

	double explosionTriggerRadius = 3, // the radius in which drowned can pull down the player
			explosionRadius = 3;

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		// Get the player who moved
		Player player = event.getPlayer();
		// Get the players location
		Location playerLocation = player.getLocation();
		// Get the players world
		World world = playerLocation.getWorld();
		
		// Check if the player is in water
		/*
		 * if (!playerLocationBlockType.contains("water") &&
		 * !playerLocationBlockType.contains("sea")) { return; }
		 */

		// Loop through all nearby entities
		for (Entity entity : player.getNearbyEntities(this.explosionTriggerRadius, this.explosionTriggerRadius,
				this.explosionTriggerRadius)) {

			// Check if the entity is a squid and in the specified range
			if (entity.getType() == EntityType.SQUID
					&& entity.getLocation().distance(playerLocation) <= this.explosionTriggerRadius) {
				world.createExplosion(entity.getLocation(), (float) this.explosionRadius, false);
				ParticleEmitter.getInstance().emitParticles(world, entity.getLocation(), Particle.SQUID_INK, 50, 0,
						new Vector(this.explosionRadius / 2, this.explosionRadius / 2, this.explosionRadius / 2));
				entity.remove();
			}
		}
	}
}
