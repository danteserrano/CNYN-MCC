package io.github.danteserrano.games;

import org.jetbrains.annotations.NotNull;

import java.util.TreeMap;

class LobbyExistsException extends Exception {
    final String mLobbyName;

    LobbyExistsException(String lobbyName) {
        this.mLobbyName = lobbyName;
    }

    public String toString() {
        return String.format("Lobby `%s` already exists", mLobbyName);
    }
}

class LobbyNotFoundException extends Exception {
    final String mLobbyName;

    LobbyNotFoundException(String lobbyName) {
        this.mLobbyName = lobbyName;
    }

    public String toString() {
        return String.format("Lobby `%s` not found", mLobbyName);
    }
}

public class GameManager {
    private final TreeMap<String, Game> mGames = new TreeMap<>();

    public GameManager() {
    }

    public void createNewGame(String lobbyName, GameType gameType) throws LobbyExistsException {
        if (mGames.containsKey(lobbyName)) {
            throw new LobbyExistsException(lobbyName);
        }
        Game game;
        switch (gameType) {
            case PARKOUR -> game = new ParkourGame();
            case RED_LIGHT_GREEN_LIGHT -> game = new RedLightGreenLightGame();
            case MUSICAL_CHAIRS -> game = new MusicalChairsGame();
            default -> game = null;
        }
        mGames.put(lobbyName, game);
    }

    public void startGame(String lobbyName) throws LobbyNotFoundException {
        if (!mGames.containsKey(lobbyName)) {
            throw new LobbyNotFoundException(lobbyName);
        }
        var game = mGames.get(lobbyName);
        game.startGame();
    }

    public void endGame(String lobbyName) throws LobbyNotFoundException {
        if (!mGames.containsKey(lobbyName)) {
            throw new LobbyNotFoundException(lobbyName);
        }
        Game game = mGames.get(lobbyName);
        game.endGame();
        mGames.remove(lobbyName);
    }

    public @NotNull TreeMap<String, Game> getGames() {
        return mGames;
    }

    public void endAllGames() {
        for (var game : mGames.values()) {
            game.endGame();
        }
        mGames.clear();
    }
}
