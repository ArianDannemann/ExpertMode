package com.ExpertMode;

import org.bukkit.plugin.java.JavaPlugin;

import com.ExpertMode.Modules.*;

public class Main extends JavaPlugin {
	
	// TODO: if one mob gets angry at the player, all nearby mobs do the same
	
	@SuppressWarnings("unused")
	@Override
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
		
		// Entity related modules
		new Enderman(this);
		new MagmaSlime(this);
		new Skeleton(this);
		new Zombie(this);
		new ZombiePigman(this);
		new Creeper(this);
		new Phantom(this);
		new Drowned(this);
	}
}
