package tech.allydoes.togglehardcore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.allydoes.togglehardcore.ToggleHardcore;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandName, String[] args) {
        if (commandSender.hasPermission("ToggleHardcore.admin") && args.length >= 2) {
            switch(args[0].toLowerCase()) {
                case "toggle":
                    toggleHardcore(commandSender, args[1]);
                    break;
                case "status":
                    sendHardcoreStatus(commandSender, args[1]);
                    break;
                 default:
                     commandSender.sendMessage("Invalid arguments");
                    break;
            }
        }

        return true;
    }

    private void toggleHardcore(CommandSender commandSender, String playerName) {
        Player targetPlayer = Bukkit.getPlayerExact(playerName);
        if (targetPlayer == null) {
            commandSender.sendMessage("This player doesn't exist");
            return;
        }

        boolean hardcoreStatus = ToggleHardcore.checkHardcoreStatus(targetPlayer);
        ToggleHardcore.setHardcoreStatus(targetPlayer, !hardcoreStatus);

        commandSender.sendMessage(playerName + "'s hardcore status was set to: " + !hardcoreStatus);
    }

    private void sendHardcoreStatus(CommandSender commandSender, String playerName) {
        Player targetPlayer = Bukkit.getPlayerExact(playerName);
        if (targetPlayer == null) {
            commandSender.sendMessage("This player doesn't exist");
            return;
        }

        boolean hardcoreStatus = ToggleHardcore.checkHardcoreStatus(targetPlayer);
        commandSender.sendMessage(playerName + "'s hardcore status: " + hardcoreStatus);
    }
}
