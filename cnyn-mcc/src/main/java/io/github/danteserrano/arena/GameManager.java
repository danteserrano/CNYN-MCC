package io.github.danteserrano.arena;

import io.github.danteserrano.games.RedLightGreenLightGame;

import java.util.TreeMap;

class LobbyExistsException extends Exception {
    String mLobbyName;
    LobbyExistsException(String lobbyName){
        this.mLobbyName = lobbyName;
    }
    public String toString() {
        return String.format("Lobby `%s` already exists", mLobbyName);
    }
}

class LobbyNotFoundException extends  Exception {
    String mLobbyName;
    LobbyNotFoundException(String lobbyName) { this.mLobbyName = lobbyName;}
    public String toString() { return String.format("Lobby `%s` not found", mLobbyName); }
}

public class GameManager {
    private final TreeMap<String, RedLightGreenLightGame> mGames = new TreeMap<>();

    public GameManager() {
    }

    public void createNewGame(String lobbyName) throws LobbyExistsException{
        if ( mGames.containsKey(lobbyName)) {
            throw new LobbyExistsException(lobbyName);
        }
        RedLightGreenLightGame game = new RedLightGreenLightGame();
        mGames.put(lobbyName, game);
        game.startGame();
    }

    public void endGame(String lobbyName) throws LobbyNotFoundException{
        if (!mGames.containsKey(lobbyName)){
            throw new LobbyNotFoundException(lobbyName);
        }
        RedLightGreenLightGame game = mGames.get(lobbyName);
        game.endGame();
        mGames.remove(lobbyName);
    }

    public void endAllGames() {
        for(var game : mGames.values()) {
            game.endGame();
        }
        mGames.clear();
    }
}
