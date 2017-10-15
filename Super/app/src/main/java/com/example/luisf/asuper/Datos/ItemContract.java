package com.example.luisf.asuper.Datos;

import android.provider.BaseColumns;

/**
 * Created by luisf on 14/10/2017.
 */

public final class ItemContract {
    private ItemContract(){}

    public static class Entrada implements BaseColumns {
        public static final String NOMBRE_TABLA = "items";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_PRECIO= "precio";
        public static final String COLUMNA_FOTO = "urlFoto";
    }
}
