package com.example.forestales;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /**hojas**/
        SeekBar hojasSeekBar = findViewById(R.id.hojasSeekBar);
        TextView hojasTextView = findViewById(R.id.hojasTextView);

        hojasSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int porcentaje = progress * 5; // porque el max es 20
                hojasTextView.setText("Hojas: " + porcentaje + "%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        /**flores**/
        SeekBar floresSeekBar = findViewById(R.id.floresSeekBar);
        TextView floresTextView = findViewById(R.id.floresTextView);

        floresSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int porcentaje = progress * 5; // porque el max es 20
                floresTextView.setText("Flores: " + porcentaje + "%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        /**frutos**/
        SeekBar frutosSeekBar = findViewById(R.id.frutosSeekBar);
        TextView frutosTextView = findViewById(R.id.frutosTextView);

        frutosSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int porcentaje = progress * 5; // porque el max es 20
                frutosTextView.setText("Flores: " + porcentaje + "%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        /*boton anterior*/
        Button anteriorButton = findViewById(R.id.previwewButton);

        anteriorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });

        /*boton siguiente*/
        Button siguienteButton = findViewById(R.id.nextButton);

        siguienteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
        });

    }
}