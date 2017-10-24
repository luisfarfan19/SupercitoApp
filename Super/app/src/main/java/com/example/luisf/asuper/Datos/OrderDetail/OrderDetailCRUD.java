package com.example.luisf.asuper.Datos.OrderDetail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.luisf.asuper.Datos.DataBaseHelper;

import java.util.ArrayList;

/**
 * Created by luisf on 16/10/2017.
 */

public class OrderDetailCRUD {
    private DataBaseHelper helper;

    public OrderDetailCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }

    public void newOrderDetail(OrderDetail orderDetail) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderDetailContract.Entrada.COLUMNA_ID_PRODUCT, orderDetail.getIdItem());
        values.put(OrderDetailContract.Entrada.COLUMNA_ID_ORDER, orderDetail.getIdOrder());
        values.put(OrderDetailContract.Entrada.COLUMNA_CANTIDAD, orderDetail.getCantidadSameItem());

        long newRowId = db.insert(OrderDetailContract.Entrada.NOMBRE_TABLA, null, values);

        db.close();
    }

    public ArrayList<OrderDetail> getOrderDetail() {
        ArrayList<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columnas = {
                OrderDetailContract.Entrada.COLUMNA_ID,
                OrderDetailContract.Entrada.COLUMNA_ID_PRODUCT,
                OrderDetailContract.Entrada.COLUMNA_ID_ORDER,
                OrderDetailContract.Entrada.COLUMNA_CANTIDAD,
        };

        Cursor c = db.query(OrderDetailContract.Entrada.NOMBRE_TABLA,
                columnas,
                null,
                null,
                null,
                null,
                null);
        while (c.moveToNext()) {
            orderDetails.add(new OrderDetail(
                    c.getString(c.getColumnIndexOrThrow(OrderDetailContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(OrderDetailContract.Entrada.COLUMNA_ID_PRODUCT)),
                    c.getString(c.getColumnIndexOrThrow(OrderDetailContract.Entrada.COLUMNA_ID_ORDER)),
                    c.getString(c.getColumnIndexOrThrow(OrderDetailContract.Entrada.COLUMNA_CANTIDAD))
            ));
        }
        c.close();
        return orderDetails;
    }

    public ArrayList<OrderDetail> getOrderDetailInLastOrder(String id) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        SQLiteDatabase db = helper.getReadableDatabase();

        String SQL = "SELECT * FROM orderdetail WHERE idorder = " + id;
        Log.d("SQL_SELECT", SQL);

        Cursor c = db.rawQuery(SQL, null);

        while (c.moveToNext()) {
            orderDetails.add(new OrderDetail(
                    c.getString(c.getColumnIndexOrThrow(OrderDetailContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(OrderDetailContract.Entrada.COLUMNA_ID_PRODUCT)),
                    c.getString(c.getColumnIndexOrThrow(OrderDetailContract.Entrada.COLUMNA_ID_ORDER)),
                    c.getString(c.getColumnIndexOrThrow(OrderDetailContract.Entrada.COLUMNA_CANTIDAD))
            ));
        }
        c.close();
        return orderDetails;
    }

    public void updateOrderDetail(OrderDetail orderDetail) {

        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderDetailContract.Entrada.COLUMNA_ID, orderDetail.getId());
        values.put(OrderDetailContract.Entrada.COLUMNA_ID_PRODUCT, orderDetail.getIdItem());
        values.put(OrderDetailContract.Entrada.COLUMNA_ID_ORDER, orderDetail.getIdOrder());
        values.put(OrderDetailContract.Entrada.COLUMNA_CANTIDAD, orderDetail.getCantidadSameItem());

        db.update(
                OrderDetailContract.Entrada.NOMBRE_TABLA,
                values,
                "id = ?",
                new String[]{String.valueOf(orderDetail.getId())}
        );
        db.close();
    }

    public void deleteOrderDetail(OrderDetail orderDetail) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(
                OrderDetailContract.Entrada.NOMBRE_TABLA,
                "id = ?",
                new String[]{String.valueOf(orderDetail.getId())}
        );
        db.close();
    }
}
