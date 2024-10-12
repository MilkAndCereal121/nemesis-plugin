package net.luxcube.minecraft.util;

import net.luxcube.minecraft.NemesisPlugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Metadatas {

  @Nullable
  public static <T> T getMetadata(Metadatable metadatable, String key) {
    List<MetadataValue> values = metadatable.getMetadata(key);
    if (values.isEmpty()) {
      return null;
    }

    return (T) values.get(0)
            .value();
  }

  public static <T> T getMetadata(Metadatable metadatable, String key, T def) {
    List<MetadataValue> values = metadatable.getMetadata(key);
    if (values.isEmpty()) {
      return def;
    }

    return (T) values.get(0)
            .value();
  }

  public static <T> void setMetadata(Metadatable metadata, String key, T value) {
    metadata.setMetadata(key, new FixedMetadataValue(NemesisPlugin.getInstance(), value));
  }

  public static void removeMetadata(Metadatable metadata, String key) {
    metadata.removeMetadata(key, NemesisPlugin.getInstance());
  }

}

