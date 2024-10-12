package net.luxcube.minecraft.entity;

import lombok.Data;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class NemesisPlayer {

    public static NemesisPlayer constructDefault(@NotNull UUID uniqueId) {
        return new NemesisPlayer(uniqueId, new LinkedHashSet<>());
    }

    private final UUID uniqueId;

    private final LinkedHashSet<Component> chatIndexes;

    private boolean staffChatEnabled;

}
