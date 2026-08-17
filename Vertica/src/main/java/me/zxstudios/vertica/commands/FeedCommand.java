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

public class FeedCommand implements CommandExecutor {

    private final ConfigHelper config;
    private final SoundLib soundLib;
    private final JavaPlugin plugin;

    public FeedCommand(Vertica plugin) {
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

        if (!(player.hasPermission(config.get("feed.permission")))) {

            player.sendActionBar(config.get("general.no-permission-message"));
            soundLib.play(player, "general.no-permission-sound");
            return true;

        }

        if (args.length == 0) {

            player.setFoodLevel(20);
            player.setSaturation(20);
            player.sendActionBar(config.get("feed.success-player"));
            soundLib.play(player, "feed.success-player-sound");
            return true;

        }

        if (args.length > 1) {

            player.sendActionBar(config.get("general.too-many-arguments"));
            soundLib.play(player, "general.too-many-arguments-sound");
            return true;

        }

        Player target = Bukkit.getPlayer(args[0]);

        if  (target == null) {

            player.sendActionBar(config.get("general.player-doesnt-exist"));
            soundLib.play(player, "general.player-doesnt-exist-sound");
            return true;

        }


        target.setFoodLevel(20);
        target.setSaturation(20);
        target.sendActionBar(config.get("feed.success-target", player));
        target.sendActionBar(config.get("feed.success-target-player", target));
        soundLib.play(player, "feed.success-target-sound");
        soundLib.play(target, "feed.success-target-sound");



        return true;
    }

}
