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
import com.example.luisf.asuper.Datos.OrderDetail.OrderDetail;
import com.example.luisf.asuper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by luisf on 19/10/2017.
 */

public class CustomAdapterHistoria extends ArrayAdapter {
    private TextView tvCostoTotalHistoria;
    private TextView tvDescripcionItemHistoria;

    public CustomAdapterHistoria(@NonNull Context context, ArrayList<Item> items){
        super(context, R.layout.file_historial, items);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View vistaCustom = layoutInflater.inflate(R.layout.file_historial, parent, false);
        Item item = (Item) getItem(position);
        Order order = (Order) getItem(position);
        OrderDetail orderDetail = (OrderDetail) getItem(position);

        tvDescripcionItemHistoria = (TextView) vistaCustom.findViewById(R.id.tvDescripcionItemHistoria);
        tvDescripcionItemHistoria.setText(item.getNombre() + "/n"
                + orderDetail.getCantidadSameItem() + " de $" + item.getPrecio());

        tvCostoTotalHistoria = (TextView) vistaCustom.findViewById(R.id.tvCostoTotalHistoria);
        int cantidadSameItem = Integer.parseInt(orderDetail.getCantidadSameItem().toString());
        int precioItem = Integer.parseInt(item.getPrecio().toString());
        int costoTotal = cantidadSameItem * precioItem;
        tvCostoTotalHistoria.setText(costoTotal);

        return vistaCustom;
    }
}
