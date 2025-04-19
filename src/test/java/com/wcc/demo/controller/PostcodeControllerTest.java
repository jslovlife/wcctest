package com.wcc.demo.controller;

import com.wcc.demo.model.entity.Postcode;
import com.wcc.demo.service.PostcodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.demo.model.enums.ErrorEnum;

@SpringBootTest
@AutoConfigureMockMvc
public class PostcodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostcodeService postcodeService;

    @Test
    public void testGetDistance() throws Exception {
        Postcode postcode1 = new Postcode();
        postcode1.setPostcode("AB10 1XG");
        postcode1.setLatitude(57.14416516);
        postcode1.setLongitude(-2.114847768);

        Postcode postcode2 = new Postcode();
        postcode2.setPostcode("AB10 6RN");
        postcode2.setLatitude(57.13787129);
        postcode2.setLongitude(-2.121487158);

        when(postcodeService.findByPostcode("AB10 1XG")).thenReturn(postcode1);
        when(postcodeService.findByPostcode("AB10 6RN")).thenReturn(postcode2);

        mockMvc.perform(get("/api/postcode/distance")
                .header("x-username", "user1")
                .header("x-password", "12345678")
                .param("postcode1", "AB10 1XG")
                .param("postcode2", "AB10 6RN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.distance").exists())
                .andExpect(jsonPath("$.unit").value("km"));
    }

    @Test
    public void testGetDistanceWithoutHeaders() throws Exception {
        mockMvc.perform(get("/api/postcode/distance")
                .param("postcode1", "AB10 1XG")
                .param("postcode2", "AB10 6RN"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(ErrorEnum.REQUIRED_HEADERS_MISSING.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorEnum.REQUIRED_HEADERS_MISSING.getMessage()));
    }

    @Test
    public void testGetDistanceWithInvalidUsername() throws Exception {
        mockMvc.perform(get("/api/postcode/distance")
                .header("x-username", "INVALID")
                .header("x-password", "12345678")
                .param("postcode1", "AB10 1XG")
                .param("postcode2", "AB10 6RN"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(ErrorEnum.INVALID_USERNAME_OR_PASSWORD.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorEnum.INVALID_USERNAME_OR_PASSWORD.getMessage()));
    }

    @Test
    public void testGetDistanceWithInvalidPostcode() throws Exception {
        when(postcodeService.findByPostcode("INVALID")).thenReturn(null);

        mockMvc.perform(get("/api/postcode/distance")
                .header("x-username", "user1")
                .header("x-password", "12345678")
                .param("postcode1", "INVALID")
                .param("postcode2", "AB10 6RN"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(ErrorEnum.POSTCODE_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorEnum.POSTCODE_NOT_FOUND.getMessage()));
    }

    @Test
    public void testGetPostcode() throws Exception {
        // Mock postcode data
        Postcode postcode = new Postcode();
        postcode.setPostcode("AB10 1XG");
        postcode.setLatitude(57.14416516);
        postcode.setLongitude(-2.114847768);

        // Mock find by Postcode repository response
        when(postcodeService.findByPostcode("AB10 1XG")).thenReturn(postcode);

        // Perform the request and verify the response  
        mockMvc.perform(get("/api/postcode/getPostcode")
                .param("postcodeStr", "AB10 1XG")
                .header("x-username", "user1")
                .header("x-password", "12345678")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postcode").value("AB10 1XG"));
    }   

    @Test
    public void testGetPostcodeWithInvalidPostcode() throws Exception {
        // Mock repository to return null for invalid postcode
        when(postcodeService.findByPostcode("INVALID")).thenReturn(null);

        // Perform the request and verify the error response
        mockMvc.perform(get("/api/postcode/getPostcode")
                .header("x-username", "user1")
                .header("x-password", "12345678")
                .param("postcodeStr", "INVALID")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(ErrorEnum.POSTCODE_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorEnum.POSTCODE_NOT_FOUND.getMessage()));
    }

    @Test
    public void testUpdatePostcode() throws Exception {
        // Mock postcode data
        Postcode postcode = new Postcode();
        postcode.setPostcode("AB10 1XG");
        postcode.setLatitude(57.14416516);
        postcode.setLongitude(-2.114847768);    

        // Mock update repository
        when(postcodeService.save(postcode)).thenReturn(postcode);

        // Perform the request and verify the response
        mockMvc.perform(put("/api/postcode/update")
                .header("x-username", "user1")
                .header("x-password", "12345678")
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
                .header("x-username", "user1")
                .header("x-password", "12345678")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postcode)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(ErrorEnum.POSTCODE_IS_REQUIRED.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorEnum.POSTCODE_IS_REQUIRED.getMessage()));
    }

    @Test
    public void testUpdatePostcodeWithInvalidUsername() throws Exception {
        // Mock postcode data
        Postcode postcode = new Postcode();
        postcode.setPostcode("AB10 1XG");
        postcode.setLatitude(57.14416516);
        postcode.setLongitude(-2.114847768);

        mockMvc.perform(put("/api/postcode/update")
                .header("x-username", "INVALID")
                .header("x-password", "12345678")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postcode)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(ErrorEnum.INVALID_USERNAME_OR_PASSWORD.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorEnum.INVALID_USERNAME_OR_PASSWORD.getMessage()));
        
    }

    @Test
    public void testUpdatePostcodeWithoutHeaders() throws Exception {
        // Mock postcode data with invalid postcode
        Postcode postcode = new Postcode();
        postcode.setPostcode("AB10 1XG");
        postcode.setLatitude(57.14416516);
        postcode.setLongitude(-2.114847768);

        // Perform the request and verify the error response
        mockMvc.perform(put("/api/postcode/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postcode)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(ErrorEnum.REQUIRED_HEADERS_MISSING.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorEnum.REQUIRED_HEADERS_MISSING.getMessage()));
    }
}
