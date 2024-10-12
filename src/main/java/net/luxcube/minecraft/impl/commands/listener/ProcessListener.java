package net.luxcube.minecraft.impl.commands.listener;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.luxcube.minecraft.impl.commands.CommandsModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ProcessListener implements Listener {

  private final CommandsModule commandsModule;

  @EventHandler
  private void onCommand(
    @NotNull PlayerCommandPreprocessEvent event
  ) {

    // Checking if the feature of spy is enabled in config.
    boolean spyEnabled = commandsModule.isSpyEnabled();
    if (!spyEnabled) {
      return;
    }

    Player player = event.getPlayer();

    String message = commandsModule.getFormatMessage()
      .replace("%player%", player.getName())
      .replace("%command%", event.getMessage());

    for (@NotNull Player onlineUser : Bukkit.getOnlinePlayers()) {
      if (!onlineUser.hasPermission("nemesis.commands.spy")) {
        continue;
      }

      if (!commandsModule.isSpyEnabled(onlineUser)) {
        continue;
      }

      onlineUser.sendMessage(
        Component.text(message)
      );
    }

  }

}
