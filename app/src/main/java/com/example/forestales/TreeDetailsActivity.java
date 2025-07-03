package com.example.forestales;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TreeDetailsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private int treeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_details);

        dbHelper = new DatabaseHelper(this);
        treeId = getIntent().getIntExtra("TREE_ID", -1);

        TextView tvBasicInfo = findViewById(R.id.tvBasicInfo);
        TextView tvDetails = findViewById(R.id.tvDetails);
        Button btnBack = findViewById(R.id.btnBack);

        if (treeId != -1) {
            displayTreeDetails(treeId, tvBasicInfo, tvDetails);
        } else {
            tvBasicInfo.setText("ID de árbol no válido");
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private void displayTreeDetails(int treeId, TextView tvBasicInfo, TextView tvDetails) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consulta información del árbol
        Cursor treeCursor = db.rawQuery(
                "SELECT nombreComun, nombreCientificoGenero, nombreCientificoEspecie, nombreFamilia, " +
                        "especieOriginaria, ecologiaDistribucion, clasificacionTaxonomica, coordenadas " +
                        "FROM Arboles WHERE numeroAcceso = ?",
                new String[]{String.valueOf(treeId)}
        );

        if (treeCursor == null) {
            tvBasicInfo.setText("Error al consultar árbol");
            return;
        }

        if (treeCursor.moveToFirst()) {
            String commonName = treeCursor.getString(0);
            String genus = treeCursor.getString(1);
            String species = treeCursor.getString(2);
            String family = treeCursor.getString(3);
            boolean isNative = treeCursor.getInt(4) == 1;
            String distribution = treeCursor.getString(5);
            String taxonomy = treeCursor.getString(6);
            String coordinates = treeCursor.getString(7);

            String basicInfo = String.format("%s\n%s %s\nFamilia: %s\nOrigen: %s",
                    commonName, genus, species, family, isNative ? "Nativo" : "No nativo");

            String fullDetails = String.format(
                    "\nDistribución ecológica: %s\n" +
                            "Clasificación taxonómica: %s\n" +
                            "Coordenadas: %s\n\n",
                    distribution, taxonomy, coordinates
            );

            tvBasicInfo.setText(basicInfo);

            // Consulta registros del árbol
            Cursor recordsCursor = db.rawQuery(
                    "SELECT fecha, habitoCrecimiento, tipoCrecimiento, altura, diametroFuste, " +
                            "EstadoSalud, disturbiosMeteorologicos, interacciones, organismoInteracciones, " +
                            "presenciaCombustibles, combustiblesFinos, combustiblesPesados, peligroIncendio, " +
                            "hojas, estadoHojas, flores, frutos, estadoFrutos, ramas, corteza, usos, Observaciones " +
                            "FROM registro WHERE numeroAcceso = ? ORDER BY fecha DESC LIMIT 1",
                    new String[]{String.valueOf(treeId)}
            );

            if (recordsCursor != null) {
                if (recordsCursor.moveToFirst()) {
                    fullDetails += "ÚLTIMO REGISTRO:\n\n";
                    fullDetails += "Fecha: " + recordsCursor.getString(0) + "\n";
                    fullDetails += "Hábito crecimiento: " + recordsCursor.getString(1) + "\n";
                    // ... (agrega todos los campos)
                } else {
                    fullDetails += "\nNo hay registros para este árbol";
                }
                recordsCursor.close();
            }

            tvDetails.setText(fullDetails);
        } else {
            tvBasicInfo.setText("Árbol no encontrado");
        }
        treeCursor.close();
    }
}