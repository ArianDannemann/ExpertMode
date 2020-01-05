package com.ExpertMode.Modules;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.MathHelper;
import com.PluginBase.SoundEmitter;

/*
 * This class will randomly aggravate wolfss towards the player
 */
public class Wolf extends Module {

	private final int aggravateWolfRange = 15, // range from the player in which wolfs will become aggresive
			aggravationPeriod = 1200; // amount of ticks between chance of aggravation
	private final double aggravateWolfChange = 30; // chance of wolfs becoming aggresive

	public Wolf(Main main) {
		super(main);

		// Start a timer that runs once every minute
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				aggravateWolfs();
			}
		};
		bukkitRunnable.runTaskTimer(main, 0, this.aggravationPeriod);
	}

	public void aggravateWolfs() {

		// Loop through all the players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			// Check if the possibility hit
			if (!MathHelper.getInstance().hasChanceHit(this.aggravateWolfChange)) {
				return;
			}

			// Loop through nearby mobs
			for (Entity nearbyEntity : onlinePlayer.getNearbyEntities(this.aggravateWolfRange, this.aggravateWolfRange,
					this.aggravateWolfRange)) {

				// Check if the entity is a zombie pigman
				if (nearbyEntity.getType() == EntityType.WOLF) {

					// Get the wolf
					org.bukkit.entity.Wolf wolf = (org.bukkit.entity.Wolf) nearbyEntity;

					// Make the wolf attack the player
					wolf.setTarget(onlinePlayer);

					// Play an angry sound at the location of the zombie pigman
					SoundEmitter.getInstance().emitSound(onlinePlayer.getLocation().getWorld(), // world
							wolf.getLocation(), // location
							Sound.ENTITY_WOLF_GROWL, // sound
							SoundCategory.HOSTILE, // sound category
							1, // volume
							1 // pitch
					);
				}
			}
		}
	}
}
