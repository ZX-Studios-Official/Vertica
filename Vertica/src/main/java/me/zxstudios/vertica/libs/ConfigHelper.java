package me.zxstudios.vertica.libs;

import me.zxstudios.vertica.Vertica;
import org.bukkit.entity.Player;

public class ConfigHelper {

    private final Vertica plugin;

    public ConfigHelper(Vertica plugin) {
        this.plugin = plugin;
    }

    public String get(String path) {

        String msg = plugin.getConfig().getString(path, "");
        msg = msg.replace("&", "§");

        return msg;
    }


    public String get(String path, Player player) {

        String msg = get(path);
        msg = msg.replace("%player%", player.getName());
        msg = msg.replace("%target%", player.getName());

        return msg;
    }

    public boolean getBoolean(String path) {
        return plugin.getConfig().getBoolean(path);
    }

}