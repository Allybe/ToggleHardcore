package tech.allydoes.togglehardcore.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.allydoes.togglehardcore.ToggleHardcore;

import java.util.HexFormat;

public class ToggleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to run this command.");
            return true;
        }

        if (ToggleHardcore.CheckHardcoreStatus((Player) sender)) {
            sender.sendMessage("You cannot change out of hardcore mode.");
            return true;
        }

        ToggleHardcore.SetHardcoreStatus((Player) sender, true);
        player.setResourcePack("https://dl.dropboxusercontent.com/scl/fi/fzh1w48rg3xjrgwqox9dw/HardcoreHearts.zip?rlkey=dnusltejyys21x89yfmxgy4c4&st=sgvdzey6&dl=0",
                HexFormat.of().parseHex("F2A1E227D1036FD1F4AA5F984F14BF20128DEFC5"),
                "This resource pack is needed if you want hardcore hearts.");
        sender.sendMessage("You are now in Hardcore mode, good luck.");

        return true;
    }
}
