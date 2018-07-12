package eu.david.paysystem.main;

import configfile.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {

    private ConfigManager manager = new ConfigManager();
    VariousStuff stuff = new VariousStuff();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        Player executor = (Player)commandSender;

        int index = 0;

        if (args.length == 0 || args.length == 1) {
            if (args.length == 0) {
                displayMoney(executor);
            }
            else {
                if (executor.isOp()) {
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
                    int amount = manager.getMoney(uuid);
                    executor.sendMessage("§3"+playerName+"§a currently has §2"+amount);
                }
                else {
                    executor.sendMessage(stuff.getOPRequiredSyntax());
                }
            }
            return false;
        }

        String moneyCommand = args[0].toLowerCase();

        if (moneyCommand.equals("set") || moneyCommand.equals("add")) {
            index = 1;
            if (executor.isOp()) {
                if (args.length == 2) {
                    int amount = Integer.parseInt(args[1]);
                    if (moneyCommand.equals("set")) {
                        setMoney(executor.getUniqueId().toString(), amount, false);
                        executor.sendMessage("§aSucessfully set your money to §2"+amount);
                    }
                    else {
                        addMoney(executor.getUniqueId().toString(), amount, false);
                        executor.sendMessage("§aSucessfully added §2"+amount+"§a to your account");
                        displayMoney(executor);
                    }
                }
                else if (args.length == 3 || args.length == 4) {
                    String playerName = args[1];
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
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        sendCommandSyntax(executor, index);
                        e.printStackTrace();
                        return false;
                    }
                    if (moneyCommand.equals("set")) {
                        if (amount < 0) {
                            executor.sendMessage("§cYou can't set a negative amount!");
                            return false;
                        }
                    }
                    else {
                        if (manager.getMoney(uuid)+amount < 0) {
                            executor.sendMessage("§cYou can't give this player bills!");
                            return false;
                        }
                    }
                    boolean notification = true;
                    if (args.length == 4) {
                        notification = Boolean.parseBoolean(args[3]);
                    }
                    if (moneyCommand.equals("set")) {
                        setMoney(uuid, amount, notification);
                        executor.sendMessage("§aSucessfully set money of §3"+playerName+"§a to §2"+amount);
                    }
                    else {
                        addMoney(uuid, amount, notification);
                        executor.sendMessage("§aSucessfully added §2"+amount+"§a to §3"+playerName);
                    }
                }
                else {
                    sendCommandSyntax(executor, index);
                }
            }
            else {
                executor.sendMessage(stuff.getOPRequiredSyntax());
            }

        }

        else if (moneyCommand.equals("pay")) {
            index = 2;
            if (args.length == 3) {
                String recieverName = args[1];
                Player reciever = Bukkit.getPlayer(recieverName);
                String uuid;
                if (stuff.playerIsOnline(reciever)) {
                    uuid = reciever.getUniqueId().toString();
                }
                else {
                    uuid = stuff.getPlayerUUID(recieverName);
                    if (uuid.equals("")) {
                        executor.sendMessage(stuff.getPlayerNotKnownSyntax(recieverName));
                        return false;
                    }
                }
                int amount = 0;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sendCommandSyntax(executor, index);
                    e.printStackTrace();
                    return false;
                }
                if (manager.getMoney(executor.getUniqueId().toString())-amount < 0) {
                    executor.sendMessage("§cYou don't have enough money!");
                    return false;
                }
                payMoney(executor.getUniqueId().toString(), executor.getName(), uuid, recieverName, amount);
            }
            else if (args.length == 4) {
                if (executor.isOp()) {
                    String senderName = args[1];
                    Player sender = Bukkit.getPlayer(senderName);
                    String senderUUID;
                    String recieverName = args[2];
                    Player reciever = Bukkit.getPlayer(recieverName);
                    String recieverUUID;
                    if (stuff.playerIsOnline(sender)) {
                        senderUUID = sender.getUniqueId().toString();
                    }
                    else {
                        senderUUID = stuff.getPlayerUUID(senderName);
                        if (senderUUID.equals("")) {
                            executor.sendMessage(stuff.getPlayerNotKnownSyntax(recieverName));
                            return false;
                        }
                    }
                    if (stuff.playerIsOnline(reciever)) {
                        recieverUUID = reciever.getUniqueId().toString();
                    }
                    else {
                        recieverUUID = stuff.getPlayerUUID(recieverName);
                        if (recieverUUID.equals("")) {
                            executor.sendMessage(stuff.getPlayerNotKnownSyntax(recieverName));
                            return false;
                        }
                    }
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        sendCommandSyntax(executor, index);
                        e.printStackTrace();
                        return false;
                    }
                    if (manager.getMoney(senderUUID)-amount < 0) {
                        executor.sendMessage(getPlayerNotEnoughMoneySyntax(senderName));
                        return false;
                    }
                    payMoney(senderUUID, senderName, recieverUUID, recieverName, amount);
                    executor.sendMessage("§aSucessfully forced §3"+senderName+"§a to pay §2"+amount+"§a to §3"+recieverName);
                }
                else {
                    executor.sendMessage(stuff.getOPRequiredSyntax());
                }
            }
            else {
                sendCommandSyntax(executor, index);
            }
        }
        else {
            sendCommandSyntax(executor, 3);
        }

        return false;
    }

    public void displayMoney(Player player) {

        int money = manager.getMoney(player.getUniqueId().toString());
        player.sendMessage("§aYour current balance is: §2"+money);
    }

    public void setMoney(String uuid, int amount, boolean notification) {

        Player player = Bukkit.getPlayer(uuid);
        manager.setMoney(uuid, amount);

        if (stuff.playerIsOnline(player) && notification) {
            player.sendMessage("§eYour money has been set to a new amount!");
            displayMoney(player);
        }
    }

    public void addMoney(String uuid, int amount, boolean notification) {

        Player player = Bukkit.getPlayer(uuid);
        manager.addMoney(uuid, amount);

        if (stuff.playerIsOnline(player) && notification) {
            player.sendMessage("§eYour money has been set to a new amount!");
            displayMoney(player);
        }
    }

    public void payMoney(String senderUUID, String senderName, String recieverUUID, String recieverName, int amount) {

        addMoney(senderUUID, -amount, false);
        addMoney(recieverUUID, amount, false);

        Player sender = Bukkit.getPlayer(senderName);
        if (stuff.playerIsOnline(sender)) {
            sender.sendMessage("§aYou sucessfully payed §2"+amount+"§a to §3"+recieverName);
            displayMoney(sender);
        }
        Player reciever = Bukkit.getPlayer(recieverName);
        if (stuff.playerIsOnline(reciever)) {
            reciever.sendMessage("§aYou recieved §2"+amount+"§a from §3"+senderName);
            displayMoney(reciever);
        }
    }



    private void sendCommandSyntax(Player player, int index) {

        String[] usages = {
                "([Player])",
                "set/add ([Player]) [Amount] ([notification])",
                "pay [Player1] ([Player2]) [Amount]",
        };

        if (index == usages.length) {
            player.sendMessage("§cSyntax:");
            for (String usage : usages) {
                player.sendMessage("§c/money "+usage);
            }
        }
        else {
            player.sendMessage("§cSyntax: /money "+usages[index]);
        }
    }

    private String getPlayerNotEnoughMoneySyntax(String player) {return "§cPlayer §3"+player+"§c has too little money!";}

}
