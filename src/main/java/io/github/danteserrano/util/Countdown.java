package io.github.danteserrano.util;

import io.github.danteserrano.Main;
import io.github.danteserrano.games.Announcer;
import org.bukkit.Bukkit;

public class Countdown {
    public static void startCountdown(int seconds, String title, Announcer announcer, Runnable onComplete) {
        for(int i = 0; i < seconds; i++){
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> announcer.sendTitle(title, String.format("%d", seconds-finalI), 0, 20, 0), 20L *finalI);
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), onComplete, 20L * seconds);
    }
}
