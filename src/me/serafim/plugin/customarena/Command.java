package me.serafim.plugin.customarena;

import org.bukkit.entity.Player;

public interface Command {
    String getPermission();

    void onCommand(Player player, org.bukkit.command.Command command, String label, String... arguments);

    String getDescription();
}
