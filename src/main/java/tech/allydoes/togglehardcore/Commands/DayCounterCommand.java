package tech.allydoes.togglehardcore.Commands;

import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.allydoes.togglehardcore.ToggleHardcore;

public class DayCounterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandName, String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("You must be a player to run this command.");
            return true;
        }

        if (ToggleHardcore.checkHardcoreStatus(player)) {
            int timeSinceDeath = player.getStatistic(Statistic.TIME_SINCE_DEATH);
            timeSinceDeath /= 1000; // Seconds
            timeSinceDeath /= 60; // Mins
            timeSinceDeath /= 20; // (20 Mins) Minecraft Days

            Math.floor(timeSinceDeath);
            commandSender.sendMessage("You've survived " + timeSinceDeath + " Days!");
        } else {
            commandSender.sendMessage("You aren't in hardcore mode");
        }

        return true;
    }
}
