package com.fleetstudio.county.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fleetstudio.county.Repositories.CountyRepository;
import com.fleetstudio.county.managers.CountyManager;
import com.fleetstudio.county.models.County;



@RestController
public class CountyController {

    @Autowired CountyRepository countyRepository;

    @GetMapping("/")
    String home() {
        return "County Suggestion";
    }

    @GetMapping("/loaddata")
    String loadData() {
        int insertedCount = CountyManager.loadData(countyRepository);
        return "Inserted "+insertedCount+" entries to DB";
    }

    @GetMapping("/suggestv1")
    List<County> getCountySuggestion(@RequestParam  String q) {
        return CountyManager.getCountySuggestion(countyRepository,q);
    }

    @GetMapping("/suggest")
    List<County> getCountySuggestionPriority(@RequestParam  String q) {
        return CountyManager.getCountySuggestionPriority(countyRepository,q);
    }

    
    
}
