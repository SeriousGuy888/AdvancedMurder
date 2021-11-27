package io.github.seriousguy888.advancedmurder.runnables;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.utils.TargetingUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class SelectTargets extends BukkitRunnable {
  @Override
  public void run() {
    Bukkit.getOnlinePlayers().forEach(player -> {
      Entity oldTargetEntity = AdvancedMurder.playersCurrentTargets.get(player);

      ItemStack heldItem = player.getInventory().getItemInMainHand();
      if(heldItem.getType() != Material.CROSSBOW)
        return;

      CrossbowMeta crossbowMeta = (CrossbowMeta) heldItem.getItemMeta();
      if(crossbowMeta == null)
        return;
      if(!crossbowMeta.hasChargedProjectiles() || crossbowMeta.getChargedProjectiles()
          .stream().noneMatch(itemStack -> itemStack.getType() == Material.FIREWORK_ROCKET)) {
        AdvancedMurder.playersPreviousTargets.put(player, oldTargetEntity);
        return;
      }


      Entity targetEntity = TargetingUtil.rayTraceTarget(player, 96);

      if(targetEntity != null && targetEntity.equals(oldTargetEntity))
        return;

      if(oldTargetEntity != null)
        AdvancedMurder.playersPreviousTargets.put(player, oldTargetEntity);
      if(targetEntity != null)
        AdvancedMurder.playersCurrentTargets.put(player, targetEntity);
    });
  }
}
