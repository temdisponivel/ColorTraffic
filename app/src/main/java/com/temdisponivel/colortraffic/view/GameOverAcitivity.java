/**
 * Matheus de Almeida
 * Jilter Araujo
 * Vicenzo Canineo
 * Victor Dias
 */

package com.temdisponivel.colortraffic.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.temdisponivel.colortraffic.model.ColorManager;
import com.temdisponivel.colortraffic.model.GameManager;
import com.temdisponivel.retrorace.colortraffic.R;

public class GameOverAcitivity extends AppCompatActivity {

    protected TextView textPoints = null;
    protected TextView textMessage = null;
    protected Button btnPlayAgain = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_game_over);
        this.fetchElement();
        this.setText();
    }

    public void fetchElement() {
        this.textPoints = (TextView) this.findViewById(R.id.txtPoint);
        this.textMessage = (TextView) this.findViewById(R.id.txtMessage);
        this.btnPlayAgain = (Button) this.findViewById(R.id.btnPlayAgain);
        this.btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMenu();
            }
        });

        this.btnPlayAgain.setBackgroundColor(ColorManager.color);
        this.textMessage.setTextColor(ColorManager.color);
        this.textPoints.setTextColor(ColorManager.color);
    }

    public void setText() {
        this.textMessage.setText("Your score is:");
        this.textPoints.setText(String.valueOf(GameManager.instance.getPoints()));
    }

    public void startMenu() {
        Intent intent = new Intent(this, MenuAcitivity.class);
        startActivity(intent);
    }
}
