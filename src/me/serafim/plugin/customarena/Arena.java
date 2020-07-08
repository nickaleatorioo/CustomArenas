package me.serafim.plugin.customarena;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Arena {
    private String name;
    private boolean open;
    private boolean clans;
    private int maxPlayerPerClan = 1;
    private List<Player> players = new ArrayList<>();
    private List<PotionEffect> potionEffects = new ArrayList<>();
    private ItemStack[] inventoryContents;
    private ItemStack[] armorContents;
    private Location entry;
    private Location exit;

    public Arena() {
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void broadcast(String message) {
        for (Player player : this.players) {
            player.sendMessage(message);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isClans() {
        return clans;
    }

    public void setClans(boolean clans) {
        this.clans = clans;
    }

    public int getMaxPlayerPerClan() {
        return maxPlayerPerClan;
    }

    public void setMaxPlayerPerClan(int maxPlayerPerClan) {
        this.maxPlayerPerClan = maxPlayerPerClan;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public void setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    public ItemStack[] getInventoryContents() {
        return inventoryContents;
    }

    public void setInventoryContents(ItemStack[] inventoryContents) {
        this.inventoryContents = inventoryContents;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public void setArmorContents(ItemStack[] armorContents) {
        this.armorContents = armorContents;
    }

    public Location getEntry() {
        return entry;
    }

    public void setEntry(Location entry) {
        this.entry = entry;
    }

    public Location getExit() {
        return exit;
    }

    public void setExit(Location exit) {
        this.exit = exit;
    }

    @Override
    public String toString() {
        return "Arena{" +
                "name='" + name + '\'' +
                ", open=" + open +
                ", clans=" + clans +
                ", maxPlayerPerClan=" + maxPlayerPerClan +
                ", players=" + players +
                ", potionEffects=" + potionEffects +
                ", inventoryContents=" + Arrays.toString(inventoryContents) +
                ", armorContents=" + Arrays.toString(armorContents) +
                ", entry=" + entry +
                ", exit=" + exit +
                '}';
    }
}
