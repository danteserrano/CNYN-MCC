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
    private final TreeMap<String, RedLightGreenLightGame> mGames = new TreeMap<>();

    public GameManager() {
    }

    public void createNewGame(String lobbyName) throws LobbyExistsException {
        if (mGames.containsKey(lobbyName)) {
            throw new LobbyExistsException(lobbyName);
        }
        RedLightGreenLightGame game = new RedLightGreenLightGame();
        mGames.put(lobbyName, game);
    }

    public boolean startGame(String lobbyName) throws LobbyNotFoundException {
        if (!mGames.containsKey(lobbyName)) {
            throw new LobbyNotFoundException(lobbyName);
        }
        var game = mGames.get(lobbyName);
        return game.transitionState(io.github.danteserrano.games.GameState.STARTING);
    }

    public void endGame(String lobbyName) throws LobbyNotFoundException {
        if (!mGames.containsKey(lobbyName)) {
            throw new LobbyNotFoundException(lobbyName);
        }
        RedLightGreenLightGame game = mGames.get(lobbyName);
        game.endGame();
        mGames.remove(lobbyName);
    }

    public @NotNull TreeMap<String, RedLightGreenLightGame> getGames() {
        return mGames;
    }

    public void endAllGames() {
        for (var game : mGames.values()) {
            game.endGame();
        }
        mGames.clear();
    }
}
