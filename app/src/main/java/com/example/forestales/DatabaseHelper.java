package com.example.forestales;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Forestales.db";

    public DatabaseHelper(@Nullable Context context){
        super(context,"Forestales.db", null, 1);
    }
    @Override
    public  void onCreate(SQLiteDatabase MyDatabase){
        MyDatabase.execSQL("create Table users(email Text primary key, password TEXT, job TEXT)");

        MyDatabase.execSQL("CREATE TABLE Arboles (" +
                "idArbol INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "numeroAcceso TEXT, " +
                "nombreFamilia TEXT, " +
                "nombreComun TEXT, " +
                "nombreCientificoGenero TEXT, " +
                "nombreCientificoEspecie TEXT, " +
                "especieOriginaria BOOLEAN, " +
                "ecologiaDistribucion TEXT, " +
                "clasificacionTaxonomica TEXT , " +
                "coordenadas TEXT)");
        MyDatabase.execSQL("CREATE TABLE registro (" +
                "idRegistro INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha DATE, " +
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

        MyDatabase.execSQL("CREATE TABLE imagen (" +
                "idRegistro INTEGER, " +
                "archivoHojas BLOB, extensionHojas TEXT, " +
                "archivoFlores BLOB, extensionFlores TEXT, " +
                "archivoFrutos BLOB, extensionFrutos TEXT, " +
                "archivoRamas BLOB, extensionRamas TEXT, " +
                "archivoCorteza BLOB, extensionCorteza TEXT, " +
                "archivoGeneral BLOB, extensionGeneral TEXT, " +
                "FOREIGN KEY(idRegistro) REFERENCES registro(idRegistro))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists Arboles");
        MyDB.execSQL("drop Table if exists registro");
        MyDB.execSQL("drop Table if exists imagen");

    }


    public Boolean insertUser(String email, String password, String job){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("job", job);
        long result = MyDatabase.insert("users", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});

        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

}
