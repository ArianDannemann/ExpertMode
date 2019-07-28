package com.ExpertMode.Modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.Chat;
import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;
import com.PluginBase.ParticleEmitter;
import com.PluginBase.TimeHelper;

public class BloodMoon extends Module {

	public BloodMoon(Main main) {
		super(main);
		
		checkForNight();
	}
	
	private List<World> checkedWorlds = new ArrayList<>();
	
	private final int 
			chance = 5,						// chance of a blood moon appear per night (in %)
			herdMinimumDistance = 15,		// minimum amount of blocks the herd has to spawn away from the nearest player
			herdMaximumDistance = 25,		// maximum amount of blocks the herd may spawn away from the player
			zombieMaximumDistance = 5,		// maximum amount of blocks a zombie may spawn away from the herd
			zombieAmount = 10;				// amount of zombies per herd
	
	private void checkForNight() {
		
		// Runnable that will check all players worlds every 10 seconds
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				// Loop through all online players
				for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

					// Get the players world
					World world = onlinePlayer.getWorld();

					// Check if it's night for the player
					if (!checkedWorlds.contains(world)
							&& TimeHelper.getInstance().isNight(world)) {

						// Check if it should be a blood moon
						if (MathHelper.getInstance().hasChanceHit(chance)) {
							runBloodMoon(world);
						}

						// Add the world to a list of worlds as to not check it again
						checkedWorlds.add(world);
					}
					else if (!TimeHelper.getInstance().isNight(world)) {
						// Remove the world from the list of checked worlds so it will be checked next night
						checkedWorlds.remove(world);
					}
				}
			}
		};
		runnable.runTaskTimer(main, 20, 200);
	}
	
	private void runBloodMoon(World world) {
		
		// Loop through all online players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			
			// Check if player is on the target world
			if (onlinePlayer.getWorld() == world) {
				
				// Notify the player of the event
				Chat.getInstance().sendMessageToPlayer(onlinePlayer, ChatColor.RED + "As the blood moon rises you hear growls nearby");
				
				// Get the player location
				Location playerLocation = onlinePlayer.getLocation();
				// Prepare a spawn location for the zombie
				Location herdLocation = playerLocation;
				
				// Generate a random spawn location for the herd of zombies
				herdLocation = LocationHelper.getInstance().getRandomNearbyPosition(playerLocation, herdMinimumDistance, herdMaximumDistance);
				
				// It should spawn 10 zombies
				for (int i = 0; i <= zombieAmount; i++) {
					
					// Generate random location near where the herd spawns
					Location spawnLocation = LocationHelper.getInstance().getRandomNearbyPosition(herdLocation, zombieMaximumDistance);
					
					// Spawn in a zombie
					Zombie zombie = (Zombie) world.spawnEntity(spawnLocation, EntityType.ZOMBIE);
					
					// Spawn a particle effect for the zombie spawning
					ParticleEmitter.getInstance().emitParticles(world, spawnLocation, Particle.CLOUD, 10, 0.1, new Vector(0, 0, 0));
					
					// Make the zombie target the player
					zombie.setTarget(onlinePlayer);
					
					// Prepare a speed potion effect
					PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 1000000, 1);
					
					// Apply the speed potion effect to the zombie
					zombie.addPotionEffect(speed);
				}
			}
		}
	}
}
