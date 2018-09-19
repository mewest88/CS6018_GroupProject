package com.example.masonwest.lifestyle_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

//TODO: need to add a tag or id to all the fragments we create and there is a method, like findByID or something to find them.

public class ItemViewDetailFragment extends Fragment {

    private TextView mTvItemDetail;

    public ItemViewDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the detail view
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

        //Get the text view
        mTvItemDetail = (TextView) view.findViewById(R.id.tv_detail);

        //Get the incoming detail text
        String detailString = getArguments().getString("item_detail");

        if (detailString != null) {
            mTvItemDetail.setText(detailString);
        }

        return view;
    }
}