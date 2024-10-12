package net.luxcube.minecraft.impl.chat.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static net.luxcube.minecraft.util.Colors.translate;

public class ChatClearCommand {

    private final static String CHAT_MUTE_PERMISSION = "nemesis.staff.chat.clear";
    private final static int LOOP_TIMES = 200;

    @Command(
      name = "chat.clear",
      target = CommandTarget.ALL,
      permission = CHAT_MUTE_PERMISSION
    )
    public void handleChatClearCommand(
      @NotNull Context<CommandSender> context
    ) {
        CommandSender commandSender = context.getSender();

        for (int i=0; i<LOOP_TIMES; i++) {
            Bukkit.broadcast(Component.text(""));
        }

        String message = translate(
          "&aChat has been cleared by %s."
            .formatted(commandSender.getName())
        );

        Bukkit.broadcast(
          Component.text(message)
        );

    }


    @Command(
      name = "clearchat",
      target = CommandTarget.ALL,
      permission = CHAT_MUTE_PERMISSION
    )
    public void handleClearChatCommand(
      @NotNull Context<CommandSender> context
    ) {
        handleChatClearCommand(context);
    }


}
