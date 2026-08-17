package me.zxstudios.vertica.commands.spawn;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final ConfigHelper config;
    private final SoundLib soundLib;
    private final Vertica plugin;

    public SetSpawnCommand(Vertica plugin) {
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

        if (!(player.hasPermission(config.get("setspawn.permission")))) {

            player.sendActionBar(config.get("general.no-permission-message"));
            soundLib.play(player, "general.no-permission-sound");
            return true;

        }

        plugin.setSpawn(player.getLocation());
        player.sendActionBar(config.get("setspawn.setting"));
        soundLib.play(player, config.get("setspawn.setting-sound"));

        return true;
    }

}
