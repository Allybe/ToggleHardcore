package tech.allydoes.togglehardcore.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.allydoes.togglehardcore.ToggleHardcore;

import java.util.HexFormat;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (ToggleHardcore.CheckHardcoreStatus(player)) {
            player.setResourcePack("https://dl.dropboxusercontent.com/scl/fi/fzh1w48rg3xjrgwqox9dw/HardcoreHearts.zip?rlkey=dnusltejyys21x89yfmxgy4c4&st=sgvdzey6&dl=0",
                    HexFormat.of().parseHex("F2A1E227D1036FD1F4AA5F984F14BF20128DEFC5"),
                    "This resource pack is needed if you want hardcore hearts.");
        }
    }
}
