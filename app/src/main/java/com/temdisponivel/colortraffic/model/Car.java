package com.temdisponivel.colortraffic.model;

import android.content.Context;
import android.view.SurfaceView;

public abstract class Car extends GameObject {
    protected  float velocity = .3f;
    protected SurfaceView surface;
    protected Context context = null;
    public Car(Context context, SurfaceView surface) {
        super();
        this.surface = surface;
        this.context = context;
    }
}
