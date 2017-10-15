package com.example.luisf.asuper.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.luisf.asuper.Adaptadores.CustomAdapter;
import com.example.luisf.asuper.Datos.Item;
import com.example.luisf.asuper.Datos.ItemCRUD;
import com.example.luisf.asuper.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ItemCRUD crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bNuevo = (Button) findViewById(R.id.bNuevo);
        ListView lvMyItems = (ListView) findViewById(R.id.lvMyItems);
        ArrayList<Item> itemsList = new ArrayList<Item>();

        crud = new ItemCRUD(this);
        //Al parecer no se encuentra la base de datos.
        crud.newItem(new Item("0", "Mouse", "300", "Foto"));
        //itemsList = crud.getItems();

        Log.d("ItemsList: ", itemsList.toString());

        ArrayAdapter<Item> adapter1 = new CustomAdapter(MainActivity.this, itemsList);
        lvMyItems.setAdapter(adapter1);
        lvMyItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                /*Intent intent = new Intent(Location.this, PlaceInfo.class);
                intent.putExtra("id", placesList.get(position).getId());
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);*/

            }
        });

        bNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItem.class);
                startActivity(intent);
            }
        });
    }
}