package tech.allydoes.togglehardcore.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import tech.allydoes.togglehardcore.ToggleHardcore;

import java.util.Calendar;

public class OnPlayerDeath implements Listener {

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (ToggleHardcore.CheckHardcoreStatus(player)) {
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int daysTillSunday = Calendar.SUNDAY - dayOfWeek;

            if (daysTillSunday <= 0) {
                daysTillSunday += 7;
            }

            calendar.add(Calendar.DAY_OF_WEEK, daysTillSunday);

            player.ban("You have been banned :(", calendar.getTime(), "Death", true);
        }
    }
}
