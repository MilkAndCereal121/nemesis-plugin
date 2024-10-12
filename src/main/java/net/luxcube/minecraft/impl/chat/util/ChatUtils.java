package net.luxcube.minecraft.impl.chat.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatUtils {

  public static void sendPrivateMessage(
    @NotNull String requiredPermission,
    @NotNull String message
  ) {

    for (Player player : Bukkit.getOnlinePlayers()) {

      if (!player.hasPermission(requiredPermission)) {
        continue;
      }

      player.sendMessage(message);
    }

  }

}
