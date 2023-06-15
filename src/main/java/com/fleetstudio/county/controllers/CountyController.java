package com.fleetstudio.county.controllers;

import java.util.List;

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

    @GetMapping("/getcounty")
    List<County> getCountySuggestion(@RequestParam  String q) {
        System.out.println("q: "+q);
        return CountyManager.getCountySuggestion(countyRepository,q);
    }

    @GetMapping("/getcountyp")
    List<County> getCountySuggestionPriority(@RequestParam  String q) {
        System.out.println("q: "+q);
        return CountyManager.getCountySuggestionPriority(countyRepository,q);
    }
    
}
