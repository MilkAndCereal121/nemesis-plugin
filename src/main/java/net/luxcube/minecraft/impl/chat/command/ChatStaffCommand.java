package net.luxcube.minecraft.impl.chat.command;

import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.luxcube.minecraft.NemesisPlugin;
import net.luxcube.minecraft.entity.NemesisPlayer;
import net.luxcube.minecraft.impl.chat.ChatModule;
import net.luxcube.minecraft.impl.chat.util.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

import static net.luxcube.minecraft.impl.chat.ChatModule.CHAT_STAFF_PERMISSION;
import static net.luxcube.minecraft.util.Colors.translate;

@RequiredArgsConstructor
public class ChatStaffCommand {

  private final NemesisPlugin nemesisPlugin;
  private final ChatModule chatModule;

  @Command(
    name = "chat.staff",
    permission = CHAT_STAFF_PERMISSION,
    target = CommandTarget.PLAYER
  )
  public void handleChatStaffCommand(
    @NotNull Context<Player> context
  ) {
    Player player = context.getSender();

    @Nullable NemesisPlayer nemesisPlayer = nemesisPlugin.getNemesisPlayerRepository()
      .find(player.getUniqueId());

    if (nemesisPlayer == null) {
      Logger logger = nemesisPlugin.getLogger();

      logger.severe("Nemesis player could not be found for %s in chat!".formatted(player.getName()));

      return;
    }

    toggleStaffChat(
      nemesisPlayer,
      player
    );
  }

  @Command(
    name = "staffchat",
    permission = CHAT_STAFF_PERMISSION,
    target = CommandTarget.ALL
  )
  public void handleStaffChatCommand(
    @NotNull Context<CommandSender> context,
    @Optional String... args
  ) {
    CommandSender commandSender = context.getSender();

    if (args == null || args.length == 0) {

      if (commandSender instanceof Player player) {
        @Nullable NemesisPlayer nemesisPlayer = nemesisPlugin.getNemesisPlayerRepository()
          .find(player.getUniqueId());

        if (nemesisPlayer == null) {
          Logger logger = nemesisPlugin.getLogger();

          logger.severe("Nemesis player could not be found for %s in chat!".formatted(player.getName()));

          return;
        }

        toggleStaffChat(
          nemesisPlayer,
          player
        );

        return;
      }

      return;
    }

    String message = chatModule.getStaffChatFormat(
      commandSender,
      String.join(" ", args)
    );

    ChatUtils.sendPrivateMessage(
      CHAT_STAFF_PERMISSION,
      message
    );

  }

  private void toggleStaffChat(
    @NotNull NemesisPlayer nemesisPlayer,
    @NotNull Player player
  ) {

    boolean enabled = nemesisPlayer.isStaffChatEnabled();

    String message = enabled
      ? translate("&aYou have disabled staff chat!")
      : translate("&aYou have enabled staff chat!");

    player.sendMessage(
      message
    );

    nemesisPlayer.setStaffChatEnabled(
      !nemesisPlayer.isStaffChatEnabled()
    );

  }


}
