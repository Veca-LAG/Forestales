package com.example.forestales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
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

        Spinner madurezFrutoSpinner = findViewById(R.id.madurezFrutoSpinner);
        Spinner estadoHojasSpinner = findViewById(R.id.estadoHojasSpinner);
        Spinner interaccionSpinner = findViewById(R.id.interaccionSpinner);
        TextView organismoTextView = findViewById(R.id.organismoTextView);
        EditText organismoEditText = findViewById(R.id.organismoEditText);

    // Opciones del spinner
        ArrayAdapter<String> madurezAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"Muy inmaduro", "Ligeramente inmaduro", "Maduro"});
        madurezFrutoSpinner.setAdapter(madurezAdapter);

        ArrayAdapter<String> estadoHojasAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"Hojas verdes", "Hojas amarillentas", "Hojas marchitas"});
        estadoHojasSpinner.setAdapter(estadoHojasAdapter);

        ArrayAdapter<String> interaccionAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"Ninguna", "Mutualismo", "Depredación", "Parasitismo"});
        interaccionSpinner.setAdapter(interaccionAdapter);

// Frutos SeekBar
        frutosSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int porcentaje = progress * 5;
                frutosTextView.setText("Frutos: " + porcentaje + "%");
                madurezFrutoSpinner.setVisibility(porcentaje > 0 ? View.VISIBLE : View.GONE);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

// Hojas SeekBar
        hojasSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int porcentaje = progress * 5;
                hojasTextView.setText("Hojas: " + porcentaje + "%");
                estadoHojasSpinner.setVisibility(porcentaje > 0 ? View.VISIBLE : View.GONE);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

// Spinner de interacción
        interaccionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                boolean visible = !selected.equals("Ninguna");
                organismoTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
                organismoEditText.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
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