package com.ExpertMode.Modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.ExpertMode.Main;
import com.ExpertMode.Module;

/*
 * This class will tip the bows of skeletons with random potion effects
 */
public class Skeleton extends Module {

	private final int 
			updatePeriod = 40, 					// the period at which we check if the player is near a skeleton
			skeletonWeaponChangeRange = 5;		// the range at which a skeleton will change to a sword as a weapon
	
	public Skeleton(Main main) {
		super(main);
		
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				updateSkeletonWeapon();
			}
		};
		bukkitRunnable.runTaskTimer(main, 0, this.updatePeriod);
	}

	public void updateSkeletonWeapon() {
		// Loop through all the players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			// Loop through nearby mobs
			for (Entity nearbyEntity : onlinePlayer.getNearbyEntities(this.skeletonWeaponChangeRange * 3,
					this.skeletonWeaponChangeRange * 3, this.skeletonWeaponChangeRange * 3)) {

				// Check if the entity is a skeleton
				if (nearbyEntity.getType() == EntityType.SKELETON) {

					// Get the skeleton
					org.bukkit.entity.Skeleton skeleton = (org.bukkit.entity.Skeleton) nearbyEntity;

					if (nearbyEntity.getLocation().distance(onlinePlayer.getLocation()) > this.skeletonWeaponChangeRange) {
						if (skeleton.getEquipment().getItemInMainHand().getType() == Material.STONE_SWORD) {
							skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
						}
					} else {
						skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
					}
				}
			}
		}
	}

	/*private final int chance = 100;
	
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
	}*/
	
	
}
