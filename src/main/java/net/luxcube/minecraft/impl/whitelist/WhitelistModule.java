package net.luxcube.minecraft.impl.whitelist;

import net.kyori.adventure.text.Component;
import net.luxcube.minecraft.ModuleType;
import net.luxcube.minecraft.NemesisModule;
import net.luxcube.minecraft.NemesisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;
import static net.luxcube.minecraft.util.Colors.translate;

public class WhitelistModule implements NemesisModule, Listener {

  private boolean attemptJoinWarningEnabled;
  private String attemptJoinMessage;

  @Override
  public @NotNull String getModuleName() {
    return "whitelist";
  }

  @Override
  public @NotNull ModuleType getModuleType() {
    return ModuleType.WHITELIST;
  }

  @Override
  public void onFirstLoad() {
    saveDefaultConfig();
  }

  @Override
  public void onReload() {
    FileConfiguration config = getConfig();

    attemptJoinWarningEnabled = config.getBoolean("whitelist-warning.enabled");

    attemptJoinMessage = config.getString("attempt-join-message");
    checkArgument(attemptJoinMessage != null, "Attempt join could not be found.");

    attemptJoinMessage = translate(attemptJoinMessage);
  }

  @Override
  public void onEnable() {
    @NotNull NemesisPlugin plugin = NemesisPlugin.getInstance();

    @NotNull PluginManager pluginManager = plugin
      .getServer()
      .getPluginManager();

    pluginManager.registerEvents(
      this, plugin
    );
  }

  @EventHandler
  private void onConnect(
    @NotNull AsyncPlayerPreLoginEvent event
  ) {

    // Checking if feature it enabled.
    if (!attemptJoinWarningEnabled) {
      return;
    }

    boolean whitelisted = Bukkit.hasWhitelist();
    // Server is not even whitelisted, this module should not function in this case.
    if (!whitelisted) {
      return;
    }

    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(event.getUniqueId());
    // If the player is whitelisted, it should not announce the attempt.
    if (offlinePlayer.isWhitelisted()) {
      return;
    }

    Component message = Component.text(
      attemptJoinMessage.replace(
        "%player%",
        event.getName()
      )
    );

    Bukkit.broadcast(message);

  }


}
