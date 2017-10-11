package com.example.luisf.app4_foursquare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        final TextView tvNameDes = (TextView) findViewById(R.id.tvNameDescripton);
        final TextView tvDirDes = (TextView) findViewById(R.id.tvDirDesc);
        final TextView tvVisit = (TextView) findViewById(R.id.tvVisit);

        Bundle bundle = getIntent().getExtras();
        String placeId = bundle.getString("id");
        String accessToken = bundle.getString("accessToken");
        String placeUrl = "https://api.foursquare.com/v2/venues/" + placeId +
                "?oauth_token=" + accessToken + "&v=20170919";

        String photoUrl = "https://api.foursquare.com/v2/venues/" + placeId +
                "/photos?oauth_token=" + accessToken + "&v=20170919";

        requesPhotoJson(photoUrl);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, placeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            JSONObject responseObject = mainObject.getJSONObject("response");
                            JSONObject venueObject = responseObject.getJSONObject("venue");
                            JSONObject locationObject = venueObject.getJSONObject("location");
                            JSONObject statsObject = venueObject.getJSONObject("stats");
                            String name = venueObject.getString("name");
                            String address = locationObject.getString("address");
                            String visitCount = statsObject.getString("visitsCount");

                            tvNameDes.setText(name);
                            tvDirDes.setText(address);
                            tvVisit.setText("Total de visitas: " + visitCount);

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

    private void requesPhotoJson(String photoJSONurl) {
        final ImageView ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        Log.d("PhotoJSONurl", photoJSONurl);
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, photoJSONurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            JSONObject responseObject = mainObject.getJSONObject("response");
                            JSONObject venueObject = responseObject.getJSONObject("photos");
                            JSONArray itemsArray = venueObject.getJSONArray("items");

                            JSONObject photoObject = itemsArray.getJSONObject(0);
                            String prefix = photoObject.getString("prefix");
                            String width = "300";
                            String height = "300";
                            String suffix = photoObject.getString("suffix");

                            String urlPhoto = prefix + width + "x" + height + suffix;
                            Log.d("urlPhotooo", urlPhoto);
                            Picasso.with(getApplicationContext()).load(urlPhoto).into(ivPhoto);

                        } catch (JSONException e) {
                            String errorPhotoUrl = "https://lh3.googleusercontent.com/81tvpT59weJbOGWT9jQ8_9RtcGXKCcVv59BU7Wl6PnS7okIgrS4iTCgwWpPQY2FRKw=w300";
                            e.printStackTrace();
                            Picasso.with(getApplicationContext()).load(errorPhotoUrl).into(ivPhoto);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("log2=", error.toString());
                Log.e("log3=", error.toString());

            }
        });
        requestQueue1.add(stringRequest);
    }
}
