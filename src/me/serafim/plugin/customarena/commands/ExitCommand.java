package me.serafim.plugin.customarena.commands;

import me.serafim.plugin.customarena.Command;
import me.serafim.plugin.customarena.CustomArena;
import me.serafim.plugin.customarena.managers.FileManager;
import org.bukkit.entity.Player;

public class ExitCommand implements Command {
    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public void onCommand(Player player, org.bukkit.command.Command command, String label, String... arguments) {
        CustomArena plugin = CustomArena.getInstance();

        if (plugin.getArenaManager().playerInArena(player)) {
            plugin.getArenaManager().removePlayer(player,true);
        } else {
            player.sendMessage(FileManager.getMessage("jogador_sem_arena"));
        }

    }

    @Override
    public String getDescription() {
        return FileManager.getMessage("cmd_sair");
    }
}
