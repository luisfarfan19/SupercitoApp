package com.example.luisf.app4_foursquare;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by luisf on 18/09/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    private TextView tvName;
    private ImageView ivIcon;

    public CustomAdapter(@NonNull Context context, ArrayList<Place> places){
        super(context, R.layout.file, places);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View vistaCustom = layoutInflater.inflate(R.layout.file, parent, false);
        Place item = (Place) getItem(position);

        tvName = (TextView) vistaCustom.findViewById(R.id.tvName);
        tvName.setText(item.getName());

        ivIcon = (ImageView) vistaCustom.findViewById(R.id.ivIcon);
        Picasso.with(getContext()).load(item.getIconUrl()).into(ivIcon);
        return vistaCustom;
    }
}
