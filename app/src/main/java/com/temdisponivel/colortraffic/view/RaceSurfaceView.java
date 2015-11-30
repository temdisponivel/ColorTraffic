/**
 * Matheus de Almeida
 * Jilter Araujo
 * Vicenzo Canineo
 * Victor Dias
 */

package com.temdisponivel.retrorace.colortraffic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.temdisponivel.retrorace.colortraffic.model.Game;
import com.temdisponivel.retrorace.colortraffic.model.GameManager;
import com.temdisponivel.retrorace.colortraffic.model.PlayerCar;

/**
 * A abstraction of surface view to the game logic.
 */
public abstract class RaceSurfaceView extends SurfaceView {
    protected SurfaceHolder holder = null;
    protected Game game = null;
    private boolean created = false;

    public RaceSurfaceView(Context context) {
        super(context);
        this.holder = this.getHolder();
        this.game = new Game(context, this);
        this.holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                game.setDrawing(false);
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (!created) {
                    created = true;
                    create();
                    game.create();
                }
                game.setDrawing(true);
                Canvas c = holder.lockCanvas(null);
                onDraw(c);
                holder.unlockCanvasAndPost(c);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }

    /**
     * Called when this surface is ready for draw and has all properties initialized.
     */
    protected void create() {
        PlayerCar playerCar = new PlayerCar(this.getGame().getContext(), this.getGame().getView());
        GameManager gameManager = new GameManager(this.game);
        this.game.addGameObject(playerCar);
        this.game.addGameObject(gameManager);
        playerCar.create();
        gameManager.create();
    }

    /**
     * @return The game instance.
     */
    public Game getGame() {
        return game;
    }
}
