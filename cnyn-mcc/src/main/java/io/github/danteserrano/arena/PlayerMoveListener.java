package io.github.danteserrano.arena;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.TreeSet;
import java.util.function.Consumer;

public class PlayerMoveListener implements Listener {
    static private final TreeSet<ComparableWrapper<Consumer<PlayerMoveEvent>>> CALLBACKS = new TreeSet<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        for (var callback : CALLBACKS ) {
            callback.getInner().accept(event);
        }
    }

    public static void addCallback(ComparableWrapper<Consumer<PlayerMoveEvent>> callback) {
        CALLBACKS.add(callback);
    }

    public static void removeCallback(ComparableWrapper<Consumer<PlayerMoveEvent>> callback) {
        CALLBACKS.remove(callback);
    }

    public static void clearCallbacks() {
        CALLBACKS.clear();
    }
}
