package com.emse.bicycleSharingStations.service;
import com.emse.bicycleSharingStations.model.BTMStations;
import com.emse.bicycleSharingStations.model.BicycleStation;
import com.emse.bicycleSharingStations.model.Hospital;
import com.emse.bicycleSharingStations.model.SNCFStations;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityGuideService {
    private List<BicycleStation> bicycleStations;
    String FUESKI_LOCAL_ENDPOINT = "http://localhost:3030/city";
    
    
    public List <BicycleStation> findBicycleStation() {

        List<BicycleStation> stationsList = new ArrayList<BicycleStation>();
//        String query = "PREFIX onto: <http://www.semanticweb.org/emse/ontologies/2019/11/bicycle_stations.owl#>\n" +
//                "PREFIX geo: <https://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
//                "PREFIX schema: <http://schema.org/>\n" +
//                "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\n" +
//                "prefix xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
//                "SELECT ?stationName ?city ?station ?capacity ?lat ?lon ?updatedDateTime ?availableBikes\n" +
//                "WHERE {\n" +
//                "  ?city onto:cityName ?cityName .\n" +
//                "  ?city onto:hasStation ?station .\n" +
//                "  ?station onto:stationName ?stationName .\n" +
//                "  ?station onto:capacity ?capacity .\n" +
//                "  ?station geo:lat ?lat .\n" +
//                "  ?station geo:long ?lon .\n" +
//                "  ?station onto:hasAvailability ?availability .\n" +
//                "  ?availability onto:updatedDatetime ?updatedDateTime .\n" +
//                "  ?availability onto:availableBikes ?availableBikes .\n" +
//                "  FILTER (?cityName = \""+cname+ "\")\n" +
//                "  }ORDER BY ?stationName   ";

        String query = "PREFIX onto: <http://www.semanticweb.org/emse/ontologies/2019/11/bicycle_stations.owl#>\n" +
                "PREFIX geo: <https://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\n" +
                "prefix xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
                "\n" +
                "SELECT ?stationName ?city ?station ?capacity ?lat ?lon ?updatedDateTime ?availableBikes\n" +
                "WHERE {\n" +
                "  ?city onto:cityName ?cityName .\n" +
                "  ?city onto:hasStation ?station .\n" +
                "  ?station onto:stationName ?stationName .\n" +
                "  ?station onto:capacity ?capacity .\n" +
                "  ?station geo:lat ?lat .\n" +
                "  ?station geo:long ?lon .\n" +
                "  ?station onto:hasAvailability ?availability .\n" +
                "  ?availability onto:updatedDatetime ?updatedDateTime .\n" +
                "  ?availability onto:availableBikes ?availableBikes .  \n" +
                "  {SELECT ?station (MAX(?dt)  AS ?updatedDateTime)\n" +
                "    WHERE {\n" +
                "      ?city onto:cityName ?cityName .\n" +
                "      ?city onto:hasStation ?station .\n" +
                "      ?station onto:hasAvailability ?ava .\n" +
                "      ?ava onto:updatedDatetime ?dt .\n" +
                "      ?ava onto:availableBikes ?availableBikes .\n" +
                "    } GROUP BY ?station\n" +
                "  }\n" +
                "}ORDER BY ?stationName   ";


        Query qu = QueryFactory.create(query);
        QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
        ResultSet results = q.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource stationIRI = (Resource) qs.get("station");
            RDFNode name = qs.get("stationName");
            RDFNode lat = qs.get("lat");
            RDFNode lon = qs.get("lon");
            RDFNode capacity = qs.get("capacity");
            RDFNode availableBikes = qs.get("availableBikes");
            RDFNode updatedDateTime = qs.get("updatedDateTime");

            String lIRI = stationIRI.getURI();
            System.out.println(lIRI);
            String lName = name.asLiteral().getString();
            String lLat = lat.asLiteral().getString();
            String lLon = lon.asLiteral().getString();
            String lCapacity = capacity.asLiteral().getString();
            String lAvailableBikes = availableBikes.asLiteral().getString();
            String lLocalUpdatedDateTime = updatedDateTime.asLiteral().getString();
            stationsList.add(new BicycleStation(lIRI, lName, lLat, lLon, lCapacity, lAvailableBikes, lLocalUpdatedDateTime));
        }
        return stationsList;
    }
    
    public List <Hospital> findHospitals() {

        List<Hospital> hospitalList = new ArrayList<Hospital>();


        String query = "PREFIX onto: <http://www.semanticweb.org/emse/ontologies/2019/11/bicycle_stations.owl#>\n" +
                "PREFIX geo: <https://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\n" +
                "prefix xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
                "\n" +
                "SELECT ?stationName ?city ?station ?capacity ?lat ?lon ?updatedDateTime ?availableBikes\n" +
                "WHERE {\n" +
                "  ?city onto:cityName ?cityName .\n" +
                "  ?city onto:hasStation ?station .\n" +
                "  ?station onto:stationName ?stationName .\n" +
                "  ?station onto:capacity ?capacity .\n" +
                "  ?station geo:lat ?lat .\n" +
                "  ?station geo:long ?lon .\n" +
                "  ?station onto:hasAvailability ?availability .\n" +
                "  ?availability onto:updatedDatetime ?updatedDateTime .\n" +
                "  ?availability onto:availableBikes ?availableBikes .  \n" +
                "  {SELECT ?station (MAX(?dt)  AS ?updatedDateTime)\n" +
                "    WHERE {\n" +
                "      ?city onto:cityName ?cityName .\n" +
                "      ?city onto:hasStation ?station .\n" +
                "      ?station onto:hasAvailability ?ava .\n" +
                "      ?ava onto:updatedDatetime ?dt .\n" +
                "      ?ava onto:availableBikes ?availableBikes .\n" +
                "    } GROUP BY ?station\n" +
                "  }\n" +
                "}ORDER BY ?stationName   ";


        Query qu = QueryFactory.create(query);
        QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
        ResultSet results = q.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource stationIRI = (Resource) qs.get("station");
            RDFNode name = qs.get("stationName");
            RDFNode lat = qs.get("lat");
            RDFNode lon = qs.get("lon");
            RDFNode capacity = qs.get("capacity");
            RDFNode availableBikes = qs.get("availableBikes");
            RDFNode updatedDateTime = qs.get("updatedDateTime");

            String lIRI = stationIRI.getURI();
            System.out.println(lIRI);
            String adresse = name.asLiteral().getString();
            double latitude = lat.asLiteral().getDouble();
            double longitude = lon.asLiteral().getDouble();
            String tel_number = capacity.asLiteral().getString();
            String catagorie = availableBikes.asLiteral().getString();
            
            hospitalList.add(new Hospital( adresse,  tel_number,  catagorie,  latitude,  longitude));
        }
        return hospitalList;
    }
   
    public List <BTMStations> findBTMmodel(String type) {

        List<BTMStations> btmList = new ArrayList<BTMStations>();


        String query = "PREFIX onto: <http://www.semanticweb.org/emse/ontologies/2019/11/bicycle_stations.owl#>\n" +
                "PREFIX geo: <https://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\n" +
                "prefix xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
                "\n" +
                "SELECT ?stationName ?city ?station ?capacity ?lat ?lon ?updatedDateTime ?availableBikes\n" +
                "WHERE {\n" +
                "  ?city onto:cityName ?cityName .\n" +
                "  ?city onto:hasStation ?station .\n" +
                "  ?station onto:stationName ?stationName .\n" +
                "  ?station onto:capacity ?capacity .\n" +
                "  ?station geo:lat ?lat .\n" +
                "  ?station geo:long ?lon .\n" +
                "  ?station onto:hasAvailability ?availability .\n" +
                "  ?availability onto:updatedDatetime ?updatedDateTime .\n" +
                "  ?availability onto:availableBikes ?availableBikes .  \n" +
                "  {SELECT ?station (MAX(?dt)  AS ?updatedDateTime)\n" +
                "    WHERE {\n" +
                "      ?city onto:cityName ?cityName .\n" +
                "      ?city onto:hasStation ?station .\n" +
                "      ?station onto:hasAvailability ?ava .\n" +
                "      ?ava onto:updatedDatetime ?dt .\n" +
                "      ?ava onto:availableBikes ?availableBikes .\n" +
                "    } GROUP BY ?station\n" +
                "  }\n" +
                "}ORDER BY ?stationName   ";


        Query qu = QueryFactory.create(query);
        QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
        ResultSet results = q.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource stationIRI = (Resource) qs.get("station");
            RDFNode name = qs.get("stationName");
            RDFNode lat = qs.get("lat");
            RDFNode lon = qs.get("lon");
            RDFNode capacity = qs.get("capacity");
            RDFNode availableBikes = qs.get("availableBikes");
            RDFNode updatedDateTime = qs.get("updatedDateTime");

            String lIRI = stationIRI.getURI();
            System.out.println(lIRI);
            String ID = availableBikes.asLiteral().getString();//TODO modifications
            String nam = name.asLiteral().getString();
            double latitude = lat.asLiteral().getDouble();
            double longitude = lon.asLiteral().getDouble();
            String numb = capacity.asLiteral().getString();//TODO modifications
            String updatedtime = updatedDateTime.asLiteral().getString();//TODO modifications
            
            btmList.add(new BTMStations( ID, nam,  latitude,  longitude, numb, updatedtime));
        }
        return btmList;
    }
    public List <SNCFStations> findSNCFmodel() {

        List<SNCFStations> sncfList = new ArrayList<SNCFStations>();


        String query = "PREFIX onto: <http://www.semanticweb.org/emse/ontologies/2019/11/bicycle_stations.owl#>\n" +
                "PREFIX geo: <https://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\n" +
                "prefix xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
                "\n" +
                "SELECT ?stationName ?city ?station ?capacity ?lat ?lon ?updatedDateTime ?availableBikes\n" +
                "WHERE {\n" +
                "  ?city onto:cityName ?cityName .\n" +
                "  ?city onto:hasStation ?station .\n" +
                "  ?station onto:stationName ?stationName .\n" +
                "  ?station onto:capacity ?capacity .\n" +
                "  ?station geo:lat ?lat .\n" +
                "  ?station geo:long ?lon .\n" +
                "  ?station onto:hasAvailability ?availability .\n" +
                "  ?availability onto:updatedDatetime ?updatedDateTime .\n" +
                "  ?availability onto:availableBikes ?availableBikes .  \n" +
                "  {SELECT ?station (MAX(?dt)  AS ?updatedDateTime)\n" +
                "    WHERE {\n" +
                "      ?city onto:cityName ?cityName .\n" +
                "      ?city onto:hasStation ?station .\n" +
                "      ?station onto:hasAvailability ?ava .\n" +
                "      ?ava onto:updatedDatetime ?dt .\n" +
                "      ?ava onto:availableBikes ?availableBikes .\n" +                "    } GROUP BY ?station\n" +
                "  }\n" +
                "}ORDER BY ?stationName   ";


        Query qu = QueryFactory.create(query);
        QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
        ResultSet results = q.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource stationIRI = (Resource) qs.get("station");
            RDFNode name = qs.get("stationName");
            RDFNode lat = qs.get("lat");
            RDFNode lon = qs.get("lon");
            RDFNode capacity = qs.get("capacity");
            RDFNode availableBikes = qs.get("availableBikes");
            RDFNode updatedDateTime = qs.get("updatedDateTime");

            String lIRI = stationIRI.getURI();
            System.out.println(lIRI);
            String ID = name.asLiteral().getString();//TODO modifications
            String nam = name.asLiteral().getString();
            double latitude = lat.asLiteral().getDouble();
            double longitude = lon.asLiteral().getDouble();
            String arrival = capacity.asLiteral().getString();//TODO modifications
            String depart = updatedDateTime.asLiteral().getString();//TODO modifications
            boolean escalator = availableBikes.asLiteral().getBoolean();//TODO modifications
            boolean ascenseur = availableBikes.asLiteral().getBoolean(); //TODO modifications
            
            sncfList.add(new SNCFStations( ID, nam, latitude,  longitude, arrival, depart, escalator, ascenseur));
        }
        return sncfList;
    }
}
