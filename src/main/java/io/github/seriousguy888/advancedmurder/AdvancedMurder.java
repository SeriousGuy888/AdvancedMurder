package io.github.seriousguy888.advancedmurder;

import io.github.seriousguy888.advancedmurder.listeners.FireworkExplodeListener;
import io.github.seriousguy888.advancedmurder.listeners.ProjectileLaunchListener;
import io.github.seriousguy888.advancedmurder.runnables.TickMissiles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Firework;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

public final class AdvancedMurder extends JavaPlugin {
//  public Logger log = Bukkit.getLogger();
  public static ArrayList<Firework> activeHomingMissiles = new ArrayList<>();

  @Override
  public void onEnable() {
    registerListeners();
    new TickMissiles().runTaskTimer(this, 0L, 1L);
  }


  private void registerListeners() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new ProjectileLaunchListener(), this);
    pm.registerEvents(new FireworkExplodeListener(), this);
  }
}
