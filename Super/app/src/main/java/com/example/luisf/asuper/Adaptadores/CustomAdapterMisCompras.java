package com.example.luisf.asuper.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisf.asuper.Datos.Item.Item;
import com.example.luisf.asuper.Datos.Order.Order;
import com.example.luisf.asuper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by luisf on 16/10/2017.
 */

public class CustomAdapterMisCompras extends ArrayAdapter {
    private TextView tvFechaMisCompras;
    private TextView tvCantidadMisCompras;
    private TextView tvPrecioMisCompras;

    public CustomAdapterMisCompras(@NonNull Context context, ArrayList<Order> orders){
        super(context, R.layout.file_mis_compras, orders);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View vistaCustom = layoutInflater.inflate(R.layout.file_mis_compras, parent, false);
        Order order = (Order) getItem(position);

        tvFechaMisCompras = (TextView) vistaCustom.findViewById(R.id.tvFechaMisCompras);
        tvFechaMisCompras.setText(order.getFecha());

        tvCantidadMisCompras = (TextView) vistaCustom.findViewById(R.id.tvCantidadMisCompras);
        tvCantidadMisCompras.setText(order.getTotal());

        tvPrecioMisCompras = (TextView) vistaCustom.findViewById(R.id.tvPrecioMisCompras);
        tvPrecioMisCompras.setText(order.getCantidad());
        return vistaCustom;
    }
}
