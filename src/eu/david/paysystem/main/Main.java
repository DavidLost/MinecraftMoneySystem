package eu.david.paysystem.main;

import commands.*;
import listener.GamemodeListener;
import listener.JoinListener;
import listener.QuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new QuitListener(), this);
		this.getServer().getPluginManager().registerEvents(new GamemodeListener(), this);

		this.getCommand("test").setExecutor(new TestCommand());
		this.getCommand("copyseed").setExecutor(new CopySeedCommand());
		this.getCommand("money").setExecutor(new MoneyCommand());
		this.getCommand("fly").setExecutor(new FlyCommand());
		this.getCommand("freeze").setExecutor(new FreezeCommand());

		System.out.println("Money-System Plugin was loaded sucessfully!");
	}
	
}