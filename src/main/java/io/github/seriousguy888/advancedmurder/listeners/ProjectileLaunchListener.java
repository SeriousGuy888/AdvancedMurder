package io.github.seriousguy888.advancedmurder.listeners;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {

  @EventHandler
  public void onLaunch(ProjectileLaunchEvent event) {
    if(!(event.getEntity() instanceof Firework))
      return;
    Firework firework = (Firework) event.getEntity();

    if(!(firework.getShooter() instanceof Player))
      return;
    Player player = (Player) firework.getShooter();

    if(!firework.isShotAtAngle()) // if firework is not shot from a crossbow
      return;
    if(!firework.getFireworkMeta().hasEffects()) // if firework does not have explosion effects
      return; // ie: the firework would not do damage upon exploding, then return

    AdvancedMurder.activeHomingMissiles.add(firework);
  }
}
