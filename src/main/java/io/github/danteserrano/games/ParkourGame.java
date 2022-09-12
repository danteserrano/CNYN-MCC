package io.github.danteserrano.games;

import io.github.danteserrano.util.Announcer;
import io.github.danteserrano.util.PlayerManager;
import org.jetbrains.annotations.NotNull;

public class ParkourGame {
    final @NotNull PlayerManager mPlayers = new PlayerManager();
    final @NotNull Announcer mAnnouncer;

    public ParkourGame() {
        mAnnouncer = new Announcer(mPlayers);
    }
}
