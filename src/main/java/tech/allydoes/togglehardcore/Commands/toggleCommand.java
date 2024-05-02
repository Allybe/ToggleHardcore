package tech.allydoes.togglehardcore.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import tech.allydoes.togglehardcore.ToggleHardcore;

import java.util.List;

public class toggleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to run this command.");
            return false;
        }

        Player player = (Player) sender;
        boolean isHardcore = player.getMetadata("isHardcore").contains(new FixedMetadataValue(new ToggleHardcore().getPlugin(), true));

        if (isHardcore) {
            sender.sendMessage("You cannot change out of hardcore mode.");
            return false;
        }

        player.setMetadata("isHardcore", new FixedMetadataValue(new ToggleHardcore().getPlugin(), true));
        //player.setResourcePack();
        sender.sendMessage("You are now in Hardcore mode, good luck.");

        return false;
    }
}
