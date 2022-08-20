package io.github.danteserrano.listeners;

import io.github.danteserrano.Manager;
import io.github.danteserrano.arena.Arena;
import io.github.danteserrano.util.Helper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);

        Arena arena = Manager.getInstance().getArena(player.getUniqueId());
        if (arena != null) {
            // Remove the player from the arena.
            arena.removePlayer(player.getUniqueId());

            if (player.getKiller() instanceof Player) {
                Player killer = player.getKiller();
                arena.broadcast(ChatColor.RED + player.getName() + " was killed by " + killer.getName() + ".");
            } else {
                // You could create death messages here depending on the cause
                // of the death, but I'm not going to do that in this tutorial.
                arena.broadcast(ChatColor.RED + player.getName() + " died.");
            }

            // Stop the player from dropping any items.
            event.getDrops().clear();

            Helper.teleportToSpawn(player);
            Helper.clearInventoryAndEffects(player);
        }
    }
}
