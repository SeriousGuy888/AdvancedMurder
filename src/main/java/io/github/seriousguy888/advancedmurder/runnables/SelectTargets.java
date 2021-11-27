package io.github.seriousguy888.advancedmurder.runnables;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.utils.TargetingUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class SelectTargets extends BukkitRunnable {
  @Override
  public void run() {
    Bukkit.getOnlinePlayers().forEach(player -> {
      if(player.getInventory().getItemInMainHand().getType() != Material.CROSSBOW)
        return;

      Entity targetEntity = TargetingUtil.rayTraceTarget(player, 96);
      Entity oldTargetEntity = AdvancedMurder.playersCurrentTargets.get(player);

      if(targetEntity != null && targetEntity.equals(oldTargetEntity))
        return;

      if(oldTargetEntity != null)
        AdvancedMurder.playersPreviousTargets.put(player, oldTargetEntity);
      if(targetEntity != null)
        AdvancedMurder.playersCurrentTargets.put(player, targetEntity);


    });
  }
}
