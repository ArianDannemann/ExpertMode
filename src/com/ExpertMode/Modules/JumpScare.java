package com.ExpertMode.Modules;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.ExpertMode.Main;
import com.ExpertMode.Module;
import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;
import com.PluginBase.SoundEmitter;
import com.PluginBase.TimeHelper;

public class JumpScare extends Module{

	public JumpScare(Main main) {
		super(main);
		
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				checkIfPlayersShouldRecieveNightTimeDebuff();
			}
		};
		bukkitRunnable.runTaskTimer(main, 20, 3600);
	}

	public void checkIfPlayersShouldRecieveNightTimeDebuff() {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if
			(
					TimeHelper.getInstance().isNight(onlinePlayer.getWorld())
					&& MathHelper.getInstance().hasChanceHit(20)
			) {
				SoundEmitter.getInstance().emitSound
				(
						onlinePlayer.getWorld(),
						LocationHelper.getInstance().offsetLocation(onlinePlayer.getLocation(), new Vector(3, 0, 3)),
						Sound.ENTITY_CREEPER_PRIMED,
						SoundCategory.HOSTILE,
						0.8f,
						1
				);
			}
		}
	}
}
