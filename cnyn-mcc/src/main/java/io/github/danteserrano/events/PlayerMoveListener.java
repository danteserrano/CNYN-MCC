package io.github.danteserrano.events;

import io.github.danteserrano.util.ComparableWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
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

    public static void addCallback(@Nullable ComparableWrapper<Consumer<PlayerMoveEvent>> callback) {
        if(Objects.isNull(callback)) { return; }
        CALLBACKS.add(callback);
    }

    public static void removeCallback(@Nullable ComparableWrapper<Consumer<PlayerMoveEvent>> callback) {
        if(Objects.isNull(callback)) { return; }
        CALLBACKS.remove(callback);
    }

    public static void clearCallbacks() {
        CALLBACKS.clear();
    }
}
