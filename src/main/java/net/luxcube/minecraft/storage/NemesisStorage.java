package net.luxcube.minecraft.storage;

import net.luxcube.minecraft.entity.NemesisPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface NemesisStorage {

    CompletableFuture<NemesisPlayer> retrievePlayer(@NotNull UUID uniqueId);

    CompletableFuture<Boolean> updatePlayer(@NotNull NemesisPlayer nemesisPlayer);

    CompletableFuture<Boolean> createPlayer(@NotNull NemesisPlayer nemesisPlayer);

}
