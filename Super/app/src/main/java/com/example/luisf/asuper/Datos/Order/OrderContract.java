package com.example.luisf.asuper.Datos.Order;

import android.provider.BaseColumns;

/**
 * Created by luisf on 16/10/2017.
 */

public final class OrderContract {
    private OrderContract(){}

    public static class Entrada implements BaseColumns {
        public static final String NOMBRE_TABLA = "orders";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_FECHA = "fecha";
        public static final String COLUMNA_TOTAL = "total";
        public static final String COLUMNA_CANTIDAD = "cantidad";
    }
}
