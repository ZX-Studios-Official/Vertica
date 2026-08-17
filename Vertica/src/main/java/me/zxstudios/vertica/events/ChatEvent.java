package me.zxstudios.vertica.events;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import me.zxstudios.vertica.libs.*;


public class ChatEvent implements Listener {

    private final Vertica plugin;
    private final ConfigHelper config;
    private final SoundLib soundLib;

    public ChatEvent(Vertica plugin) {
        this.plugin = plugin;
        this.config = new ConfigHelper(plugin);
        this.soundLib = new SoundLib(config);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {

        if (!plugin.isMuted(event.getPlayer().getUniqueId())) {

            if (config.getBoolean("custom-chat-format")) {

                Player player = event.getPlayer();

                String chatFormatMsgTemp = config.get("chat-format");
                chatFormatMsgTemp = chatFormatMsgTemp.replace("%player%", player.getName());
                chatFormatMsgTemp = chatFormatMsgTemp.replace("%message%", event.getMessage());

                if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null) {

                    LuckPerms luckPerms = LuckPermsProvider.get();
                    PlayerAdapter<Player> adapter = luckPerms.getPlayerAdapter(Player.class);
                    User user = adapter.getUser(player);

                    String prefix = user.getCachedData().getMetaData().getPrefix();

                    if (prefix == null) {
                        prefix = "";
                    }

                    prefix = prefix.replace("&", "§");

                    chatFormatMsgTemp = chatFormatMsgTemp.replace("%prefix%", prefix);

                } else {

                    chatFormatMsgTemp = chatFormatMsgTemp.replace("%prefix%", "");

                }

                event.setFormat(chatFormatMsgTemp);

            }

        } else {

            Player player = event.getPlayer();

            event.setCancelled(true);
            player.sendActionBar(config.get("mute.muted"));
            soundLib.play(player, "mute.muted-sound");

        }
    }
}