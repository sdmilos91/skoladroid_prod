package com.kodzo.tim.skoladroidprod.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.kodzo.tim.skoladroidprod.R;
import com.kodzo.tim.skoladroidprod.adapter.SchoolCommentAdapter;
import com.kodzo.tim.skoladroidprod.adapter.SchoolListAdapter;
import com.kodzo.tim.skoladroidprod.database.DatabaseHelper;
import com.kodzo.tim.skoladroidprod.model.Comment;
import com.kodzo.tim.skoladroidprod.model.School;
import com.kodzo.tim.skoladroidprod.util.Mokap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Milos PC on 03-Jul-16.
 */
public class GetRecensionsTaks extends AsyncTask<String, String, String> {

    private Context mMainActivity;
    private View mView;
    private ListView mListView;
    private DatabaseHelper dbHelper;

    private ProgressDialog mDialog;

    public GetRecensionsTaks(Context context, View view, ListView listView){
        mMainActivity = context;
        mView = view;
        mListView = listView;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        mDialog = new ProgressDialog(mMainActivity);
        mDialog.setMessage("Добављање података");
        mDialog.setCancelable(false);
        mDialog.setInverseBackgroundForced(false);
        dbHelper = new DatabaseHelper(mMainActivity);
        mDialog.show();
    }

    @Override
    protected String doInBackground(String... uri) {
        String responseString = "";


            try {
                URL url = new URL(uri[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    // Do normal input or output stream reading
                    responseString = readStream(conn.getInputStream());
                } else {
                    responseString = "FAILED"; // See documentation for more info on response handling
                }
            } catch (IOException e) {
                responseString ="-1";
            }

        return  responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
        List<Comment> comments = new ArrayList<>();
        if(result.equals("-1"))
        {
            Toast.makeText(mMainActivity, "Неуспело добављање коментара... :(", Toast.LENGTH_LONG).show();
        }else {


            try {
                JSONArray jArray = new JSONArray(result);


                for (int i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        String username = oneObject.getString("Username");
                        String comment = oneObject.getString("Comment");
                        Comment com = new Comment();
                        com.setComment(comment);
                        com.setUsername(username);

                        comments.add(com);
                    } catch (JSONException e) {
                        // Oops
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SchoolCommentAdapter mCommentAdapter = new SchoolCommentAdapter(mView, mMainActivity, comments);

        mListView.setAdapter(mCommentAdapter);

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