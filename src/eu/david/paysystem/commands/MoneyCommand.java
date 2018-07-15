package eu.david.paysystem.commands;

import eu.david.paysystem.configfile.ConfigManager;
import eu.david.paysystem.main.PlayerUUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MoneyCommand implements CommandExecutor {

    private ConfigManager manager = new ConfigManager();
    private PlayerUUID stuff = new PlayerUUID();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        boolean consoleSender = commandSender instanceof ConsoleCommandSender;

        int index;

        if (args.length == 0 || args.length == 1) {
            if (args.length == 0) {
                if (consoleSender) {
                    commandSender.sendMessage(stuff.getConsoleNotAllowedSyntax());
                    return true;
                }
                displayMoney((Player)commandSender);
            }
            else {
                if (commandSender.isOp()) {
                    String playerName = args[0];
                    Player player = Bukkit.getPlayer(playerName);
                    String uuid;
                    if (stuff.playerIsOnline(player)) {
                        uuid = player.getUniqueId().toString();
                    }
                    else {
                        uuid = stuff.getPlayerUUID(playerName);
                        if (uuid.equals("")) {
                            commandSender.sendMessage(stuff.getPlayerNotKnownSyntax(playerName));
                            return true;
                        }
                    }
                    int amount = manager.getMoney(uuid);
                    commandSender.sendMessage("§3"+playerName+"§a currently has §2"+amount);
                }
                else {
                    commandSender.sendMessage(stuff.getOPRequiredSyntax());
                }
            }
            return true;
        }

        String moneyCommand = args[0].toLowerCase();

        switch (moneyCommand) {
            case "set":
            case "add":
                index = 1;
                if (commandSender.isOp()) {
                    if (args.length == 2) {
                        if (consoleSender) {
                            commandSender.sendMessage(stuff.getConsoleNotAllowedSyntax());
                            return true;
                        }
                        Player commandPlayer = (Player)commandSender;
                        int amount;
                        try {
                            amount = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            sendCommandSyntax(commandSender, index);
                            return true;
                        }
                        if (moneyCommand.equals("set")) {
                            setMoney(commandPlayer.getUniqueId().toString(), amount, false);
                            commandSender.sendMessage("§aSucessfully set your money to §2" + amount);
                        } else {
                            addMoney(commandPlayer.getUniqueId().toString(), amount, false);
                            commandPlayer.sendMessage("§aSucessfully added §2" + amount + "§a to your account");
                            displayMoney(commandPlayer);
                        }
                    } else if (args.length == 3 || args.length == 4) {
                        String playerName = args[1];
                        Player player = Bukkit.getPlayer(playerName);
                        String uuid;
                        if (stuff.playerIsOnline(player)) {
                            uuid = player.getUniqueId().toString();
                        } else {
                            uuid = stuff.getPlayerUUID(playerName);
                            if (uuid.equals("")) {
                                commandSender.sendMessage(stuff.getPlayerNotKnownSyntax(playerName));
                                return true;
                            }
                        }
                        int amount;
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            sendCommandSyntax(commandSender, index);
                            return true;
                        }
                        if (moneyCommand.equals("set")) {
                            if (amount < 0) {
                                commandSender.sendMessage("§cYou can't set a negative amount!");
                                return true;
                            }
                        } else {
                            if (manager.getMoney(uuid) + amount < 0) {
                                commandSender.sendMessage("§cYou can't give this player bills!");
                                return true;
                            }
                        }
                        boolean notification = true;
                        if (args.length == 4) {
                            notification = Boolean.parseBoolean(args[3]);
                        }
                        if (moneyCommand.equals("set")) {
                            setMoney(uuid, amount, notification);
                            commandSender.sendMessage("§aSucessfully set money of §3" + playerName + "§a to §2" + amount);
                        } else {
                            addMoney(uuid, amount, notification);
                            commandSender.sendMessage("§aSucessfully added §2" + amount + "§a to §3" + playerName);
                        }
                    } else {
                        sendCommandSyntax(commandSender, index);
                    }
                } else {
                    commandSender.sendMessage(stuff.getOPRequiredSyntax());
                }

                break;
            case "pay":
                if (consoleSender) {
                    commandSender.sendMessage(stuff.getConsoleNotAllowedSyntax());
                    return true;
                }
                index = 2;
                if (args.length == 3) {
                    Player commandPlayer = (Player)commandSender;
                    String recieverName = args[1];
                    Player reciever = Bukkit.getPlayer(recieverName);
                    String uuid;
                    if (stuff.playerIsOnline(reciever)) {
                        uuid = reciever.getUniqueId().toString();
                    } else {
                        uuid = stuff.getPlayerUUID(recieverName);
                        if (uuid.equals("")) {
                            commandPlayer.sendMessage(stuff.getPlayerNotKnownSyntax(recieverName));
                            return true;
                        }
                    }
                    int amount;
                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        sendCommandSyntax(commandPlayer, index);
                        return true;
                    }
                    if (manager.getMoney(commandPlayer.getUniqueId().toString()) - amount < 0) {
                        commandSender.sendMessage("§cYou don't have enough money!");
                        return true;
                    }
                    payMoney(commandPlayer.getUniqueId().toString(), commandPlayer.getName(), uuid, recieverName, amount);
                } else if (args.length == 4) {
                    if (commandSender.isOp()) {
                        String senderName = args[1];
                        Player sender = Bukkit.getPlayer(senderName);
                        String senderUUID;
                        String recieverName = args[2];
                        Player reciever = Bukkit.getPlayer(recieverName);
                        String recieverUUID;
                        if (stuff.playerIsOnline(sender)) {
                            senderUUID = sender.getUniqueId().toString();
                        } else {
                            senderUUID = stuff.getPlayerUUID(senderName);
                            if (senderUUID.equals("")) {
                                commandSender.sendMessage(stuff.getPlayerNotKnownSyntax(recieverName));
                                return true;
                            }
                        }
                        if (stuff.playerIsOnline(reciever)) {
                            recieverUUID = reciever.getUniqueId().toString();
                        } else {
                            recieverUUID = stuff.getPlayerUUID(recieverName);
                            if (recieverUUID.equals("")) {
                                commandSender.sendMessage(stuff.getPlayerNotKnownSyntax(recieverName));
                                return true;
                            }
                        }
                        int amount;
                        try {
                            amount = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            sendCommandSyntax(commandSender, index);
                            
                            return true;
                        }
                        if (manager.getMoney(senderUUID) - amount < 0) {
                            commandSender.sendMessage(getPlayerNotEnoughMoneySyntax(senderName));
                            return true;
                        }
                        payMoney(senderUUID, senderName, recieverUUID, recieverName, amount);
                        commandSender.sendMessage("§aSucessfully forced §3" + senderName + "§a to pay §2" + amount + "§a to §3" + recieverName);
                    } else {
                        commandSender.sendMessage(stuff.getOPRequiredSyntax());
                    }
                } else {
                    sendCommandSyntax(commandSender, index);
                }
                break;
            default:
                sendCommandSyntax(commandSender, 3);
                break;
        }

        return true;
    }

    private void displayMoney(Player player) {

        int money = manager.getMoney(player.getUniqueId().toString());
        player.sendMessage("§aYour current balance is: §2"+money);
    }

    private void setMoney(String uuid, int amount, boolean notification) {

        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        manager.setMoney(uuid, amount);

        if (stuff.playerIsOnline(player) && notification) {
            player.sendMessage("§eYour money has been set to a new amount!");
            displayMoney(player);
        }
    }

    private void addMoney(String uuid, int amount, boolean notification) {

        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        manager.addMoney(uuid, amount);

        if (stuff.playerIsOnline(player) && notification) {
            player.sendMessage("§eYour money has been set to a new amount!");
            displayMoney(player);
        }
    }

    private void payMoney(String senderUUID, String senderName, String recieverUUID, String recieverName, int amount) {

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



    private void sendCommandSyntax(CommandSender player, int index) {

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
