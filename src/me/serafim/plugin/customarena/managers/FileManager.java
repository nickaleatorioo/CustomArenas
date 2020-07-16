package me.serafim.plugin.customarena.managers;

import me.serafim.plugin.customarena.Arena;
import me.serafim.plugin.customarena.CustomArena;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileManager {
    private final CustomArena plugin = CustomArena.getInstance();

    public static String getMessage(String key) {
        CustomArena plugin = CustomArena.getInstance();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("Mensagens");
        if (plugin.getConfigurationManager().isPrefixMessage()) {
            return section.getString("prefixo") + section.getString(key);
        } else {
            return section.getString(key);
        }
    }

    public static String getMessageNoPrefix(String key) {
        CustomArena plugin = CustomArena.getInstance();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("Mensagens");
        return section.getString(key);
    }

    public void saveArena(Arena arena) {
        try {
            File file = new File("plugins//CustomArenas//Arenas", arena.getName() + ".yml");
            YamlConfiguration configArena = YamlConfiguration.loadConfiguration(file);

            configArena.set("Open", arena.isOpen());
            configArena.set("Clans", arena.isClans());
            configArena.set("MaxPlayerPerClan", arena.getMaxPlayerPerClan());

            for (int i = 0; i < 36; i++) {
                configArena.set("Inventory." + i, arena.getInventoryContents()[i]);
            }

            for (int i = 0; i < 4; i++) {
                configArena.set("Armor." + i, arena.getArmorContents()[i]);
            }

            arena.getPotionEffects().forEach(potionEffect -> configArena.set("Potions." + potionEffect.getType(), potionEffect.serialize()));
            configArena.set("Entry", arena.getEntry().serialize());
            configArena.set("Exit", arena.getExit().serialize());
            configArena.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteArena(Player player, String name) {
        File file = new File("plugins//CustomArenas//Arenas", name + ".yml");

        if (file.delete()) {
            player.sendMessage(FileManager.getMessage("arena_deletar").replace("{0}", name));
        } else {
            player.sendMessage("§cErro ao deletar");
        }
    }

    public void loadArena(String nome) {
        File file = new File("plugins//CustomArenas//Arenas", nome + ".yml");
        YamlConfiguration configArena = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = configArena.getConfigurationSection("Potions");
        ItemStack[] inventoryContents = new ItemStack[36];
        ItemStack[] armorContents = new ItemStack[4];
        List<PotionEffect> potionEffects = new ArrayList<>();

        for (int i = 0; i < 36; i++) {
            inventoryContents[i] = configArena.getItemStack("Inventory." + i);
        }

        for (int i = 0; i < 4; i++) {
            armorContents[i] = configArena.getItemStack("Armor." + i);
        }

        if (section != null) {
            for (String string : section.getKeys(false)) {
                potionEffects.add(new PotionEffect(section.getConfigurationSection(string).getValues(false)));
            }
        }

        Arena arena = new Arena();
        arena.setName(nome);
        arena.setOpen(configArena.getBoolean("Open"));
        arena.setClans(configArena.getBoolean("Clans"));
        arena.setMaxPlayerPerClan(configArena.getInt("MaxPlayerPerClan"));
        arena.setPotionEffects(potionEffects);
        arena.setInventoryContents(inventoryContents);
        arena.setArmorContents(armorContents);
        arena.setEntry(Location.deserialize(configArena.getConfigurationSection("Entry").getValues(false)));
        arena.setExit(Location.deserialize(configArena.getConfigurationSection("Exit").getValues(false)));

        plugin.getLogger().info("Arena: " + arena.getName() + " loaded");
        plugin.getArenaManager().importArena(arena);
    }

    public void loadAllArenas() {
        File file = new File("plugins//CustomArenas//Arenas");

        if (!file.exists()) {
            if (file.mkdirs()) {
                this.plugin.getLogger().info(file.getName() + " loaded success!");
            } else {
                this.plugin.getLogger().info(file.getName() + " failed to load!");
            }
        }

        for (File file1 : Objects.requireNonNull(file.listFiles())) {
            loadArena(file1.getName().split("\\.")[0]);
        }
    }
}
