/**
 * Matheus de Almeida
 * Jilter Araujo
 * Vicenzo Canineo
 * Victor Dias
 */

package com.temdisponivel.colortraffic.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.LinkedList;

/**
 * Class reponsible for game loop, handling object and that stuff.
 */
public class Game extends Thread {

    protected LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
    protected LinkedList<GameObject> gameObjectsToAdd = new LinkedList<GameObject>();
    protected LinkedList<GameObject> gameObjectsToRemove = new LinkedList<GameObject>();
    protected boolean running = true;
    protected boolean paused = false;
    protected boolean drawing = false;
    protected float frameCap = 100;
    protected Paint paint = new Paint();
    protected SurfaceView view = null;
    protected Context context = null;

    public Game(Context context, SurfaceView view)  {
        paint.setColor(Color.WHITE);
        this.view = view;
        this.context = context;
    }

    public void draw(Canvas canvas) {
        if (this.paused) {
            return;
        }
        canvas.drawColor(paint.getColor());
        synchronized (this.gameObjects) {
            for (GameObject obj : this.gameObjects) {
                obj.draw(canvas);
            }
        }
    }

    public void run() {
        long milli = 1000;
        float delta = 0;
        while (this.running) {
            float startTime = SystemClock.uptimeMillis();
            if (!this.paused) {
                synchronized (this.gameObjects) {
                    this.validateAddObject();
                    this.validateRemoveObject();
                    for (GameObject obj : this.gameObjects) {
                        obj.update(delta);
                    }
                    this.doCollision();
                }
            }
            if (this.drawing) {
                Canvas canvas = null;
                try {
                    canvas = this.view.getHolder().lockCanvas();
                    if (canvas != null) {
                        synchronized (this.view.getHolder()) {
                            this.draw(canvas);
                        }
                    }
                } finally {
                    if (canvas != null) {
                        this.view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
            try {
                this.sleep((long)(milli / this.frameCap));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            delta = SystemClock.uptimeMillis() - startTime;
            delta /= 1000;
        }
    }

    private void validateAddObject() {
        for (GameObject obj : this.gameObjectsToAdd) {
            this.gameObjects.addFirst(obj);
        }
        this.gameObjectsToAdd.clear();
    }

    private void validateRemoveObject() {
        for (GameObject obj : this.gameObjectsToRemove) {
            this.gameObjects.remove(obj);
        }
        this.gameObjectsToRemove.clear();
    }

    /**
     * Add a gameobject to the game loop.
     * @param gameObject GameObject to add.
     */
    public void addGameObject(GameObject gameObject) {
        this.gameObjectsToAdd.add(gameObject);
    }

    /**
     * Remove a gameobject from game loop.
     * @param gameObject GameObject to remove.
     */
    public void removeGameObject(GameObject gameObject) {
        this.gameObjectsToRemove.add(gameObject);
    }

    /**
     * Terminate this GlRender.
     */
    public void finish() {
        this.running = false;
        this.paused = true;
    }

    /**
     * Pause or continue rendering and update of this GLRender.
     * @param paused
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * @return If this renderer is paused.
     */
    public boolean getPaused() {
        return this.paused;
    }

    /**
     * @param cap Number, in seconds, of max FPS.
     */
    public void setFrameCap(float cap) {
        this.frameCap = cap;
    }

    /**
     * @return The number, in seconds, of max FPS.
     */
    public float getFrameCap() {
        return this.frameCap;
    }

    public void setDrawing(boolean drawing) {
        this.drawing = drawing;
    }

    public void create() {
        this.start();
    }

    /**
     * Make all collision validation.
     */
    protected void doCollision() {
        GameObject objA;
        Rect rectA = new Rect(), rectPlayer = new Rect((int) PlayerCar.instance.position.x, (int) PlayerCar.instance.position.y,
                (int) PlayerCar.instance.position.x + PlayerCar.instance.bitmap.getWidth(), (int) PlayerCar.instance.position.y + PlayerCar.instance.bitmap.getHeight());
        synchronized (this.gameObjects) {
            for (int i = 0; i < this.gameObjects.size(); i++) {
                objA = this.gameObjects.get(i);
                if (objA.bitmap == null) {
                    continue;
                }
                rectA.set((int) objA.position.x, (int) objA.position.y, (int) objA.position.x + objA.bitmap.getWidth(), (int) objA.position.y + objA.bitmap.getHeight());
                if (rectA.intersects(rectPlayer.left, rectPlayer.top, rectPlayer.right, rectPlayer.bottom)) {
                    PlayerCar.instance.collide(objA);
                }
            }
        }
    }

    public void onTouch(MotionEvent event) {
        synchronized (this.gameObjects) {
            for (GameObject obj : this.gameObjects) {
                obj.onTouch(event);
            }
        }
    }

    /**
     * @return Context of the game.
     */
    public Context getContext() {
        return this.context;
    }

    /**
     * @return View of the game.
     */
    public SurfaceView getView() {
        return this.view;
    }
}
