package tech.allydoes.togglehardcore.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.allydoes.togglehardcore.Text.MessageBuilder;
import tech.allydoes.togglehardcore.ToggleHardcore;

public class ToggleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageBuilder.getMessage("You must be a player to run this command.", MessageBuilder.MessageLevel.WARNING));
            return true;
        }

        if (ToggleHardcore.checkHardcoreStatus(player)) {
            sender.sendMessage(MessageBuilder.getMessage("You cannot change out of hardcore mode.", MessageBuilder.MessageLevel.WARNING));
            return true;
        }

        ToggleHardcore.setHardcoreStatus(player, true);
        ToggleHardcore.setHardcoreResourcePack(player);
        sender.sendMessage(MessageBuilder.getMessage("You are now in hardcore, good luck!", MessageBuilder.MessageLevel.INFO));

        return true;
    }
}
