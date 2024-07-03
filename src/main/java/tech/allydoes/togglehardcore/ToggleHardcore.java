package tech.allydoes.togglehardcore;

import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import tech.allydoes.togglehardcore.Commands.AdminCommand;
import tech.allydoes.togglehardcore.Commands.DayCounterCommand;
import tech.allydoes.togglehardcore.Commands.ToggleCommand;
import tech.allydoes.togglehardcore.Events.OnPlayerDeath;
import tech.allydoes.togglehardcore.Events.OnPlayerJoin;
import tech.allydoes.togglehardcore.TabCompleters.AdminCommandTabCompleter;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.logging.Level;

public final class ToggleHardcore extends JavaPlugin {

    private static ToggleHardcore plugin;

    private ProtocolManager protocolManager;

    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

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

        protocolManager.addPacketListener(new PacketAdapter(
            this,
            PacketType.Play.Server.LOGIN
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                packet.
                if (checkHardcoreStatus(event.getPlayer())) {
                    
                }
            }
        });
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.FINE, "bye, bye!");
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
        player.setResourcePack(getPlugin().getClassLoader().getResource("HardcoreHearts.zip").toString(),
                HexFormat.of().parseHex("F2A1E227D1036FD1F4AA5F984F14BF20128DEFC5"),
                "This resource pack is needed if you want hardcore hearts.");
    }

    public static ToggleHardcore getPlugin() {
        return plugin;
    }
}
