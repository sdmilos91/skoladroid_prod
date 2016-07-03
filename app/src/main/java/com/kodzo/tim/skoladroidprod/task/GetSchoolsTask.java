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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import javax.net.ssl.HttpsURLConnection;


public class GetSchoolsTask extends AsyncTask<String, String, String> {

    private Context mMainActivity;
    private View mView;
    private ListView mListView;
    private DatabaseHelper dbHelper;
    private boolean isSchoolDataEmpty;

    private ProgressDialog mDialog;

    public GetSchoolsTask(Context context, View view, ListView listView){
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
        isSchoolDataEmpty = dbHelper.IsSchoolEmpty();
        mDialog.show();
    }

    @Override
    protected String doInBackground(String... uri) {
        String responseString = "";//"[{\"id\":\"21\",\"naziv\":\"НХ Синиша Николајевић\",\"adresa\":\"Тимочка 24\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Врачар\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://osnhsinisanikolajevic.nasaskola.rs\",\"tel\":\"0112836610\",\"fax\":\"0112836610\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"20\",\"gps\":\"44.795777, 20.486889\"},{\"id\":\"135\",\"naziv\":\"Радојка Лакић\",\"adresa\":\"Др Александра Костића 1-7\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Савски венац\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.rlakic.edu.rs\",\"tel\":\"0113619713\",\"fax\":\"0113619713\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"16\",\"gps\":\"44.806694, 20.457365\"},{\"id\":\"176\",\"naziv\":\"Драган Ковачевић\",\"adresa\":\"Шафарикова 8\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Стари град\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.osdragankovacevic.edu.rs\",\"tel\":\"0113225236\",\"fax\":\"0113223328\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"19\",\"gps\":\"44.8160402880635,20.466188192367554\"},{\"id\":\"22\",\"naziv\":\"Светозар Марковић\",\"adresa\":\"Хаџи Милентијева бр. 62\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Врачар\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://ossmarkovic.edu.rs/\",\"tel\":\"0112433025\",\"fax\":\"0112448744\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"30\",\"gps\":\" 44.793829, 20.474400\"},{\"id\":\"23\",\"naziv\":\"Владислав Рибникар\",\"adresa\":\"Краља Милутина 10\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Врачар\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.os-ribnikar.edu.rs\",\"tel\":\"0113640166\",\"fax\":\"0113640168\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"44\",\"gps\":\"44.805955, 20.467669\"},{\"id\":\"137\",\"naziv\":\"Војвода Радомир Путник\",\"adresa\":\"Бошка Петровића 6\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Савски венац\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.osvojvodaputnik.edu.rs\",\"tel\":\"0112667224\",\"fax\":\"0113670017\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"26\",\"gps\":\"44.77793589631623,20.4620361328125\"},{\"id\":\"167\",\"naziv\":\"Душан Дугалић\",\"adresa\":\"Ђердапска 19\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Врачар\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.osdugalic.edu.rs\",\"tel\":\"0112836769\",\"fax\":\"0112836838\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"17\",\"gps\":\"44.79298842452881,20.48565298318863\"}, {\"id\":\"21\",\"naziv\":\"НХ Синиша Николајевић\",\"adresa\":\"Тимочка 24\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Врачар\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://osnhsinisanikolajevic.nasaskola.rs\",\"tel\":\"0112836610\",\"fax\":\"0112836610\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"20\",\"gps\":\"44.795777, 20.486889\"},{\"id\":\"135\",\"naziv\":\"Радојка Лакић\",\"adresa\":\"Др Александра Костића 1-7\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Савски венац\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.rlakic.edu.rs\",\"tel\":\"0113619713\",\"fax\":\"0113619713\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"16\",\"gps\":\"44.806694, 20.457365\"},{\"id\":\"176\",\"naziv\":\"Драган Ковачевић\",\"adresa\":\"Шафарикова 8\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Стари град\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.osdragankovacevic.edu.rs\",\"tel\":\"0113225236\",\"fax\":\"0113223328\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"19\",\"gps\":\"44.8160402880635,20.466188192367554\"},{\"id\":\"22\",\"naziv\":\"Светозар Марковић\",\"adresa\":\"Хаџи Милентијева бр. 62\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Врачар\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://ossmarkovic.edu.rs/\",\"tel\":\"0112433025\",\"fax\":\"0112448744\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"30\",\"gps\":\" 44.793829, 20.474400\"},{\"id\":\"23\",\"naziv\":\"Владислав Рибникар\",\"adresa\":\"Краља Милутина 10\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Врачар\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.os-ribnikar.edu.rs\",\"tel\":\"0113640166\",\"fax\":\"0113640168\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"44\",\"gps\":\"44.805955, 20.467669\"},{\"id\":\"137\",\"naziv\":\"Војвода Радомир Путник\",\"adresa\":\"Бошка Петровића 6\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Савски венац\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.osvojvodaputnik.edu.rs\",\"tel\":\"0112667224\",\"fax\":\"0113670017\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"26\",\"gps\":\"44.77793589631623,20.4620361328125\"},{\"id\":\"167\",\"naziv\":\"Душан Дугалић\",\"adresa\":\"Ђердапска 19\",\"pbroj\":\"11000\",\"mesto\":\"Београд\",\"opstina\":\"Врачар\",\"okrug\":\"Град Београд\",\"suprava\":\"Београд\",\"www\":\"http://www.osdugalic.edu.rs\",\"tel\":\"0112836769\",\"fax\":\"0112836838\",\"vrsta\":\"Основна школа\",\"odeljenja\":\"17\",\"gps\":\"44.79298842452881,20.48565298318863\"}]";

        if(isSchoolDataEmpty) {

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
                //TODO Handle problems..
            }
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
        if(isSchoolDataEmpty) {
            try {
                JSONArray jArray = new JSONArray(result);

                Mokap.clearSchools();

                for (int i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        String name = oneObject.getString("naziv");
                        String address = oneObject.getString("adresa");
                        String id = oneObject.getString("id");
                        String county = oneObject.getString("okrug");
                        String fax = oneObject.getString("fax");
                        String postCode = oneObject.getString("pbroj");
                        String type = oneObject.getString("vrsta");
                        String municipality = oneObject.getString("opstina");
                        String site = oneObject.getString("www");
                        String city = oneObject.getString("mesto");
                        String phone = oneObject.getString("tel");
                        String gps = oneObject.getString("gps");
                        String departments = oneObject.getString("odeljenja");

                        School sc = new School(name, city, id, address, county, site, type, phone, municipality, fax, postCode, gps, departments);
                        Mokap.addSchool(sc);

                    } catch (JSONException e) {
                        // Oops
                    }
                }

                Mokap.refreshFilteredSchools();
                dbHelper.inserSchoolList(Mokap.getSchools());
                Mokap.setSchools(dbHelper.getAllSchools());
                Mokap.filterSchoolsByDistance(20000, dbHelper);
                dbHelper.exportDatabse();
               // Mokap.refreshFilteredSchools();
                //Mokap.setSchoolsDistance(dbHelper);
                //Mokap.filterSchoolsByDistance(1000000, dbHelper);
                //dbHelper.sortSchoolsByDistance(10000);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(Mokap.isSchoolListEmpty()) {
            Mokap.setSchools(dbHelper.getAllSchools());
            Mokap.refreshFilteredSchools();
            dbHelper.exportDatabse();

            Mokap.filterSchoolsByDistance(20000, dbHelper);
        }


        if(Mokap.getSchools().isEmpty())
        {
            Toast.makeText(mMainActivity, "Нажалост нема пронађених школа :(", Toast.LENGTH_LONG).show();
        }
        SchoolListAdapter adapter = new SchoolListAdapter(mView, mMainActivity, Mokap.getSchools());
        mListView.setAdapter(adapter);

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
