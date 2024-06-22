package tech.allydoes.togglehardcore;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.api.Geyser;
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
import tech.allydoes.togglehardcore.Text.MessageBuilder;
import tech.allydoes.togglehardcore.Text.MessageBuilder.MessageLevel;

import java.util.HexFormat;
import java.util.logging.Level;
import java.io.File;
import java.nio.file.Path;

public final class ToggleHardcore extends JavaPlugin implements EventRegistrar{

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

        getLogger().info("Registering Geyser event bus!");
        Path bedrockResourcePackPath = new File("").toPath();

        GeyserApi.api().eventBus().register(this, this);
        pack = ResourcePack.create(PackCodec.path(bedrockResourcePackPath));
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.FINE, "bye, bye!");
    }

    public void onSessionLoadResourcePacksEvent(SessionLoadResourcePacksEvent event) {
        Player player = Bukkit.getPlayer(event.connection().javaUuid());

        if (ToggleHardcore.checkHardcoreStatus(player)) {
            event.register(pack);
        }
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
        if (Geyser.api().isBedrockPlayer(player.getUniqueId())) {
            player.sendMessage(MessageBuilder().getMessage("You must relog to apply changes.", MessageLevel.WARNING))
            return;
        }

        player.setResourcePack("https://dl.dropboxusercontent.com/scl/fi/fzh1w48rg3xjrgwqox9dw/HardcoreHearts.zip?rlkey=dnusltejyys21x89yfmxgy4c4&st=sgvdzey6&dl=0",
                HexFormat.of().parseHex("F2A1E227D1036FD1F4AA5F984F14BF20128DEFC5"),
                "This resource pack is needed if you want hardcore hearts.");
    }

    public static ToggleHardcore getPlugin() {
        return plugin;
    }
}
