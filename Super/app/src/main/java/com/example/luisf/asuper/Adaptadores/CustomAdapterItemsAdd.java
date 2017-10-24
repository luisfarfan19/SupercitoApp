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
import com.example.luisf.asuper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by luisf on 16/10/2017.
 */

public class CustomAdapterItemsAdd extends ArrayAdapter {
    private TextView tvNombre;

    public CustomAdapterItemsAdd(@NonNull Context context, ArrayList<Item> items) {
        super(context, R.layout.file_item_add, items);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View vistaCustom = layoutInflater.inflate(R.layout.file_item_add, parent, false);
        Item item = (Item) getItem(position);

        tvNombre = (TextView) vistaCustom.findViewById(R.id.tvItemAdd);
        tvNombre.setText(item.getNombre());

        return vistaCustom;
    }
}
