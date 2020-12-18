package com.emse.lyonCityGuide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emse.lyonCityGuide.model.BTMStations;
import com.emse.lyonCityGuide.model.BicycleStation;
import com.emse.lyonCityGuide.model.Hospital;
import com.emse.lyonCityGuide.model.SNCFStations;
import com.emse.lyonCityGuide.service.CityGuideService;

import java.util.List;

@ComponentScan(value = "com.emse.bicycleSharingStations")
@Controller
public class SearchController {
    @Autowired
    CityGuideService cityGuideService;


    @RequestMapping("/api/hospitalsearch/")
    public ResponseEntity<List<Hospital>> findHospitals() {
        //System.out.println("cname::" + cname);
        List<Hospital> hospitals = cityGuideService.findHospitals();
        return new ResponseEntity<List<Hospital>>(hospitals, HttpStatus.OK);
    }
    @RequestMapping("/api/bicyclesearch/")
    public ResponseEntity<List<BicycleStation>> findBicycleStation() {
        //System.out.println("cname::" + cname);
        List<BicycleStation> bicycleStation = cityGuideService.findBicycleStation();
        return new ResponseEntity<List<BicycleStation>>(bicycleStation, HttpStatus.OK);
    }
    @RequestMapping("/api/btmsearch/{type}")
    public ResponseEntity<List<BTMStations>> findBTMmodel(@PathVariable String type) {
        System.out.println("cname::" + type);
        List<BTMStations> btmStation = cityGuideService.findBTMmodel(type);
        return new ResponseEntity<List<BTMStations>>(btmStation, HttpStatus.OK);
    }
    @RequestMapping("/api/sncfsearch/")
    public ResponseEntity<List<SNCFStations>> findSNCFmodel() {
        //System.out.println("cname::" + cname);
        List<SNCFStations> sncfStation = cityGuideService.findSNCFmodel();
        return new ResponseEntity<List<SNCFStations>>(sncfStation, HttpStatus.OK);
    }
}