package io.github.seriousguy888.advancedmurder.runnables;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.glow.GlowAPI;

public class GlowTargets extends BukkitRunnable {
  @Override
  public void run() {
    AdvancedMurder.playersCurrentTargets.forEach((player, entity) -> {
      if(entity != null)
        GlowAPI.setGlowing(entity, true, player);
    });
    AdvancedMurder.playersPreviousTargets.forEach((player, entity) -> {
      if(entity != null && !entity.isGlowing())
        GlowAPI.setGlowing(entity, false, player);
    });
  }
}
