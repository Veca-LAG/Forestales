package com.example.forestales;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EraseActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private LinearLayout listaArboles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_erase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);
        listaArboles = findViewById(R.id.listaArboles);

        cargarArboles();

        /*boton anterior*/
        Button anteriorButton = findViewById(R.id.btnVolver);
        anteriorButton.setOnClickListener(v -> finish());
    }

    private void cargarArboles() {
        listaArboles.removeAllViews();
        Cursor cursor = databaseHelper.getAllTreesDetailed();

        if (cursor.getCount() == 0) {
            TextView tvEmpty = new TextView(this);
            tvEmpty.setText("No hay árboles registrados");
            tvEmpty.setTextColor(getResources().getColor(android.R.color.white));
            tvEmpty.setTextSize(18);
            listaArboles.addView(tvEmpty);
            return;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombreComun = cursor.getString(1);
            String nombreGenero = cursor.getString(2);
            String nombreEspecie = cursor.getString(3);

            View itemView = LayoutInflater.from(this).inflate(R.layout.item_arbol, null);

            TextView tvNombre = itemView.findViewById(R.id.tvNombreArbol);
            Button btnEliminar = itemView.findViewById(R.id.btnEliminar);

            String textoArbol = nombreComun + " (" + nombreGenero + " " + nombreEspecie + ")";
            tvNombre.setText(textoArbol);

            btnEliminar.setOnClickListener(v -> mostrarDialogoConfirmacion(id, textoArbol));

            listaArboles.addView(itemView);
        }
        cursor.close();
    }

    private void mostrarDialogoConfirmacion(int idArbol, String nombreArbol) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar el árbol: " + nombreArbol + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    boolean eliminado = databaseHelper.deleteTree(idArbol);
                    if (eliminado) {
                        Toast.makeText(EraseActivity.this, "Árbol eliminado correctamente", Toast.LENGTH_SHORT).show();
                        cargarArboles(); // Recargar la lista
                    } else {
                        Toast.makeText(EraseActivity.this, "Error al eliminar el árbol", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}