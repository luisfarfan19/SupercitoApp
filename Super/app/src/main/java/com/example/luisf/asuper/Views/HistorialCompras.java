package com.example.luisf.asuper.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luisf.asuper.Adaptadores.CustomAdapter;
import com.example.luisf.asuper.Adaptadores.CustomAdapterHistoria;
import com.example.luisf.asuper.Datos.Item.Item;
import com.example.luisf.asuper.Datos.Item.ItemCRUD;
import com.example.luisf.asuper.Datos.OrderDetail.OrderDetail;
import com.example.luisf.asuper.Datos.OrderDetail.OrderDetailCRUD;
import com.example.luisf.asuper.R;

import java.util.ArrayList;

public class HistorialCompras extends AppCompatActivity {
    private ItemCRUD itemCRUD;
    private OrderDetailCRUD orderDetailCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_compras);
        ListView lvMisItemsEnCompraHistorial = (ListView) findViewById(R.id.lvMisItemsEnCompraHistorial);

        TextView tvFechaHistorial = (TextView) findViewById(R.id.tvFechaHistorial);
        TextView tvTotalHistorial = (TextView) findViewById(R.id.tvTotalHistorial);
        Bundle bundle = getIntent().getExtras();
        final String orderId = bundle.getString("OrderId");
        final String orderFecha = bundle.getString("OrderFecha");
        final String orderTotal = bundle.getString("OrderTotal");
        final String orderCantidad = bundle.getString("OrderCantidad");

        tvFechaHistorial.setText("Fecha de compra: " + orderFecha);
        tvTotalHistorial.setText("Total: $ " + orderTotal);

        orderDetailCRUD = new OrderDetailCRUD(this);
        itemCRUD = new ItemCRUD(this);

        ArrayList<OrderDetail> orderDetailsList = new ArrayList<OrderDetail>();
        ArrayList<Item> itemsInOrder = new ArrayList<Item>();
        orderDetailsList = orderDetailCRUD.getOrderDetailInLastOrder(orderId);
        int size = orderDetailsList.size();

        for (int i = 0; i < size; i++) {
            Log.d("OrderDetailList",orderDetailsList.get(i).getId().toString());
            String aux = orderDetailsList.get(i).getIdItem().toString();
            itemsInOrder.add(itemCRUD.getItem(aux));
        }
        /*int size2 = itemsInOrder.size();
        for (int i = 0; i < size2; i++) {
            Log.d("ItemsInOrder",itemsInOrder.get(i).getNombre().toString());
        }*/

        ArrayAdapter<Item> adapter1 = new CustomAdapter(HistorialCompras.this, itemsInOrder);
        lvMisItemsEnCompraHistorial.setAdapter(adapter1);
        lvMisItemsEnCompraHistorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistorialCompras.this, HistorialCompras.class);
                startActivity(intent);
            }
        });
    }
}
