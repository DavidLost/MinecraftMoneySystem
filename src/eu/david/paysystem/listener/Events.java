package eu.david.paysystem.listener;

import eu.david.paysystem.configfile.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {

    public static Plugin plugin;

    public Events(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        player.sendMessage("money plugin");

        ConfigManager manager = new ConfigManager();

        if (!player.hasPlayedBefore()) {
            manager.addMoney(player.getUniqueId().toString(), 10);
            int money = manager.getMoney(player.getUniqueId().toString());
            player.sendMessage("§aA new Account with §2"+money+"§a was created for you!");
        }

        manager.updateName(player.getUniqueId().toString(), player.getName());
    }

}