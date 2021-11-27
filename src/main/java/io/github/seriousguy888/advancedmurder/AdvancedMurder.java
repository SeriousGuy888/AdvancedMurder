package io.github.seriousguy888.advancedmurder;

import io.github.seriousguy888.advancedmurder.listeners.FireworkExplodeListener;
import io.github.seriousguy888.advancedmurder.listeners.ProjectileLaunchListener;
import io.github.seriousguy888.advancedmurder.runnables.GlowTargets;
import io.github.seriousguy888.advancedmurder.runnables.SelectTargets;
import io.github.seriousguy888.advancedmurder.runnables.TickMissiles;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class AdvancedMurder extends JavaPlugin {
  private static JavaPlugin plugin;
  public static JavaPlugin getPlugin() {
    return plugin;
  }

  public static ArrayList<Firework> activeHomingMissiles = new ArrayList<>();
  public static HashMap<Player, Entity> playersCurrentTargets = new HashMap<>();
  public static HashMap<Player, Entity> playersPreviousTargets = new HashMap<>();

  @Override
  public void onEnable() {
    plugin = this;

    registerListeners();
    registerRunnables();
  }


  private void registerListeners() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new FireworkExplodeListener(), this);
    pm.registerEvents(new ProjectileLaunchListener(), this);
  }

  private void registerRunnables() {
    new TickMissiles().runTaskTimer(this, 0L, 1L);
    new SelectTargets().runTaskTimer(this, 0L, 1L);
    new GlowTargets().runTaskTimer(this, 0L, 1L);
  }
}
