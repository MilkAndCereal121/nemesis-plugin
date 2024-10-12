package net.luxcube.minecraft.impl.commands;

import lombok.Getter;
import net.luxcube.minecraft.ModuleType;
import net.luxcube.minecraft.NemesisModule;
import net.luxcube.minecraft.NemesisPlugin;
import net.luxcube.minecraft.impl.commands.command.SpyCommand;
import net.luxcube.minecraft.impl.commands.listener.ProcessListener;
import net.luxcube.minecraft.util.Metadatas;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static net.luxcube.minecraft.util.Colors.translate;

@Getter
public class CommandsModule implements NemesisModule {

  private final static String COMMANDS_SPY_KEY = "commands_spy";

  private boolean spyEnabled;

  private String formatMessage,
    toggleOn,
    toggleOff;

  @Override
  public @NotNull String getModuleName() {
    return "commands";
  }

  @Override
  public @NotNull ModuleType getModuleType() {
    return ModuleType.COMMANDS;
  }

  @Override
  public void onFirstLoad() {
    saveDefaultConfig();
  }

  @Override
  public void onReload() {
    FileConfiguration config = getConfig();

    spyEnabled = config.getBoolean("commands-spy.enabled");

    formatMessage = config.getString(
      "commands-spy.format"
    );

    checkArgument(formatMessage != null, "Format message could not be found!");

    formatMessage = translate(formatMessage);

    toggleOn = config.getString("commands-spy.toggle-messages.on");
    toggleOff = config.getString("commands-spy.toggle-messages.off");

    checkArgument(
      toggleOn != null
      & toggleOff != null,
      "Failed to read the toggle messages correctly for commands spy."
    );

    toggleOn = translate(toggleOn);
    toggleOff = translate(toggleOff);
  }

  @Override
  public void onEnable() {
    @NotNull NemesisPlugin plugin = NemesisPlugin.getInstance();

    @NotNull PluginManager pluginManager = plugin
      .getServer()
      .getPluginManager();

    pluginManager.registerEvents(
      new ProcessListener(this),
      plugin
    );
  }

  @Override
  public @NotNull List<Object> getCommands() {
    return List.of(
      new SpyCommand(this)
    );
  }

  public boolean isSpyEnabled(
    @NotNull Player player
  ) {
    return Metadatas.getMetadata(
      player,
      COMMANDS_SPY_KEY,
      false
    );
  }

  public void setSpy(
    @NotNull Player player,
    boolean enabled
  ) {
    if (!enabled) {
      Metadatas.removeMetadata(
        player,
        COMMANDS_SPY_KEY
      );
    } else {
      Metadatas.setMetadata(
        player,
        COMMANDS_SPY_KEY,
        true
      );
    }
  }

}
