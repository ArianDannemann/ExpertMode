package com.ExpertMode.Modules;

import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TippedArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.MathHelper;

/*
 * This class will tip the bows of skeletons with random potion effects
 */
public class Skeleton extends Module {

	public Skeleton(Main main) {
		super(main);
	}

	private final int chance = 10;
	
	private final PotionType[] possiblePotionTypes = new PotionType[] {
			PotionType.SLOWNESS,
			PotionType.POISON,
			PotionType.WEAKNESS,
	};
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		
		// Check if the entity is a skeleton and it's shooting an arrow
		if
		(		event.getEntity() instanceof Skeleton == false
				|| event.getEntity() instanceof Player
				|| event.getProjectile() instanceof Arrow == false
		) {
			return;
		}
		
		// Check if the event should occur
		if (!MathHelper.getInstance().hasChanceHit(chance)) {
			return;
		}

		// Get the arrow
		Arrow arrow = (Arrow) event.getProjectile();
		// Get the world
		World world = arrow.getLocation().getWorld();
		
		// Spawn a tipped arrow
		TippedArrow tippedArrow = (TippedArrow) world.spawnEntity(arrow.getLocation(), EntityType.TIPPED_ARROW);
		
		// Copy the velocity of the normal arrow
		tippedArrow.setVelocity(arrow.getVelocity());
		
		// Remove the normal arrow
		arrow.remove();
		
		// Choose a random potionType
		PotionType potionType = possiblePotionTypes[MathHelper.getInstance().getRandom().nextInt(possiblePotionTypes.length)];
		
		// Create the potion effect
		PotionData potionData = new PotionData(potionType);
		
		// Apply the potion effect
		tippedArrow.setBasePotionData(potionData);
	}
}
