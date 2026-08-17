package me.zxstudios.vertica.events;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import me.zxstudios.vertica.libs.VersionChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.zxstudios.vertica.libs.*;

import static me.zxstudios.vertica.libs.TextFormatting.formatted;

public class JoinEvent implements Listener {

    private final Vertica plugin;
    private final ConfigHelper config;
    private final SoundLib soundLib;
    private final VersionChecker checker;

    public JoinEvent(Vertica plugin) {
        this.plugin = plugin;
        this.config = new ConfigHelper(plugin);
        this.soundLib = new SoundLib(config);
        this.checker = new VersionChecker(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (player.isOp()) {
            if (config.getBoolean("general.disable-version-check") == false) {
                if (config.getBoolean("traction.custom-join-leave-msg") == true) {

                    event.setJoinMessage(config.get("traction.join", player));
                    if (config.get("traction.leave-sound") == null) {
                        return;
                    }
                    soundLib.play(player, config.get("traction.join-sound"));

                }

                if (config.getBoolean("traction.first-join") == true) {
                    if (!player.hasPlayedBefore()) {
                        Bukkit.broadcastMessage(config.get("traction.first-join-msg-L1"));
                        Bukkit.broadcastMessage(config.get("traction.first-join-msg-L2"));
                        Bukkit.broadcastMessage(config.get("traction.first-join-msg-L3"));
                    }
                }

                player.sendMessage("");

                String current = plugin.getDescription().getVersion();
                String latest = checker.checkVersion();

                if (latest == null) {
                    player.sendMessage(formatted("<#FF75ED><bold>VERTICA</bold> <gray>»</gray> <red>Could not check updates."));
                }

                else if (current.equals(latest)) {
                    player.sendMessage(formatted(("<#FF75ED><bold>VERTICA</bold> <gray>»</gray> " + "<white>You currently have the newest version of </white><#FF75ED>Vertica</#FF75ED><white>!</white> " + "<gray>('1.0.0')</gray>")));
                }

                else {
                    player.sendMessage(formatted(("<#FF75ED><bold>VERTICA</bold> <gray>»</gray> " + "<white>Update is available:</white> " + "<red>" + current + "</red> <gray>»</gray> <green>" + latest + "</green>")));
                }

                player.sendMessage("");
            }
        }
    }
}