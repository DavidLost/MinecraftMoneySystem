package eu.david.paysystem.main;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new QuitListener(), this);

		this.getCommand("test").setExecutor(new TestCommand());
		this.getCommand("copyseed").setExecutor(new CopySeedCommand());
		this.getCommand("money").setExecutor(new MoneyCommand());

		System.out.println("Money-System Plugin was loaded sucessfully!");
	}
	
}