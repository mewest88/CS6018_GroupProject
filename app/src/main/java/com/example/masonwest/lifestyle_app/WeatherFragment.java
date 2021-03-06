package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;
import java.net.URL;

public class WeatherFragment extends Fragment {

    private TextView mTvLocation, mTvTemp, mTvPress, mTvHum;
    private String mCity, mCountry, mLocation;
//    private WeatherData mWeatherData;
    private UserViewModel mUserViewModel;
//    private Button mBtSubmit;

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
        mTvLocation = (TextView) fragmentView.findViewById(R.id.tv_location);
        mTvTemp = (TextView) fragmentView.findViewById(R.id.tv_temp);
        mTvPress = (TextView) fragmentView.findViewById(R.id.tv_pressure);
        mTvHum = (TextView) fragmentView.findViewById(R.id.tv_humidity);

        //Create the view model
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        //Set the observer
        mUserViewModel.getData().observe(this,nameObserver);

//        mCity = mUserViewModel.getCity();
        mCity = mUserViewModel.getUser().getValue().getCity();
//        mCountry = mUserViewModel.getCountry();
        mCountry = mUserViewModel.getUser().getValue().getCountry();
        mLocation = mCity + "," + mCountry;

        mTvLocation.setText("" + mCity + ", " + mCountry);
//        String inputFromEt = mTvLocation.getText().toString().replace(' ','&');
        String searchLocation = mLocation.replace(' ','&');

        loadWeatherData(searchLocation);

//        getActivity().getSupportLoaderManager().initLoader(SEARCH_LOADER, null, this);

        return fragmentView;
    }

    void loadWeatherData(String location){
        //pass the location in to the view model
        mUserViewModel.setLocation(location);
    }

    //create an observer that watches the MutableLiveData<User> object
    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
                mCity = mUserViewModel.getUser().getValue().getCity();
                mCountry = mUserViewModel.getUser().getValue().getCountry();
                mLocation = mCity + "," + mCountry;
                mTvLocation.setText("" + mLocation);
                String searchLocation = mLocation.replace(' ','&');
                loadWeatherData(searchLocation);
            }
        }
    };

    //create an observer that watches the LiveData<WeatherData> object
    final Observer<WeatherData> nameObserver  = new Observer<WeatherData>() {
        @Override
        public void onChanged(@Nullable final WeatherData weatherData) {
            // Update the UI if this data variable changes
            if(weatherData!=null) {
//                mTvTemp.setText("" + Math.round(weatherData.getTemperature().getTemp() - 273.15) + " C");
                mTvTemp.setText("" + Math.round(weatherData.getTemperature().getTemp() * 9/5 - 459.67) + " F"); //Farenheit conversion * (9/5) - 459.67
                mTvHum.setText("" + weatherData.getCurrentCondition().getHumidity() + "%");
                mTvPress.setText("" + weatherData.getCurrentCondition().getPressure() + " hPa");
            }
        }
    };
}
