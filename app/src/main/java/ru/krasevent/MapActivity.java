package ru.krasevent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.materialdrawer.Drawer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.StringTokenizer;


public class MapActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Fragment mapFragment;
    private Drawer.Result drawerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        drawerResult = Global.setUpNavigationDrawer(this, drawerResult);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.action_login:

        }
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
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
            Fragment asd;
            //asd.getFragmentManager().findFragmentById(R.id.map)
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.getUiSettings().setZoomControlsEnabled(true);

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


        RequestQueue queue = Volley.newRequestQueue(this);
        String getEvent = Global.host + "EventAPI.php?act=GetEventsByFilter&public_type=all&user_api_key=" + Global.APIkey;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, getEvent, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("MyLogs", response.toString());
                Log.d("MyLogs", Global.isLogin.toString());
                Global.isLogin = true;
                Log.d("MyLogs", Global.isLogin.toString());

                try
                {
                    JSONArray arr = response.getJSONArray("event");
                    StringTokenizer st;

                    for(int i=0; i < arr.length(); i++)
                    {
                        st = new StringTokenizer(arr.getJSONObject(i).get("event_geo").toString(), ",", true);

                        Double first = Double.valueOf(st.nextToken());
                        st.nextToken();
                        Double second = Double.valueOf(st.nextToken());

                        mMap.addMarker(new MarkerOptions().position(new LatLng(first, second)).title(arr.getJSONObject(i).get("event_name").toString()));
                    }
                }
                catch(org.json.JSONException e)
                {
                    Log.d("MyLogs","FAIL");
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyLogs", "Bad!");
            }
        });
        queue.add(jsObjRequest);
    }
}
