package io.github.danteserrano.timer;

import io.github.danteserrano.arena.Arena;
import io.github.danteserrano.arena.GameState;
import io.github.danteserrano.kits.Kit;
import io.github.danteserrano.util.Helper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map.Entry;
import java.util.UUID;

public class Game extends BukkitRunnable {

    private final Arena arena;
    private int time;

    public Game(Arena arena) {
        this.arena = arena;
        this.time = 0;
    }

    public void start() {
        arena.setState(GameState.STARTED);
        arena.broadcast(ChatColor.AQUA + "The game has started!");

        for (Entry<UUID, Kit> entry : arena.getKits().entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            Kit kit = entry.getValue();

            Helper.clearInventoryAndEffects(player);

            kit.onStart(player);

            player.teleport(arena.getLocation());

            for (int i = 0; i < kit.getItems().length; i++) {
                player.getInventory().addItem(kit.getItems()[i]);
            }
        }
    }

    @Override
    public void run() {

        for (Entry<UUID, Kit> entry : arena.getKits().entrySet()) {
            entry.getValue().update(Bukkit.getPlayer(entry.getKey()), time);
        }

        time++;
    }
}
