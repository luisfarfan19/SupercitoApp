package com.example.luisf.asuper.Views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.luisf.asuper.Adaptadores.CustomAdapter;
import com.example.luisf.asuper.Datos.Item.Item;
import com.example.luisf.asuper.Datos.Item.ItemCRUD;
import com.example.luisf.asuper.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ItemCRUD crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button bNuevo = (Button) findViewById(R.id.bNuevo);
        Button bCarrito = (Button) findViewById(R.id.bCarrito);
        ListView lvMyItems = (ListView) findViewById(R.id.lvMyItems);
        ArrayList<Item> itemsList = new ArrayList<Item>();

        crud = new ItemCRUD(this);
        itemsList = crud.getItems();

        ArrayAdapter<Item> adapter1 = new CustomAdapter(MainActivity.this, itemsList);
        lvMyItems.setAdapter(adapter1);
        final ArrayList<Item> finalItemsList = itemsList;
        lvMyItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItem.class);
                intent.putExtra("ItemId", finalItemsList.get(position).getId());
                intent.putExtra("ItemNombre", finalItemsList.get(position).getNombre());
                intent.putExtra("ItemPrecio", finalItemsList.get(position).getPrecio());
                intent.putExtra("ItemFoto", finalItemsList.get(position).getFoto());
                startActivity(intent);

            }
        });

        bCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MisCompras.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fabNuevoItem = (FloatingActionButton) findViewById(R.id.fabNuevoItem);
        fabNuevoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItem.class);
                startActivity(intent);
            }
        });
    }
}