package com.ExpertMode.Modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import com.ExpertMode.Main;
import com.ExpertMode.Module;

public class DamageAmplifier extends Module {

	public DamageAmplifier(Main main) {
		super(main);
	}
	
	private final float amplifier = 3.0f;
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		
		// Check if the entity who took damage is a player
		if (event.getEntity() instanceof Player == false) {
			return;
		}
		
		// Calculate the amplified damage
		float amplifiedDamage = (int) event.getDamage() * this.amplifier;
		
		// Set the amplified damage
		event.setDamage(amplifiedDamage);
	}
}
