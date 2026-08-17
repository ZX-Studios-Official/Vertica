package me.zxstudios.vertica.events;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import me.zxstudios.vertica.libs.VersionChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    private final Vertica plugin;
    private final ConfigHelper config;
    private final SoundLib soundLib;

    public LeaveEvent(Vertica plugin) {
        this.plugin = plugin;
        this.config = new ConfigHelper(plugin);
        this.soundLib = new SoundLib(config);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (config.getBoolean("traction.custom-join-leave-msg") == true) {

            event.setQuitMessage(config.get("traction.leave", player));
            if (config.get("traction.leave-sound") == null) {
                return;
            }
            soundLib.play(player, config.get("traction.join-sound"));
        }
    }
}
