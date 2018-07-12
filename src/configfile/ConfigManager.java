package configfile;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public File balancesFile;
    private FileConfiguration cfg;

    public ConfigManager() {

        balancesFile = new File("balances.yml");
        if (!balancesFile.exists()) {
            try {
                balancesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cfg = YamlConfiguration.loadConfiguration(balancesFile);
    }

    public int getMoney(String uuid) {

        int money = cfg.getInt(uuid+".money");
        return money;
    }

    public void setMoney(String uuid, int amount) {

        cfg.set(uuid+".money", amount);
        saveFile();
    }

    public void addMoney(String uuid, int amount) {

        int currentMoney = getMoney(uuid);
        setMoney(uuid, currentMoney+amount);
    }

    public void updateName(String uuid, String name) {

        cfg.set(uuid+".name", name);
        saveFile();
    }

    public boolean getFlyPermission(String uuid) {

        return cfg.getBoolean(uuid+".flyPermission");
    }

    public void setFlyPermission(String uuid, boolean permission) {

        cfg.set(uuid+".flyPermission", permission);
        saveFile();
    }

    public void saveFile() {

        try {
            cfg.save(balancesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
