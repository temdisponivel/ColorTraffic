/**
 * Matheus de Almeida
 * Jilter Araujo
 * Vicenzo Canineo
 * Victor Dias
 */

package com.temdisponivel.retrorace.colortraffic.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.temdisponivel.retrorace.colortraffic.R;
import com.temdisponivel.retrorace.colortraffic.model.ColorManager;

public class MenuAcitivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    protected Button btnPlay = null;
    protected SeekBar seekbarRed = null;
    protected SeekBar seekbarGreen = null;
    protected SeekBar seekbarBlue = null;
    protected View view = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_acitivity);
        this.fetchElements();
    }

    public void fetchElements() {
        this.view = (View) this.findViewById(R.id.viewColor);

        this.btnPlay = (Button) this.findViewById(R.id.btnPlay);

        this.seekbarRed = (SeekBar) this.findViewById(R.id.sliderRed);
        this.seekbarGreen = (SeekBar) this.findViewById(R.id.sliderGreen);
        this.seekbarBlue = (SeekBar) this.findViewById(R.id.sliderBlue);

        this.seekbarRed.setOnSeekBarChangeListener(this);
        this.seekbarGreen.setOnSeekBarChangeListener(this);
        this.seekbarBlue.setOnSeekBarChangeListener(this);

        this.seekbarRed.setMax(255);
        this.seekbarGreen.setMax(255);
        this.seekbarBlue.setMax(255);

        this.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        this.seekbarRed.setProgress(ColorManager.color & 0x00FF0000);
        this.seekbarGreen.setProgress(ColorManager.color & 0x0000FF00);
        this.seekbarBlue.setProgress(ColorManager.color & 0x000000FF);

        this.view.setBackgroundColor(ColorManager.color);
    }

    public void play() {
        Intent intent = new Intent(this, GamePlayActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        ColorManager.color = 0xFF000000 | (this.seekbarRed.getProgress() << 16) | (this.seekbarGreen.getProgress() << 8) | this.seekbarBlue.getProgress();
        this.view.setBackgroundColor(ColorManager.color);
        this.btnPlay.setBackgroundColor(ColorManager.color);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
