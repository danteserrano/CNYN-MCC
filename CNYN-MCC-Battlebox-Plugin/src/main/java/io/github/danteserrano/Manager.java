package io.github.danteserrano;

import io.github.danteserrano.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class Manager {

    public final static Location SPAWN_POINT = new Location(Bukkit.getWorld("world"), 0.0, 10.0, 0.0);

    public static void setup() {
        //Create 10 new arena objects

        for (int i = 1; i <= 10; i++) {

            new Arena(i, new Location(Bukkit.getWorld("world"), 50.0, 10.0, 50.0));

        }
    }

    private static Manager manager;

    public static Manager getInstance() {
        // Lazy initialisation of the singleton (the instance is checked for being null).
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    private ArrayList<Arena> arenas;

    //Constructor with private visibility prevents class being instantiated outside of this class.
    private Manager() {

        this.arenas = new ArrayList<Arena>();

    }

    public void addArena(Arena arena) {
        arenas.add(arena);
    }

    public void removeArena(Arena arena) {
        arenas.remove(arena);
    }

    public Arena getArena(int id) {
        for (int i=0; i < arenas.size(); i++) {
            final Arena arena = arenas.get(i);

            if (arena.getId() == id) {
                return arena;
            }
        }
        return null;
    }

    public boolean isArena(int id) {
        return getArena(id) != null;
    }

    public Arena getArena(UUID uuid) {
        for (int i = 0; i < arenas.size(); i++) {
            final Arena arena = arenas.get(i);

            if (arena.contains(uuid)) {
                return arena;
            }
        }
        return null;
    }

}
