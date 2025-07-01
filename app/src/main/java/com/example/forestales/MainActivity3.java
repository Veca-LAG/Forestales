package com.example.forestales;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    private SeekBar seekHojas, seekFlores, seekFrutos;
    private TextView tvHojas, tvFlores, tvFrutos;

    private Spinner spinnerHojas, spinnerFrutos;
    private LinearLayout layoutBotonesHojas, layoutBotonesFlores, layoutBotonesFrutos;
    private ImageView imgHojas, imgFlores, imgFrutos;

    private EditText etRamas, etCorteza, etUsos, etObservaciones;

    private Button btnAnterior, btnFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnFinalizar = findViewById(R.id.btnFinalizar);
        btnAnterior = findViewById(R.id.btnAnterior);

        // Referencias SeekBars y TextViews
        seekHojas = findViewById(R.id.seekHojas);
        tvHojas = findViewById(R.id.tvHojas);

        seekFlores = findViewById(R.id.seekFlores);
        tvFlores = findViewById(R.id.tvFlores);

        seekFrutos = findViewById(R.id.seekFrutos);
        tvFrutos = findViewById(R.id.tvFrutos);

        // Referencias Spinner, LayoutBotones, Imagen para cada sección
        spinnerHojas = findViewById(R.id.spinnerHojas);
        layoutBotonesHojas = findViewById(R.id.layoutBotonesHojas);
        imgHojas = findViewById(R.id.imgHojas);

        layoutBotonesFlores = findViewById(R.id.layoutBotonesFlores);
        imgFlores = findViewById(R.id.imgFlores);

        spinnerFrutos = findViewById(R.id.spinnerFrutos);
        layoutBotonesFrutos = findViewById(R.id.layoutBotonesFrutos);
        imgFrutos = findViewById(R.id.imgFrutos);

        // EditTexts multiline
        etRamas = findViewById(R.id.etRamas);
        etCorteza = findViewById(R.id.etCorteza);
        etUsos = findViewById(R.id.etUsos);
        etObservaciones = findViewById(R.id.etObservacion);

        configurarEditTextMultiline(etRamas);
        configurarEditTextMultiline(etCorteza);
        configurarEditTextMultiline(etUsos);
        configurarEditTextMultiline(etObservaciones);

        // Configura Spinners
        String[] opcionesHojas = {"Hojas verdes", "Hojas amarillentas", "Hojas marchitas"};
        spinnerHojas.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesHojas));

        String[] opcionesFrutos = {"Muy inmaduro", "Ligeramente inmaduro", "Maduro"};
        spinnerFrutos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesFrutos));

        // Configura cada SeekBar
        configurarSeekBar(seekHojas, tvHojas, "Hojas", spinnerHojas, layoutBotonesHojas, imgHojas);
        configurarSeekBar(seekFlores, tvFlores, "Flores", null, layoutBotonesFlores, imgFlores);
        configurarSeekBar(seekFrutos, tvFrutos, "Frutos", spinnerFrutos, layoutBotonesFrutos, imgFrutos);

        // Botones finales
        btnAnterior = findViewById(R.id.btnAnterior);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        btnAnterior.setOnClickListener(v -> finish());

        btnFinalizar.setOnClickListener(v -> {
            if (validarCampos()) {
                new AlertDialog.Builder(this)
                        .setTitle("Guardar datos")
                        .setMessage("¿Deseas guardar esta información?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            guardarEnBaseDeDatos();
                            Toast.makeText(this, "Datos guardados.", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                Toast.makeText(this, "Completa todos los campos obligatorios.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarSeekBar(
            SeekBar seekBar,
            TextView textView,
            String label,
            View spinner,
            View layoutBotones,
            View imageView
    ) {
        seekBar.setMax(20); // 0-100 en pasos de 5

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int valor = progress * 5;
                textView.setText(label + ": " + valor + "%");

                if (valor > 0) {
                    if (spinner != null) spinner.setVisibility(View.VISIBLE);
                    if (layoutBotones != null) layoutBotones.setVisibility(View.VISIBLE);
                    if (imageView != null) imageView.setVisibility(View.VISIBLE);
                } else {
                    if (spinner != null) spinner.setVisibility(View.GONE);
                    if (layoutBotones != null) layoutBotones.setVisibility(View.GONE);
                    if (imageView != null) imageView.setVisibility(View.GONE);
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void configurarEditTextMultiline(EditText editText) {
        editText.setSingleLine(false);
        editText.setMaxLines(10);
        editText.setLines(5);
        editText.setHorizontallyScrolling(false);
        editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    private boolean validarCampos() {
        // Aquí pones la validación real
        return true;
    }

    private void guardarEnBaseDeDatos() {
        // Aquí guardarías tu objeto FormData o como quieras persistirlo
        // Ejemplo: DatabaseHelper db = new DatabaseHelper(this);
        // db.insertRegistroCompleto(FormData.getInstance().build());
    }
}
