//package com.example.masonwest.lifestyle_app;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class CustomViewsList implements Parcelable {
//
//    //Implement the creator method
//    public static final Creator<CustomViewsList> CREATOR = new Creator<CustomViewsList>() {
//
//        //Call the private constructor
//        @Override
//        public CustomViewsList createFromParcel(Parcel in) {
//            return new CustomViewsList(in);
//        }
//
//        @Override
//        public CustomViewsList[] newArray(int size) {
//            return new CustomViewsList[size];
//        }
//    };
//    private List<String> mItemList;
//    private List<String> mItemDetails;
//
//    //Say how to read in from parcel
//    private CustomViewsList(Parcel in) {
//        in.readStringList(mItemList);
//        in.readStringList(mItemDetails);
//    }
//
//    public CustomViewsList() {
//        //Populate the item list with data
//        //and populate the details list with details at the same time
//        mItemList = new ArrayList<>();
//        mItemList.add("Weight Tracker");
//        mItemList.add("BMI");
//        mItemList.add("Weather");
//        mItemList.add("Hikes");
//        mItemList.add("User Profile");
//        mItemDetails = new ArrayList<>();
//        mItemDetails.add("Weight Tracker Details");
//        mItemDetails.add("BMI Details");
//        mItemDetails.add("Weather Details");
//        mItemDetails.add("Hikes Details");
//        mItemDetails.add("User Profile Details");
//    }
//
//    //Say how and what to write to parcel
//    @Override
//    public void writeToParcel(Parcel out, int flags) {
//        out.writeStringList(mItemList);
//        out.writeStringList(mItemDetails);
//    }
//
//    //Don't worry about this for now.
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    //Implement a getter and setter for getting whole list
//    public List<String> getItemList() {
//        return mItemList;
//    }
//
//    public void setItemList(List<String> itemList) {
//        mItemList = itemList;
//    }
//
//    //Implement getter for item details at a position
//    public String getItemDetail(int position) {
//        return mItemDetails.get(position);
//    }
//}
