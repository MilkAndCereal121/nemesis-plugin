package net.luxcube.minecraft.storage.impl;

import net.luxcube.minecraft.entity.NemesisPlayer;
import net.luxcube.minecraft.storage.NemesisStorage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class FileSystemStorage implements NemesisStorage {

    private final File playerDataFolder;

    public FileSystemStorage(@NotNull Plugin plugin) {
        playerDataFolder = new File(
            plugin.getDataFolder()
            + "/player-data"
        );

        if (!playerDataFolder.exists()) {

            boolean generated = playerDataFolder.mkdirs();
            if (generated) {

                plugin.getLogger()
                    .info("Player data has been created!");
            }

        }
    }

    @Override
    public CompletableFuture<NemesisPlayer> retrievePlayer(@NotNull UUID uniqueId) {
        return CompletableFuture.supplyAsync(() -> {
            File playerData = new File(
                playerDataFolder,
                uniqueId.toString() + ".yml"
            );

            FileConfiguration config = YamlConfiguration.loadConfiguration(playerData);

            return new NemesisPlayer(
                uniqueId
            );
        });
    }

    @Override
    public CompletableFuture<Boolean> updatePlayer(@NotNull NemesisPlayer nemesisPlayer) {
        return CompletableFuture.supplyAsync(() -> {
            File playerData = new File(
                playerDataFolder,
                nemesisPlayer.getUniqueId().toString() + ".yml"
            );

            FileConfiguration config = YamlConfiguration.loadConfiguration(playerData);
            // config set

            try {
                config.save(playerData);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> createPlayer(@NotNull NemesisPlayer nemesisPlayer) {
        return CompletableFuture.supplyAsync(() -> {
            File playerData = new File(
                playerDataFolder,
                nemesisPlayer.getUniqueId().toString() + ".yml"
            );

            try {
                return playerData.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
