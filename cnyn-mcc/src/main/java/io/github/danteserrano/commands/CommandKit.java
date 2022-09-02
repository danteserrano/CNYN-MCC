package io.github.danteserrano.commands;

import io.github.danteserrano.Manager;
import io.github.danteserrano.arena.Arena;
import io.github.danteserrano.arena.GameState;
import io.github.danteserrano.kits.Kit;
import io.github.danteserrano.kits.KitType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player may execute this command!");
            return true;
        }

        final Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("kit")) {

            if (args.length != 1) {
                // Invalid command syntax.
                player.sendMessage(ChatColor.RED + "You must include a kit to select.");
                return true;
            }

            /*
             * Check the player is in an arena (they have to be to select a kit)
             * and check if the string inputted matches a valid kit.
             */

            final Arena arena = Manager.getInstance().getArena(player.getUniqueId());

            if (arena == null) {
                player.sendMessage(ChatColor.RED + "You must be in an arena to choose a kit.");
                return true;
            }

            if (!KitType.isKit(args[0])) {
                player.sendMessage(ChatColor.RED + "That is not a valid kit.");
                return true;
            }

            final KitType type = KitType.getKit(args[0]);

            if (arena.getState() == GameState.STARTED) {
                player.sendMessage(ChatColor.RED + "You cannot select a kit after the game has started.");
                return true;
            }

            // Check if the player already has a kit.
            final Kit current = arena.getKit(player.getUniqueId());

            // If the player had a kit previously.
            if (current != null) {
                current.remove();
            }

            final Kit kit = Kit.getKit(player.getUniqueId(), type);

            arena.setKit(player.getUniqueId(), kit);

            player.sendMessage(
                    ChatColor.AQUA + "You have chosen the " + kit.getType().getName().toLowerCase() + " kit.");

            return true;
        }

        return true;
    }

}
