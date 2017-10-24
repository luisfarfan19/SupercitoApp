package com.example.luisf.asuper.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.luisf.asuper.Datos.Item.ItemContract;
import com.example.luisf.asuper.Datos.Order.OrderContract;
import com.example.luisf.asuper.Datos.OrderDetail.OrderDetailCRUD;
import com.example.luisf.asuper.Datos.OrderDetail.OrderDetailContract;

/**
 * Created by luisf on 14/10/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NOMBRE = "super.db";
    private static final int DB_VERSION = 1;

    public static final String CREATE_ITEM_TABLE = "CREATE TABLE "
            + ItemContract.Entrada.NOMBRE_TABLA + "("
            + ItemContract.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ItemContract.Entrada.COLUMNA_NOMBRE + " TEXT, "
            + ItemContract.Entrada.COLUMNA_PRECIO + " TEXT, "
            + ItemContract.Entrada.COLUMNA_FOTO + " TEXT " + ");";

    public static final String CREATE_ORDER_TABLE = "CREATE TABLE "
            + OrderContract.Entrada.NOMBRE_TABLA + "("
            + OrderContract.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + OrderContract.Entrada.COLUMNA_FECHA + " TEXT, "
            + OrderContract.Entrada.COLUMNA_TOTAL + " TEXT, "
            + OrderContract.Entrada.COLUMNA_CANTIDAD + " TEXT " + ");";

    public static final String CREATE_ORDERDETAIL_TABLE = "CREATE TABLE "
            + OrderDetailContract.Entrada.NOMBRE_TABLA + " ("
            + OrderDetailContract.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + OrderDetailContract.Entrada.COLUMNA_ID_PRODUCT + " INTEGER, "
            + OrderDetailContract.Entrada.COLUMNA_ID_ORDER + " INTEGER, "
            + OrderDetailContract.Entrada.COLUMNA_CANTIDAD + " TEXT " + ");";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + ItemContract.Entrada.NOMBRE_TABLA;

    private static final String SQL_DELETE_ORDER = "DROP TABLE IF EXISTS "
            + OrderContract.Entrada.NOMBRE_TABLA;

    private static final String SQL_DELETE_ORDERDETAIL = "DROP TABLE IF EXISTS "
            + OrderDetailContract.Entrada.NOMBRE_TABLA;

    public DataBaseHelper(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDERDETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_ORDER);
        sqLiteDatabase.execSQL(SQL_DELETE_ORDERDETAIL);
        onCreate(sqLiteDatabase);
    }
}
