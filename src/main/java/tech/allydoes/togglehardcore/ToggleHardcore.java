package tech.allydoes.togglehardcore;

import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.injector.temporary.MinimalInjector;
import com.comphenix.protocol.injector.temporary.TemporaryPlayer;
import com.comphenix.protocol.injector.temporary.TemporaryPlayerFactory;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import tech.allydoes.togglehardcore.Commands.AdminCommand;
import tech.allydoes.togglehardcore.Commands.DayCounterCommand;
import tech.allydoes.togglehardcore.Commands.ToggleCommand;
import tech.allydoes.togglehardcore.Events.OnPlayerDeath;
import tech.allydoes.togglehardcore.TabCompleters.AdminCommandTabCompleter;
import tech.allydoes.togglehardcore.Text.MessageBuilder;

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

        protocolManager.addPacketListener(new PacketAdapter(
            this,
            ListenerPriority.NORMAL,
            PacketType.Play.Server.LOGIN
        ) {
            @Override
            public void onPacketSending(PacketEvent event) {
                var fields = event.getPacket().getModifier().getFields();
                for (int i = 0; i < fields.size(); i++) {
                    var field = fields.get(i).getField();
                    if (field.getName() == "hardcore") {
                        Player player;
                        if (event.isPlayerTemporary()) {
                            getLogger().log(Level.INFO, "Temp player received");
                            //MinimalInjector injector = TemporaryPlayerFactory.getInjectorFromPlayer(event.getPlayer());
                            return;
                        } else {
                            player = event.getPlayer();
                        }

                        event.getPacket().getModifier().write(i, checkHardcoreStatus(player));
                    }
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
        } else {
            player.sendMessage(MessageBuilder.getMessage("You are no longer in hardcore mode. Please relog to complete all changes.", MessageBuilder.MessageLevel.INFO));
        }
    }

    public static ToggleHardcore getPlugin() {
        return plugin;
    }
}
