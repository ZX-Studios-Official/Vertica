package me.zxstudios.vertica;

import me.zxstudios.vertica.commands.*;
import me.zxstudios.vertica.commands.freeze.FreezeCommand;
import me.zxstudios.vertica.commands.freeze.UnfreezeCommand;
import me.zxstudios.vertica.commands.mute.MuteCommand;
import me.zxstudios.vertica.commands.mute.UnmuteCommand;
import me.zxstudios.vertica.commands.spawn.SetSpawnCommand;
import me.zxstudios.vertica.commands.spawn.SpawnCommand;
import me.zxstudios.vertica.events.*;
import me.zxstudios.vertica.libs.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;


public final class Vertica extends JavaPlugin {

    private VersionChecker checker;
    private ConfigHelper config;
    private YMLHelper mutesFile;
    private YMLHelper freezeFile;
    private YMLHelper spawnFile;

    // -------------------------------------------------------

    public Location getSpawn() {
        return spawnFile.getConfig().getLocation("spawn");
    }

    public void setSpawn(Location location) {
        spawnFile.getConfig().set("spawn", location);
        spawnFile.save();
    }

    // -------------------------------------------------------

    public boolean isMuted(UUID uuid) {
        return mutesFile.getConfig().getStringList("muted").contains(uuid.toString());
    }

    public void mute(UUID uuid) {

        List<String> muted = mutesFile.getConfig().getStringList("muted");

        if (!muted.contains(uuid.toString())) {
            muted.add(uuid.toString());

            mutesFile.getConfig().set("muted", muted);
            mutesFile.save();
        }
    }

    public void unmute(UUID uuid) {

        List<String> muted = mutesFile.getConfig().getStringList("muted");

        muted.remove(uuid.toString());

        mutesFile.getConfig().set("muted", muted);
        mutesFile.save();
    }
    // -------------------------------------------------------

    public boolean isFrozen(UUID uuid) {
        return freezeFile.getConfig().getStringList("frozen").contains(uuid.toString());
    }

    public void freeze(UUID uuid) {

        List<String> frozen = freezeFile.getConfig().getStringList("frozen");

        if (!frozen.contains(uuid.toString())) {
            frozen.add(uuid.toString());

            freezeFile.getConfig().set("frozen", frozen);
            freezeFile.save();
        }
    }

    public void unFreeze(UUID uuid) {

        List<String> frozen = freezeFile.getConfig().getStringList("frozen");

        frozen.remove(uuid.toString());

        freezeFile.getConfig().set("frozen", frozen);
        freezeFile.save();
    }
    // -------------------------------------------------------

    @Override
    public void onEnable() {

        // YML init

        mutesFile = new YMLHelper(this, "mutes.yml");
        freezeFile = new YMLHelper(this, "freeze.yml");
        spawnFile = new YMLHelper(this, "spawn.yml");

        // -------------------------------------------------------

        // Bstats integration

        getLogger().info("Initializing Bstats...");

        if (config.getBoolean("general.disable-telemetry") == false) {

            Metrics metrics = new Metrics(this, 32552);

        }

        else {

            getLogger().warning("Bstats >> Telemetry Disabled manually.");

        }

        // -------------------------------------------------------
        getLogger().info("Initializing libs...");

        config = new ConfigHelper(this);
        checker = new VersionChecker(this);

        getLogger().info("Libs loaded.");
        // -------------------------------------------------------
        getLogger().info("Initializing Vertica...");
        // -------------------------------------------------------
        getLogger().info("Loading the configuration (config.yml)...");

        saveDefaultConfig();

        getLogger().info("Configuration loaded successfully.");
        // -------------------------------------------------------
        getLogger().info("Loading commands...");

        if (config.getBoolean("heal.enable") == true) {

            getCommand("heal").setExecutor(new HealCommand(this));

        }

        else {

            getLogger().warning("[config.yml] Heal command has been disabled manually.");

        }

        if (config.getBoolean("feed.enable") == true) {

            getCommand("feed").setExecutor(new FeedCommand(this));

        }

        else {

            getLogger().warning("[config.yml] Feed command has been disabled manually.");

        }

        if (config.getBoolean("fly.enable") == true) {

            getCommand("fly").setExecutor(new FlyCommand(this));

        }

        else {

            getLogger().warning("[config.yml] Fly command has been disabled manually.");

        }

        if (config.getBoolean("mute.enable") == true) {

            getCommand("mute").setExecutor(new MuteCommand(this));
            getCommand("unmute").setExecutor(new UnmuteCommand(this));

        }

        else {

            getLogger().warning("[config.yml] Mute and Unmute commands have been disabled manually.");

        }

        if (config.getBoolean("freeze.enable") == true) {

            getCommand("freeze").setExecutor(new FreezeCommand(this));
            getCommand("unfreeze").setExecutor(new UnfreezeCommand(this));

        }

        else {

            getLogger().warning("[config.yml] Freeze and Unfreeze commands have been disabled manually.");

        }

        if (config.getBoolean("spawn.enable") == true) {

            getCommand("spawn").setExecutor(new SpawnCommand(this));
            getCommand("setspawn").setExecutor(new SetSpawnCommand(this));

        }

        else {

            getLogger().warning("[config.yml] Spawn and Setspawn commands have been disabled manually.");

        }

        if (config.getBoolean("ping.enable") == true) {

            getCommand("ping").setExecutor(new PingCommand(this));

        }

        else {

            getLogger().warning("[config.yml] Ping command has been disabled manually.");

        }

        if (config.getBoolean("tps.enable") == true) {

            getCommand("tps").setExecutor(new TpsCommand(this));

        }

        else {

            getLogger().warning("[config.yml] TPS command has been disabled manually.");

        }


        // ALWAYS ENABLED!
        getCommand("vertica").setExecutor(new VerticaCommands(this));

        getLogger().info("Commands loaded.");
        // -------------------------------------------------------
        getLogger().info("Loading events...");

        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
        getServer().getPluginManager().registerEvents(new ServerListReloadEvent(this), this);

        getLogger().info("Events loaded.");
        // -------------------------------------------------------
        checker = new VersionChecker(this);

        getLogger().info("Checking for updates...");

        String currentVersion = getDescription().getVersion();
        String latest = checker.checkVersion();

        if (latest == null) {
            getLogger().warning("Could not check for updates.");
        }
        else if (currentVersion.equals(latest)) {
            getLogger().info("You are up to date.");
        }
        else {
            getLogger().info("Update available! Latest: " + latest);
        }

        getLogger().info("Vertica enabled successfully.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Goodbye!");
    }
}