package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.nio.DoubleBuffer;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

// this video helped me a lot -> https://www.youtube.com/watch?v=XQJiiuk8Feo
//https://guides.codepath.com/android/Retrieving-Location-with-LocationServices-API

public class HikesFragment extends Fragment implements View.OnClickListener {

    private Button mSearchButton;
    private FusedLocationProviderClient locationClient;
    private String mLocation, mLatLongString;
    private String mSearchTerm = "Hikes";

    public HikesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_hikes, container, false);

        // Get current location
        requestPermission();
        locationClient = LocationServices.getFusedLocationProviderClient(getActivity());

//        mSearchButton = (Button) fragmentView.findViewById(R.id.b_SearchButton);
        mSearchButton = (Button) fragmentView.findViewById(R.id.button_submit);
        mSearchButton.setOnClickListener(this);

        return fragmentView;
    }

    /**
     * When clicked, gets the current location from the phone and searches Google maps for mSearchTerm
     * @param view
     */
    @Override
    public void onClick(final View view) {
        if (ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // Get the current location from the phone
        locationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) { //this returns [fused long,lat acc=x and some other stuff

                if (location!=null) {
                    TextView textView = view.findViewById(R.id.tv_Hikes_Information);
//                    textView.setText("" + location.toString());
                    //will comment out above once working

                    // Convert the Fused location to just latitude and longitude
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    String latString = Double.toString(lat);
                    String lonString = Double.toString(lon);
//                    mLocation = location.toString(); //needs parsing and splitting

                    mLatLongString = latString + "," + lonString;
                    // example lat and long = 40.763056, -111.858674

                    Toast.makeText(getActivity(), mLatLongString, Toast.LENGTH_LONG).show();

                    //We have to grab the search term and construct a URI object from it to search on maps
                    //We'll hardcode WEB's location here
                    Uri searchUri = Uri.parse("geo:" + mLatLongString + "?q=" + mSearchTerm);

                    //Create the implicit intent
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);

                    //If there's an activity associated with this intent, launch it
                    if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }

//                    Toast.makeText(getActivity(), "Searching Google Maps", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}
