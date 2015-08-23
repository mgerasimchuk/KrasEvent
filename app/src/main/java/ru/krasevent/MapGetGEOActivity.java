package ru.krasevent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MapGetGEOActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public String geo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_get_geo);


        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            Fragment asd;
            //asd.getFragmentManager().findFragmentById(R.id.map)
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.getUiSettings().setZoomControlsEnabled(true);

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        geo = String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude);
                        Intent intent = new Intent();
                        intent.putExtra("geo", geo);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

            }
        }
    }


}