package io.github.danteserrano;

import io.github.danteserrano.games.GameManager;
import io.github.danteserrano.games.GameManagerCommandExecutor;
import io.github.danteserrano.events.PlayerMoveListener;
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
        GameManagerCommandExecutor mGameManagerCommandExecutor = new GameManagerCommandExecutor(mGameManager);
        var gameManagerCommand = getCommand("game-manager");
        assert gameManagerCommand != null; // CHECK plugin.yml
        gameManagerCommand.setExecutor(mGameManagerCommandExecutor);


        // Create configuration file.
        instance.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        mGameManager.endAllGames();
        PlayerMoveListener.clearCallbacks();
    }
}