package io.github.danteserrano.games;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GameManagerCommandExecutor implements CommandExecutor {
    final GameManager mGameManager;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (args.length == 1 && Objects.equals(args[0], "endall")) {
            sender.sendMessage("Ending all games...");
            mGameManager.endAllGames();
            return true;
        } else if (args.length == 2 && Objects.equals(args[0], "create")) {
            String lobbyName = args[1];
            try {
                mGameManager.createNewGame(lobbyName);
            } catch (LobbyExistsException e) {
                sender.sendMessage(e.toString());
                return true;
            }
            sender.sendMessage("Created new game");
            return true;
        } else if (args.length == 3 && Objects.equals(args[0], "addplayer")) {
            String playerName = args[1];
            String lobbyName = args[2];
            var game = mGameManager.getGames().get(lobbyName);
            if(Objects.isNull(game)){
                sender.sendMessage(String.format("Lobby `%s` not found", lobbyName));
                return true;
            }
            var player = Bukkit.getPlayer(playerName);
            if(Objects.isNull(player)){
                sender.sendMessage(String.format("Player `%s` not found", playerName));
                return true;
            }
            game.mPlayers.addPlayer(player.getUniqueId());
            sender.sendMessage(String.format("Added player `%s` to lobby `%s` (%s)", playerName, lobbyName, game.getClass().getSimpleName()));
            return true;
        }
        else if (args.length == 1 && Objects.equals(args[0], "list")) {
            var games = mGameManager.getGames();
            if(games.isEmpty()){
                sender.sendMessage("No active games");
                return true;
            }
            StringBuilder s = new StringBuilder();
            for (var game : games.entrySet()) {
                var lobbyName = game.getKey();
                var gameClass = game.getValue().getClass().getSimpleName();
                s.append(lobbyName);
                s.append(" | ");
                s.append(gameClass);
                s.append("\n");
            }
            sender.sendMessage(s.toString());
            return true;
        } else if (args.length == 2 && Objects.equals(args[0], "start")) {
            var lobbyName = args[1];
            try {
                mGameManager.startGame(lobbyName);
            } catch (LobbyNotFoundException e) {
                sender.sendMessage(e.toString());
                return true;
            }
            sender.sendMessage(String.format("Starting game `%s`", lobbyName));
            return true;
        } else if (args.length == 2 && Objects.equals(args[0], "test")) {
            var testNumber = args[1];
            if(Objects.equals(testNumber, "1")){
                if(!(sender instanceof Player player)){
                    sender.sendMessage("Sender must be a Player for this command");
                    return true;
                }
                player.sendMessage("Executing test 1");
                mGameManager.endAllGames();
                try {
                    mGameManager.createNewGame("test_lobby_1");
                } catch (LobbyExistsException e) {
                    // unreachable
                    assert false;
                }
                var game = mGameManager.getGames().get("test_lobby_1");
                game.mPlayers.addPlayer(player.getUniqueId());
                try {
                    mGameManager.startGame("test_lobby_1");
                } catch (LobbyNotFoundException e) {
                    // unreachable
                    assert false;
                }
                return true;
            }
        } else {
            return false;
        }
        return true;
    }

    public GameManagerCommandExecutor(GameManager gameManager) {
        mGameManager = gameManager;
    }
}