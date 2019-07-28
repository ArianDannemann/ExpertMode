package com.ExpertMode.Modules;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.Effects.Scared;

public class Enderman extends Module {

	public Enderman(Main main) {
		super(main);
	}
	
	@EventHandler
	private void onEntityTarget(EntityTargetLivingEntityEvent event) {
		
		// Check if an enderman targets a player
		if (event.getEntity().getType() != EntityType.ENDERMAN
				|| event.getTarget() instanceof Player == false) {
			return;
		}
		
		// Get the player
		Player player = (Player) event.getTarget();
		
		// Apply effect to player
		new Scared(main, (LivingEntity) player);
	}
}
