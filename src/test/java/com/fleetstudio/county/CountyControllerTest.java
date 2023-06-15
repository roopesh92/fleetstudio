package com.fleetstudio.county;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleetstudio.county.Repositories.CountyRepository;
import com.fleetstudio.county.controllers.CountyController;
import com.fleetstudio.county.models.County;

@WebMvcTest(CountyController.class)
public class CountyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    CountyRepository countyRepository;

    County county1 = new County("01001", "AL","Autauga");
    County county2 = new County("01003", "AL","Baldwin");
    County county3 = new County("01005", "AL","Barbour");
    County county4 = new County("01007", "AL","Bibb");
    County county5 = new County("01009", "AL","Blount");
    County county6 = new County("01011", "AL","Bullock");
    County county7 = new County("01013", "AL","Butler");
    County county8 = new County("01015", "AL","Calhoun");
    County county9 = new County("01017", "AL","Chambers");

    @Test
    public void checkStatus() throws Exception {
        List<County> records = new ArrayList<>(Arrays.asList(county2, county3));
        Mockito.when(countyRepository.findByStateContainingIgnoreCase("ba",PageRequest.of(0, 5))).thenReturn(records);        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/getcountyp?q=ba")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void checkSize() throws Exception {
        List<County> records = new ArrayList<>(Arrays.asList(county2, county3));
        Mockito.when(countyRepository.findByStateContainingIgnoreCase("ba",PageRequest.of(0, 5))).thenReturn(records);        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/getcountyp?q=ba")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void checkResponse() throws Exception {
        List<County> records = new ArrayList<>(Arrays.asList(county2, county3));
        Mockito.when(countyRepository.findByStateContainingIgnoreCase("ba",PageRequest.of(0, 5))).thenReturn(records);        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/getcountyp?q=ba")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name", is("Barbour")));
    }

}
