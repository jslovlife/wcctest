package com.wcc.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.wcc.demo.service.PostcodeService;
import com.wcc.demo.repository.PostcodeRepository;
import com.wcc.demo.model.entity.Postcode;
import com.wcc.demo.model.response.PostcodeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/postcode")
public class PostcodeController {

    @Autowired
    private PostcodeService postcodeService;

    @Autowired
    private PostcodeRepository postcodeRepository;

    @GetMapping("/distance")
    public ResponseEntity<?> getDistance(@RequestParam String postcode1,
    @RequestParam String postcode2) {

        try {
            Postcode location1 = postcodeRepository.findByPostcode(postcode1);

            if (location1 == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Postcode 1 not found, value:" +postcode1);
            }

            Postcode location2 = postcodeRepository.findByPostcode(postcode2);

            if (location2 == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Postcode 2 not found, value:" +postcode2);
            }

            PostcodeResponse postcodeResponse = new PostcodeResponse();
            postcodeResponse.setLocation1(location1);
            postcodeResponse.setLocation2(location2);

            postcodeResponse.setDistance(postcodeService.calculateDistance(location1.getLatitude(), location1.getLongitude(), location2.getLatitude(), location2.getLongitude()));

            return ResponseEntity.ok(postcodeResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PostcodeResponse());
        }
    }

    @GetMapping("/getPostcode")
    public ResponseEntity<?> getPostcode(@RequestParam String postcodeStr) {
        Postcode postcode = postcodeRepository.findByPostcode(postcodeStr);

        if (postcode == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Postcode not found, value:" +postcodeStr);
        }

        return ResponseEntity.ok(postcode);
    }
    

    @PutMapping("/update")
    public ResponseEntity<?> updatePostcode(@RequestBody Postcode postcode) {

        if (postcode.getPostcode() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Postcode is required");
        }

        postcodeRepository.save(postcode);

        return ResponseEntity.ok(postcode);
    }
}
