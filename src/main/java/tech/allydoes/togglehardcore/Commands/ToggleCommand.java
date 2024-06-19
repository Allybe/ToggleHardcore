package tech.allydoes.togglehardcore.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.allydoes.togglehardcore.ToggleHardcore;

public class ToggleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to run this command.");
            return true;
        }

        if (ToggleHardcore.checkHardcoreStatus(player)) {
            sender.sendMessage("You cannot change out of hardcore mode.");
            return true;
        }

        ToggleHardcore.setHardcoreStatus(player, true);
        ToggleHardcore.setHardcoreResourcePack(player);
        sender.sendMessage("You are now in Hardcore mode, good luck.");

        return true;
    }
}
