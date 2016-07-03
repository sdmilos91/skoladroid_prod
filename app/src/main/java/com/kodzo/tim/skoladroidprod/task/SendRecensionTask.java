package com.kodzo.tim.skoladroidprod.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.kodzo.tim.skoladroidprod.adapter.SchoolListAdapter;
import com.kodzo.tim.skoladroidprod.database.DatabaseHelper;
import com.kodzo.tim.skoladroidprod.model.School;
import com.kodzo.tim.skoladroidprod.util.Mokap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SendRecensionTask extends AsyncTask<String, String, String> {
    private Context mMainActivity;
    private View mView;
    private DatabaseHelper dbHelper;
    private boolean isSchoolDataEmpty;

    private ProgressDialog mDialog;
    private int mSchoolId;
    private String mUsername;
    private String mComment;
    private int mrating;


    public SendRecensionTask(Context context, int schoolId, String username, String comment, int rating){
        mMainActivity = context;
        mUsername = username;
        mSchoolId = schoolId;
        mrating = rating;
        mComment = comment;
    }

    @Override
    protected String doInBackground(String... params) {


        URL url = null;
        String result = "";

        HttpURLConnection conn = null;
        try {
            url = new URL("http://milos91ns-001-site1.dtempurl.com/skoladroid/sendrecension");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject json2 = new JSONObject();
            json2.put("SchoolId", mSchoolId);
            json2.put("Username", mUsername);
            json2.put("UserId", 1);
            json2.put("Rating", mrating);
            json2.put("Comment", mComment);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(json2.toString());
            writer.flush();
            writer.close();
            SystemClock.sleep(1000);
            os.close();
            if(conn.getResponseCode() != 200)
            {
                result = "-1";
            }
            conn.connect();
            return  result;
        } catch (IOException e) {
            e.printStackTrace();
            return  "-1";
        } catch (JSONException e) {
            e.printStackTrace();
            return  "-1";
        }
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        mDialog = new ProgressDialog(mMainActivity);
        mDialog.setMessage("Добављање података");
        mDialog.setCancelable(false);
        mDialog.setInverseBackgroundForced(false);
        dbHelper = new DatabaseHelper(mMainActivity);
        isSchoolDataEmpty = dbHelper.IsSchoolEmpty();
        mDialog.show();
    }


    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        if(aVoid.equals("-1"))
            Toast.makeText(mMainActivity,"Неуспешно слање коментара...", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mMainActivity,"Успешно слање коментара. :)", Toast.LENGTH_SHORT).show();
        mDialog.dismiss();
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
