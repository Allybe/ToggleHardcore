package tech.allydoes.togglehardcore;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import tech.allydoes.togglehardcore.Commands.AdminCommand;
import tech.allydoes.togglehardcore.Commands.ToggleCommand;
import tech.allydoes.togglehardcore.Events.OnPlayerDeath;
import tech.allydoes.togglehardcore.Events.OnPlayerJoin;

import java.util.logging.Level;

public final class ToggleHardcore extends JavaPlugin {

    private static ToggleHardcore plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.getCommand("toggleHardcore").setExecutor(new ToggleCommand());
        this.getCommand("hardcore").setExecutor(new AdminCommand());

        if (getServer().isHardcore()) {
            this.getLogger().log(Level.WARNING, "Your server is set to hardcore! Please set 'hardcore' in 'server.properties to properly use this.'");
        }

        getServer().getPluginManager().registerEvents(new OnPlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.FINE, "bye, bye!");
    }

    public static boolean CheckHardcoreStatus(Player player) {
        boolean isHardcore = false;
        NamespacedKey key = new NamespacedKey(ToggleHardcore.getPlugin(), "isHardcore");
        if (player.getPersistentDataContainer().has(key)) {
            isHardcore = Boolean.TRUE.equals(player.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN));
        }

        return isHardcore;
    }

    public static void SetHardcoreStatus(Player player, boolean status) {
        NamespacedKey key = new NamespacedKey(ToggleHardcore.getPlugin(), "isHardcore");
        player.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, status);
    }

    public static ToggleHardcore getPlugin() {
        return plugin;
    }
}
