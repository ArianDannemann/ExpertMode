package com.ExpertMode;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class Module implements Listener {
	
	protected Main main;
	
	public Module(Main main) {
		this.main = main;
		Bukkit.getServer().getPluginManager().registerEvents(this, main);
	}
	
}
