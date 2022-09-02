package io.github.danteserrano.util;

import io.github.danteserrano.Manager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;


public class Helper {

    public static ItemStack createItem(ChatColor colour, String name, Material material){
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName((colour + name));
        item.setItemMeta(meta);

        return item;
    }

    public static void teleportToSpawn(Player player) {
        player.teleport(Manager.SPAWN_POINT);
    }

    public static void clearInventoryAndEffects(Player player) {
        clearInventory(player);
        clearPotionEffects(player);
    }

    public static void clearInventory(Player player) {
        player.getInventory().clear();
        player.getEquipment().clear();
    }

    public static void clearPotionEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

}
