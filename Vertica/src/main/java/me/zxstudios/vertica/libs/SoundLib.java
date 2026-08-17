package me.zxstudios.vertica.libs;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;

public class SoundLib {

    private final ConfigHelper config;

    public SoundLib(ConfigHelper config) {
        this.config = config;
    }

    public void play(Player player, String path) {
        play(player, path, 1f, 1f);
    }

    public void play(Player player, String path, float volume, float pitch) {

        String soundId = config.get(path);

        if (soundId == null) return;

        Sound sound = Sound.sound(
                Key.key(soundId),
                Sound.Source.MASTER,
                volume,
                pitch
        );

        player.playSound(sound);
    }
}