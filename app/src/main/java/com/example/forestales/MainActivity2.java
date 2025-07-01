package com.example.forestales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    Button btnSiguiente, btnAnterior;
    EditText etFecha;
    CheckBox chkFechaPersonalizada, presencia;

    View tvFinos, layoutFinos, tvPesados, layoutPesados, tvPeligro;
    RadioGroup rgPeligro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Botones
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAnterior = findViewById(R.id.btnAnterior);

        // Fecha personalizada
        etFecha = findViewById(R.id.etFecha);
        chkFechaPersonalizada = findViewById(R.id.chkFechaPersonalizada);
        chkFechaPersonalizada.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etFecha.setEnabled(isChecked);
            etFecha.setText(isChecked ? "" : obtenerFechaActual());
        });

        // Otros componentes
        Spinner spinnerHabito = findViewById(R.id.spinnerHabito);
        Spinner spinnerTipo = findViewById(R.id.spinnerTipo);
        EditText tvAltura = findViewById(R.id.tvAltura);
        RadioGroup rgAltura = findViewById(R.id.rgAltura);
        EditText tvDiametro = findViewById(R.id.tvDiametro);
        RadioGroup rgDiametro = findViewById(R.id.rgDiametro);
        RadioGroup rgSalud = findViewById(R.id.rgSalud);
        Spinner spinnerDisturbios = findViewById(R.id.spinnerDisturbios);
        Spinner spinnerInteraccion = findViewById(R.id.spinnerInteraccion);
        EditText etEspecie = findViewById(R.id.etEspecie);

        CheckBox chkHojas = findViewById(R.id.chkHojas);
        CheckBox chkRamitas = findViewById(R.id.chkRamitas);
        CheckBox chkHierbas = findViewById(R.id.chkHierbas);
        CheckBox chkHumus = findViewById(R.id.chkHumus);
        CheckBox chkRamas = findViewById(R.id.chkRamas);
        CheckBox chkTroncos = findViewById(R.id.chkTroncos);
        CheckBox chkTallos = findViewById(R.id.chkTallos);
        CheckBox chkArbustos = findViewById(R.id.chkArbustos);

        // Combustibles
        presencia = findViewById(R.id.presencia);
        tvFinos = findViewById(R.id.finos);
        layoutFinos = findViewById(R.id.layoutCombustiblesFinos);
        tvPesados = findViewById(R.id.pesados);
        layoutPesados = findViewById(R.id.layoutCombustiblesPesados);
        tvPeligro = findViewById(R.id.tvPeligro);
        rgPeligro = findViewById(R.id.rgPeligro);

        actualizarVisibilidadCombustibles(presencia.isChecked());

        presencia.setOnCheckedChangeListener((buttonView, isChecked) -> {
            actualizarVisibilidadCombustibles(isChecked);
        });

        if (!chkFechaPersonalizada.isChecked()) {
            etFecha.setEnabled(false);
            etFecha.setText(obtenerFechaActual());
        }

        // Adapters para Spinners
        spinnerHabito.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Enredadera", "Liana", "Hierba", "Arbóreo", "Arbustivo"}));
        spinnerTipo.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Prioritaria", "Endémica", "Microendémica", "Nativa", "Introducida", "Invasora"}));
        spinnerDisturbios.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Tormentas", "Sequías", "Huracanes"}));
        spinnerInteraccion.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Depredación", "Mutualismo", "Parasitismo", "Comensalismo", "Ninguna"}));

        String intera = spinnerInteraccion.getSelectedItem().toString().trim();
        if(!(intera.equals("Ninguna"))){
            etEspecie.setVisibility(View.VISIBLE);
        }

        btnAnterior.setOnClickListener(v -> finish());

        btnSiguiente.setOnClickListener(v -> {
            // Validaciones
            String fec = etFecha.getText().toString().trim();
            String habito = spinnerHabito.getSelectedItem().toString().trim();
            String tipo = spinnerTipo.getSelectedItem().toString().trim();
            String alt = tvAltura.getText().toString().trim();
            String fuste = tvDiametro.getText().toString().trim();
            String disturbio = spinnerDisturbios.getSelectedItem().toString().trim();
            String organismo="";
            if(!(intera.equals("Ninguna"))) {
                organismo = etEspecie.getText().toString().trim();
            }
            boolean pres = presencia.isChecked();

            int idAlt = rgAltura.getCheckedRadioButtonId();
            int idFuste = rgDiametro.getCheckedRadioButtonId();
            int idSalud = rgSalud.getCheckedRadioButtonId();
            int idPel = rgPeligro.getCheckedRadioButtonId();

            if (idAlt == -1 || idFuste == -1 || idSalud == -1) {
                Toast.makeText(this, "Selecciona todas las opciones de botones.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (habito.isEmpty() || tipo.isEmpty() || alt.isEmpty() || fuste.isEmpty() || disturbio.isEmpty() || intera.isEmpty() || organismo.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pres && !(chkHojas.isChecked() || chkRamitas.isChecked() || chkHierbas.isChecked() || chkHumus.isChecked() ||
                    chkRamas.isChecked() || chkTroncos.isChecked() || chkTallos.isChecked() || chkArbustos.isChecked())) {
                Toast.makeText(this, "Selecciona algún tipo de combustible.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pres && idPel == -1) {
                Toast.makeText(this, "Selecciona nivel de peligro de incendio.", Toast.LENGTH_SHORT).show();
                return;
            }

            Date fecha = null;
            try {
                fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fec);
            } catch (ParseException e) {
                Toast.makeText(this, "Fecha inválida. Usa el formato dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                return;
            }

            String medirAlt = ((RadioButton) findViewById(idAlt)).getText().toString();
            String medirFuste = ((RadioButton) findViewById(idFuste)).getText().toString();
            String estSalud = ((RadioButton) findViewById(idSalud)).getText().toString();
            String medirPel = pres ? ((RadioButton) findViewById(idPel)).getText().toString() : "";

            // Combustibles seleccionados
            String combustibleFino = obtenerCombustibles(chkHojas, chkRamitas, chkHierbas, chkHumus);
            String combustiblePesado = obtenerCombustibles(chkRamas, chkTroncos, chkTallos, chkArbustos);

            // Guarda campos de esta pantalla en FormData
            FormData.getInstance().fecha = fecha;
            FormData.getInstance().habitoCrecimiento = habito;
            FormData.getInstance().tipoCrecimiento = tipo;
            FormData.getInstance().altura = Float.parseFloat(alt);
            FormData.getInstance().medirAltura = medirAlt;
            FormData.getInstance().diametroFuste = Float.parseFloat(fuste);
            FormData.getInstance().medirDiametroFuste = medirFuste;
            FormData.getInstance().estadoSalud = estSalud;
            FormData.getInstance().disturbiosMeteorologicos = disturbio;
            FormData.getInstance().interacciones = intera;
            FormData.getInstance().organismoInteracciones = organismo;
            FormData.getInstance().presenciaCombustibles = pres;
            FormData.getInstance().combustiblesFinos = combustibleFino;
            FormData.getInstance().combustiblesPesados = combustiblePesado;
            FormData.getInstance().peligroIncendio = medirPel;


            // Aquí podrías guardar en una clase o enviar al intent
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
        });
    }

    private String obtenerFechaActual() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    private void actualizarVisibilidadCombustibles(boolean mostrar) {
        int vis = mostrar ? View.VISIBLE : View.GONE;
        tvFinos.setVisibility(vis);
        layoutFinos.setVisibility(vis);
        tvPesados.setVisibility(vis);
        layoutPesados.setVisibility(vis);
        tvPeligro.setVisibility(vis);
        rgPeligro.setVisibility(vis);
    }

    private String obtenerCombustibles(CheckBox... checks) {
        StringBuilder sb = new StringBuilder();
        for (CheckBox cb : checks) {
            if (cb.isChecked()) {
                sb.append(cb.getText().toString()).append(", ");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2); // Quita última coma y espacio
        }
        return sb.toString();
    }
}
