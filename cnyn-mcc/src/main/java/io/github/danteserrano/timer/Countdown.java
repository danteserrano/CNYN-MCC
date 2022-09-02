package io.github.danteserrano.timer;

import io.github.danteserrano.Main;
import io.github.danteserrano.arena.Arena;
import io.github.danteserrano.arena.GameState;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private int time;
    private final Arena arena;

    public Countdown(Arena arena) {
        this.arena = arena;
        this.time = 0;
    }

    public void start(int time) {
        arena.setState(GameState.COUNTDOWN);
        this.time = time;
        this.runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    @Override
    public void run() {

        if (time == 0) {
            cancel();
            arena.getGame().start();
            return;
        }

        if (time % 15 == 0 || time <= 10) {
            if (time != 1) {
                arena.broadcast(ChatColor.AQUA + "Game will start in " + time + " seconds.");
            } else {
                arena.broadcast(ChatColor.AQUA + "Game will start in " + time + " seconds.");
            }
        }

        if (arena.getPlayers().size() < arena.getRequiredPlayers()) {
            cancel();
            arena.setState(GameState.WAITING);
            arena.broadcast(ChatColor.RED + "There are too few players. Countdown stopped.");
            return;
        }

        time--;
    }

    public boolean isRunning() {
        return arena.getState() == GameState.COUNTDOWN;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Arena getArena() {
        return arena;
    }
}
