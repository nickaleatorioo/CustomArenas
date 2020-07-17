package me.serafim.plugin.customarena.managers;

import me.serafim.plugin.customarena.CustomArena;

import java.util.List;

/**
 * Class responsible for managing settings
 **/
public final class ConfigurationManager {
    private final boolean useSimpleClan;
    private final boolean prefixMessage;
    private final boolean teleport;
    private final boolean dropItem;
    private final boolean dropItemOnDead;
    private final boolean useCommands;
    private final int immortalTime;
    private final double healingSoup;
    private final double snowBallDamage;
    private final double eggDamage;
    private final List<String> commandsAllowed;

    public ConfigurationManager() {
        CustomArena plugin = CustomArena.getInstance();

        this.useSimpleClan = plugin.getServer().getPluginManager().getPlugin("SimpleClans") != null;
        this.prefixMessage = plugin.getConfig().getBoolean("PrefixMessage");
        this.teleport = plugin.getConfig().getBoolean("TeleporteNaArena");
        this.dropItem = plugin.getConfig().getBoolean("DroparItemNaArena");
        this.dropItemOnDead = plugin.getConfig().getBoolean("DroparItemAoMorrer");
        this.useCommands = plugin.getConfig().getBoolean("UsarComandosNaArena");
        this.immortalTime = plugin.getConfig().getInt("TempoImortal");
        this.healingSoup = plugin.getConfig().getDouble("CuraDaSopa");
        this.snowBallDamage = plugin.getConfig().getDouble("DanoBolaDeNeve");
        this.eggDamage = plugin.getConfig().getDouble("DanoOvo");
        this.commandsAllowed = plugin.getConfig().getStringList("ComandosPermitidos");
    }

    public boolean isUseSimpleClan() {
        return useSimpleClan;
    }

    public boolean isPrefixMessage() {
        return prefixMessage;
    }

    public boolean isTeleport() {
        return teleport;
    }

    public boolean isDropItem() {
        return dropItem;
    }

    public boolean isDropItemOnDead() {
        return dropItemOnDead;
    }

    public boolean isUseCommands() {
        return useCommands;
    }

    public int getImmortalTime() {
        return immortalTime;
    }

    public double getHealingSoup() {
        return healingSoup;
    }

    public double getSnowBallDamage() {
        return snowBallDamage;
    }

    public double getEggDamage() {
        return eggDamage;
    }

    public List<String> getCommandsAllowed() {
        return commandsAllowed;
    }
}
