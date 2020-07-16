package me.serafim.plugin.customarena.managers;

import me.serafim.plugin.customarena.Arena;
import me.serafim.plugin.customarena.CustomArena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final Map<String, me.serafim.plugin.customarena.Command> commands = new HashMap<>();
    private final CustomArena plugin = CustomArena.getInstance();

    public Map<String, me.serafim.plugin.customarena.Command> getCommands() {
        return commands;
    }

    public void registerCommand(String name, me.serafim.plugin.customarena.Command command) {
        this.commands.put(name, command);
    }

    public void unRegisterCommand(String name) {
        this.commands.remove(name);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length <= 0) {
                this.commands.get("default").onCommand(player, command, s, strings);
            } else {
                String commandName = strings[0];
                if (this.commands.containsKey(commandName)) {
                    me.serafim.plugin.customarena.Command commandArena = this.commands.get(commandName);
                    if (commandArena.getPermission() == null || player.hasPermission(commandArena.getPermission())) {
                        commandArena.onCommand(player, command, s, strings);
                    } else {
                        player.sendMessage(FileManager.getMessage("cmd_no_perm"));
                    }
                } else {
                    player.sendMessage(FileManager.getMessage("cmd_desconhecido"));
                }
            }
        } else {
            commandSender.sendMessage("comando destinado a jogador");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> stringList = new ArrayList<>();

        if (strings.length == 1) {
            for (String string : this.commands.keySet()) {
                if (string.startsWith(strings[0])) {
                    stringList.add(string);
                }
            }
            return stringList;
        }

        if (strings.length == 2) {
            switch (strings[0].toLowerCase()) {
                case "open":
                case "entrar":
                case "deletar":
                case "setentrada":
                case "setsaida":
                case "setpot":
                case "setkit":
                case "setclan":
                case "setmembros":

                    for (Arena arena : plugin.getArenaManager().getArenas().values()) {
                        if (arena.getName().startsWith(strings[1])) {
                            stringList.add(arena.getName());
                        }
                    }

                    return stringList;
                default:
                    return null;
            }
        }
        return null;
    }
}
