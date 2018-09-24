package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 */
public class EditUserDetailsFragment extends Fragment
                                    implements View.OnClickListener {

    // TODO: needs weight, height, sex, location added to everything!
    //Member variables
    private EditText mEtFirstName, mEtLastName ;
    private Spinner mEtAge;
    private ImageButton mBtSubmit ;
    private Button mBtPicture;
    private String mFirstName, mLastName, mAgeString;
    private ImageView mIvPic;
    int mAge;
    Bundle thumbnailImage;
    Bitmap mProfPic;

    OnDataPass mDataPasser;

    //Define a request code for the camera
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public EditUserDetailsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mDataPasser = (OnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    //Callback interface
    // TODO: needs weight, height, sex, location added
    public interface OnDataPass{
        public void onDataPass(String firstName, String lastName, int age, Bundle thumbnailImage);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_edit_user_details, container, false);

        //Get the views
        mEtFirstName = (EditText) fragmentView.findViewById(R.id.et_firstName);
        mEtLastName = (EditText) fragmentView.findViewById(R.id.et_lastName);
        mEtAge = (Spinner) fragmentView.findViewById(R.id.et_Age);
        mBtPicture = (Button) fragmentView.findViewById(R.id.button_takePicture);
        mBtSubmit = (ImageButton) fragmentView.findViewById(R.id.button_submit);
//        mIvPic = (ImageView) fragmentView.findViewById(R.id.iv_pic);
        mBtSubmit.setOnClickListener(this);
        mBtPicture.setOnClickListener(this);
        mEtAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                switch (position) {
                    case 0:
                        mAge = Integer.parseInt("");
                        break;
                    case 1:
                        mAge = Integer.parseInt("");
                        break;
                    case 2:
                        mAge = Integer.parseInt("");
                        break;
                    case 3:
                        mAge = Integer.parseInt("");
                        break;
                    case 4:
                        mAge = Integer.parseInt("");
                        break;
                    case 5:
                        mAge = Integer.parseInt("");
                        break;
                }
//                if(mAge > 2) {
////                    Toast.makeText(getBaseContext(), weightChange[position], Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mEtAge.setSelection(1);
            }
        });

        return fragmentView;
    }

    /**
     * Collect the image from the camera and save the thumbnail image to be access on the second page
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK){
            thumbnailImage = data.getExtras();
            mProfPic = (Bitmap) thumbnailImage.get("data");
//            mIvPic.setImageBitmap(mProfPic);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_submit: {
                //Collect the first name, last name
                mFirstName = mEtFirstName.getText().toString();
                mLastName = mEtLastName.getText().toString();

                //Remove any leading spaces or tabs on the first and last name
                mFirstName = mFirstName.replaceAll("^\\s+", "");
                mLastName = mLastName.replaceAll("^\\s+", "");

                //Collect the age
//                mAgeString = mEtAge.getText().toString();
//                mAge = Integer.parseInt(mAgeString);

                //Check if the EditText's for first and last name strings are empty
                if (mFirstName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a first name please!", Toast.LENGTH_SHORT).show();
                } else if (mLastName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a last name please!", Toast.LENGTH_SHORT).show();
//                } else if (mAgeString.matches("")) {
//                    Toast.makeText(getActivity(), "Please enter a valid birth date in the form mm/dd/yyyy.", Toast.LENGTH_SHORT).show();
                    // TODO: Add a line to convert the entered age into an int, than I can do this check easily.
//                } else if (mAge < 0 || mAge > 150) {
//                    Toast.makeText(getActivity(), "Please enter a valid birth date. 0 > Age < 150."
//                            , Toast.LENGTH_SHORT).show();
                } else if (thumbnailImage == null){
                    Toast.makeText(getActivity(), "Please use the button to take a picture!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Good job!", Toast.LENGTH_SHORT).show();
                    //Start an activity and pass the EditText string to it.
                    mDataPasser.onDataPass(mFirstName, mLastName, mAge, thumbnailImage);
                }
                break;
            }
            case R.id.button_takePicture: {
                //The button press should open a camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }
}

