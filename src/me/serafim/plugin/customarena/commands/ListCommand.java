package me.serafim.plugin.customarena.commands;

import me.serafim.plugin.customarena.Arena;
import me.serafim.plugin.customarena.Command;
import me.serafim.plugin.customarena.CustomArena;
import me.serafim.plugin.customarena.managers.FileManager;
import org.bukkit.entity.Player;

public class ListCommand implements Command {
    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public void onCommand(Player player, org.bukkit.command.Command command, String label, String... arguments) {
        StringBuilder builder = new StringBuilder();
        CustomArena plugin = CustomArena.getInstance();

        for (Arena arena : plugin.getArenaManager().getArenas().values()) {
            builder.append(FileManager.getMessage("cmd_lista_formato").replace("{0}", arena.getName()).replace("{1}", String.valueOf(arena.getPlayers().size()))).append("\n");
        }

        if (builder.toString().isEmpty()) {
            player.sendMessage(FileManager.getMessage("arena_lista_vazia"));
            return;
        }

        player.sendMessage(builder.toString());
    }

    @Override
    public String getDescription() {
        return FileManager.getMessage("cmd_lista");
    }
}
