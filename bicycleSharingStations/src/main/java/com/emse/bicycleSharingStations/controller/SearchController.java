package com.emse.bicycleSharingStations.controller;

import com.emse.bicycleSharingStations.model.BTMStations;
import com.emse.bicycleSharingStations.model.BicycleStation;
import com.emse.bicycleSharingStations.model.Hospital;
import com.emse.bicycleSharingStations.model.SNCFStations;
import com.emse.bicycleSharingStations.service.CityGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/api/btmsearch/")
    public ResponseEntity<List<BTMStations>> findBTMmodel(@PathVariable String type) {
        //System.out.println("cname::" + cname);
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