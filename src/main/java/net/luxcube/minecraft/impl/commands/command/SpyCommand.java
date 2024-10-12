package net.luxcube.minecraft.impl.commands.command;

import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.luxcube.minecraft.impl.commands.CommandsModule;
import net.luxcube.minecraft.util.Metadatas;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class SpyCommand {

  private final CommandsModule commandsModule;

  @Command(
    name = "spy",
    permission = "nemesis.commands.spy",
    target = CommandTarget.PLAYER
  )
  public void handleSpyCommand(
    @NotNull Context<Player> context
  ) {

    Player player = context.getSender();

    boolean playerIsSpying = commandsModule.isSpyEnabled(player);
    playerIsSpying = !playerIsSpying;

    commandsModule.setSpy(player, playerIsSpying);
    if (playerIsSpying) {
      context.sendMessage(
        commandsModule.getToggleOn()
      );
    } else {
      context.sendMessage(
        commandsModule.getToggleOff()
      );
    }

  }


}
