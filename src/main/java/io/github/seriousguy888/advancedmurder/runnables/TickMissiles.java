package io.github.seriousguy888.advancedmurder.runnables;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
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

      int radius = 16;
      List<Entity> nearbyEntities = firework.getNearbyEntities(radius, radius, radius)
          .stream()
          .filter(entity -> {
            Entity shooter = (Entity) firework.getShooter();
            int shooterId = 0;
            if (shooter != null)
              shooterId = shooter.getEntityId();

            return entity.getEntityId() != shooterId &&
                entity instanceof Villager;
          })
//          .sorted((a, b) -> { // sort entities from nearest to furthest
//            double distA = firework.getLocation().subtract(a.getLocation()).length();
//            double distB = firework.getLocation().subtract(b.getLocation()).length();
//            return distA < distB ? 1 : 0;
//          })
          .collect(Collectors.toList());

      if(nearbyEntities.size() == 0)
        return;

      // find first entity where there are no blocks between it and the firework
      Optional<Entity> targetEntity = nearbyEntities.stream().filter(entity -> {
        Vector direction = entity.getLocation().subtract(firework.getLocation()).toVector();
        Vector fireworkVel = firework.getVelocity();

        boolean pathClear = firework.getWorld().rayTraceBlocks(firework.getLocation(), direction, radius) == null;
        if(!pathClear)
          return false;

        double dotProd = dot(direction, fireworkVel);
        double angleRad = Math.acos(dotProd / (direction.length() * fireworkVel.length()));
        double angleDeg = Math.toDegrees(angleRad);

        return angleDeg <= 67.5;
      }).findFirst();

      // stop if the firework cannot find a target
      if(!targetEntity.isPresent())
        return;


      Location targetLoc = targetEntity.get().getLocation();
      if(targetEntity.get() instanceof LivingEntity) {
        targetLoc = ((LivingEntity) targetEntity.get()).getEyeLocation();
      }

//      Vector oldDirection = firework.getVelocity();
      Vector newDirection = targetLoc.subtract(firework.getLocation()).toVector();


      firework.setVelocity(newDirection.normalize());
    });
  }


  double dot(Vector a, Vector b) {
    return a.getX() * b.getX() +
        a.getY() * b.getY() +
        a.getZ() * b.getZ();
  }
}
