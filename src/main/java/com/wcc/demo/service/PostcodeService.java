package com.wcc.demo.service;

public interface PostcodeService {

    double calculateDistance(double latitude, double longitude, double latitude2, double longitude2);

    double haversine(double deg1, double deg2);

    double square(double x);
}
