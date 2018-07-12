package eu.david.paysystem.main;

import commands.*;
import listener.JoinListener;
import listener.QuitListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new QuitListener(), this);

		this.getCommand("test").setExecutor(new TestCommand());
		this.getCommand("copyseed").setExecutor(new CopySeedCommand());
		this.getCommand("money").setExecutor(new MoneyCommand());
		this.getCommand("fly").setExecutor(new FlyCommand());
		this.getCommand("freeze").setExecutor(new FreezeCommand());

		System.out.println("Money-System Plugin was loaded sucessfully!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		System.out.println(command.getName()+" was executed by "+sender.getName());

		return false;
	}
}