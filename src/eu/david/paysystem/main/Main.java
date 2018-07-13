package eu.david.paysystem.main;

import commands.*;
import listener.JoinListener;
import listener.QuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new QuitListener(), this);

		this.getCommand("money").setExecutor(new MoneyCommand());

		System.out.println("Money-System Plugin was loaded sucessfully!");
	}

}