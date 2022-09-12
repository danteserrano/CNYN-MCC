package io.github.danteserrano.games;

public enum GameState {
    // This is the state just after game instance creation
    // In this state, players can join and form teams
    WAITING,
    // This is the state after calling Game.create()
    // During STARTING, the game teleports players to spawnpoints
    // and initializes other variables.
    STARTING,
    // This is the active state of the game
    GAME,
    // This state occurs when the game finishes and winners are announced
    PODIUM,
    // This state gets sets after calling Game.end()
    // The game should remove all events and tasks
    ENDING,
    ENDED,
}
