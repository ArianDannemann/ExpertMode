package com.ExpertMode.Modules;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.Chat;

public class Mimic extends Module {

	public Mimic(Main main) {
		super(main);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		// Check if the player right clicked a chest
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK
				|| event.getClickedBlock() == null
				|| event.getClickedBlock().getType() != Material.CHEST) {
			return;
		}
		
		// Get the player
		Player player = event.getPlayer();
		
		// Remove the chest
		event.getClickedBlock().setType(Material.AIR);
		
		Chat.getInstance().sendMessageToPlayer(player, ChatColor.RED + "This chest is trapped");
	}
}
