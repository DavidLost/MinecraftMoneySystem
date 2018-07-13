package configfile;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private File balancesFile;
    private FileConfiguration cfg;

    public ConfigManager() {

        balancesFile = new File("playerdata.yml");
        if (!balancesFile.exists()) {
            try {
                balancesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cfg = YamlConfiguration.loadConfiguration(balancesFile);
    }

    public int getMoney(String uuid, String name) {

        int money = cfg.getInt(uuid+".money");
        saveFile();
        return money;
    }

    public void setMoney(String uuid, String name, int amount) {

        cfg.set(uuid+".money", amount);
        saveFile();
    }

    public void addMoney(String uuid, String name, int amount) {

        int currentMoney = getMoney(uuid, name);
        setMoney(uuid, name, currentMoney+amount);
    }

    public void updateName(String uuid, String name) {

        cfg.set(uuid+".name", name);
    }

    private void saveFile() {

        try {
            cfg.save(balancesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}