package me.serafim.plugin.customarena.listeners;

import me.serafim.plugin.customarena.CustomArena;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnPlayerInteract implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        CustomArena plugin = CustomArena.getInstance();
        Player player = event.getPlayer();

        if (plugin.getArenaManager().playerInArena(player)) {
            if (player.getItemInHand().getType().equals(Material.MUSHROOM_SOUP)) {
                if (player.getHealth() < player.getMaxHealth()) {
                    double healingSoup = plugin.getConfigurationManager().getHealingSoup();
                    if (player.getHealth() + healingSoup > player.getMaxHealth()) {
                        player.setHealth(player.getMaxHealth() - player.getHealth() + player.getHealth());
                    } else {
                        player.setHealth(player.getHealth() + healingSoup);
                    }
                    player.getItemInHand().setType(Material.BOWL);
                }
            }
        }
    }
}
