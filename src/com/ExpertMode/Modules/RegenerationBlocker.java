package com.ExpertMode.Modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ExpertMode.Main;
import com.ExpertMode.Module;

public class RegenerationBlocker extends Module {

	public RegenerationBlocker(Main main) {
		super(main);
	}
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		
		// Check if a player is trying to regenerate health
		if (event.getEntity() instanceof Player
				&& event.getRegainReason() == RegainReason.SATIATED) {
			
			// Prepare a weakness potion effect
			PotionEffect weakness = new PotionEffect(PotionEffectType.WEAKNESS, 600, 0);
			
			// Get the player
			Player player = (Player) event.getEntity();
			
			// Add the potion effect to the player
			player.addPotionEffect(weakness);
			
			// Cancel the regeneration event
			event.setCancelled(true);
		}
	}
}
