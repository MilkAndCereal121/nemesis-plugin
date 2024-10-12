package net.luxcube.minecraft.impl.hider.listener;

import lombok.RequiredArgsConstructor;
import net.luxcube.minecraft.impl.hider.HiderModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HiderListener implements Listener {

  private final static String BYPASS_PERMISSION = "nemesis.hider.bypass";

  private final HiderModule hiderModule;

  @EventHandler
  private void onCommandSend(@NotNull PlayerCommandSendEvent event) {
    @NotNull Player player = event.getPlayer();

    if (player.hasPermission(BYPASS_PERMISSION)) {
      return;
    }

    List<String> toRemove = new ArrayList<>();
    for (String command : event.getCommands()) {

      if (hiderModule.isBlacklisted(command)) {
        toRemove.add(command);
      }
    }

    event.getCommands()
      .removeAll(toRemove);
  }

}
