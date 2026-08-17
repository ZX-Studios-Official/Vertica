package me.zxstudios.vertica.events;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerListReloadEvent implements Listener {

    private final ConfigHelper config;
    private final JavaPlugin plugin;

    public ServerListReloadEvent(Vertica plugin) {
        this.plugin = plugin;
        this.config = new ConfigHelper(plugin);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {

        if (config.getBoolean("MOTD.custom-MOTD") == true) {

            String motdL1 = config.get("MOTD.Line-1");
            String motdL2 = config.get("MOTD.Line-2");

            String motdFinal = motdL1 + "§r\n§r" + motdL2;

            event.setMotd(motdFinal);

        }
    }
}