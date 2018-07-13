package listener;

import configfile.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        ConfigManager manager = new ConfigManager();

        if (player.hasPlayedBefore()) {
            event.setJoinMessage("§3"+player.getName()+"§a is now in da hood!");
        }
        else {
            event.setJoinMessage("§2Welcome to the Server §3"+player.getName()+"!");
            manager.addMoney(player.getUniqueId().toString(), player.getName(), 10);
            int money = manager.getMoney(player.getUniqueId().toString(), player.getName());
            player.sendMessage("§aA new Account with §2"+money+"§a was created for you!");
        }

        manager.updateName(player.getUniqueId().toString(), player.getName());
    }

}