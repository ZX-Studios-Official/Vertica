package me.zxstudios.vertica.commands;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PingCommand implements CommandExecutor {

    private final ConfigHelper config;
    private final SoundLib soundLib;
    private final JavaPlugin plugin;

    public PingCommand(Vertica plugin) {
        this.plugin = plugin;
        this.config = new ConfigHelper(plugin);
        this.soundLib = new SoundLib(config);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {

            plugin.getLogger().info(config.get("general.not-player"));
            return true;

        }

        Player player = (Player) sender;

        if (!(player.hasPermission(config.get("ping.permission")))) {

            player.sendActionBar(config.get("general.no-permission-message"));
            soundLib.play(player, "general.no-permission-sound");
            return true;

        }

        if (args.length <= 1) {

            player.sendActionBar(config.get("general.too-many-arguments"));
            soundLib.play(player, "general.too-many-arguments-sound");

        }

        player.sendActionBar(config.get("ping.message").replace("%ping%", String.valueOf(player.getPing())));
        soundLib.play(player, "ping.message-sound");



        return true;
    }

}
