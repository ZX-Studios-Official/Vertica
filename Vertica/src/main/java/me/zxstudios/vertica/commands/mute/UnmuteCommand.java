package me.zxstudios.vertica.commands.mute;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand implements CommandExecutor {

    private final Vertica plugin;
    private final ConfigHelper config;
    private final SoundLib soundLib;

    public UnmuteCommand(Vertica plugin) {
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

        Player target = Bukkit.getPlayer(args[0]);

        if (!player.hasPermission(config.get("unmute-permission"))) {

            player.sendMessage(config.get("general.no-permission"));
            soundLib.play(player, config.get("general.no-permission-sound"));
            return true;

        }

        if (!target.isOnline()) {

            player.sendActionBar(config.get("general.player-doesnt-exist"));
            soundLib.play(player, "general.player-doesnt-exist-sound");
            return true;

        }

        if (args.length == 0) {

            player.sendMessage(config.get("general.no-arguments"));
            soundLib.play(player, "general.no-arguments-sound");

        }

        if (args.length == 1) {

            if (plugin.isMuted(target.getUniqueId()) == true) {

                player.sendActionBar(config.get("mute.player-not-muted"));
                soundLib.play(player, "mute.player-not-muted-sound");

            }

            plugin.unmute(target.getUniqueId());
            player.sendActionBar(config.get("mute.unmute-success").replace("%target%", target.getName()));
            soundLib.play(player, "mute.unmute-success-sound");

            target.sendActionBar(config.get("mute.unmute-success-target").replace("%player%", player.getName()));
            soundLib.play(target, "mute.unmute-success-target-sound");
        }

        if (args.length > 1) {

            player.sendActionBar(config.get("general.too-many-arguments"));
            soundLib.play(player, "general.too-many-arguments-sound");

        }

        return true;
    }

}
