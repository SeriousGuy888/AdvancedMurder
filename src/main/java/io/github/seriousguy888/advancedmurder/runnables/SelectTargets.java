package io.github.seriousguy888.advancedmurder.runnables;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.files.DataManager;
import io.github.seriousguy888.advancedmurder.utils.TargetingUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class SelectTargets extends BukkitRunnable {
  DataManager dataManager = AdvancedMurder.getDataManager();

  @Override
  public void run() {
    Bukkit.getOnlinePlayers().forEach(player -> {

      String key = "players." + player.getUniqueId() + ".missiles.disable_homing";
      if(dataManager.getConfig().contains(key) && dataManager.getConfig().getBoolean(key))
        return;


      Entity targetEntity = TargetingUtil.rayTraceTarget(player, 96);
      Entity oldTargetEntity = AdvancedMurder.playersCurrentTargets.get(player);

      ItemStack heldItem = player.getInventory().getItemInMainHand();
      if(heldItem.getType() != Material.CROSSBOW) {
        AdvancedMurder.playersPreviousTargets.put(player, oldTargetEntity);
        return;
      }

      CrossbowMeta crossbowMeta = (CrossbowMeta) heldItem.getItemMeta();
      if(crossbowMeta == null)
        return;
      if(!crossbowMeta.hasChargedProjectiles() || crossbowMeta.getChargedProjectiles()
          .stream().noneMatch(itemStack -> itemStack.getType() == Material.FIREWORK_ROCKET)) {
        AdvancedMurder.playersPreviousTargets.put(player, oldTargetEntity);
        return;
      }


      if(targetEntity != null && targetEntity.equals(oldTargetEntity))
        return;

      AdvancedMurder.playersPreviousTargets.put(player, oldTargetEntity);
      AdvancedMurder.playersCurrentTargets.put(player, targetEntity);
    });
  }
}
