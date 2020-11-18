package com.faizurazadri.trackingfaizurazadri;

import androidx.fragment.app.FragmentActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName() ;
    private GoogleMap mMap;
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private final float INITIAL_ZOOM = 11f;

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
        LatLng pnp = new LatLng(-0.884810, 100.653482);

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