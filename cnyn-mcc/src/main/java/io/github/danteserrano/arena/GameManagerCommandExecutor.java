package io.github.danteserrano.arena;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.logging.Level;

public class GameManagerCommandExecutor implements CommandExecutor {
    GameManager mGameManager;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1) {
            sender.sendMessage("Provide command arguments");
            return false;
        }
        sender.sendMessage("First argument:");
        sender.sendMessage(args[0]);
        Bukkit.getLogger().log(Level.INFO, "First argument:");
        Bukkit.getLogger().log(Level.INFO, args[0]);
        if(Objects.equals(args[0], "create")){
            sender.sendMessage("Called create");
            if(args.length < 2){
                sender.sendMessage("Please specify lobby name");
                return false;
            }
            String lobbyName = args[1];
            try {
                mGameManager.createNewGame(lobbyName);
            } catch (LobbyExistsException e) {
                sender.sendMessage("Lobby already exists. Use a different name.");
                return true;
            }
            sender.sendMessage("Created new game");
            return true;
        }
        sender.sendMessage(String.format("Argument count: %d", args.length));
        return false;
    }

    public GameManagerCommandExecutor(GameManager gameManager) {
        mGameManager = gameManager;
    }
}