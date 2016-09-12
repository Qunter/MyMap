package com.yufa.mymap.Entity;

/**
 * Created by luyufa on 2016/8/28.
 */
public class LocationInfo {
    private double longitude;   //经度
    private double latitude;    //纬度

    public LocationInfo() {
    }

    public LocationInfo(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
