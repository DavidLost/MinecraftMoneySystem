package eu.david.paysystem.main;

import moneysystem.MoneyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        MoneyManager manager = new MoneyManager();

        if (player.hasPlayedBefore()) {
            event.setJoinMessage("§3"+player.getName()+"§a is now in da hood!");
            manager.updateName(player.getUniqueId().toString(), player.getName());
        }
        else {
            event.setJoinMessage("§2Welcome to the Server §3"+player.getName()+"!");
            manager.addMoney(player.getUniqueId().toString(), 10);
            player.sendMessage("§aA new Account was created for you!");
            int money = manager.getMoney(player.getUniqueId().toString());
            player.sendMessage("§aYour current balance is: §2"+money);
        }

    }

}