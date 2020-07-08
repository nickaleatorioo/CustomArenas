package me.serafim.plugin.customarena.commands;

import me.serafim.plugin.customarena.Command;
import me.serafim.plugin.customarena.CustomArena;
import org.bukkit.entity.Player;

public class DefaultCommand implements Command {
    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public void onCommand(Player player, org.bukkit.command.Command command, String label, String... arguments) {
        for (Command comando : CustomArena.getInstance().getCommandManager().getCommands().values()) {
            if (comando.getPermission() == null || player.hasPermission(comando.getPermission())) {
                player.sendMessage(comando.getDescription());
            }
        }
    }

    @Override
    public String getDescription() {
        return null;
    }
}
