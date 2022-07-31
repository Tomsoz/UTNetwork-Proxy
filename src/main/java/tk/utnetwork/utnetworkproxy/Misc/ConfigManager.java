package tk.utnetwork.utnetworkproxy.Misc;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigManager {
    UTNetworkProxy plugin;
    public ConfigManager(UTNetworkProxy plugin) {
        this.plugin = plugin;
    }

    private Configuration config;

    public boolean initialise() {
        try {
            if (generateConfig()) {
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
            } else {
                return false;
            }
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean generateConfig() {
        try {
            if (!plugin.getDataFolder().exists()) {
                Utils.log("Generated configuration folder: " + plugin.getDataFolder().mkdir());
            }

            File configFile = new File(plugin.getDataFolder(), "config.yml");

            if (!configFile.exists()) {
                FileOutputStream outputStream = new FileOutputStream(configFile);
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.yml");
                in.transferTo(outputStream);
            }
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(plugin.getDataFolder(), "config.yml"));
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reloadConfig() {
        try {
            if (generateConfig()) {
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
            } else {
                return false;
            }
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Configuration getConfig() {
        return config;
    }
}
