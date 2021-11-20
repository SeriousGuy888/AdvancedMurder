package io.github.seriousguy888.advancedmurder;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.seriousguy888.advancedmurder.listeners.FireworkExplodeListener;
import io.github.seriousguy888.advancedmurder.listeners.PlayerMoveListener;
import io.github.seriousguy888.advancedmurder.listeners.ProjectileLaunchListener;
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

  private static ProtocolManager protocolManager;
  public static ProtocolManager getProtocolManager() {
    return protocolManager;
  }

  public static ArrayList<Firework> activeHomingMissiles = new ArrayList<>();
  public static HashMap<Player, Entity> playerTargetedEntities = new HashMap<>();

  @Override
  public void onEnable() {
    plugin = this;
    protocolManager = ProtocolLibrary.getProtocolManager();

    registerListeners();
    new TickMissiles().runTaskTimer(this, 0L, 1L);
  }


  private void registerListeners() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new FireworkExplodeListener(), this);
    pm.registerEvents(new PlayerMoveListener(), this);
    pm.registerEvents(new ProjectileLaunchListener(), this);
  }
}
