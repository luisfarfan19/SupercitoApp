package com.example.luisf.asuper.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.luisf.asuper.Datos.Item.Item;
import com.example.luisf.asuper.Datos.Item.ItemCRUD;
import com.example.luisf.asuper.R;
import com.squareup.picasso.Picasso;

public class EditItem extends AppCompatActivity {
    private Button bGuardarCambios, bEliminar;
    private ImageView ivEditFoto;
    private EditText etEditNombre, etEditPrecio;
    private ItemCRUD crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Bundle bundle = getIntent().getExtras();
        final String itemId = bundle.getString("ItemId");
        final String itemNombre = bundle.getString("ItemNombre");
        final String itemPrecio = bundle.getString("ItemPrecio");
        final String itemFoto = bundle.getString("ItemFoto");

        crud = new ItemCRUD(this);

        etEditNombre = (EditText) findViewById(R.id.etEditNombre);
        etEditPrecio = (EditText) findViewById(R.id.etEditPrecio);
        bGuardarCambios = (Button) findViewById(R.id.bGuardarCambios);
        bEliminar = (Button) findViewById(R.id.bEliminar);
        ivEditFoto = (ImageView) findViewById(R.id.ivEditFoto);

        etEditNombre.setText(itemNombre);
        etEditPrecio.setText(itemPrecio);

        Picasso.with(this).load(itemFoto).into(ivEditFoto);

        bGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = etEditNombre.getText().toString();
                String precio = etEditPrecio.getText().toString();

                if (nombre.isEmpty()) {
                    Toast.makeText(EditItem.this, "Ingresar Nombre",
                            Toast.LENGTH_LONG).show();
                } else if (precio.isEmpty()) {
                    Toast.makeText(EditItem.this, "Ingresar Precio",
                            Toast.LENGTH_LONG).show();
                } else {
                    crud.updateItem(new Item(itemId, nombre, precio, itemFoto));
                    Intent intent = new Intent(EditItem.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        bEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crud.deleteItem(new Item(itemId, itemNombre, itemPrecio, itemFoto));
                Intent intent = new Intent(EditItem.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
