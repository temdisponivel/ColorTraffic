/**
 * Matheus de Almeida
 * Jilter Araujo
 * Vicenzo Canineo
 * Victor Dias
 */

package com.temdisponivel.colortraffic.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * Abstraction of a object that will be draw and updated in game loop.
 */
public abstract class GameObject {
    protected PointF position = null;
    protected Paint paint = null;
    protected Bitmap bitmap = null;
    public abstract void update(float deltaTime);

    public GameObject() {
        this.position = new PointF();
        this.paint = new Paint();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.bitmap, this.position.x, this.position.y, this.paint);
    }

    public void collide(GameObject other) {}

    public void onTouch(MotionEvent event) {}
}
