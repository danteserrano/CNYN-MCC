package io.github.danteserrano.games;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import io.github.danteserrano.Main;
import io.github.danteserrano.events.VehicleEnterListener;
import io.github.danteserrano.util.Announcer;
import io.github.danteserrano.util.ComparableWrapper;
import io.github.danteserrano.util.Countdown;
import io.github.danteserrano.util.PlayerManager;
import io.github.danteserrano.util.Util;
import io.github.danteserrano.util.Vector3;

public class MusicalChairsGame implements Game {
    PlayerManager mPlayers = new PlayerManager();
    PlayerManager mPlayingPlayers = new PlayerManager();
    GameState mState = GameState.WAITING;
    Announcer mAnnouncer;
    boolean mIsMusicPlaying = false;
    int mAvailableChairs = 0;
    Random mRandom = new Random();
    Vector3 mGameCenter = Vector3.fromConfig("musical-chairs-game-center");
    private ComparableWrapper<Consumer<VehicleEnterEvent>> mVehicleEnterCallback;

    public MusicalChairsGame() {
        mAnnouncer = new Announcer(mPlayers);
    }

    private void gameSetup() {
        int numPlayers = mPlayers.getPlayers().size();
        double distanceBetweenChairs = 2.0;
        double radius = (numPlayers * distanceBetweenChairs) / (2 * Math.PI);
        double angleBetweenChairs = 360.0 / numPlayers;
        double angle = 0.0;
        var players = mPlayers.getPlayers().iterator();
        for (int i = 0; i < numPlayers; i++) {
            double x = mGameCenter.x + radius * Math.cos(Math.toRadians(angle));
            double z = mGameCenter.z + radius * Math.sin(Math.toRadians(angle));
            angle += angleBetweenChairs;
            UUID playerUniqueId = players.next();
            Player player = Bukkit.getPlayer(playerUniqueId);
            if (!Objects.isNull(player)) {
                Location l = Util.lookAt(new Location(player.getWorld(), x, mGameCenter.y, z), mGameCenter.toLocation(player.getWorld()));
                player.teleport(l);
            }
        }
    }

    public void playMusic() {
        if (!mIsMusicPlaying) {
            mIsMusicPlaying = true;
            mAnnouncer.sendMessage("Music is playing!");
            for (Player player : mPlayers.getOnlinePlayersEntities()) {
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_5, 1, 1);
                player.leaveVehicle();
            }
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                stopMusic();
            }, 20 * (mRandom.nextInt(10) + 5));
        }
    }

    public void stopMusic() {
        if (mIsMusicPlaying) {
            mIsMusicPlaying = false;
            mAnnouncer.sendMessage("Music has stopped!");
            for (Player player : mPlayers.getOnlinePlayersEntities()) {
                player.stopSound(Sound.MUSIC_DISC_5);
            }
        }
    }

    @Override
    public void startGame() {
        mState = GameState.STARTING;
        mAnnouncer.sendMessage("Musical Chairs game is starting!");
        gameSetup();
        registerEventCallbacks();
        // Start countdown:
        Countdown.startCountdown(5, "Musical Chairs are starting!", mAnnouncer, () -> {
            mState = GameState.GAME;
            mAnnouncer.sendMessage("Game has started!");
            playMusic();
        });
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
        mPlayingPlayers.addPlayer(playerUniqueId);
    }

    @Override
    public GameState getState() {
        return mState;
    }

    private void onVehicleEnterEvent(VehicleEnterEvent event) {
        if(mState != GameState.GAME) { return; }
        if(!(event.getEntered() instanceof Player)) { return; }
        Player player = (Player) event.getEntered();
        if(!mPlayers.getPlayers().contains(player.getUniqueId())) { return; }
        if(!mPlayingPlayers.getPlayers().contains(player.getUniqueId())) { 
            // ur not playing dude
            event.setCancelled(true);
            return;
        }
    }

    private void registerEventCallbacks() {
        mVehicleEnterCallback = new ComparableWrapper<>(this::onVehicleEnterEvent);
        VehicleEnterListener.addCallback(mVehicleEnterCallback);
    }

    private void unregisterEventCallbacks() {
        VehicleEnterListener.removeCallback(mVehicleEnterCallback);
    }
}
