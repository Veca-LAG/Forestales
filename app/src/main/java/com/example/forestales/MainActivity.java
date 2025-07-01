package com.example.forestales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnSiguiente, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Botones
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Campos de entrada
        EditText etNumeroAcceso = findViewById(R.id.etNumeroAcceso);
        EditText etNombreFamilia = findViewById(R.id.etNombreFamilia);
        EditText etNombreComun = findViewById(R.id.etNombreComun);
        EditText etGenero = findViewById(R.id.etGenero);
        EditText etEspecie = findViewById(R.id.etEspecie);
        EditText etEcologia = findViewById(R.id.etEcologia);
        EditText etClasificacion = findViewById(R.id.etClasificacion);
        EditText etCoordenadas = findViewById(R.id.etCoordenadas);
        CheckBox chkNoOriginaria = findViewById(R.id.chkNoOriginaria);

        // Ocultar campo de ecología al inicio
        etEcologia.setVisibility(View.GONE);
        chkNoOriginaria.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etEcologia.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        // Botón siguiente
        btnSiguiente.setOnClickListener(v -> {
            String acceso = etNumeroAcceso.getText().toString().trim();
            String familia = etNombreFamilia.getText().toString().trim();
            String comun = etNombreComun.getText().toString().trim();
            String genero = etGenero.getText().toString().trim();
            String especie = etEspecie.getText().toString().trim();
            boolean noOriginaria = chkNoOriginaria.isChecked();
            String ecologia = etEcologia.getText().toString().trim();
            String clasificacion = etClasificacion.getText().toString().trim();
            String coordenadas = etCoordenadas.getText().toString().trim();

            // Validación
            if (acceso.isEmpty() || familia.isEmpty() || comun.isEmpty() || genero.isEmpty()
                    || especie.isEmpty() || clasificacion.isEmpty() || coordenadas.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (noOriginaria && ecologia.isEmpty()) {
                Toast.makeText(this, "Debe llenar el campo de ecología si no es originaria", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardado opcional en clase FormData

            FormData.getInstance().numeroAcceso = Integer.parseInt(acceso);
            FormData.getInstance().nombreFamilia = familia;
            FormData.getInstance().nombreComun = comun;
            FormData.getInstance().nombreCientificoGenero = genero;
            FormData.getInstance().nombreCientificoEspecie = especie;
            FormData.getInstance().especieOriginaria = !noOriginaria;
            FormData.getInstance().ecologiaDistribucion = ecologia;
            FormData.getInstance().clasificaionTaxonomica = clasificacion;
            FormData.getInstance().coordenadas = coordenadas;


            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        // Botón cancelar
        btnCancelar.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Cancelar formulario")
                    .setMessage("¿Estás seguro de cancelar? Se perderán todos los datos.")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Opcional: limpiar datos globales
                        // FormData.getInstance().clear();
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
