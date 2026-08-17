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

public class FlyCommand implements CommandExecutor {

    private final ConfigHelper config;
    private final SoundLib soundLib;
    private final JavaPlugin plugin;

    public FlyCommand(Vertica plugin) {
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

        if (!(player.hasPermission(config.get("fly.permission")))) {

            player.sendActionBar(config.get("general.no-permission-message"));
            soundLib.play(player, "general.no-permission-sound");
            return true;

        }

        if (args.length == 0) {

            if (player.getAllowFlight() == false) {

                player.setAllowFlight(true);
                player.sendActionBar(config.get("fly.enabled-player"));
                soundLib.play(player, "fly.enabled-player-sound");
                return true;

            }

            player.setAllowFlight(false);
            player.sendActionBar(config.get("fly.disabled-player"));
            soundLib.play(player, "fly.disabled-player-sound");
            return true;

        }

        if (args.length > 1) {

            player.sendActionBar(config.get("general.too-many-arguments"));
            soundLib.play(player, "general.too-many-arguments-sound");
            return true;

        }

        Player target = Bukkit.getPlayer(args[0]);

        if  (target == null) {

            player.sendActionBar(config.get("general.player-donesnt-exist"));
            soundLib.play(player, "general.player-doesnt-exist-sound");
            return true;

        }

        if (target.getAllowFlight() == false) {

            target.setAllowFlight(true);
            target.sendActionBar(config.get("fly.enabled-target", player));
            target.sendActionBar(config.get("fly.enabled-target-player", target));
            soundLib.play(player, "fly.enabled-target-sound");
            soundLib.play(target, "fly.enabled-target-sound");

        }

        else {

            target.setAllowFlight(false);
            target.sendActionBar(config.get("fly.disabled-target", player));
            target.sendActionBar(config.get("fly.disabled-target-player", target));
            soundLib.play(player, "fly.disabled-target-sound");
            soundLib.play(target, "fly.disabled-target-sound");

        }

        return true;
    }

}
