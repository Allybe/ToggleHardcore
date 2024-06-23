package tech.allydoes.togglehardcore;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.bedrock.SessionLoadResourcePacksEvent;
import org.geysermc.geyser.api.pack.PackCodec;
import org.geysermc.geyser.api.pack.ResourcePack;

import tech.allydoes.togglehardcore.Commands.AdminCommand;
import tech.allydoes.togglehardcore.Commands.DayCounterCommand;
import tech.allydoes.togglehardcore.Commands.ToggleCommand;
import tech.allydoes.togglehardcore.Events.OnPlayerDeath;
import tech.allydoes.togglehardcore.Events.OnPlayerJoin;
import tech.allydoes.togglehardcore.TabCompleters.AdminCommandTabCompleter;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HexFormat;
import java.util.Objects;
import java.util.logging.Level;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

public final class ToggleHardcore extends JavaPlugin implements EventRegistrar {

    private static ToggleHardcore plugin;
    private static ResourcePack pack;

    @Override
    public void onEnable() {
        plugin = this;
        this.getCommand("toggleHardcore").setExecutor(new ToggleCommand());
        this.getCommand("hardcore").setExecutor(new AdminCommand());
        this.getCommand("hardcore").setTabCompleter(new AdminCommandTabCompleter());
        this.getCommand("dayCounter").setExecutor(new DayCounterCommand());

        if (getServer().isHardcore()) {
            this.getLogger().log(Level.SEVERE, "Your server is set to hardcore! Please set 'hardcore' in 'server.properties' to 'true' for ToggleHardcore to work properly.");
        }

        getServer().getPluginManager().registerEvents(new OnPlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);

        performChecks();

        Path bedrockResourcePackPath = new File(getDataFolder(), "bedrock_hearts.zip").toPath();
        getLogger().info("Registering Geyser event bus!");
        GeyserApi.api().eventBus().register(this, this);
        GeyserApi.api().eventBus().subscribe(this, SessionLoadResourcePacksEvent.class, this::onSessionLoadResourcePacksEvent);
        pack = ResourcePack.create(PackCodec.path(bedrockResourcePackPath));
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.FINE, "bye, bye!");
    }

    private void performChecks() {
        if (!Files.exists(getDataFolder().toPath())) {
            try {
                System.out.println(getDataFolder().toPath());
                Files.createDirectory(getDataFolder().toPath());
                Files.copy(
                        Objects.requireNonNull(getResource("bedrock_hearts.zip")),
                        getDataFolder().toPath().resolve("bedrock_hearts.zip")
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Files.exists(getDataFolder().toPath().resolve("bedrock_hearts.zip"))) {
            try {
                Files.copy(
                        Objects.requireNonNull(getResource("bedrock_hearts.zip")),
                        getDataFolder().toPath().resolve("bedrock_hearts.zip")
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onSessionLoadResourcePacksEvent(SessionLoadResourcePacksEvent event) {
        //TODO: get texture pack to only send to hardcore players
    }

    public static boolean checkHardcoreStatus(Player player) {
        boolean isHardcore = false;
        NamespacedKey key = new NamespacedKey(ToggleHardcore.getPlugin(), "isHardcore");
        if (player.getPersistentDataContainer().has(key)) {
            isHardcore = Boolean.TRUE.equals(player.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN));
        }

        return isHardcore;
    }

    public static void setHardcoreStatus(Player player, boolean status) {
        NamespacedKey isHardcoreKey = new NamespacedKey(ToggleHardcore.getPlugin(), "isHardcore");
        player.getPersistentDataContainer().set(isHardcoreKey, PersistentDataType.BOOLEAN, status);

        if (status) {
            player.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
            setHardcoreResourcePack(player);
        } else {
            player.removeResourcePacks();
        }
    }

    public static void setHardcoreResourcePack(Player player) {
        player.setResourcePack("https://dl.dropboxusercontent.com/scl/fi/fzh1w48rg3xjrgwqox9dw/HardcoreHearts.zip?rlkey=dnusltejyys21x89yfmxgy4c4&st=sgvdzey6&dl=0",
                HexFormat.of().parseHex("F2A1E227D1036FD1F4AA5F984F14BF20128DEFC5"),
                "This resource pack is needed if you want hardcore hearts.");
    }

    public static ToggleHardcore getPlugin() {
        return plugin;
    }
}
