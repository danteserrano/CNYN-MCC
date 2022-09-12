package io.github.danteserrano.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

class TeamNotFoundException extends Exception {
    final String mTeamName;
    TeamNotFoundException(String teamName){
        this.mTeamName = teamName;
    }
    public String toString() {
        return String.format("Team `%s` not found", mTeamName);
    }
}

public class PlayerManager {
    static TreeMap<String, TreeSet<UUID>> TEAMS;

    private final TreeSet<UUID> mPlayers = new TreeSet<>();

    public void addPlayer(UUID uuid) {
        mPlayers.add(uuid);
    }

    public @NotNull TreeSet<UUID> getPlayers() {
        return mPlayers;
    }

    public void addPlayersFromTeam(String teamName) throws TeamNotFoundException{
        var team = PlayerManager.TEAMS.get(teamName);
        if (Objects.isNull(team)) {
            throw new TeamNotFoundException(teamName);
        }
        for (var player : team) {
            this.addPlayer(player);
        }
    }

    public @NotNull Vector<Player> getOnlinePlayersEntities() {
        Vector<Player> output = new Vector<>();
        for (UUID uuid : getPlayers()) {
            var player = Bukkit.getPlayer(uuid);
            if(Objects.isNull(player)){
                continue;
            }
            output.add(player);
        }
        return output;
    }

    public void clearInventories() {
        for (var player : getOnlinePlayersEntities()) {
            player.getInventory().clear();
        }
    }

    public boolean contains(Player player) {
        return mPlayers.contains(player.getUniqueId());
    }
}
