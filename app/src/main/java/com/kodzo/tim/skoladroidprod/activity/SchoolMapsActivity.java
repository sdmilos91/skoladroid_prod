package com.kodzo.tim.skoladroidprod.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.kodzo.tim.skoladroidprod.R;
import com.kodzo.tim.skoladroidprod.model.School;
import com.kodzo.tim.skoladroidprod.util.Mokap;

public class SchoolMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ClusterManager<MyItem> mClusterManager;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_maps);
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
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); Bilo

        LatLng ns = new LatLng(45.255, 19.845);



        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ns, 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        addItems();


    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.

        for (School s: Mokap.getSchools()) {
            try{
                Double lat = Double.parseDouble(s.getGps().split(",")[0]);
                Double lng = Double.parseDouble(s.getGps().split(",")[1]);

                //mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(s.getName()));

                MyItem offsetItem = new MyItem(lat, lng);
                mClusterManager.addItem(offsetItem);

            }catch (Exception e){
                //
            }

        }

        // Add ten cluster items in close proximity, for purposes of this example.

    }

    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(double lat, double lng) {
            mPosition = new LatLng(lat, lng);
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

    }
}