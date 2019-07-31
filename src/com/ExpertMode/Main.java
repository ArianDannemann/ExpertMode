package com.ExpertMode;

import org.bukkit.plugin.java.JavaPlugin;

import com.ExpertMode.Modules.*;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		// General settings
		new DifficultySetter(this);
		new DamageAmplifier(this);

		// Mechanics
		new ElytraBlocker(this);
		new RegenerationBlocker(this);
		new PlayerDeath(this);
		
		// Events
		new BloodMoon(this);
		
		new MobManipulator(this);
		
		// Entity related modules
		new Enderman(this);
		new MagmaSlime(this);
		new Skeleton(this);
		new Zombie(this);
		new ZombiePigman(this);
	}
}
