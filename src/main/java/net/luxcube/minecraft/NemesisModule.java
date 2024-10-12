package net.luxcube.minecraft;

import me.saiintbrisson.minecraft.AbstractView;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
import java.util.List;

public interface NemesisModule {

  @NotNull
  String getModuleName();

  @NotNull
  ModuleType getModuleType();

  default void onFirstLoad() {}

  default void onReload() {}

  default void onEnable() {}

  default void onDisable() {}

  @NotNull
  default List<Object> getCommands() {
    return Collections.emptyList();
  }

  @NotNull
  default List<AbstractView> getViews() {
    return Collections.emptyList();
  }

  default String getFolderPath() {
    return "modules/%s".formatted(getModuleName());
  }

  default void saveDefaultConfig() {
    NemesisPlugin.getInstance().saveResource(
            "%s/config.yml".formatted(getFolderPath()),
            false
    );
  }

  default File getConfigFile() {
    return new File(
            NemesisPlugin.getInstance().getDataFolder(),
            "%s/config.yml".formatted(getFolderPath())
    );
  }

  default FileConfiguration getConfig() {
    return YamlConfiguration.loadConfiguration(getConfigFile());
  }


}
