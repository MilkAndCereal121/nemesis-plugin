package net.luxcube.minecraft.impl.chat.command;

import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.kyori.adventure.text.Component;
import net.luxcube.minecraft.impl.chat.ChatModule;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static net.luxcube.minecraft.util.Colors.translate;

@RequiredArgsConstructor
public class ChatMuteCommand {

    private final static String CHAT_MUTE_PERMISSION = "nemesis.staff.chat.mute";

    private final ChatModule chatModule;

    @Command(
        name = "chat.mute",
        permission = CHAT_MUTE_PERMISSION,
        target = CommandTarget.ALL
    )
    public void handleChatMuteCommand(
        @NotNull Context<CommandSender> context
    ) {

        boolean chatMuted = chatModule.isChatMuted();

        CommandSender commandSender = context.getSender();

        String message = chatMuted
            ? translate("&aChat has been un-muted by %s.".formatted(commandSender.getName()))
            : translate("&aChat has been muted by %s.".formatted(commandSender.getName()));

        Bukkit.broadcast(
            Component.text(message)
        );

        chatModule.setChatMuted(
            !chatMuted
        );

    }

    @Command(
        name = "mutechat",
        permission = CHAT_MUTE_PERMISSION,
        target = CommandTarget.ALL
    )
    public void handleMuteChatCommand(
        @NotNull Context<CommandSender> context
    ) {
        handleChatMuteCommand(context);
    }

}
