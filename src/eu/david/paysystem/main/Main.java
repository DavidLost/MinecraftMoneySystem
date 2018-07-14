package eu.david.paysystem.main;

import eu.david.paysystem.commands.MoneyCommand;
import eu.david.paysystem.listener.Events;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(new Events(this), this);

		this.getCommand("money").setExecutor(new MoneyCommand());

		System.out.println("Money-System Plugin was loaded sucessfully!");
		System.out.println(this.getName());
	}

}