package com.ExpertMode.Modules;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.SoundEmitter;
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
		
		// Play an enderman scream sound
		SoundEmitter.getInstance().emitSound
		(
				player.getWorld(),
				player.getLocation(), 
				Sound.ENTITY_ENDERMAN_SCREAM,
				SoundCategory.HOSTILE,
				1,
				1
		);
	}
}
