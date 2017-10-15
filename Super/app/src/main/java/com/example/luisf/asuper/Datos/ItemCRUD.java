package com.example.luisf.asuper.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by luisf on 14/10/2017.
 */

public class ItemCRUD {
    private DataBaseHelper helper;

    public ItemCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }

    public void newItem(Item item) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Log.d("Hola","Muundo");
        helper.onCreate(db);
        ContentValues values = new ContentValues();
        values.put(ItemContract.Entrada.COLUMNA_ID, item.getId());
        values.put(ItemContract.Entrada.COLUMNA_NOMBRE, item.getNombre());
        values.put(ItemContract.Entrada.COLUMNA_PRECIO, item.getPrecio());
        values.put(ItemContract.Entrada.COLUMNA_FOTO, item.getFoto());

        long newRowId = db.insert(ItemContract.Entrada.NOMBRE_TABLA, null, values);

        db.close();
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columnas = {
                ItemContract.Entrada.COLUMNA_ID,
                ItemContract.Entrada.COLUMNA_NOMBRE,
                ItemContract.Entrada.COLUMNA_PRECIO,
                ItemContract.Entrada.COLUMNA_FOTO,
        };

        Cursor c = db.query(ItemContract.Entrada.NOMBRE_TABLA,
                columnas,
                null,
                null,
                null,
                null,
                null);
        while (c.moveToNext()) {
            items.add(new Item(
                    c.getString(c.getColumnIndexOrThrow(ItemContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(ItemContract.Entrada.COLUMNA_NOMBRE)),
                    c.getString(c.getColumnIndexOrThrow(ItemContract.Entrada.COLUMNA_PRECIO)),
                    c.getString(c.getColumnIndexOrThrow(ItemContract.Entrada.COLUMNA_FOTO))
            ));
        }
        c.close();
        return items;
    }

    public Item getItem(String id) {
        Item item = null;

        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columnas = {
                ItemContract.Entrada.COLUMNA_ID,
                ItemContract.Entrada.COLUMNA_NOMBRE,
                ItemContract.Entrada.COLUMNA_PRECIO,
                ItemContract.Entrada.COLUMNA_FOTO,
        };

        Cursor c = db.query(ItemContract.Entrada.NOMBRE_TABLA,
                columnas,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        while (c.moveToNext()) {
            item = new Item(
                    c.getString(c.getColumnIndexOrThrow(ItemContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(ItemContract.Entrada.COLUMNA_NOMBRE)),
                    c.getString(c.getColumnIndexOrThrow(ItemContract.Entrada.COLUMNA_PRECIO)),
                    c.getString(c.getColumnIndexOrThrow(ItemContract.Entrada.COLUMNA_FOTO))
            );
        }
        c.close();
        return item;
    }

    public void updateItem(Item item) {

        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemContract.Entrada.COLUMNA_ID, item.getId());
        values.put(ItemContract.Entrada.COLUMNA_NOMBRE, item.getNombre());
        values.put(ItemContract.Entrada.COLUMNA_PRECIO, item.getPrecio());
        values.put(ItemContract.Entrada.COLUMNA_FOTO, item.getFoto());

        db.update(
                ItemContract.Entrada.NOMBRE_TABLA,
                values,
                "id = ?",
                new String[]{String.valueOf(item.getId())}
        );
        db.close();
    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(
                ItemContract.Entrada.NOMBRE_TABLA,
                "id = ?",
                new String[]{String.valueOf(item.getId())}
        );
        db.close();
    }
}

