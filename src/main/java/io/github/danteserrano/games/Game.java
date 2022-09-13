package io.github.danteserrano.games;

import java.util.UUID;

public interface Game {
    // State should be STARTING after this
    // and shortly thereafter transition to GAME
    void startGame();
    // State should be ENDED after this
    void endGame();

    // Add player to game
    void addPlayer(UUID playerUniqueId);

    GameState getState();
}
