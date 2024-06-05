package tech.allydoes.togglehardcore;

import org.bukkit.plugin.java.JavaPlugin;
import tech.allydoes.togglehardcore.Commands.toggleCommand;
import tech.allydoes.togglehardcore.Events.OnPlayerDeath;
import tech.allydoes.togglehardcore.Events.OnPlayerJoin;

import java.util.logging.Level;

public final class ToggleHardcore extends JavaPlugin {

    private static ToggleHardcore plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.getCommand("toggleHardcore").setExecutor(new toggleCommand());

        if (getServer().isHardcore()) {
            this.getLogger().log(Level.WARNING, "Your server is set to hardcore! Please set 'hardcore' in 'server.properties to properly use this.'");
        }

        getServer().getPluginManager().registerEvents(new OnPlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ToggleHardcore getPlugin() {
        return plugin;
    }
}
