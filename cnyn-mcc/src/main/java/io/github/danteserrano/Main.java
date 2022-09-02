package io.github.danteserrano;

import io.github.danteserrano.arena.GameManager;
import io.github.danteserrano.arena.GameManagerCommandExecutor;
import io.github.danteserrano.arena.PlayerMoveListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private GameManager mGameManager;
    private GameManagerCommandExecutor mGameManagerCommandExecutor;
    private PlayerMoveListener mPlayerMoveListener;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;


        PluginManager pm = Bukkit.getPluginManager();

        mPlayerMoveListener = new PlayerMoveListener();
        pm.registerEvents(mPlayerMoveListener, this);


        mGameManager = new GameManager();
        mGameManagerCommandExecutor = new GameManagerCommandExecutor(mGameManager);
        var gameManagerCommand = getCommand("game-manager");
        gameManagerCommand.setExecutor(mGameManagerCommandExecutor);

        // Create configuration file.
        // instance.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        mGameManager.endAllGames();
        PlayerMoveListener.clearCallbacks();
    }
}