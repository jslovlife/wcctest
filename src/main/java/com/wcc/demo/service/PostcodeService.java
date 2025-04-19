package com.wcc.demo.service;

import com.wcc.demo.model.entity.Postcode;

public interface PostcodeService {

    double calculateDistance(double latitude, double longitude, double latitude2, double longitude2);

    double haversine(double deg1, double deg2);

    double square(double x);

    Postcode findByPostcode(String postcode);

    Postcode save(Postcode postcode);

    Postcode update(Postcode postcode);
}
