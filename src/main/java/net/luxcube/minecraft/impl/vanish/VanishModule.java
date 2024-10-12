package net.luxcube.minecraft.impl.vanish;

import net.luxcube.minecraft.ModuleType;
import net.luxcube.minecraft.NemesisModule;
import net.luxcube.minecraft.NemesisPlugin;
import net.luxcube.minecraft.impl.commands.listener.ProcessListener;
import net.luxcube.minecraft.impl.vanish.command.VanishCommand;
import net.luxcube.minecraft.impl.vanish.listener.VanishListener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VanishModule implements NemesisModule {

  @Override
  public @NotNull String getModuleName() {
    return "vanish";
  }

  @Override
  public @NotNull ModuleType getModuleType() {
    return ModuleType.VANISH;
  }

  @Override
  public void onEnable() {
    @NotNull NemesisPlugin plugin = NemesisPlugin.getInstance();

    @NotNull PluginManager pluginManager = plugin
      .getServer()
      .getPluginManager();

    pluginManager.registerEvents(
      new VanishListener(plugin),
      plugin
    );
  }

  @Override
  public @NotNull List<Object> getCommands() {
    @NotNull NemesisPlugin plugin = NemesisPlugin.getInstance();
    return List.of(
      new VanishCommand(plugin)
    );
  }
}
