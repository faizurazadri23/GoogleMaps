package com.faizurazadri.trackingfaizurazadri;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName() ;
    private GoogleMap mMap;
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private final float INITIAL_ZOOM = 11f;

    public Context context;
    private JSONArray result;
    private static final String URL = "http://tukangkanaja.site/Serverfaizurazadri/addmarker.php";

    HashMap<String, HashMap> extraMarkerInfo = new HashMap<String, HashMap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pnp = new LatLng(-0.8851825931320403, 100.65336005211003);

        latlngs.add(new LatLng(-0.950061, 100.355620));
        latlngs.add(new LatLng(-0.901915, 100.351125));
        latlngs.add(new LatLng(-0.913525, 100.466162));
        latlngs.add(new LatLng(-0.945490, 100.351394));

        mMap.addMarker(new MarkerOptions().position(pnp).title("My Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pnp, INITIAL_ZOOM));
        mMap.addMarker(new MarkerOptions().position(latlngs.get(0)).title("Ramayana"));
        mMap.addMarker(new MarkerOptions().position(latlngs.get(1)).title("GrandMall Basko"));
        mMap.addMarker(new MarkerOptions().position(latlngs.get(2)).title("Politeknik Negeri Padang"));
        mMap.addMarker(new MarkerOptions().position(latlngs.get(3)).title("Pantai Padang"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pnp));

        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);


        enableMapStyles(mMap);
        enableLongClick(mMap);
        enableDynamicMarker();

    }

    private void enableDynamicMarker() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSONResult", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String lat_i = jsonObject1.getString("latitude");
                                String long_i = jsonObject1.getString("longitude");
                                String locationName = jsonObject1.getString("locationName");
                                mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(lat_i), Double.parseDouble(long_i)))
                                        .title(Double.valueOf(lat_i).toString() +"," + Double.valueOf(long_i).toString())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                        .snippet(locationName));

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-0.9021187, 100.3489041),11.0f));
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MapsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        int socketTimeout = 10000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void enableLongClick(final GoogleMap mMap) {

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String snipet = String.format(Locale.getDefault(),
                        getString(R.string.lat_long_snippet),
                        latLng.latitude,
                        latLng.longitude);

                mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.dropped_pin))
                .snippet(snipet)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        });
    }

    private void enableMapStyles(GoogleMap mMap){
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_styles));
            if (!success) {
                Log.e(TAG, "parsing style maps gagal.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Tidak dapat menemukan style. Error: ", e);
        }
    }
}