package commands;

import configfile.ConfigManager;
import eu.david.paysystem.main.VariousStuff;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ConfigManager manager = new ConfigManager();
        VariousStuff stuff = new VariousStuff();

        Player executor = (Player) sender;

        if (executor.isOp()) {
            if (args.length == 0) {
                boolean permission = manager.getFlyPermission(executor.getUniqueId().toString());
                permission = !permission;
                manager.setFlyPermission(executor.getUniqueId().toString(), permission);
                executor.setAllowFlight(permission);
                executor.setFlying(permission);
                String mode = getPermissionString(permission);
                executor.sendMessage("§aYour ability to fly is now "+mode);
            }
            else if (args.length == 1) {
                String playerName = args[0];
                Player player = Bukkit.getPlayer(playerName);
                String uuid;
                if (stuff.playerIsOnline(player)) {
                    uuid = player.getUniqueId().toString();
                }
                else {
                    uuid = stuff.getPlayerUUID(playerName);
                    if (uuid.equals("")) {
                        executor.sendMessage(stuff.getPlayerNotKnownSyntax(playerName));
                        return false;
                    }
                }
                boolean permission = manager.getFlyPermission(uuid);
                permission = !permission;
                manager.setFlyPermission(uuid, permission);
                String mode = getPermissionString(permission);
                if (stuff.playerIsOnline(player)) {
                    player.setAllowFlight(permission);
                    player.setFlying(permission);
                    player.sendMessage("§aYour permission to fly has changed! Its now "+mode);
                }
                executor.sendMessage("§aSucessfully changed fly permission for §3"+playerName+"§a Its now "+mode);
            }
            else {

            }
        }
        else {
            executor.sendMessage(stuff.getOPRequiredSyntax());
        }

        return false;
    }

    private String getPermissionString(boolean permission) {

        String mode;
        if (permission) {
            mode = "§2enabled!";
        }
        else {
            mode = "§4disabled!";
        }
        return mode;
    }
}
