package io.github.danteserrano.games;

public class Announcer {
    private final PlayerManager mPlayerManager;
    private int mFadeIn = 5;
    private int mStay = 40;
    private int mFadeOut = 10;

    public Announcer(PlayerManager playerManager) {
        mPlayerManager = playerManager;
    }

    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for(var player : mPlayerManager.getOnlinePlayersEntities()) {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    public void sendTitle(String title, String subtitle) {
        for(var player : mPlayerManager.getOnlinePlayersEntities()) {
            player.sendTitle(title, subtitle, mFadeIn, mStay, mFadeOut);
        }
    }

    public void sendMessage(String message) {
        for(var player : mPlayerManager.getOnlinePlayersEntities()) {
            player.sendMessage(message);
        }
    }

    public int getFadeIn() {
        return mFadeIn;
    }

    public void setFadeIn(int fadeIn) {
        this.mFadeIn = fadeIn;
    }

    public int getStay() {
        return mStay;
    }

    public void setStay(int stay) {
        mStay = stay;
    }

    public int getFadeOut() {
        return mFadeOut;
    }

    public void setFadeOut(int fadeOut) {
        mFadeOut = fadeOut;
    }
}
