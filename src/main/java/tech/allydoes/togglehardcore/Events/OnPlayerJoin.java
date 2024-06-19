package tech.allydoes.togglehardcore.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.allydoes.togglehardcore.ToggleHardcore;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (ToggleHardcore.checkHardcoreStatus(player)) {
            ToggleHardcore.setHardcoreResourcePack(player);
        }
    }
}
