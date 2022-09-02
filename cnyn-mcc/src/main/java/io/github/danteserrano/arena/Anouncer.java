package io.github.danteserrano.arena;

public class Anouncer {
    private PlayerManager mPlayerManager;

    public Anouncer(PlayerManager playerManager) {
        mPlayerManager = playerManager;
    }

    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for(var player : mPlayerManager.getOnlinePlayersEntities()) {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }
}
