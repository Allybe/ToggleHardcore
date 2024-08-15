package tech.allydoes.togglehardcore.Commands;

import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.allydoes.togglehardcore.Text.MessageBuilder;
import tech.allydoes.togglehardcore.ToggleHardcore;

public class DayCounterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandName, String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(MessageBuilder.getMessage("You must be a player to run this command.", MessageBuilder.MessageLevel.ERROR));
            return true;
        }

        if (ToggleHardcore.checkHardcoreStatus(player)) {
            int timeSinceDeath = player.getStatistic(Statistic.TIME_SINCE_DEATH);
            timeSinceDeath /= 20; // Assuming 20 TPS we convert the amount of ticks to seconds,
            timeSinceDeath /= 60; // Then minutes
            timeSinceDeath /= 20; // And Minecraft Days which is 20 minutes

            commandSender.sendMessage(MessageBuilder.getMessage("You've survived " + (double) timeSinceDeath + " Days!", MessageBuilder.MessageLevel.INFO));
        } else {
            commandSender.sendMessage(MessageBuilder.getMessage("You aren't in hardcore mode", MessageBuilder.MessageLevel.WARNING));
        }

        return true;
    }
}
