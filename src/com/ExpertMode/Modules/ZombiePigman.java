package com.ExpertMode.Modules;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.MathHelper;
import com.PluginBase.SoundEmitter;

/*
 * This class will randomly aggravate zombie pigmen towards the player
 */
public class ZombiePigman extends Module {

	private final int 
			aggravateZombiePigmenRange = 30,				// range from the player in which zombie pigmen will become aggresive
			aggrovationPeriod = 1200;						// amount of ticks between chance of aggravation
	private final double aggravateZombiePigmenChange = 30;	// chance of zombie pigmen becoming aggresive
	
	public ZombiePigman(Main main) {
		super(main);
		
		// Start a timer that runs once every minute
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				aggravateZombiePigmen();
			}
		};
		bukkitRunnable.runTaskTimer(main, 0, aggrovationPeriod);
	}
	
	public void aggravateZombiePigmen() {

		// Loop through all the players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			// Check if the possibility hit
			if (!MathHelper.getInstance().hasChanceHit(aggravateZombiePigmenChange)) {
				return;
			}
			// Loop through nearby mobs
			for (Entity nearbyEntity : onlinePlayer.getNearbyEntities(aggravateZombiePigmenRange, aggravateZombiePigmenRange, aggravateZombiePigmenRange)) {

				// Check if the entity is a zombie pigman
				if (nearbyEntity.getType() == EntityType.PIG_ZOMBIE) {

					// Get the zombie pigman
					PigZombie pigZombie = (PigZombie) nearbyEntity;

					// Make the zombie pigman attack the player
					pigZombie.setTarget(onlinePlayer);
					
					// Play an angry sound at the location of the zombie pigman
					SoundEmitter.getInstance().emitSound
					(
							onlinePlayer.getLocation().getWorld(),		// world
							pigZombie.getLocation(),					// location
							Sound.ENTITY_ZOMBIE_PIGMAN_ANGRY,			// sound
							SoundCategory.HOSTILE,						// sound category
							1,											// volume
							1											// pitch
					);
				}
			}
		}
	}
}
