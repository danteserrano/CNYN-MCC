package io.github.danteserrano.kits;

import io.github.danteserrano.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class Kit implements Listener {

    public static Kit getKit(UUID uuid, KitType type) {
        switch (type) {
            case EMPTYBAG:
                return new Emptybag(uuid);
            case DIAMONDHAND:
                return new Diamondhand(uuid);
            default:
                return null;
        }
    }

    protected final UUID uuid;
    protected final KitType type;
    protected final ItemStack[] items;

    public Kit(UUID uuid, KitType type, ItemStack... items){
        this.uuid = uuid;
        this.type = type;
        this.items = items;

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    public void remove() {
        HandlerList.unregisterAll(this);
    }

    public abstract void onStart(Player player);

    public abstract void update(Player player, int time);

    public UUID getUUID() {
        return uuid;
    }

    public KitType getType() {
        return type;
    }

    public ItemStack[] getItems() {
        return items;
    }
}
