package com.ExpertMode;

import org.bukkit.plugin.java.JavaPlugin;

import com.ExpertMode.Modules.*;
import com.PluginBase.VersionChecker;

public class Main extends JavaPlugin {
	
	/*
	 * Make wolfs randomly attack player
	 * Make skeleton switch to sword when in close combat
	 */
	
	@SuppressWarnings("unused")
	@Override
	public void onEnable() {
		
		// Check if we are using the correct plugin base version
		VersionChecker.getInstance().checkVersion(this, "1.2");
		
		// General settings
		new DifficultySetter(this);
		new DamageAmplifier(this);
		new PlayerHurt(this);

		// Mechanics
		new ElytraBlocker(this);
		new RegenerationBlocker(this);
		new PlayerDeath(this);
		
		// Events
		new BloodMoon(this);
		
		// Entity related modules
		new Enderman(this);
		new MagmaSlime(this);
		//new Skeleton(this);
		new Zombie(this);
		new ZombiePigman(this);
		new Creeper(this);
		new Phantom(this);
		new Drowned(this);
		new Wolf(this);
		
		// Mixed stuff to mess with the player
		new DarkerNight(this);
		new JumpScare(this);
	}
}
