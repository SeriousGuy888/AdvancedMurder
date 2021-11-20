package io.github.seriousguy888.advancedmurder.runnables;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.utils.FireworkMetaUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TickMissiles extends BukkitRunnable {
  @Override
  public void run() {
    AdvancedMurder.activeHomingMissiles.forEach(firework -> {

      int searchRadius = 16;
      List<Entity> nearbyEntities = firework.getNearbyEntities(searchRadius, searchRadius, searchRadius)
          .stream()
          .filter(entity -> {
            Entity shooter = (Entity) firework.getShooter();
            int shooterId = 0;
            if (shooter != null)
              shooterId = shooter.getEntityId();

            return entity.getEntityId() != shooterId &&
                entity instanceof Villager;
          })
          .collect(Collectors.toList());

      if(nearbyEntities.size() == 0)
        return;

      Optional<Entity> targetEntity = nearbyEntities.stream().filter(entity -> {
        Location fireworkLoc = firework.getLocation();
        Vector direction = entity.getLocation().subtract(fireworkLoc).toVector();

        boolean pathClear = firework.getWorld().rayTraceBlocks(fireworkLoc, direction, searchRadius) == null;
        if(!pathClear)
          return false;

        String targetUuid = new FireworkMetaUtil(firework).getTargetUuid();
        String currUuid = entity.getUniqueId().toString();
        return targetUuid != null && targetUuid.equals(currUuid);
      }).findFirst();

      // stop if the firework cannot find a target
      if(!targetEntity.isPresent())
        return;


      Location targetLoc = targetEntity.get().getLocation();
      if(targetEntity.get() instanceof LivingEntity) {
        targetLoc = ((LivingEntity) targetEntity.get()).getEyeLocation();
      }

      Vector oldDirection = firework.getVelocity();
      Vector newDirection = targetLoc.subtract(firework.getLocation()).toVector();

      double dotProd = dot(newDirection, oldDirection);
      double angleRad = Math.acos(dotProd / (newDirection.length() * oldDirection.length()));
      double angleDeg = Math.toDegrees(angleRad);
      if(angleDeg > 90)
        return;

      // I want to eventually make it so that the firework can only turn up to something
      // like 20 deg at a time, but that it can turn in the direction of the new vector
      // even if that would be a 90 deg turn, but it can only turn 20 deg per tick.
      // math is difficult
      //
      // currently it just stops if the angle between the vectors is more than 90 deg


      firework.setVelocity(newDirection.normalize());
    });
  }


  double dot(Vector a, Vector b) {
    return a.getX() * b.getX() +
        a.getY() * b.getY() +
        a.getZ() * b.getZ();
  }
}
