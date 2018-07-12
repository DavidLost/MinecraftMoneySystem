package eu.david.paysystem.main;

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
        long seed = Bukkit.getWorld("world").getSeed();
        StringSelection stringSelection = new StringSelection(Long.toString(seed));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        player.sendMessage("§aSucessfully copied seed to the seed to the Clipboard: §f"+seed);

        return false;
    }
}