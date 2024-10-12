package net.luxcube.minecraft.impl.vanish.util;

import net.luxcube.minecraft.util.Metadatas;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class VanishUtils {

  private final static String VANISH_KEY = "nemesis_vanish",
    VANISH_BYPASS_PERMISSION = "nemesis.vanish.bypass";

  public static void vanish(
    @NotNull Plugin plugin,
    @NotNull Player player
  ) {
    Metadatas.setMetadata(
      player,
      VANISH_KEY,
      "true"
    );

    for (@NotNull Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (canSeeVanish(onlinePlayer)) {
        continue;
      }

      if (player.getUniqueId().equals(onlinePlayer.getUniqueId())) {
        continue;
      }

      onlinePlayer.hidePlayer(plugin, player);
    }
  }

  public static void unVanish(
    @NotNull Plugin plugin,
    @NotNull Player player
  ) {
    Metadatas.removeMetadata(
      player,
      VANISH_KEY
    );

    for (@NotNull Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (onlinePlayer.canSee(player)) {
        continue;
      }

      onlinePlayer.showPlayer(plugin, player);
    }
  }

  public static boolean isVanish(@NotNull Player player) {
    return Metadatas.getMetadata(
      player,
      VANISH_KEY
    ) != null;
  }

  public static boolean canSeeVanish(@NotNull Player player) {
    return player.hasPermission(VANISH_BYPASS_PERMISSION);
  }

}
