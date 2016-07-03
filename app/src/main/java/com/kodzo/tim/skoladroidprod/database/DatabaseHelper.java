package com.kodzo.tim.skoladroidprod.database;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.kodzo.tim.skoladroidprod.model.School;
import com.kodzo.tim.skoladroidprod.util.WordConverter;

import org.w3c.dom.Comment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Skoladroid.db";

    public static final String TABLE_SCHOOL = "School";

    public static final String COL_ID = "Id";
    public static final String COL_NAME = "Name";
    public static final String COL_CITY = "City";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_COUNTY = "County";
    public static final String COL_SITE = "Site";
    public static final String COL_TYPE = "Type";
    public static final String COL_PHONE = "Phone";
    public static final String COL_MUNICIPALITY = "Municipality";
    public static final String COL_FAX = "Fax";
    public static final String COL_POST_CODE = "PostCode";
    public static final String COL_GPS = "Gps";
    public static final String COL_DEPARTMENTS = "Departments";
    public static final String COL_DISTANCE = "Distance";

    public static final String TABLE_RECENSION = "Recension";

    public static final String COL_REC_ID = "Id";
    public static final String COL_USERNAME = "Username";
    public static final String COL_SCHOOL_ID = "SchoolId";
    public static final String COL_COMMENT = "Comment";
    public static final String COL_RATING = "Rating";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "CREATE TABLE " + TABLE_SCHOOL
                + " (Id INTEGER PRIMARY KEY, "
                + "Name TEXT, "
                + "City TEXT, "
                + "Address TEXT, "
                + "County TEXT, "
                + "Site TEXT, "
                + "Type TEXT, "
                + "Phone TEXT, "
                + "Municipality TEXT, "
                + "Fax TEXT, "
                + "PostCode TEXT, "
                + "Gps TEXT, "
                + "Departments INTEGER, "
                + "Distance REAL )";


        db.execSQL(sql);

        String sql2 = "CREATE TABLE " + TABLE_RECENSION
                + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Username TEXT, "
                + "Comment TEXT, "
                + "SchoolId INTEGER, "
                + "Rating INTEGER )";

        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql, sql2;
        sql = "DROP TABLE IF EXIST " + TABLE_SCHOOL;
        sql2 = "DROP TABLE IF EXIST " + TABLE_RECENSION;
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    public void inserSchoolList(List<School> schools){
        SQLiteDatabase db = this.getWritableDatabase();

        for (School s : schools) {
            insertSchool(s);
        }

    }

    public void insertSchool(School s){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(COL_ID, s.getId());
        cValues.put(COL_NAME, s.getName());
        cValues.put(COL_ADDRESS, s.getAddress());
        cValues.put(COL_COUNTY, s.getCounty());
        cValues.put(COL_CITY, s.getCity());
        cValues.put(COL_SITE, s.getSite());
        cValues.put(COL_GPS, s.getGps());
        cValues.put(COL_MUNICIPALITY, s.getMunicipality());
        cValues.put(COL_PHONE, s.getPhone());
        cValues.put(COL_FAX, s.getFax());
        cValues.put(COL_POST_CODE, s.getPostCode());
        cValues.put(COL_DEPARTMENTS, s.getDepartments());
        cValues.put(COL_TYPE, s.getType());
        cValues.put(COL_DISTANCE, s.getDistance());

        db.insert(TABLE_SCHOOL, null, cValues);


    }

    public void insertRecension(int schoolId, String username, String comment, int rating){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(COL_USERNAME, username);
        cValues.put(COL_RATING, rating);
        cValues.put(COL_SCHOOL_ID, schoolId);
        cValues.put(COL_COMMENT, comment);

        db.insert(TABLE_RECENSION, null, cValues);

   }

    public boolean IsSchoolEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * from " + TABLE_SCHOOL;
        Cursor c = db.rawQuery(query, null);
        return c.getCount() < 1;
    }

    public  List<School> getAllSchools(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * from " + TABLE_SCHOOL;
        Cursor c = db.rawQuery(query, null);

        List<School> schools = new ArrayList<>();

        while (c.moveToNext()){
            School s = new School();
            s.setId(c.getString(0));
            s.setName(c.getString(1));
            s.setCity(c.getString(2));
            s.setAddress(c.getString(3));
            s.setCounty(c.getString(4));
            s.setSite(c.getString(5));
            s.setType(c.getString(6));
            s.setPhone(c.getString(7));
            s.setMunicipality(c.getString(8));
            s.setFax(c.getString(9));
            s.setPostCode(c.getString(10));
            s.setGps(c.getString(11));
            s.setDepartments(c.getString(12));
            s.setDistance(c.getFloat(13));

            schools.add(s);
        }

        return schools;
    }

    public  List<School> sortSchoolsByDistance(float dist){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * from " + TABLE_SCHOOL  + " where " + COL_DISTANCE + " < " + dist + " order by " + COL_DISTANCE;
        Cursor c = db.rawQuery(query, null);

        List<School> schools = new ArrayList<>();

        while (c.moveToNext()){
            School s = new School();
            s.setId(c.getString(0));
            s.setName(c.getString(1));
            s.setCity(c.getString(2));
            s.setAddress(c.getString(3));
            s.setCounty(c.getString(4));
            s.setSite(c.getString(5));
            s.setType(c.getString(6));
            s.setPhone(c.getString(7));
            s.setMunicipality(c.getString(8));
            s.setFax(c.getString(9));
            s.setPostCode(c.getString(10));
            s.setGps(c.getString(11));
            s.setDepartments(c.getString(12));
            s.setDistance(c.getFloat(13));

            schools.add(s);
        }

        return schools;
    }

    public  List<School> filterSchools(String name, String city, boolean isElementary, boolean isPrimary, String dist){
        SQLiteDatabase db = this.getWritableDatabase();

        String type = "";

        if((!isPrimary || !isElementary) && (isPrimary || isElementary)){
                type = "Основна школа";
            if(isPrimary)
                type = "Средња школа";
        }
        float d = 0;
        try {
            d= Float.parseFloat(dist);
        }catch (Exception e){
            d = 9999999f;
        }

        d = d * 1000;
        String query = "Select * from " + TABLE_SCHOOL  + " where LOWER(" + COL_NAME + ") like '%" + name.toLowerCase() + "%' and LOWER("
                        + COL_CITY + ") like '%" + city.toLowerCase() + "%' and "
                        + COL_TYPE + " like '%" + type + "%' and "
                        + COL_DISTANCE + " < " + d + " order by " + COL_DISTANCE;
        Cursor c = db.rawQuery(query, null);

        List<School> schools = new ArrayList<>();

        while (c.moveToNext()){
            School s = new School();
            s.setId(c.getString(0));
            s.setName(c.getString(1));
            s.setCity(c.getString(2));
            s.setAddress(c.getString(3));
            s.setCounty(c.getString(4));
            s.setSite(c.getString(5));
            s.setType(c.getString(6));
            s.setPhone(c.getString(7));
            s.setMunicipality(c.getString(8));
            s.setFax(c.getString(9));
            s.setPostCode(c.getString(10));
            s.setGps(c.getString(11));
            s.setDepartments(c.getString(12));
            s.setDistance(c.getFloat(13));

            schools.add(s);
        }

        return schools;
    }

    public  void updateSchoolDistance(String id, float dist){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(COL_DISTANCE, dist);

        db.update(TABLE_SCHOOL, cValues, "Id = ?", new String[] {id});
    }

    public void exportDatabse() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.kodzo.tim.skoladroidprod//databases//"+DATABASE_NAME+"";
                String backupDBPath = "backupname.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            Log.d("EROOOOOOOOOOR", "********************************************************************************");
            Log.d("EROOOOOOOOOOR", e.getMessage());
        }


    }
}
