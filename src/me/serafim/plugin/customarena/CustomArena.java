package me.serafim.plugin.customarena;

import me.serafim.plugin.customarena.commands.*;
import me.serafim.plugin.customarena.listeners.*;
import me.serafim.plugin.customarena.managers.ArenaManager;
import me.serafim.plugin.customarena.managers.CommandManager;
import me.serafim.plugin.customarena.managers.ConfigurationManager;
import me.serafim.plugin.customarena.managers.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

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
        this.getLogger().info("Arenas Carregadas: " + getArenaManager().getArenas().size());
    }

    @Override
    public void onDisable() {
    }
}
