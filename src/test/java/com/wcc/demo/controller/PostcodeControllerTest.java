package com.wcc.demo.controller;

import com.wcc.demo.model.entity.Postcode;
import com.wcc.demo.repository.PostcodeRepository;
import com.wcc.demo.service.PostcodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PostcodeController.class)
public class PostcodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostcodeService postcodeService;

    @MockBean
    private PostcodeRepository postcodeRepository;

    @Test
    public void testGetDistance() throws Exception {
        // Mock postcode data
        Postcode postcode1 = new Postcode();
        postcode1.setPostcode("AB10 1XG");
        postcode1.setLatitude(57.14416516);
        postcode1.setLongitude(-2.114847768);

        Postcode postcode2 = new Postcode();
        postcode2.setPostcode("AB15 6NA");
        postcode2.setLatitude(57.147701);
        postcode2.setLongitude(-2.095797);

        // Mock find by Postcode repository responses
        when(postcodeRepository.findByPostcode("AB10 1XG")).thenReturn(postcode1);
        when(postcodeRepository.findByPostcode("AB15 6NA")).thenReturn(postcode2);

        // Mock calculate distnace service response
        when(postcodeService.calculateDistance(
            postcode1.getLatitude(), postcode1.getLongitude(),
            postcode2.getLatitude(), postcode2.getLongitude()
        )).thenReturn(3.6);

        // Perform the request and verify the response
        mockMvc.perform(get("/api/postcode/distance")
                .param("postcode1", "AB10 1XG")
                .param("postcode2", "AB15 6NA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.distance").value(3.6))
                .andExpect(jsonPath("$.unit").value("km"));
    }

    @Test
    public void testGetDistanceWithInvalidPostcode() throws Exception {
        // Mock repository to return null for invalid postcode
        when(postcodeRepository.findByPostcode("INVALID")).thenReturn(null);

        // Perform the request and verify the error response
        mockMvc.perform(get("/api/postcode/distance")
                .param("postcode1", "INVALID")
                .param("postcode2", "AB15 6NA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Postcode 1 not found, value:INVALID"));
    }

    @Test
    public void testGetPostcode() throws Exception {
        // Mock postcode data
        Postcode postcode = new Postcode();
        postcode.setPostcode("AB10 1XG");
        postcode.setLatitude(57.14416516);
        postcode.setLongitude(-2.114847768);

        // Mock find by Postcode repository response
        when(postcodeRepository.findByPostcode("AB10 1XG")).thenReturn(postcode);

        // Perform the request and verify the response  
        mockMvc.perform(get("/api/postcode/getPostcode")
                .param("postcodeStr", "AB10 1XG")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postcode").value("AB10 1XG"));
    }   

    @Test
    public void testGetPostcodeWithInvalidPostcode() throws Exception {
        // Mock repository to return null for invalid postcode
        when(postcodeRepository.findByPostcode("INVALID")).thenReturn(null);

        // Perform the request and verify the error response
        mockMvc.perform(get("/api/postcode/getPostcode")
                .param("postcodeStr", "INVALID")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Postcode not found, value:INVALID"));
    }

    @Test
    public void testUpdatePostcode() throws Exception {
        // Mock postcode data
        Postcode postcode = new Postcode();
        postcode.setPostcode("AB10 1XG");
        postcode.setLatitude(57.14416516);
        postcode.setLongitude(-2.114847768);    

        // Mock update repository
        when(postcodeRepository.save(postcode)).thenReturn(postcode);

        // Perform the request and verify the response
        mockMvc.perform(put("/api/postcode/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postcode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postcode").value("AB10 1XG"));
    }

    @Test   
    public void testUpdatePostcodeWithMissingPostcode() throws Exception {
        // Mock postcode data with missing postcode
        Postcode postcode = new Postcode();
        postcode.setLatitude(57.14416516);
        postcode.setLongitude(-2.114847768);    

        // Perform the request and verify the error response            
        mockMvc.perform(put("/api/postcode/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postcode)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Postcode is required"));
    }
    
        
}
