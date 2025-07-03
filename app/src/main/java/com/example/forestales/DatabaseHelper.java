package com.example.forestales;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Forestales.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Forestales.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(email TEXT PRIMARY KEY, password TEXT, job TEXT)");

        db.execSQL("CREATE TABLE Arboles (" +
                "numeroAcceso INTEGER PRIMARY KEY, " +
                "nombreFamilia TEXT, " +
                "nombreComun TEXT, " +
                "nombreCientificoGenero TEXT, " +
                "nombreCientificoEspecie TEXT, " +
                "especieOriginaria BOOLEAN, " +
                "ecologiaDistribucion TEXT, " +
                "clasificacionTaxonomica TEXT, " +
                "coordenadas TEXT)");

        db.execSQL("CREATE TABLE registro (" +
                "idRegistro INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha TEXT, " + // Cambiado de DATE a TEXT para mayor compatibilidad
                "numeroAcceso INTEGER, " +
                "habitoCrecimiento TEXT, " +
                "tipoCrecimiento TEXT, " +
                "altura REAL, " +
                "medirAltura TEXT, " +
                "diametroFuste REAL, " +
                "medirDiametroFuste TEXT, " +
                "EstadoSalud TEXT, " +
                "disturbiosMeteorologicos TEXT, " +
                "interacciones TEXT, " +
                "organismoInteracciones TEXT, " +
                "presenciaCombustibles BOOLEAN, " +
                "combustiblesFinos TEXT, " +
                "combustiblesPesados TEXT, " +
                "peligroIncendio TEXT, " +
                "hojas INTEGER, " +
                "estadoHojas TEXT, " +
                "flores INTEGER, " +
                "frutos INTEGER, " +
                "estadoFrutos TEXT, " +
                "ramas TEXT, " +
                "corteza TEXT, " +
                "usos TEXT, " +
                "Observaciones TEXT, " +
                "FOREIGN KEY(numeroAcceso) REFERENCES Arboles(numeroAcceso))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS Arboles");
        db.execSQL("DROP TABLE IF EXISTS registro");
        onCreate(db);
    }

    // Métodos para usuarios
    public boolean insertUser(String email, String password, String job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        values.put("job", job);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email = ? AND password = ?",
                new String[]{email, password}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Métodos para árboles
    public Cursor getAllTrees() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT numeroAcceso, nombreComun, nombreCientificoGenero, nombreCientificoEspecie " +
                        "FROM Arboles ORDER BY nombreComun",
                null
        );
    }

    public Cursor getTreeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM Arboles WHERE numeroAcceso = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public boolean insertArbol(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert("Arboles", null, values);
        return result != -1;
    }

    public boolean insertRegistro(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert("registro", null, values);
        return result != -1;
    }
}