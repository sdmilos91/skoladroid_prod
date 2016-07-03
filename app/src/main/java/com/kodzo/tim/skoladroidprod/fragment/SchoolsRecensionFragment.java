package com.kodzo.tim.skoladroidprod.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.kodzo.tim.skoladroidprod.R;
import com.kodzo.tim.skoladroidprod.adapter.SchoolCommentAdapter;
import com.kodzo.tim.skoladroidprod.database.DatabaseHelper;
import com.kodzo.tim.skoladroidprod.model.Comment;
import com.kodzo.tim.skoladroidprod.task.GetRecensionsTaks;
import com.kodzo.tim.skoladroidprod.task.SendRecensionTask;
import com.kodzo.tim.skoladroidprod.util.Mokap;
import com.kodzo.tim.skoladroidprod.util.WordConverter;

import java.util.ArrayList;
import java.util.List;

public class SchoolsRecensionFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private int mSchoolId;

    private Button mRecButton;
    private EditText mUsername;
    private EditText mComment;
    private RatingBar mRating;
    private  View view;

    private String mUsernameText;
    private String mCommentext;
    private int mRateNumber;


    private Bundle savedState = null;

    public interface RecensionListener {
        void onRecension();
    }

    // Empty constructor required for DialogFragment
    public SchoolsRecensionFragment() {

    }

    public SchoolsRecensionFragment(int schoolId) {
        mSchoolId = schoolId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_school_recension, container);
        mUsername = (EditText) view.findViewById(R.id.recUserName);
        mComment = (EditText) view.findViewById(R.id.recComment);

        mRating = (RatingBar) view.findViewById(R.id.recRating);


        // set this instance as callback for editor action
        mUsername.setOnEditorActionListener(this);
        mUsername.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Оставите коментар");

        mRecButton = (Button) view.findViewById(R.id.recSendBtn);
        mRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsernameText = mUsername.getText().toString();
                mCommentext = mComment.getText().toString();


                mRateNumber = mRating.getProgress();

              //Pozvati servis za komentare i recenzije
                if(!mUsernameText.isEmpty() && !mCommentext.isEmpty()){
                    DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                    dbHelper.insertRecension(mSchoolId, mUsernameText, mCommentext, mRateNumber);
                    SendRecensionTask task = new SendRecensionTask(getActivity(), mSchoolId, mUsernameText, mCommentext, mRateNumber);
                    task.execute();
                    dbHelper.exportDatabse();
                }

                RecensionListener activity = (RecensionListener) getActivity();
                activity.onRecension();

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