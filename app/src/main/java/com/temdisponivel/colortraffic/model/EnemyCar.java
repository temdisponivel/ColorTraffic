/**
 * Matheus de Almeida
 * Jilter Araujo
 * Vicenzo Canineo
 * Victor Dias
 */

package com.temdisponivel.retrorace.colortraffic.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.temdisponivel.retrorace.colortraffic.R;

import java.util.Random;

/**
 * Class that represents the enemy cars.
 */
public class EnemyCar extends Car {

    protected PointF lastPosition = null;
    protected boolean touch = false;

    public EnemyCar(Context context, SurfaceView surface) {
        super(context, surface);
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_car);
        this.position.y = -this.bitmap.getHeight();
        this.velocity = 200;
        this.paint.setColor(0xFF000000 | new Random().nextInt());
    }

    @Override
    public void update(float deltaTime) {
        this.lastPosition = this.position;
        float deltaPosition = velocity * deltaTime;
        if (touch) {
             deltaPosition *= 2;
        }
        this.position.y += deltaPosition;
        if (this.position.y > this.surface.getHeight()) {
            GameManager.instance.game.removeGameObject(this);
            float points = GameManager.instance.pointsPerEnemy;
            if (touch) {
                points *= 2;
            }
            GameManager.instance.points += points;
            return;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.position.x, this.position.y, this.position.x + this.bitmap.getWidth(), this.position.y + this.bitmap.getHeight(), this.paint);
    }

    @Override
    public void onTouch(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            touch = true;
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            touch = false;
        }
    }
}
