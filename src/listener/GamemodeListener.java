package listener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GamemodeListener implements Listener {

    @EventHandler
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        System.out.println(command.getName()+" was executed by "+sender.getName());

        return false;
    }

}
