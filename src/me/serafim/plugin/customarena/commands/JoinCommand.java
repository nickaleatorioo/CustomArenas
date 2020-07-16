package me.serafim.plugin.customarena.commands;

import me.serafim.plugin.customarena.Arena;
import me.serafim.plugin.customarena.Command;
import me.serafim.plugin.customarena.CustomArena;
import me.serafim.plugin.customarena.managers.FileManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JoinCommand implements Command {
    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public void onCommand(Player player, org.bukkit.command.Command command, String label, String... arguments) {
        CustomArena plugin = CustomArena.getInstance();

        if (arguments.length >= 2) {
            String arenaNome = arguments[1].toLowerCase();
            if (plugin.getArenaManager().playerInArena(player)) {
                player.sendMessage(FileManager.getMessage("jogador_na_arena"));
            } else {
                if (plugin.getArenaManager().arenaExists(arenaNome)) {
                    Arena arena = plugin.getArenaManager().getArena(arenaNome);
                    if (arena.isOpen()) {
                        if (!inventoryEmpty(player.getInventory().getContents())) {
                            player.sendMessage(FileManager.getMessage("jogador_com_itens"));
                        } else if (!inventoryEmpty(player.getInventory().getArmorContents())) {
                            player.sendMessage(FileManager.getMessage("jogador_com_itens"));
                        } else {

                            if (arena.isClans()) {
                                if (plugin.getArenaManager().maxMembersClanInArena(player, arenaNome)) {
                                    player.sendMessage(FileManager.getMessage("arena_maximo_clan_member"));
                                } else {
                                    player.teleport(arena.getEntry());
                                    plugin.getArenaManager().addPlayer(player, arenaNome);
                                }
                            } else {
                                player.teleport(arena.getEntry());
                                plugin.getArenaManager().addPlayer(player, arenaNome);
                            }
                        }
                    } else {
                        player.sendMessage(FileManager.getMessage("arena_fechada"));
                    }
                } else {
                    player.sendMessage(FileManager.getMessage("arena_nao_existe").replace("{0}", arenaNome));
                }
            }
        } else {
            player.sendMessage(getDescription());
        }
    }

    private boolean inventoryEmpty(ItemStack... itemStacks) {

        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && !itemStack.getType().equals(Material.AIR)) return false;
        }

        return true;
    }

    @Override
    public String getDescription() {
        return FileManager.getMessage("cmd_entrar");
    }
}
