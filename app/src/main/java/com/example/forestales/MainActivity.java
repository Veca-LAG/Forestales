package com.example.forestales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*radio de fustes*/
        RadioGroup tipoFusteRadioGroup = findViewById(R.id.tipoFusteRadioGroup);
        RadioButton regularRadio = findViewById(R.id.regular);
        RadioButton irregularRadio = findViewById(R.id.irregular);

        EditText fusteEditText = findViewById(R.id.fusteEditText);
        EditText fusteMayorEditText = findViewById(R.id.fusteMayorEditText);
        EditText fusteMenorEditText = findViewById(R.id.fusteMenorEditText);

        tipoFusteRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.regular) {
                // Mostrar fusteEditText, ocultar los otros
                fusteEditText.setVisibility(View.VISIBLE);
                fusteMayorEditText.setVisibility(View.GONE);
                fusteMenorEditText.setVisibility(View.GONE);
            } else if (checkedId == R.id.irregular) {
                // Mostrar fusteMayor y fusteMenor, ocultar fusteEditText
                fusteEditText.setVisibility(View.GONE);
                fusteMayorEditText.setVisibility(View.VISIBLE);
                fusteMenorEditText.setVisibility(View.VISIBLE);
            }
        });


        /*boton siguiente*/
        Button siguienteButton = findViewById(R.id.nextButton);

        siguienteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        /*boton anterior*/
        Button anteriorButton = findViewById(R.id.previwewButton);

        anteriorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OptionActivity.class);
            startActivity(intent);
        });
    }
}