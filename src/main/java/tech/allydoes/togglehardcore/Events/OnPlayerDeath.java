package tech.allydoes.togglehardcore.Events;

import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import tech.allydoes.togglehardcore.ToggleHardcore;

public class OnPlayerDeath implements Listener {

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        NamespacedKey key = new NamespacedKey(ToggleHardcore.getPlugin(), "isHardcore");
        boolean isHardcore = player.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN);

        if (isHardcore) {
            player.setGameMode(GameMode.SPECTATOR);
            event.setDeathMessage("You were in Hardcore Mode! Sorry, but you are now in spectator until the end of the week.");
        }
    }
}
