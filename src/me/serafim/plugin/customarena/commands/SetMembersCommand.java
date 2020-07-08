package me.serafim.plugin.customarena.commands;

import me.serafim.plugin.customarena.Arena;
import me.serafim.plugin.customarena.Command;
import me.serafim.plugin.customarena.CustomArena;
import me.serafim.plugin.customarena.managers.FileManager;
import org.bukkit.entity.Player;

public class SetMembersCommand implements Command {
    @Override
    public String getPermission() {
        return "arena.admin";
    }

    @Override
    public void onCommand(Player player, org.bukkit.command.Command command, String label, String... arguments) {
        CustomArena plugin = CustomArena.getInstance();

        if (arguments.length >= 3) {

            if (isNumeric(arguments[2])) {
                String arenaNome = arguments[1].toLowerCase();
                int size = Integer.parseInt(arguments[2]);
                if (size < 1) {
                    player.sendMessage(getDescription());
                } else {
                    if (plugin.getArenaManager().arenaExists(arenaNome)) {
                        player.sendMessage(FileManager.getMessage("arena_setmembros").replace("{0}", arguments[2]).replace("{1}", arenaNome));
                        Arena arena = plugin.getArenaManager().getArena(arenaNome);
                        arena.setMaxPlayerPerClan(size);
                        plugin.getFileManager().saveArena(arena);
                    } else {
                        player.sendMessage(FileManager.getMessage("arena_nao_existe").replace("{0}", arenaNome));
                    }
                }
            }
        } else {
            player.sendMessage(getDescription());
        }
    }

    private boolean isNumeric(String string) {
        return string.matches("-?\\d+(\\.\\d+)?");
    }

    @Override
    public String getDescription() {
        return FileManager.getMessage("cmd_setmembros");
    }
}
