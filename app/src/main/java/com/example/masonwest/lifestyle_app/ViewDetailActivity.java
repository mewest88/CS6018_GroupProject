package com.example.masonwest.lifestyle_app;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewDetailActivity extends AppCompatActivity {

    private ItemViewDetailFragment mItemDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        //Create the fragment
        mItemDetailFragment = new ItemViewDetailFragment();

        //Pass data to the fragment
        mItemDetailFragment.setArguments(getIntent().getExtras());

        //No need to check if we're on a tablet. This activity only gets created on phones.
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mItemDetailFragment, "frag_itemdetail");
        fTrans.commit();
    }
}
