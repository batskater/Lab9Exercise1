package com.example.kiiroz.myapplication;

import android.content.Context;
//import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity
        implements GoogleMap.OnMapLongClickListener,LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationManager locationManager;
    LatLng oldpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mMap.setOnMapLongClickListener(this);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    int m=0;
    public void onMapLongClick(LatLng latLng){
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker" + ++m));

    }

    int asdf;

    public void onLocationChanged(Location location) {
        LatLng LL = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(LL).title("Location Change" + ++asdf));

        CameraUpdate center_point = CameraUpdateFactory.newLatLng(LL);
        CameraUpdate zoom_level = CameraUpdateFactory.zoomTo(7);
        mMap.animateCamera(zoom_level);
        mMap.moveCamera(center_point);

        if(asdf != 1)
            mMap.addPolyline(new PolylineOptions().add(LL).add(oldpoint));
        oldpoint = LL;

    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LatLng BKK = new LatLng(13.7563,100.5018);
        LatLng HK = new LatLng(22.2783,114.1747);
        //LatLng VT = new LatLng(20.993776,105.811417);

        mMap.addMarker(new MarkerOptions().position(BKK).title("Bangkok"));
        mMap.addMarker(new MarkerOptions().position(HK).title("Hong Kong"));
        //mMap.addMarker(new MarkerOptions().position(VT).title("Hanoi"));

        //move map center point
        CameraUpdate center_point = CameraUpdateFactory.newLatLng(BKK);
        mMap.moveCamera(center_point);

        //change map zoom level
        CameraUpdate zoom_level = CameraUpdateFactory.zoomTo(7);
        mMap.animateCamera(zoom_level);

        //draw line
        //first way
        //mMap.addPolyline(new PolylineOptions().add(BKK).add(VT).add(HK));

        //second way
        mMap.addPolyline(new PolylineOptions().add(BKK).add(HK));
        //mMap.addPolyline(new PolylineOptions().add(HK).add(VT));
    }
}
