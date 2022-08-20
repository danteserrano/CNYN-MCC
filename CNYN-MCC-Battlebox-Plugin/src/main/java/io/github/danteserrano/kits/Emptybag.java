package io.github.danteserrano.kits;

import io.github.danteserrano.util.Helper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Emptybag extends Kit {

    public Emptybag(UUID uuid) {
        super(uuid, KitType.EMPTYBAG, Helper.createItem(ChatColor.GREEN, "Empty bag", Material.SHULKER_BOX),
                Helper.createItem(ChatColor.GREEN, "Empty bag creature", Material.SHULKER_SPAWN_EGG ));
    }

    @Override
    public void onStart(Player player) {

    }

    @Override
    public void update(Player player, int time) {

    }
}
