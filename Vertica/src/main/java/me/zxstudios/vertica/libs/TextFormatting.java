package me.zxstudios.vertica.libs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TextFormatting {

    public static Component formatted(String text) {
        return MiniMessage.miniMessage().deserialize(text);
    }

}