package net.luxcube.minecraft.impl.chat.command;

import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Completer;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import net.kyori.adventure.text.Component;
import net.luxcube.minecraft.NemesisPlugin;
import net.luxcube.minecraft.entity.NemesisPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static net.luxcube.minecraft.util.Colors.translate;

@RequiredArgsConstructor
public class ChatHistoryCommand {

    private final static String CHAT_HISTORY_PERMISSION = "nemesis.staff.chat.history";

    private final NemesisPlugin nemesisPlugin;

    @Command(
        name = "chat.history",
        permission = CHAT_HISTORY_PERMISSION,
        async = true
    )
    public void handleChatHistoryCommand(
        @NotNull Context<CommandSender> context,
        @Optional String playerName,
        @Optional Integer page
    ) {
        CommandSender commandSender = context.getSender();

        if (playerName == null || page == null) {
            commandSender.sendMessage(translate(
                " &e/chat history <player> <page>"
            ));
            return;
        }

        commandSender.sendMessage(translate(
            "&aRetrieving player information..."
        ));

        OfflinePlayer offlinePlayer = nemesisPlugin.getServer()
            .getOfflinePlayer(playerName);

        if (!offlinePlayer.hasPlayedBefore()) {

            commandSender.sendMessage(translate(
                "&7That player has never played before."
            ));

            return;
        }

        UUID uniqueId = offlinePlayer.getUniqueId();

        @Nullable NemesisPlayer nemesisPlayer = nemesisPlugin.getNemesisPlayerRepository()
            .find(uniqueId);

        if (nemesisPlayer == null) {
            nemesisPlayer = nemesisPlugin.getNemesisStorage()
                .retrievePlayer(uniqueId).join();
        }

        if (nemesisPlayer == null) {
            commandSender.sendMessage(translate(
                "&cPlayer chat logs for this player could not be found."
            ));

           return;
        }

        Set<Component> chatIndexes = nemesisPlayer.getChatIndexes();

        int maxPages = (int) Math.ceil(chatIndexes.size() / 10.0);

        if (page > maxPages) {
            commandSender.sendMessage(translate(
                "&cMax page is %s".formatted(maxPages)
            ));

            return;
        }

        AtomicInteger index = new AtomicInteger(1);

        chatIndexes.stream()
            .skip((page - 1) * 10L)
            .limit(10)
            .forEach(chatLog -> {
                commandSender.sendMessage(
                    Component.text("#%s ".formatted(index.getAndIncrement()))
                        .append(chatLog)
                );
            });
    }

    @Completer(name = "chat")
    public List<String> tabComplete(@NotNull Context<Player> context) {

        String[] args = context.getArgs();
        String start = args[args.length-1].toLowerCase();

        if (args.length == 1) {
            return Stream.of("history")
                .filter(name -> name.startsWith(start))
                .toList();
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("history")) {
            return Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(start))
                .toList();
        }

        return Collections.emptyList();
    }

}
