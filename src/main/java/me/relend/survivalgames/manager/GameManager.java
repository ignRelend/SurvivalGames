package me.relend.survivalgames.manager;

import me.relend.survivalgames.SurvivalGames;

public class GameManager {

    private GameState gameState;
    private final BlockManager blockManager;
    private final SurvivalGames plugin;

    public GameManager(SurvivalGames plugin) {
        this.plugin = plugin;
        gameState = GameState.WAITING;
        blockManager = new BlockManager();
    }

    public void setGameState(GameState gameState) {
        if (this.gameState == GameState.WAITING && gameState == GameState.COUNTDOWN) return;
        if (this.gameState == GameState.COUNTDOWN && gameState == GameState.IN_GAME) return;
        if (this.gameState == GameState.IN_GAME && gameState == GameState.FINISH) return;
        if (this.gameState == GameState.FINISH && gameState == GameState.RESETTING) return;
        if (this.gameState == GameState.RESETTING && gameState == GameState.WAITING) return;

        this.gameState = gameState;
        switch (gameState) {
            case WAITING:
                // dont let players move
                // give new players kit selector
                break;
            case COUNTDOWN:
                // start countdown
                // dont let players move
                // give new players kit selector
                break;
            case IN_GAME:
                // wait 30 seconds to give kits
                // let players move and fight
                break;
            case FINISH:
                // display winning text
                // send winning chat message
                // dont let players fight
                break;
            case RESETTING:
                // reset worlds
                // send players to hub
                break;
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }
}
