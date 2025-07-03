package com.example.forestales;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.ByteArrayOutputStream;
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
    private EditText etRamas, etCorteza, etUsos, etObservacion;
    private Button btnAnterior, btnFinalizar;

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private Uri photoURI;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private String currentPhotoPath;
    private String tipoImagen = "";
    private ImageView imageViewActual = null;

    DatabaseHelper myDBHelper = new DatabaseHelper(this);

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
        etObservacion = findViewById(R.id.etObservacion);

        btnAnterior = findViewById(R.id.btnAnterior);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        configurarEditTextMultiline(etRamas);
        configurarEditTextMultiline(etCorteza);
        configurarEditTextMultiline(etUsos);
        configurarEditTextMultiline(etObservacion);

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
            // Porcentajes y datos
            int hojasPorcentaje = seekHojas.getProgress();
            String hojasInteraccion = spinnerHojas.getSelectedItem() != null ?
                    spinnerHojas.getSelectedItem().toString().trim() : "";

            int floresPorcentaje = seekFlores.getProgress();
            int frutosPorcentaje = seekFrutos.getProgress();
            String frutosInteraccion = spinnerFrutos.getSelectedItem() != null ?
                    spinnerFrutos.getSelectedItem().toString().trim() : "";

            String descripcionRamas = etRamas.getText().toString().trim();
            String descripcionCorteza = etCorteza.getText().toString().trim();
            String descripcionUsos = etUsos.getText().toString().trim();
            String descripcionObservaciones = etObservacion.getText().toString().trim();

            // Validaciones básicas

            FormData data = FormData.getInstance();
            data.hojas = hojasPorcentaje;
            data.estadoHojas = hojasInteraccion;
            data.flores = floresPorcentaje;
            data.frutos = frutosPorcentaje;
            data.estadoFrutos = frutosInteraccion;

            data.Ramas = descripcionRamas;
            data.corteza = descripcionCorteza;
            data.usos = descripcionUsos;
            data.observaciones = descripcionObservaciones;


            // Hojas
            if (imgHojas.getDrawable() != null) {
                data.archivoHojas = imageViewToByteArray(imgHojas, "jpg");
                data.extensionHojas = ".jpg";
            }

// Flores
            if (imgFlores.getDrawable() != null) {
                data.archivoFlores = imageViewToByteArray(imgFlores, "jpg");
                data.extensionFlores = ".jpg";
            }

// Frutos
            if (imgFrutos.getDrawable() != null) {
                data.archivoFrutos = imageViewToByteArray(imgFrutos, "jpg");
                data.extensionFrutos = ".jpg";
            }

// Ramas
            if (imgRamas.getDrawable() != null) {
                data.archivoRamas = imageViewToByteArray(imgRamas, "jpg");
                data.extensionRamas = ".jpg";
            }

// Corteza
            if (imgCorteza.getDrawable() != null) {
                data.archivoCorteza = imageViewToByteArray(imgCorteza, "jpg");
                data.extensionCorteza = ".jpg";
            }

// General
            if (imgGeneral.getDrawable() != null) {
                data.archivoGeneral = imageViewToByteArray(imgGeneral, "jpg");
                data.extensionGeneral = ".jpg";
            }


            if (validarCampos()) {
                new AlertDialog.Builder(this)
                        .setTitle("Guardar datos")
                        .setMessage("¿Deseas guardar esta información?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            guardarEnBaseDeDatos();
                            Toast.makeText(this, "Datos guardados.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity3.this, OptionActivity.class);
                            startActivity(intent);
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
            photoURI = FileProvider.getUriForFile(this, "com.example.forestales.fileprovider", photoFile);
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

    private byte[] imageViewToByteArray(ImageView imageView, String format) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Bitmap.CompressFormat compressFormat;
        switch (format.toLowerCase()) {
            case "png":
                compressFormat = Bitmap.CompressFormat.PNG;
                break;
            case "webp":
                compressFormat = Bitmap.CompressFormat.WEBP;
                break;
            default:
                compressFormat = Bitmap.CompressFormat.JPEG;
                break;
        }

        bitmap.compress(compressFormat, 100, stream);
        return stream.toByteArray();
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
        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        FormData.RegistroCompleto data = FormData.getInstance().build();

        // 1️⃣ Insertar en tabla Arboles (usa REPLACE si quieres actualizar)
        ContentValues arbolValues = new ContentValues();
        arbolValues.put("numeroAcceso", data.numeroAcceso);
        arbolValues.put("nombreFamilia", data.nombreFamilia);
        arbolValues.put("nombreComun", data.nombreComun);
        arbolValues.put("nombreCientificoGenero", data.nombreCientificoGenero);
        arbolValues.put("nombreCientificoEspecie", data.nombreCientificoEspecie);
        arbolValues.put("especieOriginaria", data.especieOriginaria ? 1 : 0);
        arbolValues.put("ecologiaDistribucion", data.ecologiaDistribucion);
        arbolValues.put("clasificacionTaxonomica", data.clasificaionTaxonomica);
        arbolValues.put("coordenadas", data.coordenadas);

        db.insertWithOnConflict("Arboles", null, arbolValues, SQLiteDatabase.CONFLICT_REPLACE);

        // 2️⃣ Insertar en tabla registro
        ContentValues registroValues = new ContentValues();
        registroValues.put("fecha", new java.text.SimpleDateFormat("yyyy-MM-dd").format(data.fecha));
        registroValues.put("numeroAcceso", data.numeroAcceso);
        registroValues.put("habitoCrecimiento", data.habitoCrecimiento);
        registroValues.put("tipoCrecimiento", data.tipoCrecimiento);
        registroValues.put("altura", data.altura);
        registroValues.put("medirAltura", data.medirAltura);
        registroValues.put("diametroFuste", data.diametroFuste);
        registroValues.put("medirDiametroFuste", data.medirDiametroFuste);
        registroValues.put("EstadoSalud", data.estadoSalud);
        registroValues.put("disturbiosMeteorologicos", data.disturbiosMeteorologicos);
        registroValues.put("interacciones", data.interacciones);
        registroValues.put("organismoInteracciones", data.organismoInteracciones);
        registroValues.put("presenciaCombustibles", data.presenciaCombustibles ? 1 : 0);
        registroValues.put("combustiblesFinos", data.combustiblesFinos);
        registroValues.put("combustiblesPesados", data.combustiblesPesados);
        registroValues.put("peligroIncendio", data.peligroIncendio);
        registroValues.put("hojas", data.hojas);
        registroValues.put("estadoHojas", data.estadoHojas);
        registroValues.put("flores", data.flores);
        registroValues.put("frutos", data.frutos);
        registroValues.put("estadoFrutos", data.estadoFrutos);
        registroValues.put("ramas", data.Ramas);
        registroValues.put("corteza", data.corteza);
        registroValues.put("usos", data.usos);
        registroValues.put("Observaciones", data.observaciones);

        long idRegistro = db.insert("registro", null, registroValues);

        // 3️⃣ Insertar en tabla imagen
        ContentValues imagenValues = new ContentValues();
        imagenValues.put("idRegistro", idRegistro);
        imagenValues.put("archivoHojas", data.archivoHojas);
        imagenValues.put("extensionHojas", data.extensionHojas);
        imagenValues.put("archivoFlores", data.archivoFlores);
        imagenValues.put("extensionFlores", data.extensionFlores);
        imagenValues.put("archivoFrutos", data.archivoFrutos);
        imagenValues.put("extensionFrutos", data.extensionFrutos);
        imagenValues.put("archivoRamas", data.archivoRamas);
        imagenValues.put("extensionRamas", data.extensionRamas);
        imagenValues.put("archivoCorteza", data.archivoCorteza);
        imagenValues.put("extensionCorteza", data.extensionCorteza);
        imagenValues.put("archivoGeneral", data.archivoGeneral);
        imagenValues.put("extensionGeneral", data.extensionGeneral);

        db.insert("imagen", null, imagenValues);

        db.close();

        Toast.makeText(this, "Registro guardado correctamente.", Toast.LENGTH_SHORT).show();
    }
}
