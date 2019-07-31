package com.ExpertMode;

import org.bukkit.plugin.java.JavaPlugin;

import com.ExpertMode.Modules.*;

public class Main extends JavaPlugin {
	
	// TODO: Make magma slimes stop spawning when there are to many
	// TODO: Dont spawn blood moon enemies on spectators and creatives
	
	public void onEnable() {
		new DifficultySetter(this);
		new ElytraBlocker(this);
		new DamageAmplifier(this);
		new Zombifier(this);
		new MobManipulator(this);
		new BowTipper(this);
		new RegenerationBlocker(this);
		//new PlayerBanner(this);
		new PlayerDeath(this);
		new BloodMoon(this);
		new Enderman(this);
		//new Mimic(this);
	}
}
