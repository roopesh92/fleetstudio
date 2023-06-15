package com.fleetstudio.county.managers;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.PageRequest;
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
        if(validateQuery(query)){
            String[] sQuery = query.split(",");
            if(sQuery.length>1){
                return countyRepository.getCountySuggestionByStateAndName(
                    formatQuery(sQuery[1]),
                    formatQuery(sQuery[0]),
                    PageRequest.of(0, 5));
            }
            return countyRepository.getCountySuggestion(formatQuery(query),PageRequest.of(0, 5));
        }
        return (new ArrayList<County>());
        
    }

    public static List<County> getCountySuggestionPriority(CountyRepository countyRepository, String query){
        if(validateQuery(query)){
            String[] sQuery = query.split(",");
            if(sQuery.length>1){
                return countyRepository.getCountySuggestionByStateAndName(
                    formatQuery(sQuery[1]),
                    formatQuery(sQuery[0]),
                    PageRequest.of(0, 5));
            }
            List<County> countyList = countyRepository.findByStateContainingIgnoreCase(query,PageRequest.of(0, 5));
            if(countyList.size()<5){
                countyList.addAll(countyRepository.findByNameContainingIgnoreCase(query,PageRequest.of(0, 5-countyList.size())));
            }
            return countyList;
        }
        return (new ArrayList<County>());
    }

    public static String formatQuery(String query){
        return "%"+query.toLowerCase().trim()+"%";
    }

    public static boolean validateQuery(String q){
        String regex = "^[a-zA-Z ,]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(q);
        return matcher.matches();
    }
    
}
