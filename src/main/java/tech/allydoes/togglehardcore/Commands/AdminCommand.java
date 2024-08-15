package tech.allydoes.togglehardcore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.allydoes.togglehardcore.Text.MessageBuilder;
import tech.allydoes.togglehardcore.ToggleHardcore;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandName, String[] args) {
        if (commandSender.hasPermission("ToggleHardcore.admin") && args.length >= 2) {
            Player targetPlayer = Bukkit.getPlayerExact(args[1]);
            if (targetPlayer == null) {
                commandSender.sendMessage(MessageBuilder.getMessage("This player doesn't exist", MessageBuilder.MessageLevel.WARNING));
                return true;
            }

            switch(args[0].toLowerCase()) {
                case "toggle":
                    toggleHardcore(commandSender, targetPlayer);
                    break;
                case "status":
                    sendHardcoreStatus(commandSender, targetPlayer);
                    break;
                 default:
                     commandSender.sendMessage(MessageBuilder.getMessage("This command doesn't exist", MessageBuilder.MessageLevel.WARNING));
                    break;
            }
        }

        return true;
    }

    private void toggleHardcore(CommandSender commandSender, Player player) {
        boolean hardcoreStatus = ToggleHardcore.checkHardcoreStatus(player);
        ToggleHardcore.setHardcoreStatus(player, !hardcoreStatus);

        commandSender.sendMessage(MessageBuilder.getMessage(player.getDisplayName() + "'s hardcore status was set to: " + !hardcoreStatus, MessageBuilder.MessageLevel.INFO));
    }

    private void sendHardcoreStatus(CommandSender commandSender, Player player) {
        boolean hardcoreStatus = ToggleHardcore.checkHardcoreStatus(player);
        commandSender.sendMessage(MessageBuilder.getMessage(player.getDisplayName() + "'s hardcore status: " + hardcoreStatus, MessageBuilder.MessageLevel.INFO));
    }
}
