package com.example.luisf.asuper.Datos.OrderDetail;

/**
 * Created by luisf on 16/10/2017.
 */

public class OrderDetail {
    private String id;
    private String idItem;
    private String idOrder;
    private String cantidadSameItem;

    public OrderDetail(String id, String idItem, String idOrder, String cantidadSameItem) {
        this.id = id;
        this.idItem = idItem;
        this.idOrder = idOrder;
        this.cantidadSameItem = cantidadSameItem;
    }

    public OrderDetail(String idItem, String idOrder, String cantidadSameItem) {
        this.idItem = idItem;
        this.idOrder = idOrder;
        this.cantidadSameItem = cantidadSameItem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getCantidadSameItem() {
        return cantidadSameItem;
    }

    public void setCantidadSameItem(String cantidadSameItem) {
        this.cantidadSameItem = cantidadSameItem;
    }
}
