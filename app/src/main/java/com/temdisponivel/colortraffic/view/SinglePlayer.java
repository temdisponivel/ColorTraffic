/**
 * Matheus de Almeida
 * Jilter Araujo
 * Vicenzo Canineo
 * Victor Dias
 */

package com.temdisponivel.retrorace.colortraffic.view;

import android.content.Context;
import android.view.MotionEvent;


public class SinglePlayer extends RaceSurfaceView {
    public SinglePlayer(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getGame().onTouch(event);
        return true;
    }
}
