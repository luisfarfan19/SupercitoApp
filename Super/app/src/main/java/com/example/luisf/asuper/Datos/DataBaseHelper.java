package com.example.luisf.asuper.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by luisf on 14/10/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NOMBRE = "super.db";
    private static final int DB_VERSION = 1;

    public static final String CREATE_ITEM_TABLE = "CREATE TABLE "
            + ItemContract.Entrada.NOMBRE_TABLA + "("
            + ItemContract.Entrada.COLUMNA_ID + " TEXT PRIMARY KEY, "
            + ItemContract.Entrada.COLUMNA_NOMBRE + " TEXT, "
            + ItemContract.Entrada.COLUMNA_PRECIO + " TEXT, "
            + ItemContract.Entrada.COLUMNA_FOTO + " TEXT " + ");";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + ItemContract.Entrada.NOMBRE_TABLA;

    public DataBaseHelper(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
