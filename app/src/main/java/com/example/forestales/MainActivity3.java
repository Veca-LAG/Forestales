package com.example.forestales;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity {

    private SeekBar seekHojas, seekFlores, seekFrutos;
    private TextView tvHojas, tvFlores, tvFrutos;
    private Spinner spinnerHojas, spinnerFrutos;
    private LinearLayout layoutBotonesHojas, layoutBotonesFlores, layoutBotonesFrutos;
    private ImageView imgHojas, imgFlores, imgFrutos, imgRamas, imgCorteza, imgGeneral;
    private EditText etRamas, etCorteza, etUsos, etObservaciones;
    private Button btnAnterior, btnFinalizar;

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private Uri photoURI;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private String currentPhotoPath;
    private String tipoImagen = "";
    private ImageView imageViewActual = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Inicializa vistas
        seekHojas = findViewById(R.id.seekHojas);
        tvHojas = findViewById(R.id.tvHojas);
        seekFlores = findViewById(R.id.seekFlores);
        tvFlores = findViewById(R.id.tvFlores);
        seekFrutos = findViewById(R.id.seekFrutos);
        tvFrutos = findViewById(R.id.tvFrutos);

        spinnerHojas = findViewById(R.id.spinnerHojas);
        spinnerFrutos = findViewById(R.id.spinnerFrutos);

        layoutBotonesHojas = findViewById(R.id.layoutBotonesHojas);
        layoutBotonesFlores = findViewById(R.id.layoutBotonesFlores);
        layoutBotonesFrutos = findViewById(R.id.layoutBotonesFrutos);

        imgHojas = findViewById(R.id.imgHojas);
        imgFlores = findViewById(R.id.imgFlores);
        imgFrutos = findViewById(R.id.imgFrutos);
        imgRamas = findViewById(R.id.imgRamas);
        imgCorteza = findViewById(R.id.imgCorteza);
        imgGeneral = findViewById(R.id.imgGeneral);

        etRamas = findViewById(R.id.etRamas);
        etCorteza = findViewById(R.id.etCorteza);
        etUsos = findViewById(R.id.etUsos);
        etObservaciones = findViewById(R.id.etObservacion);

        btnAnterior = findViewById(R.id.btnAnterior);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        configurarEditTextMultiline(etRamas);
        configurarEditTextMultiline(etCorteza);
        configurarEditTextMultiline(etUsos);
        configurarEditTextMultiline(etObservaciones);

        String[] opcionesHojas = {"Hojas verdes", "Hojas amarillentas", "Hojas marchitas"};
        spinnerHojas.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesHojas));

        String[] opcionesFrutos = {"Muy inmaduro", "Ligeramente inmaduro", "Maduro"};
        spinnerFrutos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesFrutos));

        configurarSeekBar(seekHojas, tvHojas, "Hojas", spinnerHojas, layoutBotonesHojas, imgHojas);
        configurarSeekBar(seekFlores, tvFlores, "Flores", null, layoutBotonesFlores, imgFlores);
        configurarSeekBar(seekFrutos, tvFrutos, "Frutos", spinnerFrutos, layoutBotonesFrutos, imgFrutos);

        initCameraLauncher();

        findViewById(R.id.btnCamaraHojas).setOnClickListener(v -> {
            tipoImagen = "Hojas";
            imageViewActual = imgHojas;
            checkCameraPermission();
        });
        findViewById(R.id.btnCamaraFlores).setOnClickListener(v -> {
            tipoImagen = "Flores";
            imageViewActual = imgFlores;
            checkCameraPermission();
        });
        findViewById(R.id.btnCamaraFrutos).setOnClickListener(v -> {
            tipoImagen = "Frutos";
            imageViewActual = imgFrutos;
            checkCameraPermission();
        });
        findViewById(R.id.btnCamaraRamas).setOnClickListener(v -> {
            tipoImagen = "Ramas";
            imageViewActual = imgRamas;
            checkCameraPermission();
        });
        findViewById(R.id.btnCamaraCorteza).setOnClickListener(v -> {
            tipoImagen = "Corteza";
            imageViewActual = imgCorteza;
            checkCameraPermission();
        });
        findViewById(R.id.btnCamaraGeneral).setOnClickListener(v -> {
            tipoImagen = "General";
            imageViewActual = imgGeneral;
            checkCameraPermission();
        });

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

    private void initCameraLauncher() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result && imageViewActual != null) {
                        imageViewActual.setImageURI(photoURI);
                        pedirNombreParaFoto();
                    } else {
                        Toast.makeText(this, "No se capturó imagen", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void pedirNombreParaFoto() {
        int acceso = FormData.getInstance().numeroAcceso;
        String genero = FormData.getInstance().nombreCientificoGenero;
        String especie = FormData.getInstance().nombreCientificoEspecie;
        String fecha = String.valueOf(FormData.getInstance().fecha);

        String fechaLimpia = fecha.replace("/", "-");
        if (tipoImagen.isEmpty()) tipoImagen = "Desconocido";

        String nombreGenerado = acceso + "-" + genero + "-" + especie + "-" + fechaLimpia + "-" + tipoImagen;

        new AlertDialog.Builder(this)
                .setTitle("¿Guardar la foto?")
                .setMessage("Nombre generado:\n\n" + nombreGenerado + ".jpg\n\n¿Deseas guardar esta foto con ese nombre?")
                .setPositiveButton("Guardar", (dialog, which) -> renombrarFoto(nombreGenerado))
                .setNegativeButton("Descartar", (dialog, which) -> descartarFoto())
                .setCancelable(false)
                .show();
    }

    private void descartarFoto() {
        File file = new File(currentPhotoPath);
        if (file.exists() && file.delete()) {
            if (imageViewActual != null) imageViewActual.setImageDrawable(null);
            Toast.makeText(this, "Foto descartada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo eliminar la foto", Toast.LENGTH_SHORT).show();
        }
        imageViewActual = null;
        tipoImagen = "";
    }

    private void renombrarFoto(String nuevoNombre) {
        File originalFile = new File(currentPhotoPath);
        File nuevoArchivo = new File(originalFile.getParent(), nuevoNombre + ".jpg");

        if (originalFile.renameTo(nuevoArchivo)) {
            currentPhotoPath = nuevoArchivo.getAbsolutePath();
            guardarEnGaleria(nuevoArchivo, nuevoNombre);
            Toast.makeText(this, "Foto guardada como: " + nuevoNombre + ".jpg", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al renombrar la foto", Toast.LENGTH_SHORT).show();
        }
        imageViewActual = null;
        tipoImagen = "";
    }

    private void guardarEnGaleria(File origen, String nombre) {
        File directorioGaleria = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Forestales");
        if (!directorioGaleria.exists()) {
            if (!directorioGaleria.mkdirs()) {
                Toast.makeText(this, "No se pudo crear carpeta Forestales", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        File destino = new File(directorioGaleria, nombre + ".jpg");
        try (InputStream in = new FileInputStream(origen); OutputStream out = new FileOutputStream(destino)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) out.write(buffer, 0, length);
            MediaScannerConnection.scanFile(this, new String[]{destino.getAbsolutePath()}, new String[]{"image/jpeg"}, null);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al copiar a galería", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        try {
            File photoFile = createImageFile();
            photoURI = FileProvider.getUriForFile(this, "com.example.FotosCarpeta.fileprovider", photoFile);
            takePictureLauncher.launch(photoURI);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al crear archivo de imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void configurarSeekBar(SeekBar seekBar, TextView textView, String label, View spinner, View layoutBotones, View imageView) {
        seekBar.setMax(20);
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
        return true;
    }

    private void guardarEnBaseDeDatos() {
        // Tu lógica aquí
    }
}
