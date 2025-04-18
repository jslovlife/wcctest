package com.wcc.demo.model.response;

import lombok.Data;
import com.wcc.demo.model.entity.Postcode;

@Data
public class PostcodeResponse {
    private Postcode location1;
    private Postcode location2;
    private double distance;
    private final String unit = "km";
}
