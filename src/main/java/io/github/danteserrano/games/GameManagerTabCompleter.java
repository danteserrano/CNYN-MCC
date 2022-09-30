package io.github.danteserrano.games;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameManagerTabCompleter implements TabCompleter {
    private final GameManager mGameManager;

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> output = new ArrayList<>();
        if(args.length == 1){
            output.add("endall");
            output.add("create");
            output.add("addplayer");
            output.add("list");
            output.add("start");
            output.add("test");
        } else if (args.length == 2 && args[0].equals("create")) {
            output.add("RED_LIGHT_GREEN_LIGHT");
            output.add("PARKOUR");
            output.add("MUSICAL_CHAIRS");
        } else if (args.length == 2 && args[0].equals("start")) {
            output.addAll(mGameManager.getGames().keySet());
        } else if (args.length == 3 && args[0].equals("addplayer")) {
            output.addAll(mGameManager.getGames().keySet());
        } else if (args.length == 2 && args[0].equals("addplayer")) {
            var players = Bukkit.getOnlinePlayers();
            for (var player : players) {
                output.add(player.getName());
            }
        } else if (args.length == 2 && args[0].equals("test")) {
            output.add("1");
            output.add("2");
        }
        return output;
    }

    public GameManagerTabCompleter(GameManager gameManager) {
        mGameManager = gameManager;
    }
}
