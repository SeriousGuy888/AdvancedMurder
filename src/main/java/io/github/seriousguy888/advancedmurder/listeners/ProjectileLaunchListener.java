package io.github.seriousguy888.advancedmurder.listeners;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.utils.FireworkMetaUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;

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


    RayTraceResult rayTraceResult = player.getWorld()
        .rayTraceEntities(
            player.getLocation(),
            player.getEyeLocation().getDirection(),
            96,
            2,
            entity -> entity instanceof Villager);
    if(rayTraceResult == null)
      return;

    Entity targetEntity = rayTraceResult.getHitEntity();

    // todo
    // calculate vector between player and target entity
    // raycast to find any obstructing blocks
    // obstructions invalidate the targeting

//    if(targetEntity != null) {
//      Bukkit.broadcastMessage(targetEntity.getUniqueId().toString());
//    }


//    FireworkMeta meta = firework.getFireworkMeta();
//    PersistentDataContainer data = meta.getPersistentDataContainer();
//
//    if(targetEntity != null) {
//      data.set(
//          new NamespacedKey(AdvancedMurder.getPlugin(), "target_entity_uuid"),
//          PersistentDataType.STRING, targetEntity.getUniqueId().toString());
//    }
//
//    firework.setFireworkMeta(meta);

    if(targetEntity != null) {
      FireworkMetaUtil metaUtil = new FireworkMetaUtil(firework);
      metaUtil.setTargetUuid(targetEntity.getUniqueId().toString());
    }

    AdvancedMurder.activeHomingMissiles.add(firework);
  }
}
