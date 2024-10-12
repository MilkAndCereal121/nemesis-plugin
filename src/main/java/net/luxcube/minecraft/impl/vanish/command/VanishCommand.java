package net.luxcube.minecraft.impl.vanish.command;

import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static net.luxcube.minecraft.impl.vanish.util.VanishUtils.*;
import static net.luxcube.minecraft.util.Colors.translate;

@RequiredArgsConstructor
public class VanishCommand {

  private final static String VANISH_PERMISSION = "nemesis.vanish";
  private final static String VANISH_OTHER_PERMISSION = "nemesis.vanish.other";
  
  private final Plugin plugin;

  @Command(
    name = "vanish",
    permission = VANISH_PERMISSION
  )
  public void handleVanishCommand(
    @NotNull Context<CommandSender> context,
    @Optional Player target
  ) {
    CommandSender commandSender = context.getSender();

    Player player = null;

    if (target != null) {

      if (!commandSender.hasPermission(VANISH_OTHER_PERMISSION)) {
        commandSender.sendMessage(
          translate("&cYou do not have permission to vanish others.")
        );
        return;
      }

      boolean vanish = isVanish(target);

      String message = vanish
        ? "&a%s is no longer in vanish!"
        : "&a%s is now in vanish!";

      commandSender.sendMessage(
        translate(
          message.formatted(target.getName())
        )
      );

      player = target;
    }

    if (player == null && (!(commandSender instanceof Player))) {
      commandSender.sendMessage(
        translate("&c/vanish <player>")
      );
      return;
    } else {
      player = (Player) commandSender;
    }

    boolean vanish = isVanish(player);
    // Un vanish player
    if (vanish) {
      unVanish(
        plugin,
        player
      );
    } else {
      vanish(
        plugin,
        player
      );
    }
    
    String message = vanish
      ? translate("&aYou're no longer in vanish!")
      : translate("&aYou're now in vanish!");
    
    player.sendMessage(message);
  }

}
