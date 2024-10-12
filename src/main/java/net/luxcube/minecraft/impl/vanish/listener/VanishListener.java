package net.luxcube.minecraft.impl.vanish.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static net.luxcube.minecraft.impl.vanish.util.VanishUtils.canSeeVanish;
import static net.luxcube.minecraft.impl.vanish.util.VanishUtils.isVanish;

@RequiredArgsConstructor
public class VanishListener implements Listener {

  private final Plugin plugin;

  @EventHandler
  private void onJoin(@NotNull PlayerJoinEvent event) {
    Player player = event.getPlayer();

    if (canSeeVanish(player)) {
      return;
    }

    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (!isVanish(onlinePlayer)) {
        continue;
      }

      player.hidePlayer(
        plugin,
        onlinePlayer
      );
    }
  }

}
