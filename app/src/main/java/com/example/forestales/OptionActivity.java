package com.example.forestales;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OptionActivity extends AppCompatActivity {

    private Button nuevoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);


        /*boton nuevo arbol*/
        Button nuevoButton = findViewById(R.id.newButton);

        nuevoButton.setOnClickListener(v -> {
            Intent intent = new Intent(OptionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        /*boton modificar arbol*/
        Button modificarButton = findViewById(R.id.modButton);

        modificarButton.setOnClickListener(v -> {
            Intent intent = new Intent(OptionActivity.this, ModifyActivity.class);
            startActivity(intent);
        });

        /*boton eliminar arbol*/
        Button eliminarButton = findViewById(R.id.eraseButton);

        eliminarButton.setOnClickListener(v -> {
            Intent intent = new Intent(OptionActivity.this, EraseActivity.class);
            startActivity(intent);
        });

        /*boton ver arbol*/
        Button verButton = findViewById(R.id.viewButton);

        verButton.setOnClickListener(v -> {
            Intent intent = new Intent(OptionActivity.this, ViewActivity.class);
            startActivity(intent);
        });

        Button exitButton = findViewById(R.id.exitButton);

        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(OptionActivity.this, LoginActivity.class);
            // Limpia el stack de actividades
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            // Cierra la actividad actual
            finish();
        });


    }

}