package io.github.danteserrano.events;

import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Consumer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.jetbrains.annotations.Nullable;

import io.github.danteserrano.util.ComparableWrapper;

public class VehicleEnterListener {
    static private final TreeSet<ComparableWrapper<Consumer<VehicleEnterEvent>>> CALLBACKS = new TreeSet<>();

    @EventHandler
    public void onPlayerMove(VehicleEnterEvent event) {
        for (var callback : CALLBACKS ) {
            callback.getInner().accept(event);
        }
    }

    public static void addCallback(@Nullable ComparableWrapper<Consumer<VehicleEnterEvent>> callback) {
        if(Objects.isNull(callback)) { return; }
        CALLBACKS.add(callback);
    }

    public static void removeCallback(@Nullable ComparableWrapper<Consumer<VehicleEnterEvent>> callback) {
        if(Objects.isNull(callback)) { return; }
        CALLBACKS.remove(callback);
    }

    public static void clearCallbacks() {
        CALLBACKS.clear();
    }
}
