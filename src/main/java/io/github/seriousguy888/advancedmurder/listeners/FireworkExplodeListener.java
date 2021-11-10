package io.github.seriousguy888.advancedmurder.listeners;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FireworkExplodeEvent;

public class FireworkExplodeListener implements Listener {
  @EventHandler
  public void onExplode(FireworkExplodeEvent event) {
    // removes tracked fireworks when they explode.

    Firework firework = event.getEntity();
    AdvancedMurder.activeHomingMissiles.remove(firework);
  }
}
