package com.ExpertMode.Modules;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.ExpertMode.Main;
import com.ExpertMode.Module;

public class Zombifier extends Module {

	public Zombifier(Main main) {
		super(main);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		// Check if the damaged entity is a player and the damager is a zombie
		if (event.getEntity() instanceof Player == false
				|| event.getDamager() instanceof Zombie == false) {
			return;
		}
		
		// Get the player
		Player player = (Player) event.getEntity();
		// Get the world
		World world = player.getLocation().getWorld();
		
		// Check if the player has died
		if (player.getHealth() - event.getFinalDamage() > 0) {
			return;
		}
		
		// Spawn in the zombie
		Zombie zombie = (Zombie) world.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
		
		// Set the zombie attributes
		zombie.setCanPickupItems(true);
		zombie.setCustomName(player.getName());
	}
}
