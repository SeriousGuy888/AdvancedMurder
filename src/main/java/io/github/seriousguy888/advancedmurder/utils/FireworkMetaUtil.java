package io.github.seriousguy888.advancedmurder.utils;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class FireworkMetaUtil {
  public Firework firework;
  public FireworkMeta meta;
  public PersistentDataContainer dataContainer;

  public FireworkMetaUtil(Firework firework) {
    this.firework = firework;
    this.meta = this.firework.getFireworkMeta();
    this.dataContainer = this.meta.getPersistentDataContainer();
  }

  public void setTargetUuid(String uuid) {
    this.dataContainer.set(
        new NamespacedKey(AdvancedMurder.getPlugin(), "target_entity_uuid"),
        PersistentDataType.STRING, uuid);

    this.firework.setFireworkMeta(this.meta);
  }

  public String getTargetUuid() {
    return this.dataContainer.get(
        new NamespacedKey(AdvancedMurder.getPlugin(), "target_entity_uuid"),
        PersistentDataType.STRING);
  }
}