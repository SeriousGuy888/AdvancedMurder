package io.github.seriousguy888.advancedmurder.listeners;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.utils.TargetingUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
  @EventHandler
  public void onMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();

    if(player.getInventory().getItemInMainHand().getType() != Material.CROSSBOW)
      return;

    Entity targetEntity = TargetingUtil.rayTraceTarget(player, 96);
    Entity oldTargetEntity = AdvancedMurder.playersCurrentTargets.get(player);

    if(targetEntity != null && targetEntity.equals(oldTargetEntity))
      return;

    if(oldTargetEntity != null) {
      AdvancedMurder.playersPreviousTargets.put(player, oldTargetEntity);
    }
    if(targetEntity != null) {
      AdvancedMurder.playersCurrentTargets.put(player, targetEntity);
    }
  }
}
