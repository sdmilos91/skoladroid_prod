package com.kodzo.tim.skoladroidprod.model;

public class School implements Comparable {
    private String name;
    private String city;
    private String id;
    private String address;
    private String county;
    private String site;
    private String type;
    private String phone;
    private String municipality;
    private String fax;
    private String postCode;
    private String gps;
    private String departments;
    private float distance;

    public School() {
    }

    public School(String name, String city, String id, String address, String county, String site, String type, String phone, String municipality, String fax, String postCode, String gps, String departments) {
        this.name = name;
        this.city = city;
        this.id = id;
        this.address = address;
        this.county = county;
        this.site = site;
        this.type = type;
        this.phone = phone;
        this.municipality = municipality;
        this.fax = fax;
        this.postCode = postCode;
        this.gps = gps;
        this.departments = departments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Object o) {
        School s = (School)o;

        if(this.getDistance() < s.getDistance())
            return -1;

        if(this.getDistance() > s.getDistance())
            return 1;

        return 0;
    }
}
