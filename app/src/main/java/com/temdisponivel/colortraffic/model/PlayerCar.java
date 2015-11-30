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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceView;

import com.temdisponivel.retrorace.colortraffic.R;

/**
 * A class that represents the car of the player.
 */
public class PlayerCar extends Car implements SensorEventListener {

    static protected PlayerCar instance  = null;
    protected SensorManager sensorManager;
    protected Sensor sensor;
    protected float sensorX = 0;
    protected float sensorY = 0;
    protected PointF lastPosition = null;
    final protected float alpha = 0.8f;
    final protected float[] gravity = new float[3];

    public PlayerCar(Context context, SurfaceView surface) {
        super(context, surface);
        PlayerCar.instance = this;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_car);
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if ((this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) == null) {
            //can't play
        }
        this.sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_GAME);
        this.velocity = 500f;
        this.paint.setColor(ColorManager.color);
    }

    @Override
    public void update(float deltaTime) {
        this.lastPosition = this.position;
        float deltaMovimentX = -this.sensorX * deltaTime * this.velocity;
        if (this.position.x + deltaMovimentX + this.bitmap.getWidth() < this.surface.getWidth() && this.position.x + deltaMovimentX >= 0) {
            this.position.x += deltaMovimentX;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.position.x, this.position.y, this.position.x + this.bitmap.getWidth(), this.position.y + this.bitmap.getHeight(), this.paint);
    }

    /**
     * This method should be call when the player car should initiate.
     */
    public void create() {
        this.position.x = (this.surface.getWidth() / 2) - (this.bitmap.getWidth() / 2);
        this.position.y = this.surface.getHeight() - this.bitmap.getHeight();
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            this.gravity[0] = this.alpha * this.gravity[0] + (1 - this.alpha) * event.values[0];
            this.gravity[1] = this.alpha * this.gravity[1] + (1 - this.alpha) * event.values[1];
            this.sensorX = event.values[0] - this.gravity[0];
            this.sensorY = event.values[1] - this.gravity[1];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void collide(GameObject other) {
        if (other instanceof EnemyCar) {
            GameManager.instance.gameOver();
        }
    }
}
