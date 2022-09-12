package io.github.danteserrano;

import io.github.danteserrano.games.GameManager;
import io.github.danteserrano.games.GameManagerCommandExecutor;
import io.github.danteserrano.events.PlayerMoveListener;
import io.github.danteserrano.games.GameManagerTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private GameManager mGameManager;

    public static Main getInstance() {
        return instance;
    }

    @MinecraftEntryPoint
    @Override
    public void onEnable() {
        instance = this;


        PluginManager pm = Bukkit.getPluginManager();

        PlayerMoveListener mPlayerMoveListener = new PlayerMoveListener();
        pm.registerEvents(mPlayerMoveListener, this);


        mGameManager = new GameManager();
        var gameManagerCommand = getCommand("game-manager");
        assert gameManagerCommand != null; // CHECK plugin.yml
        gameManagerCommand.setExecutor(new GameManagerCommandExecutor(mGameManager));
        gameManagerCommand.setTabCompleter(new GameManagerTabCompleter(mGameManager));


        // Create configuration file.
        instance.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        mGameManager.endAllGames();
        PlayerMoveListener.clearCallbacks();
    }
}