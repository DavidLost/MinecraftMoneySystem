package moneysystem;

import eu.david.paysystem.main.MoneyCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import web.JsonReader;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MoneyManager {

    public File balancesFile;
    private FileConfiguration cfg;

    public MoneyManager() {

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
        try {
            cfg.save(balancesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMoney(String uuid, int amount) {

        int currentMoney = getMoney(uuid);
        setMoney(uuid, currentMoney+amount);
    }

    public void updateName(String uuid, String name) {

        cfg.set(uuid+".name", name);
        try {
            cfg.save(balancesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
