package net.luxcube.minecraft.entity.repository;

import net.luxcube.minecraft.entity.NemesisPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NemesisPlayerRepository {

    private final Map<UUID, NemesisPlayer> nemesisPlayerMap = new HashMap<>();

    public void putInCache(@NotNull NemesisPlayer nemesisPlayer) {
        nemesisPlayerMap.put(nemesisPlayer.getUniqueId(), nemesisPlayer);
    }

    @Nullable
    public NemesisPlayer removeFromCache(@NotNull UUID uniqueId) {
        return nemesisPlayerMap.remove(uniqueId);
    }

    @Nullable
    public NemesisPlayer find(@NotNull UUID uniqueId) {
        return nemesisPlayerMap.get(uniqueId);
    }

}
