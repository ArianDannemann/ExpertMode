package com.ExpertMode.Modules;

import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
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

	private final int chance = 100;
	
	private final PotionType[] possiblePotionTypes = new PotionType[] 
	{
			PotionType.SLOWNESS,
			PotionType.POISON,
			PotionType.WEAKNESS,
	};
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		
		// Check if the entity is a skeleton and it's shooting an arrow
		if
		(		
				event.getEntity().getType() != EntityType.SKELETON
				|| event.getProjectile().getType() != EntityType.ARROW
		) {
			return;
		}
		
		// Check if the event should occure
		if (!MathHelper.getInstance().hasChanceHit(this.chance)) {
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
		
		// Choose a random index in the bound of possiblePotionTypes
		int potionTypeIndex = MathHelper.getInstance().getRandom().nextInt(this.possiblePotionTypes.length);
		
		// Choose a random potionType
		PotionType potionType = this.possiblePotionTypes[potionTypeIndex];
		
		// Create the potion effect
		PotionData potionData = new PotionData(potionType);
		
		// Apply the potion effect
		tippedArrow.setBasePotionData(potionData);
	}
}
