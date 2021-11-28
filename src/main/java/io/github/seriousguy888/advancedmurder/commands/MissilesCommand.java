package io.github.seriousguy888.advancedmurder.commands;

import io.github.seriousguy888.advancedmurder.AdvancedMurder;
import io.github.seriousguy888.advancedmurder.files.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MissilesCommand implements CommandExecutor {
  DataManager dataManager = AdvancedMurder.getDataManager();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(!(sender instanceof Player player))
      return false;

    if(args.length > 0 && args[0].equalsIgnoreCase("toggle")) {
      if(args.length > 1 && args[1].equalsIgnoreCase("homing")) {
        boolean homingDisabled = false;
        String key = "players." + player.getUniqueId() + ".missiles.disable_homing";
        if(dataManager.getConfig().contains(key))
          homingDisabled = dataManager.getConfig().getBoolean(key);
        dataManager.getConfig().set(key, !homingDisabled);
        dataManager.saveConfig();

        player.sendMessage("Homing missiles are now " + (homingDisabled ? "enabled" : "disabled") + ".");
      } else {
        return false;
      }
    } else {
      return false;
    }

    return true;
  }
}
