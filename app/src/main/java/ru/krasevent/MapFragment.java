package ru.krasevent;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends android.support.v4.app.Fragment {

    private GoogleMap mMap = null; // Might be null if Google Play services APK is not available.
    private Fragment mapFragment;
    private FragmentManager SFmanager = null;
    View view;
    public MapFragment() {
        // Required empty public constructor
    }

    public void setUpFragmentManager(FragmentManager manager) {
        SFmanager = manager;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        return view;
    }

    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            Fragment asd;
            //asd.getFragmentManager().findFragmentById(R.id.map)
            mMap = ((SupportMapFragment) SFmanager.findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.getUiSettings().setZoomControlsEnabled(true);

                setUpMap();
            }
        }
    }

    private void setUpMap() {


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://10.0.2.2/KrasEvent/api/UserAPI.php?act=LoginUser&login=test&pass=test";
        String url2 = "http://www.googleapis.com/customsearch/v1?key=AIzaSyBmSXUzVZBKQv9FJkTpZXn0dObKgEQOIFU&cx=014099860786446192319:t5mr0xnusiy&q=AndroidDev&alt=json&searchType=image";
        String url3 = "http://10.0.2.2/KrasEvent/api/UserAPI.php?act=CreateUser&login=test&name=test&pass=test";
        String getEvent = "http://10.0.2.2/KrasEvent/api/EventAPI.php?act=GetEventsByFilter&public_type=all&user_api_key=29c3d379de43e28c5f2657db3b96eaf11f5d308a0ae3a2083913313ecaf630e8";

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
