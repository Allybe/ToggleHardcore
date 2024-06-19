package tech.allydoes.togglehardcore.TabCompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class AdminCommandTabCompleter implements TabCompleter {
    private static final String[] SubStrings = {"toggle", "status"};

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();

        if (commandSender.hasPermission("ToggleHardcore.admin")) {
            if (args.length >= 2) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getDisplayName());
                }
            } else {
                StringUtil.copyPartialMatches(args[0].toLowerCase(), List.of(SubStrings), completions);
            }
        }

        return completions;
    }
}
