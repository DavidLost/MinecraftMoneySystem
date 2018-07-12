package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CopySeedCommand implements CommandExecutor {

    @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player)sender;
        long seed;
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "overworld": seed = Bukkit.getWorld("world").getSeed();
                break;
                case "nether": seed = Bukkit.getWorld("world_nether").getSeed();
                break;
                case "end": seed = Bukkit.getWorld("world_the_end").getSeed();
                break;
                default: player.sendMessage(getCommandSyntax()); return false;
            }
        }
        else {
            player.sendMessage(getCommandSyntax());
            return false;
        }
        StringSelection stringSelection = new StringSelection(Long.toString(seed));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        player.sendMessage("§aSucessfully copied "+args[0]+"-seed to the seed to the Clipboard: §f"+seed);

        return false;
    }

    private String getCommandSyntax() {return "§cSyntax: /copyseed [worldname] (overworld, nether, end)";}
}