package net.luxcube.minecraft.impl.suspicious;

import net.luxcube.minecraft.ModuleType;
import net.luxcube.minecraft.NemesisModule;
import org.jetbrains.annotations.NotNull;

public class SuspiciousModule implements NemesisModule {

  @Override
  public @NotNull String getModuleName() {
    return "suspicious";
  }

  @Override
  public @NotNull ModuleType getModuleType() {
    return ModuleType.SUSPICIOUS;
  }

}
