package com.example.luisf.app4_foursquare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Location extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private TextView tvLat, tvLon;
    private String latitud, longitud;
    private String accessToken;
    private GoogleApiClient googleApiClient; // Cliente de Google API
    private android.location.Location location;           // Objeto para obtener ubicación
    private final int REQUEST_LOCATION = 1;   // Código de petición para ubicación

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Bundle bundle = getIntent().getExtras();
        accessToken = bundle.getString("accessToken");

        //tvLat = (TextView) findViewById(R.id.tvLat);
        //tvLon = (TextView) findViewById(R.id.tvLon);

        // TODO: 3.- Inicializar cliente de Google API
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //Mandamos a llamar el método processLocation donde vamos a validar
    //permisos, ubicación y errores
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        processLocation();
    }

    //Obtiene la ubicación y el permiso, y adicional valida si el objeto Location es vacío o no
    //para poder actualizar la interfaz de usuario
    private void processLocation() {
        // Se trata de obtener la última ubicación registrada
        getLocation();

        // Si ubicación es diferente de nulo se actualizan los campos para escribir la ubicación
        if (location != null) {
            updateLocationUI();
        }
    }

    private void getLocation() {
        // Se valida que haya permisos garantizados
        if (isLocationPermissionGranted()) {
            // Si los hay se regresa un objeto con información de ubicacion
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        } else {
            // Sino se administra la petición de permisos
            requestPermission();
        }
    }

    private boolean isLocationPermissionGranted() {
        /* Valida si ya se dio permiso */
        int permission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        /* Se regresa un valor booleano para saber si la app tiene permisos o no */
        return permission == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            /*
                Aquí muestras confirmación explicativa al usuario
                por si rechazó los permisos
              */
            Toast.makeText(this, "No quisiste dar acceso a tu ubicación", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    // TODO: 9.- Se obtienen los valores de la ubicación
    private void updateLocationUI() {
        //tvLat.setText(String.valueOf(location.getLatitude()));
        //tvLon.setText(String.valueOf(location.getLongitude()));

        latitud = String.valueOf(location.getLatitude());
        longitud = String.valueOf(location.getLongitude());

        url = "https://api.foursquare.com/v2/venues/search?ll=" + latitud + "," + longitud +
                "&oauth_token=" + accessToken + "&v=20170918";
        //Log.d("The Url is: ",url);
        requestJSON(url);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                /* se pide la última ubicación */
                location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                /* Si la ubicación es diferente de nulo, es decir, se regresó la ubicación
                *   Entonces se actualiza la interfaz con los valores
                *   */
                if (location != null)
                    updateLocationUI();
                else
                    Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permisos no otorgados", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        this.location = location;
        Log.d("onLocationChanged", "cambió ubicación");
        updateLocationUI();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    //Funciones para la parte de JSON

    private void requestJSON(String jsonUrl) {
        final ArrayList<Place> placesList = new ArrayList<Place>();
        final ListView lvListView = (ListView) findViewById(R.id.lvListView);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            JSONObject responseObject = mainObject.getJSONObject("response");
                            JSONArray placesArray = responseObject.getJSONArray("venues");
                            for (int i = 0; i < 20; i++) {
                                JSONObject placeObject = placesArray.getJSONObject(i);
                                String id = placeObject.getString("id");
                                String name = placeObject.getString("name");
                                Log.d("NAAME", name);
                                JSONArray categoriesArray = placeObject.getJSONArray("categories");
                                JSONObject category1Object = categoriesArray.getJSONObject(0);
                                JSONObject iconObject = category1Object.getJSONObject("icon");
                                //Log.d("Nalgas","Error");
                                String prefix = iconObject.getString("prefix");
                                String suffix = iconObject.getString("suffix");
                                String iconUrl = prefix + "bg_64" + suffix;
                                Log.d("Imagee",iconUrl);

                                placesList.add(new Place(name, id, iconUrl));
                            }
                            ArrayAdapter<Place> adapter1 = new CustomAdapter(Location.this, placesList);
                            lvListView.setAdapter(adapter1);
                            lvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //String city = (String) parent.getItemAtPosition(position);
                                    Intent intent = new Intent(Location.this, PlaceInfo.class);
                                    intent.putExtra("id", placesList.get(position).getId());
                                    intent.putExtra("accessToken", accessToken);
                                    startActivity(intent);

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("log2=", error.toString());
                Log.e("log3=", error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
}
