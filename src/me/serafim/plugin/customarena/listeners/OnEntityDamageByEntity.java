package me.serafim.plugin.customarena.listeners;

import me.serafim.plugin.customarena.CustomArena;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnEntityDamageByEntity implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        CustomArena plugin = CustomArena.getInstance();
        Player player;
        Player damage;
        Snowball snowball;
        Egg egg;
        Entity entity = event.getEntity();
        Entity entityDamage = event.getDamager();

        if (entity instanceof Player) {
            player = (Player) entity;
            if (plugin.getArenaManager().playerInArena(player)) {
                if (entityDamage instanceof Snowball) {
                    snowball = (Snowball) entityDamage;
                    if (snowball.getShooter() instanceof Player) {
                        damage = (Player) snowball.getShooter();
                        if (plugin.getArenaManager().playerInArena(damage)) {
                            event.setDamage(plugin.getConfigurationManager().getSnowBallDamage());
                        } else {
                            event.setCancelled(true);
                        }
                    }
                } else if (entityDamage instanceof Egg) {
                    egg = (Egg) entityDamage;
                    if (egg.getShooter() instanceof Player) {
                        damage = (Player) egg.getShooter();
                        if (plugin.getArenaManager().playerInArena(damage)) {
                            event.setDamage(plugin.getConfigurationManager().getEggDamage());
                        } else {
                            event.setCancelled(true);
                        }
                    }
                } else if (entityDamage instanceof Player) {
                    damage = (Player) entityDamage;
                    if (!plugin.getArenaManager().playerInArena(damage)) {
                        event.setCancelled(true);
                    } else if (plugin.getArenaManager().getPlayersGod().contains(player)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
