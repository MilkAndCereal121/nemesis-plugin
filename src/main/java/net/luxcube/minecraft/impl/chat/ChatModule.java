package net.luxcube.minecraft.impl.chat;

import lombok.Getter;
import lombok.Setter;
import net.luxcube.minecraft.ModuleType;
import net.luxcube.minecraft.NemesisModule;
import net.luxcube.minecraft.NemesisPlugin;
import net.luxcube.minecraft.impl.chat.command.ChatClearCommand;
import net.luxcube.minecraft.impl.chat.command.ChatHistoryCommand;
import net.luxcube.minecraft.impl.chat.command.ChatMuteCommand;
import net.luxcube.minecraft.impl.chat.command.ChatStaffCommand;
import net.luxcube.minecraft.impl.chat.listener.ChatListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
public class ChatModule implements NemesisModule {

    public final static String CHAT_STAFF_PERMISSION = "nemesis.staff.chat.staff";

    private boolean chatMuted;

    @Override
    public @NotNull String getModuleName() {
        return "chat";
    }

    @Override
    public @NotNull ModuleType getModuleType() {
        return ModuleType.CHAT;
    }

    @Override
    public void onEnable() {
        @NotNull NemesisPlugin plugin = NemesisPlugin.getInstance();

        @NotNull PluginManager pluginManager = plugin
          .getServer()
          .getPluginManager();

        pluginManager.registerEvents(
          new ChatListener(plugin),
          plugin
        );
    }

    @Override
    public @NotNull List<Object> getCommands() {
        @NotNull NemesisPlugin plugin = NemesisPlugin.getInstance();

        return List.of(
          new ChatHistoryCommand(plugin),
          new ChatMuteCommand(this),
          new ChatClearCommand(),
          new ChatStaffCommand()
        );
    }

    private final static String STAFF_CHAT_FORMAT = "&c&lSC&f %s: %s";

    public String getStaffChatFormat(
      @NotNull CommandSender from,
      @NotNull String message
    ) {

      return STAFF_CHAT_FORMAT.formatted(
        from.getName(),
        message
      );
    }

}
