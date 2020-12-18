package com.emse.lyonCityGuide.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emse.lyonCityGuide.model.BTMStations;
import com.emse.lyonCityGuide.model.BicycleStation;
import com.emse.lyonCityGuide.model.DynamicData;
import com.emse.lyonCityGuide.model.Hospital;
import com.emse.lyonCityGuide.model.Museum;
import com.emse.lyonCityGuide.model.SNCFStations;
import com.emse.lyonCityGuide.service.CityGuideService;

import java.io.IOException;
import java.util.List;

@ComponentScan(value = "com.emse.bicycleSharingStations")
@Controller
public class SearchController {
    @Autowired
    CityGuideService cityGuideService;


    @RequestMapping("/api/hospitalsearch/")
    public ResponseEntity<List<Hospital>> findHospitals() {
        List<Hospital> hospitals = cityGuideService.findHospitals();
        return new ResponseEntity<List<Hospital>>(hospitals, HttpStatus.OK);
    }
    @RequestMapping("/api/bicyclesearch/")
    public ResponseEntity<List<BicycleStation>> findBicycleStation() {
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
        List<SNCFStations> sncfStation = cityGuideService.findSNCFmodel();
        return new ResponseEntity<List<SNCFStations>>(sncfStation, HttpStatus.OK);
    }
    @RequestMapping("/api/fetchDynamic/")
    public ResponseEntity<List<DynamicData>> fetchDynamic() throws JSONException, IOException {
        List<DynamicData> dynamicData = cityGuideService.findDynamicData();
        return new ResponseEntity<List<DynamicData>>(dynamicData, HttpStatus.OK);
    }
    @RequestMapping("/api/getMuseums/")
    public ResponseEntity<List<Museum>> getMuseums() throws JSONException, IOException {
        List<Museum> museumList = cityGuideService.getMuseums();
        return new ResponseEntity<List<Museum>>(museumList, HttpStatus.OK);
    }
}