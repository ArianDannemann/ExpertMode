package com.ExpertMode.Modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.LocationHelper;
import com.PluginBase.TimeHelper;
import com.PluginBase.Effects.Darkness;

public class DarkerNight extends Module {

	public DarkerNight(Main main) {
		super(main);
		
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				checkIfPlayersShouldRecieveNightTimeDebuff();
			}
		};
		bukkitRunnable.runTaskTimer(main, 20, 5);
	}

	@SuppressWarnings("unused")
	public void checkIfPlayersShouldRecieveNightTimeDebuff() {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if
			(
					TimeHelper.getInstance().isNight(onlinePlayer.getWorld())
					&& !LocationHelper.getInstance().isLocationUnderBlocks(onlinePlayer.getLocation())
					&& !onlinePlayer.hasPotionEffect(PotionEffectType.BLINDNESS)
			) {
				new Darkness(this.main, onlinePlayer);
			}
		}
	}
}