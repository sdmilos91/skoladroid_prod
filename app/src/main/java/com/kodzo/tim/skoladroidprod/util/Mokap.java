package com.kodzo.tim.skoladroidprod.util;

import com.kodzo.tim.skoladroidprod.database.DatabaseHelper;
import com.kodzo.tim.skoladroidprod.model.Position;
import com.kodzo.tim.skoladroidprod.model.School;
import com.kodzo.tim.skoladroidprod.task.GetSchoolsTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

//Pomocna klasa sa metodama za rad sa kolekcijom skola
public class Mokap {

    //Sve skole koje se ucitaju
    private static List<School> schools = new ArrayList<School>();

    //Lista filtriranih skola
    private static List<School> filtSchools = new ArrayList<School>();

    //Trenutna lokacija korisnika
    //Novi Sad - Podrazumevana lokacija ako pregled lokacije nije dozvoljen
    private static Position mPosition = new Position(45.255f, 19.84f);


    //Da li je lista skola prazna?
    public  static boolean isSchoolListEmpty(){
        return schools.size() == 0;
    }

    //Dodaj skolu u listu skola
    public static void addSchool(School school) {
        school.setDistance(calculateSchoolDistance(school));
        schools.add(school);
    }

    //Filtrirane skole
    public static List<School> getSchools () {
        return filtSchools;
    }

    public static void setSchools (List<School> newSchools) {
        schools = new ArrayList<>(newSchools);
    }


    //Resetuj filtrirane skole
    public static void refreshFilteredSchools(){
        filtSchools = new ArrayList<>(schools);
    }


    //Ukloni sve skole iz liste skola
    public static void clearSchools () {
        schools = new ArrayList<School>();
    }

    //Racunanje distance izmedju dve tacke
    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    //Filtriraj skole po distanci
    public static void filterSchoolsByDistance (float dist, DatabaseHelper helper) {
        if(mPosition != null) {
            List<School> filteredSchools = new ArrayList<School>();
            filtSchools = helper.sortSchoolsByDistance(dist);
        }
    }

    public static float calculateSchoolDistance(School s){
        try {
            float lat1 = Float.parseFloat(s.getGps().split(",")[0]);
            float lng1 = Float.parseFloat(s.getGps().split(",")[1]);
            float dist = distFrom(mPosition.getLatitude(), mPosition.getLongitude(), lat1, lng1);

            s.setDistance(dist);
            return  dist;
          //  dbHelper.updateSchoolDistance(s.getId(), dist);

        } catch (Exception ex) {
            //Ako dodje do greske u parsiranju postavljamo vecu vrednost da ne bi imali problema kod sortiranja
            float dist = 9999999;
            s.setDistance(dist);
            //dbHelper.updateSchoolDistance(s.getId(), dist);
            return  dist;
        }
    }

    //Filtriraj skole
    public static void filterSchools(String name, String city, boolean isElementary, boolean isPrimary, String dist, DatabaseHelper helper) {

        filtSchools = helper.filterSchools(name, city, isElementary, isPrimary, dist);
    }

    //Setuj trenutnu poziciju
    public static void setPosition(float lat, float lon){
        mPosition = new Position(lat, lon);
    }


}
