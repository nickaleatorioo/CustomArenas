package me.serafim.plugin.customarena;

import com.jackproehl.plugins.CombatLog;
import me.serafim.plugin.customarena.commands.*;
import me.serafim.plugin.customarena.listeners.*;
import me.serafim.plugin.customarena.managers.ArenaManager;
import me.serafim.plugin.customarena.managers.CommandManager;
import me.serafim.plugin.customarena.managers.ConfigurationManager;
import me.serafim.plugin.customarena.managers.FileManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomArena extends JavaPlugin {
    private static CustomArena instance;
    private ConfigurationManager configurationManager;
    private CommandManager commandManager;
    private ArenaManager arenaManager;
    private FileManager fileManager;

    public static CustomArena getInstance() {
        return instance;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.configurationManager = new ConfigurationManager();
        this.commandManager = new CommandManager();
        this.arenaManager = new ArenaManager();
        this.fileManager = new FileManager();
        this.commandManager.registerCommand("default", new DefaultCommand());
        this.commandManager.registerCommand("open", new OpenCloseCommand());
        this.commandManager.registerCommand("entrar", new JoinCommand());
        this.commandManager.registerCommand("sair", new ExitCommand());
        this.commandManager.registerCommand("lista", new ListCommand());
        this.commandManager.registerCommand("criar", new CreateCommand());
        this.commandManager.registerCommand("deletar", new DeleteCommand());
        this.commandManager.registerCommand("setentrada", new SetEntryCommand());
        this.commandManager.registerCommand("setsaida", new SetExitCommand());
        this.commandManager.registerCommand("setkit", new SetKitCommand());
        this.commandManager.registerCommand("setpot", new SetPotCommand());
        this.commandManager.registerCommand("setclan", new SetClanCommand());
        this.commandManager.registerCommand("setmembros", new SetMembersCommand());
        this.getFileManager().loadAllArenas();
        this.getCommand("arena").setExecutor(this.commandManager);
        this.getCommand("arena").setTabCompleter(this.commandManager);
        this.getServer().getPluginManager().registerEvents(new OnEntityDamageByEntity(), instance);
        this.getServer().getPluginManager().registerEvents(new OnPlayerCommandPreprocess(), instance);
        this.getServer().getPluginManager().registerEvents(new OnPlayerDeath(), instance);
        this.getServer().getPluginManager().registerEvents(new OnPlayerDropItem(), instance);
        this.getServer().getPluginManager().registerEvents(new OnPlayerInteract(), instance);
        this.getServer().getPluginManager().registerEvents(new OnPlayerQuit(), instance);
        this.getServer().getPluginManager().registerEvents(new OnPlayerTeleport(), instance);
        this.getLogger().info(this.getDescription().getName() + " loaded in version " + this.getDescription().getVersion());
        this.getLogger().info("Use SimpleClans: " + getConfigurationManager().isUseSimpleClan());
        boolean combatLog = getServer().getPluginManager().getPlugin("CombatLog") != null;
        this.getLogger().info("Use CombatLog: " + combatLog);
        this.getLogger().info("Arenas Carregadas: " + getArenaManager().getArenas().size());
    }

    @Override
    public void onDisable() {

        Collection<Arena> arenas = this.getArenaManager().getArenas().values();
        Map<Player, Location> playerLocationMap = new HashMap<>();

        for (Arena arena : arenas) {
            for (Player player : arena.getPlayers()) {
                playerLocationMap.put(player, arena.getExit());
            }
        }

        boolean b = true;

        if ((this.getServer().getPluginManager().getPlugin("CombatLog") != null)) {
            b = CombatLog.getPlugin(CombatLog.class).blockTeleportationEnabled;
            CombatLog.getPlugin(CombatLog.class).blockTeleportationEnabled = false;
        }

        for (Map.Entry<Player, Location> player : playerLocationMap.entrySet()) {
            this.getArenaManager().limparJogador(player.getKey());
            player.getKey().teleport(player.getValue().add(0, 2, 0));
            this.getLogger().info("Jogador \"" + player.getKey().getName() + "\" foi forçado a sair da arena");
        }

        if ((this.getServer().getPluginManager().getPlugin("CombatLog") != null)) {
            CombatLog.getPlugin(CombatLog.class).blockTeleportationEnabled = b;
        }

        this.getArenaManager().getArenas().clear();
    }
}
