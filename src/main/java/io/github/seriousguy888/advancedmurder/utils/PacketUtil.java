package io.github.seriousguy888.advancedmurder.utils;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class PacketUtil {
  public static void sendPacket(Player player, PacketContainer packet) {
    ProtocolManager protocolManager = AdvancedMurder.getProtocolManager();

    try {
      protocolManager.sendServerPacket(player, packet);
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
