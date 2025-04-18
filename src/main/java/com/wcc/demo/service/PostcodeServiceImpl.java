package com.wcc.demo.service;

import com.wcc.demo.model.entity.Postcode;
import com.wcc.demo.repository.PostcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostcodeServiceImpl implements PostcodeService {

    @Autowired
    private PostcodeRepository postcodeRepository;

    private final static double EARTH_RADIUS = 6371; // radius in kilometers

    @Override
    public double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
        // Using Haversine formula! See Wikipedia;
        double lon1Radians = Math.toRadians(longitude);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude);
        double lat2Radians = Math.toRadians(latitude2);
        double a = haversine(lat1Radians, lat2Radians)
        + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (EARTH_RADIUS * c);
    }

    @Override
    public double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    @Override
    public double square(double x) {
        return x * x;
    }

    @Override
    public Postcode save(Postcode postcode) {
        return postcodeRepository.save(postcode);
    }

    @Override
    public Postcode update(Postcode postcode) {
        return postcodeRepository.save(postcode);
    }

    @Override
    public Postcode findByPostcode(String postcode) {
        return postcodeRepository.findByPostcode(postcode).orElse(null);
    }
}