package io.github.seriousguy888.advancedmurder.runnables;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.utils.FireworkMetaUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TickMissiles extends BukkitRunnable {
  @Override
  public void run() {
    AdvancedMurder.activeHomingMissiles.forEach(firework -> {
      int searchRadius = 24;

      String targetUuid = new FireworkMetaUtil(firework).getTargetUuid();
      if(targetUuid == null)
        return;

      Entity targetEntity = getEntityByUuid(targetUuid);
      if(targetEntity == null)
        return;

      Location fireworkLoc = firework.getLocation();
      Location targetLoc = targetEntity.getLocation();

      // tests if the firework's target is within searchRadius blocks without using sqrt
//      if(!firework.getWorld().equals(targetEntity.getWorld()) ||
//          (Math.pow(fireworkLoc.getX() - targetLoc.getX(), 2) +
//          Math.pow(fireworkLoc.getY() - targetLoc.getY(), 2) +
//          Math.pow(fireworkLoc.getZ() - targetLoc.getZ(), 2)) >
//          Math.pow(searchRadius, 2))
      if(!firework.getWorld().equals(targetEntity.getWorld()) ||
          fireworkLoc.subtract(targetLoc).length() > searchRadius)
        return;


      Vector direction = targetLoc.subtract(fireworkLoc).toVector();
      if(firework.getWorld().rayTraceBlocks(fireworkLoc, direction, searchRadius) != null)
        return;


      if(targetEntity instanceof LivingEntity) {
        targetLoc = ((LivingEntity) targetEntity).getEyeLocation();
      }

      Vector oldDirection = firework.getVelocity();
      Vector newDirection = targetLoc.subtract(firework.getLocation()).toVector();

//      double dotProd = dot(newDirection, oldDirection);
//      double angleRad = Math.acos(dotProd / (newDirection.length() * oldDirection.length()));
//      double angleDeg = Math.toDegrees(angleRad);
//      if(angleDeg > 135)
//        return;

      // I want to eventually make it so that the firework can only turn up to something
      // like 20 deg at a time, but that it can turn in the direction of the new vector
      // even if that would be a 90 deg turn, but it can only turn 20 deg per tick.
      // math is difficult
      //
      // currently it just stops if the angle between the vectors is more than 90 deg


      firework.setVelocity(newDirection.normalize());
    });
  }


//  double dot(Vector a, Vector b) {
//    return a.getX() * b.getX() +
//        a.getY() * b.getY() +
//        a.getZ() * b.getZ();
//  }

  Entity getEntityByUuid(String uuidStr) {
    for(World world : Bukkit.getWorlds()) {
      for(Entity entity : world.getEntities()) {
        if(entity.getUniqueId().toString().equals(uuidStr))
          return entity;
      }
    }

    return null;
  }
}
