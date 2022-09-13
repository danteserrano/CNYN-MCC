package io.github.danteserrano.games;

import io.github.danteserrano.events.PlayerMoveListener;
import io.github.danteserrano.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;

public class ParkourGame implements Game{
    final @NotNull PlayerManager mPlayers = new PlayerManager();
    final @NotNull Announcer mAnnouncer;
    @Nullable ComparableWrapper<Consumer<PlayerMoveEvent>> mPlayerMoveCallback;
    @NotNull TreeMap<UUID, Integer> mPlayerCheckpoints = new TreeMap<UUID, Integer>();

    GameState mState = GameState.WAITING;
    CollisionBox mKillZone;
    ArrayList<Vector3> mCheckpoints;

    public ParkourGame() {
        mAnnouncer = new Announcer(mPlayers);
        mKillZone = CollisionBox.fromConfig("parkour-kill-zone");
        mCheckpoints = Vector3.arrayFromConfig("parkour-checkpoints");
    }

    @Override
    public void startGame() {
        mState = GameState.STARTING;
        Bukkit.getLogger().log(Level.INFO, "Starting Game: Parkour");
        mAnnouncer.sendMessage("Parkour game is starting!");

        mPlayers.clearInventories();
        registerEventCallbacks();

        Countdown.startCountdown(5, "Starting in...", mAnnouncer, () -> mState = GameState.GAME);
    }

    @Override
    public void endGame() {
        mState = GameState.ENDING;
        unregisterEventCallbacks();
        mState = GameState.ENDED;
    }

    @Override
    public void addPlayer(UUID playerUniqueId) {
        mPlayers.addPlayer(playerUniqueId);
        mPlayerCheckpoints.put(playerUniqueId, 0);
    }

    @Override
    public GameState getState() {
        return mState;
    }

    private void registerEventCallbacks() {
        mPlayerMoveCallback = new ComparableWrapper<>(this::onPlayerMoveEvent);
        PlayerMoveListener.addCallback(mPlayerMoveCallback);
    }

    private void unregisterEventCallbacks() {
        PlayerMoveListener.removeCallback(mPlayerMoveCallback);
    }

    private void switchPlayerCheckpoint(Player player, Integer checkpointIndex) {
        if(checkpointIndex >= mCheckpoints.size()) {
            player.sendMessage("Tried to switch to a checkpoint that doesn't exist");
            return;
        }
        UUID uuid = player.getUniqueId();
        mPlayerCheckpoints.put(uuid, checkpointIndex);
        player.sendMessage(String.format("Checkpoint! (%d/%d)", checkpointIndex+1, mCheckpoints.size()));
        player.stopSound(Sound.ENTITY_PLAYER_LEVELUP);
    }

    private void incrementPlayerCheckpoint(Player player) {
        UUID uuid = player.getUniqueId();
        Integer currentCheckpointIndex = mPlayerCheckpoints.get(uuid);
        Integer nextCheckpointIndex = currentCheckpointIndex + 1;
        switchPlayerCheckpoint(player, nextCheckpointIndex);
    }

    private boolean isNearNextCheckpoint(Player player) {
        Vector3 location = new Vector3(player.getLocation());
        UUID uuid = player.getUniqueId();
        Integer currentCheckpointIndex = mPlayerCheckpoints.get(uuid);
        Integer nextCheckpointIndex = currentCheckpointIndex + 1;
        if(nextCheckpointIndex >= mCheckpoints.size()) { return false; }
        Vector3 nextCheckpointLocation = mCheckpoints.get(nextCheckpointIndex);
        double distance =  nextCheckpointLocation.distance(location);
        return (distance < 2.0);
    }

    private void onPlayerMoveEvent(@NotNull PlayerMoveEvent event) {
        if (!mPlayers.getPlayers().contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (mState == GameState.STARTING) {
            event.setCancelled(true);
        }
        if (mState == GameState.GAME) {
            Player player = event.getPlayer();
            if(isNearNextCheckpoint(player)){
                incrementPlayerCheckpoint(player);
            }
        }
    }
}
