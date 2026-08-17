package me.zxstudios.vertica.commands;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.zxstudios.vertica.libs.TextFormatting.formatted;

public class VerticaCommands implements CommandExecutor, TabCompleter {

    private final Vertica plugin;
    private final ConfigHelper config;
    private final SoundLib soundLib;
    private final VersionChecker checker;

    public VerticaCommands(Vertica plugin) {
        this.plugin = plugin;
        this.config = new ConfigHelper(plugin);
        this.soundLib = new SoundLib(config);
        this.checker = new VersionChecker(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            plugin.getLogger().info(config.get("general.not-player"));
            return true;
        }

        Player player = (Player) sender;


        if (args.length == 0) {

            player.sendMessage("§r------------- §d§lVertica Help §r-------------");
            player.sendMessage("§7- §r/heal §7» §rHeals the player.*");
            player.sendMessage("§7- §r/feed §7» §rFeeds the player.*");
            player.sendMessage("§7- §r/fly §7» §rGives the player the ability to fly.*");
            player.sendMessage("§7- §rCustom MOTD §7» §rSet a custom MOTD for your server!");
            player.sendMessage("§7- §rCustom Join & Leave Messages §7» §rSet a custom Join & Leave Messages for your server!");
            player.sendMessage("§7§o* Or target if specified");
            player.sendMessage("§r----------------------------------------");

        }

        if (args.length > 1) {
            player.sendActionBar(config.get("general.too-many-arguments"));
            soundLib.play(player, "general.too-many-arguments-sound");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        // -------------------------------------------------------
        // reload
        if (subCommand.equals("reload")) {

            if (!player.hasPermission(config.get("general.reload.permission"))) {
                player.sendActionBar(config.get("general.no-permission-message"));
                soundLib.play(player, "general.no-permission-sound");
                return true;
            }

            plugin.reloadConfig();

            player.sendActionBar(config.get("general.reload.success"));
            soundLib.play(player, "general.reload.success-sound");

            return true;
        }

        // -------------------------------------------------------
        // update
        if (subCommand.equals("update")) {

            if (!player.hasPermission(config.get("general.update.permission"))) {
                player.sendActionBar(config.get("general.no-permission-message"));
                soundLib.play(player, "general.no-permission-sound");
                return true;
            }

            Bukkit.getScheduler().runTaskLater(plugin, () -> {

                player.sendMessage("");

                String current = plugin.getDescription().getVersion();
                String latest = checker.checkVersion();

                if (latest == null) {
                    player.sendMessage(formatted("<#FF75ED><bold>VERTICA</bold> <gray>»</gray> <red>Could not check updates."));
                } else if (current.equals(latest)) {
                    player.sendMessage(formatted("<#FF75ED><bold>VERTICA</bold> <gray>»</gray> " + "<white>You currently have the newest version of </white><#FF75ED>Vertica</#FF75ED><white>!</white> " + "<gray>('1.0.0')</gray>"));
                } else {
                    player.sendMessage(formatted("<#FF75ED><bold>VERTICA</bold> <gray>»</gray> " + "<white>Update is available:</white> " + "<red>" + current + "</red> <gray>»</gray> <green>" + latest + "</green>"));
                }


                player.sendMessage("");
            }, 20L * 2);

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        String subCommand = args[0].toLowerCase();

        List<String> list = new ArrayList<>();

        if (args.length == 1) {

            if ("reload".startsWith(subCommand)) {
                list.add("reload");
            }

            if ("update".startsWith(subCommand)) {
                list.add("update");
            }

        }

        return list;
    }


}