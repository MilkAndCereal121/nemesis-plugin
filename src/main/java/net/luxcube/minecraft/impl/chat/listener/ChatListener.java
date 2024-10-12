package net.luxcube.minecraft.impl.chat.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.luxcube.minecraft.NemesisPlugin;
import net.luxcube.minecraft.entity.NemesisPlayer;
import net.luxcube.minecraft.impl.chat.ChatModule;
import net.luxcube.minecraft.impl.chat.util.ChatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.logging.Logger;

import static net.luxcube.minecraft.impl.chat.ChatModule.CHAT_STAFF_PERMISSION;
import static net.luxcube.minecraft.util.Colors.translate;

@RequiredArgsConstructor
public class ChatListener implements Listener {

    private final NemesisPlugin nemesisPlugin;
    private final ChatModule chatModule;

    @EventHandler
    private void onChat(@NotNull AsyncChatEvent event) {

        @Nullable NemesisPlayer nemesisPlayer = nemesisPlugin.getNemesisPlayerRepository()
          .find(event.getPlayer().getUniqueId());

        if (nemesisPlayer == null) {
            Logger logger = nemesisPlugin.getLogger();

            logger.warning(
              "Nemesis player could not be found for %s in module chat!"
                .formatted(event.getPlayer().getName())
            );

            return;
        }

        if (nemesisPlayer.isStaffChatEnabled()) {
            String message = chatModule.getStaffChatFormat(
              event.getPlayer(),
              String.join(" ", event.message().insertion())
            );

            ChatUtils.sendPrivateMessage(
              CHAT_STAFF_PERMISSION,
              message
            );
        }

        if (chatModule.isChatMuted()) {
            String message = translate("&cChat is muted!");

            event.getPlayer()
              .sendMessage(
                message
              );

            event.setCancelled(true);

            return;
        }

        Set<Component> chatIndexes = nemesisPlayer.getChatIndexes();

        chatIndexes.add(event.message());

    }

}
