/**
 * Matheus de Almeida
 * Jilter Araujo
 * Vicenzo Canineo
 * Victor Dias
 */

package com.temdisponivel.retrorace.colortraffic.model;

import android.content.Intent;
import android.graphics.Canvas;

import com.temdisponivel.retrorace.colortraffic.view.GameOverAcitivity;

import java.util.Random;

/**
 * Class responsible for coordinate spawn of enemies, velocity and such.
 */
public class GameManager extends GameObject {

    static public GameManager instance = null;
    protected float elapsedTime = 0;
    protected int points = 0;
    protected int pointsPerEnemy = 3;
    protected Game game;
    protected int quantityRows = 0;
    protected float respawnDelay = .6f;
    protected float lastSpawn = 0;
    protected float paddingCars = 8f;
    protected float enemyVelocity = 450f;
    protected Random auxRandom = new Random();

    public GameManager(Game game) {
        super();
        GameManager.instance = this;
        this.game = game;
        this.paint.setColor(ColorManager.color);
        this.paint.setTextSize(48);
        this.paint.setFakeBoldText(true);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(String.valueOf(this.points), canvas.getWidth() - 150, 50, this.paint);
    }

    /**
     * This method should be call when the game manager should initiate.
     */
    public void create() {
        Car car = null;
        car = new EnemyCar(this.game.context, this.game.view);
        this.quantityRows = this.game.view.getWidth() / (int)(car.bitmap.getWidth() + this.paddingCars);
        this.game.addGameObject(car);
    }

    @Override
    public void update(float deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime - lastSpawn < respawnDelay) {
            return;
        }
        EnemyCar car;
        auxRandom.setSeed(System.currentTimeMillis());
        this.game.addGameObject(car = new EnemyCar(this.game.context, this.game.view));
        car.position.x = (car.bitmap.getWidth() * auxRandom.nextInt(quantityRows)) + paddingCars;
        car.velocity = enemyVelocity + auxRandom.nextInt(50);

        auxRandom.setSeed(System.currentTimeMillis());
        this.game.addGameObject(car = new EnemyCar(this.game.context, this.game.view));
        car.position.x = (car.bitmap.getWidth() * auxRandom.nextInt(quantityRows)) + paddingCars;
        car.velocity = enemyVelocity + auxRandom.nextInt(50);

        lastSpawn = elapsedTime;
        enemyVelocity += 1;
        pointsPerEnemy += 1;

        if (respawnDelay > .3) {
            respawnDelay -= .001;
        }
    }

    /**
     * @return Points.
     */
    public float getPoints() {
        return this.points;
    }

    /**
     * Set game over.
     */
    public void gameOver() {
        this.game.finish();
        Intent intent = new Intent(this.game.context, GameOverAcitivity.class);
        this.game.context.startActivity(intent);
    }
}
