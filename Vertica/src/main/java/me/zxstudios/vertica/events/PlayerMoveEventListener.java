package me.zxstudios.vertica.events;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class PlayerMoveEventListener implements Listener {

    private final ConfigHelper config;
    private final Vertica plugin;

    public PlayerMoveEventListener(Vertica plugin) {
        this.plugin = plugin;
        this.config = new ConfigHelper(plugin);
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (plugin.isFrozen(player.getUniqueId())) {

            event.setCancelled(true);
            player.sendActionBar(config.get("freeze.frozen"));

        }
    }
    
}
