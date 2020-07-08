package me.serafim.plugin.customarena.listeners;

import me.serafim.plugin.customarena.CustomArena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeath implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        CustomArena plugin = CustomArena.getInstance();
        Player player = event.getEntity();

        if (plugin.getArenaManager().playerInArena(player)) {
            plugin.getArenaManager().removePlayer(player);
            if (!plugin.getConfigurationManager().isDropItemOnDead()) {
                event.getDrops().clear();
                event.setDroppedExp(0);
            }
        }
    }
}
