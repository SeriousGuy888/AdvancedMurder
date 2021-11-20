package io.github.seriousguy888.advancedmurder.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;
import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.utils.PacketUtil;
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
    Entity oldTargetEntity = AdvancedMurder.playerTargetedEntities.get(player);

    Serializer byteSerializer = Registry.get(Byte.class);

    if(oldTargetEntity != null && !oldTargetEntity.equals(targetEntity)) {
      WrappedDataWatcher watcher = new WrappedDataWatcher();
      watcher.setEntity(player);
      watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, byteSerializer), (byte) 0x0);

      PacketContainer realMetadataPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
      realMetadataPacket.getIntegers().write(0, oldTargetEntity.getEntityId());
      realMetadataPacket.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

      PacketUtil.sendPacket(player, realMetadataPacket);
    }

    if(targetEntity == null)
      return;


    AdvancedMurder.playerTargetedEntities.put(player, targetEntity);

    WrappedDataWatcher watcher = new WrappedDataWatcher();
    watcher.setEntity(player);
    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, byteSerializer), (byte) 0x40);

    PacketContainer glowPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
    glowPacket.getIntegers().write(0, targetEntity.getEntityId());
    glowPacket.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

    PacketUtil.sendPacket(player, glowPacket);
  }
}
