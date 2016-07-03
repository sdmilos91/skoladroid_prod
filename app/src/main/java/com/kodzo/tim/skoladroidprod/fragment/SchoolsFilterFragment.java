package com.kodzo.tim.skoladroidprod.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.kodzo.tim.skoladroidprod.R;
import com.kodzo.tim.skoladroidprod.database.DatabaseHelper;
import com.kodzo.tim.skoladroidprod.util.Mokap;
import com.kodzo.tim.skoladroidprod.util.WordConverter;

public class SchoolsFilterFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private Button mFilterButtom;
    private EditText mSchoolName;
    private EditText mSchoolCity;
    private EditText mSchoolDist;
    private Switch mPrimarySchool;
    private Switch mElementarySchool;

    private String mSchoolNameText;
    private String mSchoolCityText;
    private String mSchoolDistText;
    private boolean mIsPrimarySchool;
    private boolean mIsElementarySchool;

    private DatabaseHelper helper;

    public interface FilterListener {
        void onFilterSchools();
    }

    // Empty constructor required for DialogFragment
    public SchoolsFilterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_filter, container);
        mSchoolName = (EditText) view.findViewById(R.id.filterSchooName);
        mSchoolCity = (EditText) view.findViewById(R.id.filterSchoolCity);
        mSchoolDist = (EditText) view.findViewById(R.id.filterSchoolDist);
        mElementarySchool = (Switch) view.findViewById(R.id.filterSchoolElementary);
        mPrimarySchool = (Switch) view.findViewById(R.id.filterSchoolPrimary);

       helper = new DatabaseHelper(getActivity());


        // set this instance as callback for editor action
        mSchoolName.setOnEditorActionListener(this);
        mSchoolName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Филтрирај школе");

        mFilterButtom = (Button) view.findViewById(R.id.filterButton);
        mFilterButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSchoolNameText = mSchoolName.getText().toString();
                mSchoolCityText = mSchoolCity.getText().toString();
                mSchoolDistText = mSchoolDist.getText().toString();
                mIsElementarySchool = mElementarySchool.isChecked();
                mIsPrimarySchool = mPrimarySchool.isChecked();

                String schoolNameCirylic = WordConverter.latinToCirylic(mSchoolNameText);
                String schoolCityCirylic = WordConverter.latinToCirylic(mSchoolCityText);

                Mokap.filterSchools(schoolNameCirylic, schoolCityCirylic, mIsElementarySchool, mIsPrimarySchool, mSchoolDistText, helper);


                FilterListener activity = (FilterListener) getActivity();
                activity.onFilterSchools();

                getDialog().dismiss();

            }
        });

        return view;
    }


    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }*/

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        // Return input text to activity
        this.dismiss();
        return true;
    }
}