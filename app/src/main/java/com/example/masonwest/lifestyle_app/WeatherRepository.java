//package com.example.masonwest.lifestyle_app;
//
//import android.app.Application;
//import android.arch.lifecycle.MutableLiveData;
//import android.os.AsyncTask;
//
//import org.json.JSONException;
//
//import java.io.IOException;
//import java.net.URL;
//
//public class WeatherRepository {
//    private final MutableLiveData<WeatherData> jsonData = new MutableLiveData<WeatherData>();
//    private String mLocation;
//
//    WeatherRepository(Application application){
//        loadData();
//    }
//
//    public void setLocation(String location){
//        mLocation = location;
//        loadData();
//    }
//
//    public MutableLiveData<WeatherData> getData() {
//        return jsonData;
//    }
//
//    private void loadData(){
//        new AsyncTask<String,Void,String>(){
//            @Override
//            protected String doInBackground(String... strings) {
//                String location = strings[0];
//                URL weatherDataURL = null;
//                String retrievedJsonData = null;
//                if(location!=null) {
//                    weatherDataURL = NetworkUtils.buildURLFromString(location);
//                    try {
//                        retrievedJsonData = NetworkUtils.getDataFromURL(weatherDataURL);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return retrievedJsonData;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                if(s != null) {
//                    try {
//                        jsonData.setValue(JSONWeatherUtils.getWeatherData(s));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.execute(mLocation);
//    }
//}
