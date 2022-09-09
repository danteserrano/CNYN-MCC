package io.github.danteserrano.games;

import io.github.danteserrano.Main;
import io.github.danteserrano.util.ComparableWrapper;
import io.github.danteserrano.events.PlayerMoveListener;
import io.github.danteserrano.util.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.logging.Level;

class StateTransition implements Comparable<StateTransition> {
    final GameState mFrom;
    final GameState mTo;

    StateTransition(GameState from, GameState to) {
        mFrom = from;
        mTo = to;
    }

    @Override
    public int compareTo(@NotNull StateTransition o) {
        int x = mFrom.compareTo(o.mFrom);
        if (x != 0) {
            return x;
        }
        x = mTo.compareTo(o.mTo);
        return x;
    }
}

public class RedLightGreenLightGame {
    final @NotNull PlayerManager mPlayers = new PlayerManager();
    final @NotNull PlayerManager mDeadPlayers = new PlayerManager();
    final @NotNull PlayerManager mSafePlayers = new PlayerManager();
    @Nullable ComparableWrapper<Consumer<PlayerMoveEvent>> mPlayerMoveCallback;
    final @NotNull Announcer mAnnouncer;
    @NotNull
    GameState mState = GameState.WAITING;
    boolean mIsRedLight = false;
    @Nullable BukkitTask mGameLogicTickTask;
    @NotNull Random mRand = new Random();

    public RedLightGreenLightGame() {
        mAnnouncer = new Announcer(mPlayers);
    }

    public void endGame() {
        if(!Objects.isNull(mGameLogicTickTask)){
            mGameLogicTickTask.cancel();
        }
        unregisterEventCallbacks();
        for (var player : mPlayers.getOnlinePlayersEntities()) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

    /**
     * Transitions game to target state if a transition is defined
     *
     * @return true if transition is valid, false if transition is invalid
     */
    public boolean transitionState(GameState target) {
        TreeMap<StateTransition, Runnable> transitions = new TreeMap<>();
        transitions.put(new StateTransition(GameState.WAITING, GameState.STARTING), () -> {
            mState = GameState.STARTING;
            Bukkit.getLogger().log(Level.INFO, "Starting Game: RedLightGreenLight");
            mAnnouncer.sendMessage("Red Light Green Light game is starting!");
            mPlayers.clearInventories();
            registerEventCallbacks();
            Countdown.startCountdown(5, "Starting in...", mAnnouncer, () -> {
                mState = GameState.GAME;
                mGameLogicTickTask = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), this::gameLogicTick, 0, 40);
            });
        });
        var transition = transitions.get(new StateTransition(mState, target));
        if (Objects.isNull(transition)) {
            return false;
        }
        transition.run();
        return true;
    }

    void gameLogicTick() {
        if(mIsRedLight){
            if(mRand.nextDouble() <= 0.3){
                switchToGreenLight();
            }
        } else {
            if(mRand.nextDouble() <= 0.7){
                switchToRedLight();
            }
        }
    }

    public @NotNull GameState getState() {
        return mState;
    }

    private void registerEventCallbacks() {
        mPlayerMoveCallback = new ComparableWrapper<>(this::onPlayerMoveEvent);
        PlayerMoveListener.addCallback(mPlayerMoveCallback);
    }

    private void unregisterEventCallbacks() {
        PlayerMoveListener.removeCallback(mPlayerMoveCallback);
    }

    private void onPlayerMoveEvent(@NotNull PlayerMoveEvent event) {
        if (!mPlayers.getPlayers().contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (mState == GameState.STARTING) {
            event.setCancelled(true);
        }
        if (mState == GameState.GAME && mIsRedLight) {
            redLightMove(event);
        }
    }

    private void redLightMove(PlayerMoveEvent event) {
        var player = event.getPlayer();
        if(mDeadPlayers.contains(player)) { return; }
        if(mSafePlayers.contains(player)) { return; }
        var from = new Vector3(event.getFrom());
        var toL = event.getTo();
        if(Objects.isNull(toL)){ return; }
        var to = new Vector3(toL);
        if(from.distance_squared(to) < 0.01) { return; }
        killPlayer(player);
    }

    private void killPlayer(Player player) {
        player.sendTitle(ChatColor.RED+"BAM!", "you moved", 0, 40, 10);
        mDeadPlayers.addPlayer(player.getUniqueId());
        player.setAllowFlight(true);
        player.setFlying(true);
        player.teleport(player.getLocation().add(0, 5, 0));
    }

    private void switchToRedLight() {
        mAnnouncer.sendTitle(ChatColor.RED+"RED LIGHT", "stop", 0, 40, 20);
        mAnnouncer.sendMessage(ChatColor.RED+"Red Light");
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            mIsRedLight = true;
        }, 20);
    }

    private void switchToGreenLight() {
        mAnnouncer.sendTitle(ChatColor.GREEN+"GREEN LIGHT", "run", 0, 20, 10);
        mAnnouncer.sendMessage(ChatColor.GREEN+"Green Light");
        mIsRedLight = false;
    }
}
