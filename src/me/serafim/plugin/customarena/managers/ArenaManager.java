package me.serafim.plugin.customarena.managers;

import com.jackproehl.plugins.CombatLog;
import me.serafim.plugin.customarena.Arena;
import me.serafim.plugin.customarena.CustomArena;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaManager {
    private final CustomArena plugin = CustomArena.getInstance();
    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<Player, String> players = new HashMap<>();
    private final List<Player> playersGod = new ArrayList<>();

    public ArenaManager() {
    }

    public void createArena(Player player, String name) {
        Arena arena = new Arena();
        arena.setName(name);
        arena.setOpen(true);
        arena.setClans(false);
        arena.getPotionEffects().addAll(player.getActivePotionEffects());
        arena.setInventoryContents(player.getInventory().getContents());
        arena.setArmorContents(player.getInventory().getArmorContents());
        arena.setEntry(player.getLocation().add(0, 1, 0));
        arena.setExit(this.plugin.getServer().getWorlds().get(0).getSpawnLocation());

        this.plugin.getFileManager().saveArena(arena);
        this.plugin.getFileManager().loadArena(name);
    }

    public void deleteArena(Player player, String name) {
        Arena arena = this.getArena(name);
        List<Player> players = arena.getPlayers();

        for (Player player1 : players) {
            this.removePlayer(player1, true);
        }

        this.arenas.remove(name);
        this.plugin.getFileManager().deleteArena(player, name);
    }

    public void updateArena(Arena arena) {
        this.arenas.remove(arena.getName());
        this.importArena(arena);
        this.plugin.getFileManager().saveArena(arena);
    }

    public boolean maxMembersClanInArena(Player player, String arenaNome) {

        if (!this.plugin.getConfigurationManager().isUseSimpleClan()) {
            return false;
        }


        if (SimpleClans.getInstance().getClanManager().getClanPlayer(player) == null) {
            return false;
        }

        Arena arena = this.getArena(arenaNome);
        Clan clan = SimpleClans.getInstance().getClanManager().getClanPlayer(player).getClan();
        int members = 0;

        if (clan == null) {
            return false;
        }

        for (Player playerArena : arena.getPlayers()) {
            if (clan.isMember(playerArena)) {
                members++;
            }

            if (members >= arena.getMaxPlayerPerClan()) {
                return true;
            }
        }

        return false;
    }

    public void importArena(Arena arena) {
        this.arenas.put(arena.getName(), arena);
    }

    public boolean arenaExists(String name) {
        return this.arenas.containsKey(name);
    }

    public void addPlayer(Player player, String arenaNome) {
        this.players.put(player, arenaNome);
        this.playersGod.add(player);

        Arena arena = this.getArena(arenaNome);

        if (this.plugin.getConfigurationManager().isUseSimpleClan()) {
            if (SimpleClans.getInstance().getClanManager().getClanPlayer(player) != null) {
                SimpleClans.getInstance().getClanManager().getClanPlayer(player).setFriendlyFire(!arena.isClans());
            }
        }

        arena.addPlayer(player);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        arena.broadcast(FileManager.getMessage("arena_jogador_entrou").replace("{0}", player.getName()));
        player.getInventory().setContents(arena.getInventoryContents());
        player.getInventory().setArmorContents(arena.getArmorContents());
        player.addPotionEffects(arena.getPotionEffects());

        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getArenaManager().removePlayerGod(player);
                player.sendMessage(FileManager.getMessage("jogador_imortal_acabou"));
            }
        }.runTaskLater(plugin, plugin.getConfigurationManager().getImmortalTime() * 20);
    }

    public void removePlayer(Player player, boolean teleport) {
        Arena arena = this.getArenaByPlayer(player);
        this.players.remove(player);
        this.playersGod.remove(player);

        if (this.plugin.getConfigurationManager().isUseSimpleClan()) {
            if (SimpleClans.getInstance().getClanManager().getClanPlayer(player) != null) {
                SimpleClans.getInstance().getClanManager().getClanPlayer(player).setFriendlyFire(false);
            }
        }

        arena.removePlayer(player);
        arena.broadcast(FileManager.getMessage("arena_jogador_saiu").replace("{0}", player.getName()));
        limparJogador(player);

        boolean b = true;

        if (teleport) {
            if ((plugin.getServer().getPluginManager().getPlugin("CombatLog") != null)) {
                b = CombatLog.getPlugin(CombatLog.class).blockTeleportationEnabled;
                CombatLog.getPlugin(CombatLog.class).blockTeleportationEnabled = false;
            }

            player.teleport(arena.getExit());

            if ((plugin.getServer().getPluginManager().getPlugin("CombatLog") != null)) {
                CombatLog.getPlugin(CombatLog.class).blockTeleportationEnabled = b;
            }
        }
    }

    public void limparJogador(Player player) {
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void removePlayerGod(Player player) {
        this.playersGod.remove(player);
    }

    public boolean playerInArena(Player player) {
        return this.players.containsKey(player);
    }

    public Arena getArenaByPlayer(Player player) {
        return this.arenas.get(this.players.get(player));
    }

    public Arena getArena(String name) {
        return this.arenas.get(name);
    }

    public Map<String, Arena> getArenas() {
        return arenas;
    }

    public Map<Player, String> getPlayers() {
        return players;
    }

    public List<Player> getPlayersGod() {
        return playersGod;
    }
}
