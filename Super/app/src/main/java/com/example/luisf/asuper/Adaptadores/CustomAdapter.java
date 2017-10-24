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
 * Created by luisf on 14/10/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    private TextView tvNombre;
    private TextView tvPrecio;
    private TextView tvFoto;
    private ImageView ivFoto;

    public CustomAdapter(@NonNull Context context, ArrayList<Item> items){
        super(context, R.layout.file, items);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View vistaCustom = layoutInflater.inflate(R.layout.file, parent, false);
        Item item = (Item) getItem(position);

        tvNombre = (TextView) vistaCustom.findViewById(R.id.tvNombre);
        tvNombre.setText(item.getNombre());

        tvPrecio = (TextView) vistaCustom.findViewById(R.id.tvPrecio);
        tvPrecio.setText(item.getPrecio());

        /*tvFoto = (TextView) vistaCustom.findViewById(R.id.tvFoto);
        tvFoto.setText(item.getFoto());*/

        ivFoto = (ImageView) vistaCustom.findViewById(R.id.ivFoto);
        Picasso.with(getContext()).load(item.getFoto()).into(ivFoto);
        return vistaCustom;
    }
}

