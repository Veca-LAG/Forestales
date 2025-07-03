package com.example.forestales;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        dbHelper = new DatabaseHelper(this);
        container = findViewById(R.id.layoutContenedor);

        displayTrees();

        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> finish());
    }

    private void displayTrees() {
        container.removeAllViews();

        // Agregar título
        TextView title = new TextView(this);
        title.setText("Árboles Registrados");
        title.setTextColor(getResources().getColor(R.color.green_light));
        title.setTextSize(24);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParams.gravity = 1;
        titleParams.bottomMargin = dpToPx(16);
        title.setLayoutParams(titleParams);
        container.addView(title);

        // Obtener árboles
        Cursor cursor = dbHelper.getAllTrees();

        if (cursor == null) {
            Log.e("DB_ERROR", "Cursor es null");
            return;
        }

        if (cursor.getCount() == 0) {
            TextView noTrees = new TextView(this);
            noTrees.setText("No hay árboles registrados");
            noTrees.setTextColor(getResources().getColor(android.R.color.black));
            noTrees.setTextSize(18);
            container.addView(noTrees);
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String commonName = cursor.getString(1);
                String genus = cursor.getString(2);
                String species = cursor.getString(3);

                // Crear fila
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                rowParams.bottomMargin = dpToPx(8);
                row.setLayoutParams(rowParams);

                // Info árbol
                TextView treeInfo = new TextView(this);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1);
                treeInfo.setLayoutParams(textParams);
                treeInfo.setText(String.format("%s (%s %s)", commonName, genus, species));
                treeInfo.setTextColor(getResources().getColor(android.R.color.black));
                treeInfo.setTextSize(18);
                treeInfo.setPadding(dpToPx(8), 0, 0, 0);

                // Botón detalles
                Button detailsBtn = new Button(this);
                detailsBtn.setLayoutParams(new LinearLayout.LayoutParams(
                        dpToPx(150),
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                detailsBtn.setText("Detalles");
                detailsBtn.setBackgroundColor(getResources().getColor(R.color.green_accent));
                detailsBtn.setTextColor(getResources().getColor(android.R.color.black));
                detailsBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(ViewActivity.this, TreeDetailsActivity.class);
                    intent.putExtra("TREE_ID", id);
                    startActivity(intent);
                });

                row.addView(treeInfo);
                row.addView(detailsBtn);
                container.addView(row);
            }
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTrees();
    }
}