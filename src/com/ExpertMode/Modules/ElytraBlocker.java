package com.ExpertMode.Modules;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.LocationHelper;
import com.PluginBase.ParticleEmitter;
import com.PluginBase.SoundEmitter;
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
			
			// Get the location where the particles should spawn
			Location particleLocation =
					LocationHelper.getInstance().offsetLocation
					(
							player.getLocation(),					// player location
							new Vector								// location slightly in front of the player
							(
									player.getVelocity().getX() * 20,
									0,
									player.getVelocity().getZ() * 20
							)
					);
			
			// Emit some particles to let the player know he's not lagging
			ParticleEmitter.getInstance().emitParticles
			(
					world,											// world
					particleLocation,								// location
					Particle.SWEEP_ATTACK,							// particle
					1,												// amount
					1,												// speed
					new Vector(0, 0, 0)								// spread
			);
			ParticleEmitter.getInstance().emitParticles
			(
					world,
					particleLocation,
					Particle.CLOUD,
					5,
					0.1,
					new Vector(0.25, 0.25, 0.25)
			);
			
			// Player some sound at the players location
			SoundEmitter.getInstance().emitSound
			(
					world,											// world
					player.getLocation(),							// location
					Sound.BLOCK_BEACON_DEACTIVATE,					// sound
					SoundCategory.BLOCKS,							// sound category
					1,												// volume
					1												// pitch
			);
		}
	}
}
