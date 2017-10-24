package com.example.luisf.asuper.Datos.Order;

/**
 * Created by luisf on 16/10/2017.
 */

public class Order {
    private String id;
    private String fecha;
    private String total;
    private String cantidad;

    public Order(String id, String fecha, String total, String cantidad) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.cantidad = cantidad;
    }

    public Order(String fecha, String total, String cantidad) {
        this.fecha = fecha;
        this.total = total;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
