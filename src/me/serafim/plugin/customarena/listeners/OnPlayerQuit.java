package me.serafim.plugin.customarena.listeners;

import com.jackproehl.plugins.CombatLog;
import me.serafim.plugin.customarena.CustomArena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuit implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        CustomArena plugin = CustomArena.getInstance();
        Player player = event.getPlayer();

        if (plugin.getArenaManager().playerInArena(player)) {
            boolean combat = false;

            if ((plugin.getServer().getPluginManager().getPlugin("CombatLog") != null)) {
                combat = CombatLog.getPlugin(CombatLog.class).taggedPlayers.containsKey(player.getName());
            }

            plugin.getArenaManager().removePlayer(player, !combat);
        }
    }
}
