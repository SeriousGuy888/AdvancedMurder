package io.github.seriousguy888.advancedmurder.listeners;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.utils.FireworkMetaUtil;
import io.github.seriousguy888.advancedmurder.utils.TargetingUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {

  @EventHandler
  public void onLaunch(ProjectileLaunchEvent event) {
    if(!(event.getEntity() instanceof Firework firework))
      return;
    if(!(firework.getShooter() instanceof Player player))
      return;
    if(!firework.isShotAtAngle()) // if firework is not shot from a crossbow
      return;
    if(!firework.getFireworkMeta().hasEffects()) // if firework does not have explosion effects
      return; // ie: the firework would not do damage upon exploding, then return


    Entity targetEntity = TargetingUtil.rayTraceTarget(player, 96);

    if(targetEntity != null) {
      FireworkMetaUtil metaUtil = new FireworkMetaUtil(firework);
      metaUtil.setTargetUuid(targetEntity.getUniqueId().toString());
    }

    AdvancedMurder.activeHomingMissiles.add(firework);
  }
}
