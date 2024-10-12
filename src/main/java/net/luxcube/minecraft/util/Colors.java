package net.luxcube.minecraft.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class Colors {

  public static String translate(@NotNull String color) {
    return ChatColor.translateAlternateColorCodes('&', color);
  }

}
