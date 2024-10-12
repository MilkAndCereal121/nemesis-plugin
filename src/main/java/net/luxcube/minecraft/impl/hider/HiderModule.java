package net.luxcube.minecraft.impl.hider;

import net.luxcube.minecraft.ModuleType;
import net.luxcube.minecraft.NemesisModule;
import org.jetbrains.annotations.NotNull;

public class HiderModule implements NemesisModule {

  @Override
  public @NotNull String getModuleName() {
    return "hider";
  }

  @Override
  public @NotNull ModuleType getModuleType() {
    return ModuleType.HIDER;
  }

  public boolean isBlacklisted(@NotNull String command) {
    return false;
  }

}
