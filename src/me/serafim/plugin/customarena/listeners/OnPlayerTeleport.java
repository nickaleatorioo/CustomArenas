package me.serafim.plugin.customarena.listeners;

import me.serafim.plugin.customarena.CustomArena;
import me.serafim.plugin.customarena.managers.FileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class OnPlayerTeleport implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        CustomArena plugin = CustomArena.getInstance();
        Player player = event.getPlayer();

        if (plugin.getArenaManager().playerInArena(player)) {
            if (!plugin.getConfigurationManager().isTeleport()) {
                event.setCancelled(true);
                player.sendMessage(FileManager.getMessage("arena_teleporte_bloqueado"));
            }
        }
    }
}
