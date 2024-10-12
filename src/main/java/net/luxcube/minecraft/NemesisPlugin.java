package net.luxcube.minecraft;

import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.bukkit.command.executor.BukkitSchedulerExecutor;
import me.saiintbrisson.minecraft.AbstractView;
import me.saiintbrisson.minecraft.ViewFrame;
import net.luxcube.minecraft.entity.repository.NemesisPlayerRepository;
import net.luxcube.minecraft.listener.ConnectionListener;
import net.luxcube.minecraft.storage.NemesisStorage;
import net.luxcube.minecraft.storage.impl.FileSystemStorage;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class NemesisPlugin extends JavaPlugin {

    public static NemesisPlugin getInstance() {
        return JavaPlugin.getPlugin(NemesisPlugin.class);
    }

    private final Map<ModuleType, NemesisModule> modules = new HashMap<>();

    private ViewFrame viewFrame;

    private NemesisPlayerRepository nemesisPlayerRepository = new NemesisPlayerRepository();
    private NemesisStorage nemesisStorage = new FileSystemStorage(this);

    @Override
    public void onLoad() {

        registerModule(

        );

        for (NemesisModule module : getModules()) {
            module.onFirstLoad();
        }

        getLogger().info("Enabled %s modules in total!".formatted(modules.size()));
    }

    public void onReload() {

        for (NemesisModule module : getModules()) {
            module.onReload();
        }

    }

    @Override
    public void onEnable() {

        // Register GUI's
        AbstractView[] views = getModules().stream()
                .map(NemesisModule::getViews)
                .flatMap(Collection::stream)
                .toList()
                .toArray(new AbstractView[0]);

        viewFrame = ViewFrame.of(this, views)
                .register();

        // Register commands
        BukkitFrame bukkitFrame = new BukkitFrame(this);
        bukkitFrame.setExecutor(new BukkitSchedulerExecutor(this));

        Object[] commands = getModules().stream()
                .map(NemesisModule::getCommands)
                .flatMap(Collection::stream)
                .toList()
                .toArray(new Object[0]);

        bukkitFrame.registerCommands(commands);

        for (NemesisModule module : getModules()) {
            module.onEnable();
        }

        // Register general listeners ( do not include modules ).
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(
            new ConnectionListener(this),
            this
        );

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);

        for (NemesisModule module : getModules()) {
            module.onDisable();
        }

    }

    public void registerModule(@NotNull NemesisModule... nemesisModules) {
        for (@NotNull NemesisModule nemesisModule : nemesisModules) {
            ModuleType moduleType = nemesisModule.getModuleType();

            boolean moduleTypeExist = modules.containsKey(moduleType);
            if (moduleTypeExist) {
                throw new IllegalStateException("Failed to register multiple modules of the same type.");
            }

            modules.put(moduleType, nemesisModule);
            getLogger().info("Registered %s module.".formatted(nemesisModule.getModuleName()));
        }
    }

    public List<NemesisModule> getModules() {
        return List.copyOf(modules.values());
    }

}
