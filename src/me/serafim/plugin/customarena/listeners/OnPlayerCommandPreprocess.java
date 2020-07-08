package me.serafim.plugin.customarena.listeners;

import me.serafim.plugin.customarena.CustomArena;
import me.serafim.plugin.customarena.managers.FileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnPlayerCommandPreprocess implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        CustomArena plugin = CustomArena.getInstance();
        Player player = event.getPlayer();

        if (plugin.getArenaManager().playerInArena(player)) {
            if (!plugin.getConfigurationManager().isUseCommands()) {
                String command = event.getMessage().split("\\s+")[0].substring(1);
                if (!plugin.getConfigurationManager().getCommandsAllowed().contains(command)) {
                    event.setCancelled(true);
                    player.sendMessage(FileManager.getMessage("arena_comando_bloqueado"));
                }
            }
        }
    }
}
