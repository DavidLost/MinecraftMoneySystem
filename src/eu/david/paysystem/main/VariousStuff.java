package eu.david.paysystem.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import web.JsonReader;

public class VariousStuff {

    public String apiURL = "https://api.mojang.com/users/profiles/minecraft/";

    public String getPlayerUUID(String name) {

        Player player = Bukkit.getPlayer(name);
        if (playerIsOnline(player)) {
            System.out.println("online");
            return player.getUniqueId().toString();
        }
        else {
            System.out.println("offline");
            JsonReader jsonReader = new JsonReader();
            String uuid = "";
            try {
                uuid = modifyUUID(jsonReader.readJsonFromUrl(apiURL+name).get("id").toString());
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
            System.out.println("uuid: "+uuid);
            return uuid;
        }
    }

    public String modifyUUID(String uuid) {

        String newUUID = "";
        char[] chars = uuid.toCharArray();
        int[] missingChar = {8, 12, 16, 20};
        for (int counter = 0; counter < chars.length; counter++) {
            for (int i = 0; i < missingChar.length; i++) {
                if (counter == missingChar[i]) {
                    newUUID += "-";
                }
            }
            newUUID += chars[counter];
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

}
