package com.example.luisf.asuper.Datos.OrderDetail;

import android.provider.BaseColumns;

/**
 * Created by luisf on 16/10/2017.
 */

public final class OrderDetailContract {
    private OrderDetailContract(){}

    public static class Entrada implements BaseColumns {
        public static final String NOMBRE_TABLA = "orderdetail";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_ID_PRODUCT = "iditem";
        public static final String COLUMNA_ID_ORDER = "idorder";
        public static final String COLUMNA_CANTIDAD = "cantidad";
    }
}
