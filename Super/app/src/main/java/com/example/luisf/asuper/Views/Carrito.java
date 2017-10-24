package com.example.luisf.asuper.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.luisf.asuper.Adaptadores.CustomAdapter;
import com.example.luisf.asuper.Adaptadores.CustomAdapterItemsAdd;
import com.example.luisf.asuper.Datos.Item.Item;
import com.example.luisf.asuper.Datos.Item.ItemCRUD;
import com.example.luisf.asuper.Datos.Order.Order;
import com.example.luisf.asuper.Datos.Order.OrderCRUD;
import com.example.luisf.asuper.Datos.OrderDetail.OrderDetail;
import com.example.luisf.asuper.Datos.OrderDetail.OrderDetailCRUD;
import com.example.luisf.asuper.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Carrito extends AppCompatActivity {
    private ItemCRUD itemCRUD;
    private OrderCRUD orderCRUD;
    private OrderDetailCRUD orderDetailCRUD;
    Item selectedItem;
    private EditText etCantidad;
    String idLastOrden;
    int addTotal = 0;
    int cantidadTodosItems =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        orderDetailCRUD = new OrderDetailCRUD(this);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        //Create new orden
        orderCRUD = new OrderCRUD(this);
        orderCRUD.newOrder(new Order(date, "", ""));

        //Get id of last orden
        idLastOrden = orderCRUD.getLastOrder().getId();

        ListView lvMisItemsParaAgregar = (ListView) findViewById(R.id.lvMisItemsParaAgregar);
        etCantidad = (EditText) findViewById(R.id.etCantidad);
        ArrayList<Item> itemsList = new ArrayList<Item>();
        itemCRUD = new ItemCRUD(this);
        itemsList = itemCRUD.getItems();

        ArrayAdapter<Item> adapter1 = new CustomAdapterItemsAdd(Carrito.this, itemsList);
        lvMisItemsParaAgregar.setAdapter(adapter1);
        final ArrayList<Item> finalItemsList = itemsList;
        lvMisItemsParaAgregar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = finalItemsList.get(position);
                Snackbar.make(view, "Item seleccionado: " + selectedItem.getNombre(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fabGuardarCarrito = (FloatingActionButton) findViewById(R.id.fabGuardarCarrito);
        fabGuardarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Carrito.this, MisCompras.class);
                Snackbar.make(view, "CantidadTodosItems: " + String.valueOf(cantidadTodosItems), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(intent);
            }
        });

        FloatingActionButton fabNuevoItemCarrito = (FloatingActionButton) findViewById(R.id.fabNuevoItemCarrito);
        fabNuevoItemCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedItem == null) {
                    Snackbar.make(view, "Selecciona un item", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (etCantidad.getText().toString().equals("")) {
                    Snackbar.make(view, "Agregar la cantidad", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //Agregar nuevo item a una compra
                    orderDetailCRUD.newOrderDetail(new OrderDetail(selectedItem.getId(), idLastOrden, etCantidad.getText().toString()));
                    cantidadTodosItems += Integer.parseInt(etCantidad.getText().toString());
                    refreshDetail();
                }
            }
        });
    }

    private void refreshDetail() {
        ListView lvMisItemsEnCompra = (ListView) findViewById(R.id.lvMisItemsEnCompra);
        int precioTotalUnItem;

        orderDetailCRUD = new OrderDetailCRUD(this);
        itemCRUD = new ItemCRUD(this);

        ArrayList<OrderDetail> orderDetailsList = new ArrayList<OrderDetail>();
        ArrayList<Item> itemsInOrder = new ArrayList<Item>();
        orderDetailsList = orderDetailCRUD.getOrderDetailInLastOrder(idLastOrden);
        int size = orderDetailsList.size();
        for (int i = 0; i < size; i++) {
            Log.d("OrderDetailCantidad", orderDetailsList.get(i).getCantidadSameItem().toString());
            int cantidadEnOrderDetail = Integer.parseInt(orderDetailsList.get(i).getCantidadSameItem().toString());
            String aux = orderDetailsList.get(i).getIdItem().toString();
            Item itemToAdd = itemCRUD.getItem(aux);
            int precioItem = Integer.parseInt(itemToAdd.getPrecio().toString());
            addTotal += (precioItem * cantidadEnOrderDetail);

            itemsInOrder.add(itemToAdd);
        }
        Log.d("AddTotal: ", String.valueOf(addTotal));
        Log.d("CantidadTotal: ", String.valueOf(addTotal));
        //addTotal es el precio total de la Compra va en campo: total
        //cantidadTotal es el numero de items en la compra va en campo: cantidad
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        orderCRUD.updateOrder(new Order(idLastOrden, date, String.valueOf(addTotal), String.valueOf(cantidadTodosItems)));
        //Actualizar total y cantidad de Order


        ArrayAdapter<Item> adapter1 = new CustomAdapter(Carrito.this, itemsInOrder);
        lvMisItemsEnCompra.setAdapter(adapter1);
        lvMisItemsEnCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(Carrito.this, HistorialCompras.class);
                //startActivity(intent);
            }
        });
    }

}
