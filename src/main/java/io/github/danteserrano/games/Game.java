package io.github.danteserrano.games;

public interface Game {
    // State should be STARTING after this
    // and shortly thereafter transition to GAME
    void startGame();
    // State should be ENDED after this
    void endGame();

    GameState getState();
}
