package me.serafim.plugin.customarena.commands;

import me.serafim.plugin.customarena.Arena;
import me.serafim.plugin.customarena.Command;
import me.serafim.plugin.customarena.CustomArena;
import me.serafim.plugin.customarena.managers.FileManager;
import org.bukkit.entity.Player;

public class DeleteCommand implements Command {
    @Override
    public String getPermission() {
        return "arena.admin";
    }

    @Override
    public void onCommand(Player player, org.bukkit.command.Command command, String label, String... arguments) {

        CustomArena plugin = CustomArena.getInstance();

        if (arguments.length >= 2) {
            String arenaNome = arguments[1].toLowerCase();
            if (plugin.getArenaManager().arenaExists(arenaNome)) {
                Arena arena = plugin.getArenaManager().getArena(arenaNome);
                if (arena.getPlayers().isEmpty()) {
                    plugin.getArenaManager().deleteArena(player, arenaNome);
                } else {
                    player.sendMessage(FileManager.getMessage("arena_com_players"));
                }
            } else {
                player.sendMessage(FileManager.getMessage("arena_nao_existe").replace("{0}", arenaNome));
            }
        } else {
            player.sendMessage(getDescription());
        }
    }

    @Override
    public String getDescription() {
        return FileManager.getMessage("cmd_deletar");
    }
}
