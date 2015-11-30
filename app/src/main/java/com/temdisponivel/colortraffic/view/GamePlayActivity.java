/**
 * Matheus de Almeida
 * Jilter Araujo
 * Vicenzo Canineo
 * Victor Dias
 */

package com.temdisponivel.colortraffic.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class GamePlayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SinglePlayer(this));
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}