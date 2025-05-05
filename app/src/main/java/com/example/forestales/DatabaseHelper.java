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
        MyDatabase.execSQL("create Table users(email Text primary key, password TEXT)");
        MyDatabase.execSQL("create Table arboles(numero INTEGER  primary key, nombreCientifico TEXT, nombreComun TEXT, " +
                " tipoFuste TEXT, diametroFuste REAL, diametroFusteM REAL, altura REAL," +
                "porHojas INTEGER, porFlores INTEGER, porFrutos INTEGER, madurezFruto TEXT, estadoHojas TEXT, " +
                "interaccion TEXT, organismo TEXT, observaciones TEXT)");
        MyDatabase.execSQL("create Table fotos(id INTEGER PRIMARY KEY AUTOINCREMENT, numero INTEGER, URL TEXT, fuente ENUM)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists arboles");
        MyDB.execSQL("drop Table if exists fotos");
    }

    public Boolean insertUser(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
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
