package com.kodzo.tim.skoladroidprod.task;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.kodzo.tim.skoladroidprod.MainActivity;
import com.kodzo.tim.skoladroidprod.activity.SplashScreenActivity;

public class SplashScreenTask extends AsyncTask<Void, Void, Void> {

    private static int SPLASH_TIME_OUT = 3000;
    private long startTime;
    private SplashScreenActivity mActivity;

    public SplashScreenTask(SplashScreenActivity activity){
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();

        startTime=System.currentTimeMillis();

    }
    @Override
    protected Void doInBackground(Void... params) {

        long timeLeft = SPLASH_TIME_OUT - (System.currentTimeMillis()-startTime);
        if (timeLeft < 0) {
            timeLeft = 0;
        }

        SystemClock.sleep(timeLeft);
        Intent i = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(i);

        mActivity.finish();

        return null;
    }
}
