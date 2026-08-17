package me.zxstudios.vertica.libs;


import me.zxstudios.vertica.Vertica;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YMLHelper {

    private final Vertica plugin;
    private final String fileName;
    private File file;
    private FileConfiguration config;


    public YMLHelper(Vertica plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;


        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {

            try {

                file.createNewFile();

            }

            catch (IOException e) {
                e.printStackTrace();

            }

        }

        config = YamlConfiguration.loadConfiguration(file);

    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
