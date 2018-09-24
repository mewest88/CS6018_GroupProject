package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.net.URL;

public class WeatherFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener { //extend AppCompatActivity???

//    private EditText mEtLocation; //TODO: change variable names appropriatley if this works
    private TextView mEtLocation;
    private TextView mTvTemp;
    private TextView mTvPress;
    private TextView mTvHum;
    private String mLocation;
    private WeatherData mWeatherData;
    private Button mBtSubmit;

    //Uniquely identify loader
    private static final int SEARCH_LOADER = 11;

    //Uniquely identify string you passed in
    public static final String URL_STRING = "query";

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_weather, container, false);

        //Get the edit text and all the text views
//        mEtLocation = (EditText) fragmentView.findViewById(R.id.et_location);
        mEtLocation = (TextView) fragmentView.findViewById(R.id.et_location);
        mTvTemp = (TextView) fragmentView.findViewById(R.id.tv_temp);
        mTvPress = (TextView) fragmentView.findViewById(R.id.tv_pressure);
        mTvHum = (TextView) fragmentView.findViewById(R.id.tv_humidity);

        mBtSubmit = (Button) fragmentView.findViewById(R.id.button_submit);
        mBtSubmit.setOnClickListener(this);

//        if (savedInstanceState != null) {
//            mEtLocation = savedInstanceState.getString("BMI_TEXT");
//        }
//        else {
            //Get the bmi double to display
            mLocation = getArguments().getString("location_data");
//        }

        //Set the text in the fragment
        mEtLocation.setText("" + mLocation);

        getActivity().getSupportLoaderManager().initLoader(SEARCH_LOADER, null, this);

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_submit:{
                //Get the string from the edit text and sanitize the input
                String inputFromEt = mEtLocation.getText().toString().replace(' ','&');
                loadWeatherData(inputFromEt);
                Toast.makeText(getActivity(), "click worked", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private void loadWeatherData(String location){
        Bundle searchQueryBundle = new Bundle();
        searchQueryBundle.putString(URL_STRING,location);
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> searchLoader = loaderManager.getLoader(SEARCH_LOADER);
        if(searchLoader==null){
            loaderManager.initLoader(SEARCH_LOADER,searchQueryBundle,this);
        }
        else{
            loaderManager.restartLoader(SEARCH_LOADER,searchQueryBundle,this);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable final Bundle bundle) {
        return new AsyncTaskLoader<String>(getActivity()) {
            private String mLoaderData;

            @Override
            protected void onStartLoading() {
                if(bundle==null){
                    return;
                }
                if(mLoaderData!=null){
                    //Cache data for onPause instead of loading all over again
                    //Other config changes are handled automatically
                    deliverResult(mLoaderData);
                }
                else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                String location = bundle.getString(URL_STRING);
                URL weatherDataURL = NetworkUtils.buildURLFromString(location);
                String jsonWeatherData = null;
                try{
                    jsonWeatherData = NetworkUtils.getDataFromURL(weatherDataURL);
                    return jsonWeatherData;
                }catch(Exception e){
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(String data) {
                mLoaderData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String jsonWeatherData) {
        if (jsonWeatherData!=null){
            try {
                mWeatherData = JSONWeatherUtils.getWeatherData(jsonWeatherData);
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            if(mWeatherData!=null) {
                mTvTemp.setText("" + Math.round(mWeatherData.getTemperature().getTemp() - 273.15) + " C");
                mTvHum.setText("" + mWeatherData.getCurrentCondition().getHumidity() + "%");
                mTvPress.setText("" + mWeatherData.getCurrentCondition().getPressure() + " hPa");
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
