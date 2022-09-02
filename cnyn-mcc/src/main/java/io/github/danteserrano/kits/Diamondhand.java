package io.github.danteserrano.kits;

import io.github.danteserrano.util.Helper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Diamondhand extends Kit {

    public Diamondhand(UUID uuid) {
        super(uuid, KitType.DIAMONDHAND, Helper.createItem(ChatColor.GREEN, "Shinny Rock", Material.DIAMOND),
                Helper.createItem(ChatColor.GREEN, "Shinny Rock Hat", Material.DIAMOND_HELMET ));
    }

    @Override
    public void onStart(Player player) {

    }

    @Override
    public void update(Player player, int time) {

    }
}
