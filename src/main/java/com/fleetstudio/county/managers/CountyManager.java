package com.fleetstudio.county.managers;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleetstudio.county.Repositories.CountyRepository;
import com.fleetstudio.county.models.County;

public class CountyManager {

    public static int loadData(CountyRepository countyRepository) {
        List<County> counties = new ArrayList<>(); 
        try{
            File dataFile = ResourceUtils.getFile("classpath:data/data.json");
            String data = Files.readString(dataFile.toPath(), StandardCharsets.UTF_8);
            final ObjectMapper objectMapper = new ObjectMapper();
            counties = objectMapper.readValue(data, new TypeReference<List<County>>(){});
            countyRepository.deleteAll();
            for(County county : counties){
                countyRepository.save(county);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return counties.size();
    }

    public static List<County> getCountySuggestion(CountyRepository countyRepository, String query) {
        String formattedQuery = "%"+query.toLowerCase()+"%";
        List<County> counties = countyRepository.getCountySuggestion(formattedQuery,PageRequest.of(0, 5));
        System.out.println("formattedQuery: "+formattedQuery+" <=> counties: "+counties);
        return counties;
    }

    public static List<County> getCountySuggestionPriority(CountyRepository countyRepository, String query){

        List<County> countyList = countyRepository.findByStateContainingIgnoreCase(query,PageRequest.of(0, 5));
        if(countyList.size()<5){
            countyList.addAll(countyRepository.findByNameContainingIgnoreCase(query,PageRequest.of(0, 5-countyList.size())));
        }
        return countyList;
    }
    
}
