package net.luxcube.minecraft.listener;

import lombok.RequiredArgsConstructor;
import net.luxcube.minecraft.NemesisPlugin;
import net.luxcube.minecraft.entity.NemesisPlayer;
import net.luxcube.minecraft.storage.NemesisStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class ConnectionListener implements Listener {

    private final NemesisPlugin nemesisPlugin;

    @EventHandler
    private void onConnect(@NotNull AsyncPlayerPreLoginEvent event) {

        NemesisStorage nemesisStorage = nemesisPlugin.getNemesisStorage();
        Logger logger = nemesisPlugin.getLogger();

        nemesisStorage.retrievePlayer(event.getUniqueId())
            .whenComplete(((nemesisPlayer, throwable) -> {

                if (throwable != null) {
                    logger.severe(
                        "Failed to retrieve user %s: %s"
                        .formatted(event.getName(), throwable.getMessage())
                    );
                    return;
                }

                if (nemesisPlayer == null) {
                    nemesisPlayer = NemesisPlayer.constructDefault(event.getUniqueId());
                    nemesisStorage.createPlayer(nemesisPlayer);
                }

                nemesisPlugin.getNemesisPlayerRepository()
                    .putInCache(nemesisPlayer);
            }));

    }

    @EventHandler
    private void onQuit(@NotNull PlayerQuitEvent event) {

        @Nullable NemesisPlayer nemesisPlayer = nemesisPlugin.getNemesisPlayerRepository()
            .removeFromCache(event.getPlayer().getUniqueId());

        if (nemesisPlayer == null) {
            return;
        }

        NemesisStorage nemesisStorage = nemesisPlugin.getNemesisStorage();
        Logger logger = nemesisPlugin.getLogger();

        long start = System.currentTimeMillis();

        nemesisStorage.updatePlayer(nemesisPlayer)
            .whenComplete(((aBoolean, throwable) -> {

                if (throwable != null) {
                    logger.severe(
                        "Failed to update user %s: %s"
                            .formatted(event.getPlayer().getName(), throwable.getMessage())
                    );
                    return;
                }

                long end = System.currentTimeMillis();

                logger.info(
                    "Successfully updated data for %s in %sms"
                    .formatted(event.getPlayer().getName(), (end - start))
                );

            }));
    }

}
