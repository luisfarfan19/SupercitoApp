package com.example.luisf.asuper.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.luisf.asuper.Adaptadores.CustomAdapterMisCompras;
import com.example.luisf.asuper.Datos.Item.Item;
import com.example.luisf.asuper.Datos.Order.Order;
import com.example.luisf.asuper.Datos.Order.OrderCRUD;
import com.example.luisf.asuper.R;

import java.util.ArrayList;

public class MisCompras extends AppCompatActivity {
    private OrderCRUD orderCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_compras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lvMisCompras = (ListView) findViewById(R.id.lvMisCompras);
        ArrayList<Order> ordersList = new ArrayList<Order>();
        orderCRUD = new OrderCRUD(this);
        ordersList = orderCRUD.getOrders();

        ArrayAdapter<Order> adapter1 = new CustomAdapterMisCompras(MisCompras.this, ordersList);
        lvMisCompras.setAdapter(adapter1);
        final ArrayList<Order> finalOrdersList = ordersList;
        lvMisCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MisCompras.this, HistorialCompras.class);
                intent.putExtra("OrderId", finalOrdersList.get(position).getId().toString());
                intent.putExtra("OrderFecha", finalOrdersList.get(position).getFecha().toString());
                intent.putExtra("OrderTotal", finalOrdersList.get(position).getTotal().toString());
                intent.putExtra("OrderCantidad", finalOrdersList.get(position).getCantidad().toString());
                startActivity(intent);

            }
        });

        FloatingActionButton fabNuevoTicket = (FloatingActionButton) findViewById(R.id.fabNuevoTicket);
        fabNuevoTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crear compra nueva AQUI
                Intent intent = new Intent(MisCompras.this, Carrito.class);
                startActivity(intent);
            }
        });
    }

}
