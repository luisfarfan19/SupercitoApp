package com.example.luisf.asuper.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisf.asuper.Datos.Item;
import com.example.luisf.asuper.Datos.ItemCRUD;
import com.example.luisf.asuper.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddItem extends AppCompatActivity {

    private EditText etNombre, etPrecio, etId;
    private Button bTomar, bSeleccionar, bSave;
    private ImageView ivFoto;
    private TextView tvUrl;
    private ItemCRUD crud;

    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_CAMERA = 200;
    private static final String FILE_PROVIDER = "com.example.luisf.asuper.fileprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etPrecio = (EditText) findViewById(R.id.etPrecio);
        etId = (EditText) findViewById(R.id.etId);
        bTomar = (Button) findViewById(R.id.bTomar);
        bSeleccionar = (Button) findViewById(R.id.bSeleccionar);
        bSave = (Button) findViewById(R.id.bGuardar);
        ivFoto = (ImageView) findViewById(R.id.ivNewFoto);
        tvUrl = (TextView) findViewById(R.id.tvUrl);

        crud = new ItemCRUD(this);

        /*bSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarPermisosStorage();
            }
        });

        bTomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarPermisosCamara();
            }
        });*/

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se dio click en guardar
                String id = etId.getText().toString();
                String nombre = etNombre.getText().toString();
                String precio = etNombre.getText().toString();
                //String urlFoto = tvUrl.getText().toString();
                String urlFoto = "Foto";

                if (nombre.isEmpty()) {
                    // Favor de escribir algo en nombre
                } else if (precio.isEmpty()) {
                    // Favor de escribir algo en precio
                } else if (urlFoto.isEmpty()) {
                    // Favor de elegir una imagen
                } else {
                    crud.newItem(new Item(id, nombre, precio, urlFoto));
                    Intent intent = new Intent(AddItem.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    /*public void validarPermisosStorage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            iniciarIntentSeleccionarFotos();
        }
    }

    private void iniciarIntentSeleccionarFotos() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    iniciarIntentSeleccionarFotos();
                } else {
                    // Permiso negado
                }
                return;
            case REQUEST_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    iniciarIntentTomarFoto();
                } else {
                    // permiso negado
                }
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri imagenSeleccionada = imageReturnedIntent.getData();
                    try {
                        InputStream imagenStream = getContentResolver().openInputStream(imagenSeleccionada);
                        Bitmap imagen = BitmapFactory.decodeStream(imagenStream);
                        ivFoto.setImageBitmap(imagen);
                        tvUrl.setText(imagenSeleccionada.toString());

                    } catch (FileNotFoundException fnte) {
                        Toast.makeText(this, fnte.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    Picasso.with(this).load(tvUrl.getText().toString()).into(ivFoto);
                }
                return;
        }
    }

    public void validarPermisosCamara() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
        } else {
            iniciarIntentTomarFoto();
        }
    }

    private void iniciarIntentTomarFoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error creando archivo
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, FILE_PROVIDER, photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreImagen = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                nombreImagen,
                ".jpg",
                storageDir
        );

        String urlName = "file://" + image.getAbsolutePath();
        tvUrl.setText(urlName);
        return image;
    }*/
}
