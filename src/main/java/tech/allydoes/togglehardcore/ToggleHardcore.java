package tech.allydoes.togglehardcore;

import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.lifecycle.GeyserLoadResourcePacksEvent;
import tech.allydoes.togglehardcore.Commands.toggleCommand;
import tech.allydoes.togglehardcore.Events.OnPlayerDeath;

import java.io.File;
import java.util.logging.Level;

public final class ToggleHardcore extends JavaPlugin implements EventRegistrar {

    private static ToggleHardcore plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.getCommand("toggleHardcore").setExecutor(new toggleCommand());

        if (getServer().isHardcore()) {
            this.getLogger().log(Level.WARNING, "Your server is set to hardcore! Please set 'hardcore' in 'server.properties to properly use this.'");
        }

        GeyserApi.api().eventBus().register(this, this);
        getServer().getPluginManager().registerEvents(new OnPlayerDeath(), this);
    }

    @Subscribe
    public void onGeyserLoadResourcePacksEvent(GeyserLoadResourcePacksEvent event) {
        File file = new File("./resources/Packs/HardcoreHearts.zip");
        event.resourcePacks().add(file.toPath());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ToggleHardcore getPlugin() {
        return plugin;
    }
}
