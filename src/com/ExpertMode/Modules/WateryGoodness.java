package com.ExpertMode.Modules;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.Chat;
import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

/*
 * This class will make creepers much faster
 */
public class WateryGoodness extends Module {

	public WateryGoodness(Main main) {
		super(main);
	}

	/*
	 * Gives creepers speed effect
	 */
	/*@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		// Check if a block was right clicked
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		// Get the player who clicked a block
		Player player = event.getPlayer();

		// Check if the player is holding a water bucket
		/*
		 * if (player.getInventory().getItemInMainHand() == null ||
		 * player.getInventory().getItemInMainHand().getType() != Material.WATER_BUCKET)
		 * { return; }
		 *

		// Get the location that was clicked
		Location clickedLocation = event.getClickedBlock().getLocation();

		// Get all water blocks near the clicked block
		List<Block> nearbyWaterBlocks = LocationHelper.getInstance().findNearbyBlocks(clickedLocation, 1,
				Material.WATER);

		for (Block waterBlock : nearbyWaterBlocks) {
			Levelled levelled = (Levelled) waterBlock.getBlockData();
			levelled.setLevel(0);
			waterBlock.setBlockData(levelled);
		}
	}*/

	@EventHandler
	public void testBlockEvent(org.bukkit.event.block.BlockFromToEvent event) {
		//Chat.getInstance().broadcastMessage("Event: " + event.getEventName() + " " + event.getBlock().getType() + " -> "
		//		+ event.getToBlock().getType());

		Location location = event.getBlock().getLocation();
		
		// Get all water blocks near the clicked block
		List<Block> nearbyWaterBlocks = LocationHelper.getInstance().findNearbyBlocks(location, 1,
				Material.WATER);

		for (Block waterBlock : nearbyWaterBlocks) {
			Levelled levelled = (Levelled) waterBlock.getBlockData();
			levelled.setLevel(0);
			waterBlock.setBlockData(levelled);
		}
	}
}
