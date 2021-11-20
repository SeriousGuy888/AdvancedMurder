package io.github.seriousguy888.advancedmurder.utils;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class TargetingUtil {
  public static Entity rayTraceTarget(Player player, int maxDistance) {
    World world = player.getWorld();

    RayTraceResult entityRayTraceResult = world
        .rayTraceEntities(
            player.getLocation(),
            player.getEyeLocation().getDirection(),
            maxDistance,
            0.5,
            entity -> (!entity.equals(player) &&
                    entity instanceof LivingEntity &&
                    !entity.isDead()));

    if(entityRayTraceResult == null)
      return null;
    Entity entity = entityRayTraceResult.getHitEntity();
    if(entity == null)
      return null;

    // use a raycast to determine if there are any blocks between the player at the found entity
    // if the block ray trace does not return false, ie that there a blocks between, then return null

    Vector vecBetween = player.getEyeLocation().toVector().subtract(entity.getLocation().toVector());
    double lenBetween = entity.getLocation().toVector().distance(player.getEyeLocation().toVector());

    RayTraceResult blockRayTraceResult = world.rayTraceBlocks(entity.getLocation(), vecBetween, lenBetween);

    if(blockRayTraceResult != null && blockRayTraceResult.getHitBlock() != null)
      return null;


    return entity;
  }
}
