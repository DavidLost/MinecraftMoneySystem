package eu.david.paysystem.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import eu.david.paysystem.web.JsonReader;

public class PlayerUUID {

    private String apiURL;

    public PlayerUUID() {

       apiURL = "https://api.mojang.com/users/profiles/minecraft/";
    }

    public String getPlayerUUID(String name) throws NullPointerException {

        Player player = Bukkit.getPlayer(name);
        if (playerIsOnline(player)) {
            return player.getUniqueId().toString();
        }
        else {
            JsonReader jsonReader = new JsonReader();
            String uuid = "";
            uuid = modifyUUID(jsonReader.readJsonFromUrl(apiURL+name).get("id").toString());
            return uuid;
        }
    }

    private String modifyUUID(String uuid) {

        String newUUID = "";
        char[] chars = uuid.toCharArray();
        int[] missingChar = {8, 12, 16, 20};
        for (int i = 0; i < chars.length; i++) {
            for (int aMissingChar : missingChar) {
                if (i == aMissingChar) {
                    newUUID += "-";
                }
            }
            newUUID += chars[i];
        }
        return newUUID;
    }

    public boolean playerIsOnline(Player player) {

        try {
            if (player.isOnline()) {
                return true;
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getOPRequiredSyntax() {return "§cYou need to be opped to perform this command!";}

    public String getPlayerNotKnownSyntax(String player) {return "§cThe player §3"+player+"§c is unknown!";}

    public String getConsoleNotAllowedSyntax() {return "§cYou cant perform this command in the console!";}

}
