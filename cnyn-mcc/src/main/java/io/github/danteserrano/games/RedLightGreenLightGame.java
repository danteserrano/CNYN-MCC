package io.github.danteserrano.games;

import io.github.danteserrano.arena.Anouncer;
import io.github.danteserrano.arena.ComparableWrapper;
import io.github.danteserrano.arena.PlayerManager;
import io.github.danteserrano.arena.PlayerMoveListener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.function.Consumer;
import java.util.logging.Level;

enum GameState {
    WAITING,
    STARTING,
    GAME,
    PODIUM,
    ENDED,
}

public class RedLightGreenLightGame {
    PlayerManager mPlayers;
    ComparableWrapper<Consumer<PlayerMoveEvent>> mPlayerMoveCallback;
    Anouncer mAnouncer;
    GameState mState = GameState.WAITING;

    public RedLightGreenLightGame() {
        mPlayers = new PlayerManager();
        mAnouncer  = new Anouncer(mPlayers);
    }

    public void endGame() {
        unregisterEventCallbacks();
    }

    public void startGame() {
        registerEventCallbacks();
        Bukkit.getLogger().log(Level.INFO, "Starting Game: RedLightGreenLight");
    }

    void registerEventCallbacks() {
        mPlayerMoveCallback = new ComparableWrapper<>(this::onPlayerMoveEvent);
        PlayerMoveListener.addCallback(mPlayerMoveCallback);
    }

    void unregisterEventCallbacks() {
        PlayerMoveListener.removeCallback(mPlayerMoveCallback);
    }

    void onPlayerMoveEvent(PlayerMoveEvent event) {
        if( mPlayers.getPlayers().contains(event.getPlayer().getUniqueId() )){
            return;
        }
    }
}
