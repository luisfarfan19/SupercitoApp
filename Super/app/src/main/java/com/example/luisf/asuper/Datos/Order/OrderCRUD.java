package com.example.luisf.asuper.Datos.Order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.luisf.asuper.Datos.DataBaseHelper;

import java.util.ArrayList;

/**
 * Created by luisf on 16/10/2017.
 */

public class OrderCRUD {
    private DataBaseHelper helper;

    public OrderCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }

    public void newOrder(Order order) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderContract.Entrada.COLUMNA_FECHA, order.getFecha());
        values.put(OrderContract.Entrada.COLUMNA_TOTAL, order.getTotal());
        values.put(OrderContract.Entrada.COLUMNA_CANTIDAD, order.getCantidad());

        long newRowId = db.insert(OrderContract.Entrada.NOMBRE_TABLA, null, values);

        db.close();
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<Order>();
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columnas = {
                OrderContract.Entrada.COLUMNA_ID,
                OrderContract.Entrada.COLUMNA_FECHA,
                OrderContract.Entrada.COLUMNA_TOTAL,
                OrderContract.Entrada.COLUMNA_CANTIDAD,
        };

        Cursor c = db.query(OrderContract.Entrada.NOMBRE_TABLA,
                columnas,
                null,
                null,
                null,
                null,
                null);
        while (c.moveToNext()) {
            orders.add(new Order(
                    c.getString(c.getColumnIndexOrThrow(OrderContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(OrderContract.Entrada.COLUMNA_FECHA)),
                    c.getString(c.getColumnIndexOrThrow(OrderContract.Entrada.COLUMNA_TOTAL)),
                    c.getString(c.getColumnIndexOrThrow(OrderContract.Entrada.COLUMNA_CANTIDAD))
            ));
        }
        c.close();
        return orders;
    }

    public Order getLastOrder() {
        Order order = null;

        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columnas = {
                OrderContract.Entrada.COLUMNA_ID,
                OrderContract.Entrada.COLUMNA_FECHA,
                OrderContract.Entrada.COLUMNA_TOTAL,
                OrderContract.Entrada.COLUMNA_CANTIDAD,
        };

        Cursor c = db.query(OrderContract.Entrada.NOMBRE_TABLA,
                columnas,
                null,
                null,
                null,
                null,
                null);
        while (c.move(c.getCount())) {
            order = new Order(
                    c.getString(c.getColumnIndexOrThrow(OrderContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(OrderContract.Entrada.COLUMNA_FECHA)),
                    c.getString(c.getColumnIndexOrThrow(OrderContract.Entrada.COLUMNA_TOTAL)),
                    c.getString(c.getColumnIndexOrThrow(OrderContract.Entrada.COLUMNA_CANTIDAD))
            );
        }
        c.close();
        return order;
    }

    public void updateOrder(Order order) {

        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderContract.Entrada.COLUMNA_ID, order.getId());
        values.put(OrderContract.Entrada.COLUMNA_FECHA, order.getFecha());
        values.put(OrderContract.Entrada.COLUMNA_TOTAL, order.getTotal());
        values.put(OrderContract.Entrada.COLUMNA_CANTIDAD, order.getCantidad());

        db.update(
                OrderContract.Entrada.NOMBRE_TABLA,
                values,
                "id = ?",
                new String[]{String.valueOf(order.getId())}
        );
        db.close();
    }

    public void deleteOrder(Order order) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(
                OrderContract.Entrada.NOMBRE_TABLA,
                "id = ?",
                new String[]{String.valueOf(order.getId())}
        );
        db.close();
    }
}
