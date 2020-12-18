package com.emse.lyonCityGuide.service;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;

import com.emse.lyonCityGuide.model.BTMStations;
import com.emse.lyonCityGuide.model.BicycleStation;
import com.emse.lyonCityGuide.model.Hospital;
import com.emse.lyonCityGuide.model.SNCFStations;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityGuideService {
    private List<BicycleStation> bicycleStations;
    String FUESKI_LOCAL_ENDPOINT = "http://localhost:3030/city";
    
    
    public List <BicycleStation> findBicycleStation() {

        List<BicycleStation> stationsList = new ArrayList<BicycleStation>();


        String query = "PREFIX schema: <http://schema.org/> \r\n" + 
        		"PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n" + 
        		"PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n" + 
        		"PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n" + 
        		"PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + 
        		"\r\n" + 
        		"SELECT ?stopname  ?lat ?lon ?capacity \r\n" + 
        		"WHERE{\r\n" + 
        		" \r\n" + 
        		"  ?b onto:hasName ?stopname.\r\n" + 
        		"  ?b geo:Lat ?lat .\r\n" + 
        		"  ?b geo:Long ?lon .\r\n" + 
        		"  ?b onto:hascapacity ?capacity .\r\n" + 
        		"  \r\n" + 
        		"}";


        Query qu = QueryFactory.create(query);
        QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
        ResultSet results = q.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource stationIRI = (Resource) qs.get("station");
            RDFNode name = qs.get("stopname");
            RDFNode lat = qs.get("lat");
            RDFNode lon = qs.get("lon");
            RDFNode capacity = qs.get("capacity");
//            RDFNode availableBikes = qs.get("availableBikes");
//            RDFNode updatedDateTime = qs.get("updatedDateTime");

            //String lIRI = stationIRI.getURI();
            //System.out.println(lIRI);
            String lName = name.asLiteral().getString();
            String lLat = lat.asLiteral().getString();
            String lLon = lon.asLiteral().getString();
            String lCapacity = capacity.asLiteral().getString();
//            String lAvailableBikes = availableBikes.asLiteral().getString();
//            String lLocalUpdatedDateTime = updatedDateTime.asLiteral().getString();
            stationsList.add(new BicycleStation( lName, lLat, lLon, lCapacity));
        }
        return stationsList;
    }
    
    public List <Hospital> findHospitals() {

        List<Hospital> hospitalList = new ArrayList<Hospital>();


        String query = "PREFIX schema: <http://schema.org/> \r\n" + 
        		"PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n" + 
        		"PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n" + 
        		"PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n" + 
        		"PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + 
        		"\r\n" + 
        		"SELECT ?category  ?lat ?lon ?address ?telephone ?hospital\r\n" + 
        		"WHERE{\r\n" + 
        		"  ?hospital onto:catagorie ?category.\r\n" + 
        		"  ?hospital geo:Lat ?lat .\r\n" + 
        		"  ?hospital geo:Long ?lon .\r\n" + 
        		"  ?hospital onto:tel_number ?telephone .\r\n" + 
        		"  ?hospital onto:adresse ?address.\r\n" + 
        		"}";


        Query qu = QueryFactory.create(query);
        QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
        ResultSet results = q.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource hospitalIRI = (Resource) qs.get("hospital");
            RDFNode category = qs.get("category");
            RDFNode lat = qs.get("lat");
            RDFNode lon = qs.get("lon");
            RDFNode address = qs.get("address");
            RDFNode telephone = qs.get("telephone");
//            RDFNode updatedDateTime = qs.get("updatedDateTime");

            String lIRI = hospitalIRI.getURI();
            System.out.println(lIRI);
            String adresse = address.asLiteral().getString();
            double latitude = lat.asLiteral().getDouble();
            double longitude = lon.asLiteral().getDouble();
            String tel_number = telephone.asLiteral().getString();
            String catagorie = category.asLiteral().getString();
            
            hospitalList.add(new Hospital( adresse,  tel_number,  catagorie,  latitude,  longitude));
        }
        return hospitalList;
    }
   
    public List <BTMStations> findBTMmodel(String type) {
    	 List<BTMStations> btmList = new ArrayList<BTMStations>();
         System.out.println("888888cccccccccccc//======="+type);
    	 String query = "";
    	if(type.equals("BUSSTATIONS")) {
    		 query = "PREFIX schema: <http://schema.org/> \r\n" + 
    				"PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n" + 
    				"PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n" + 
    				"PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n" + 
    				"PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + 
    				"\r\n" + 
    				"SELECT ?stopName ?id ?lat ?lon ?busNumber \r\n" + 
    				"WHERE{\r\n" + 
    				"  ?bus onto:hasID ?id .\r\n" + 
    				"  ?bus onto:hasName ?stopName.\r\n" + 
    				"  ?bus geo:Lat ?lat .\r\n" + 
    				"  ?bus geo:Long ?lon .\r\n" + 
    				"  ?bus onto:hasBusNumber ?busNumber .\r\n" + 
    				"  \r\n" + 
    				"}";

    		
    	}
    	else if(type.equals("TRAMSTATIONS") ) {
    	       System.out.println("tran==========");
    		 query =    "PREFIX schema: <http://schema.org/> \r\n" + 
    				"PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n" + 
    				"PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n" + 
    				"PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n" + 
    				"PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + 
    				"\r\n" + 
    				"SELECT ?stopName ?id ?lat ?lon ?tramNumber \r\n" + 
    				"WHERE{\r\n" + 
    				"  ?t onto:hasID ?id .\r\n" + 
    				"  ?t onto:hasName ?stopName.\r\n" + 
    				"  ?t geo:Lat ?lat .\r\n" + 
    				"  ?t geo:Long ?lon .\r\n" + 
    				"  ?t onto:hasTramNumber ?tramNumber .\r\n" + 
    				"  \r\n" + 
    				"}";
    	       System.out.println("gggg==========");
    	       System.out.println(query);
    		
    	}
    	else if(type.equals("METROSTATIONS")) {
    		 query =    "PREFIX schema: <http://schema.org/> \r\n" + 
    				"PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n" + 
    				"PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n" + 
    				"PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n" + 
    				"PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + 
    				"\r\n" + 
    				"SELECT ?stopName ?id ?lat ?lon ?metroNumber \r\n" + 
    				"WHERE{\r\n" + 
    				"  ?metro onto:hasID ?id .\r\n" + 
    				"  ?metro onto:hasName ?stopName.\r\n" + 
    				"  ?metro geo:Lat ?lat .\r\n" + 
    				"  ?metro geo:Long ?lon .\r\n" + 
    				"  ?metro onto:hasMetroNumber ?metroNumber .\r\n" + 
    				"  \r\n" + 
    				"}";
    		
    	}

       System.out.println("88888888888==========");
       System.out.println(query);
       System.out.println("88888888888==========");


        Query qu = QueryFactory.create(query);
        QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
        ResultSet results = q.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource stationIRI = (Resource) qs.get("station");
            RDFNode id = qs.get("id");
            RDFNode name = qs.get("stopName");
            RDFNode lat = qs.get("lat");
            RDFNode lon = qs.get("lon");
            String numb="";
            if(type.equals("BUSSTATIONS")) {
                RDFNode transportNumber = qs.get("busNumber");  
                numb = transportNumber.asLiteral().getString();//TODO modifications
            }
            if(type.equals("TRAMSTATIONS")) {
                RDFNode transportNumber = qs.get("tramNumber");  
                numb = transportNumber.asLiteral().getString();//TODO modifications
            }
            if(type.equals("METROSTATIONS")) {
                RDFNode transportNumber = qs.get("metroNumber");    
                 numb = transportNumber.asLiteral().getString();//TODO modifications
            }
           
//
//            String lIRI = stationIRI.getURI();
//            System.out.println(lIRI);
            String ID = id.asLiteral().getString();//TODO modifications
            String nam = name.asLiteral().getString();
            double latitude = lat.asLiteral().getDouble();
            double longitude = lon.asLiteral().getDouble();          
            
            btmList.add(new BTMStations( ID, nam,  latitude,  longitude, numb));
        }
        return btmList;
    }
    public List <SNCFStations> findSNCFmodel() {

        List<SNCFStations> sncfList = new ArrayList<SNCFStations>();


        String query =  "PREFIX schema: <http://schema.org/> \r\n" + 
        		"PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n" + 
        		"PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n" + 
        		"PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n" + 
        		"PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + 
        		"\r\n" + 
        		"SELECT ?y  ?x ?lon ?lat ?station ?at ?dt \r\n" + 
        		"WHERE{\r\n" + 
        		" \r\n" + 
        		"  ?sncf onto:hasAscenseur ?y.\r\n" + 
        		"  ?sncf onto:hasEscalator ?x.\r\n" + 
        		"  ?sncf geo:Lat ?lat .\r\n" + 
        		"  ?sncf geo:Long ?lon .\r\n" + 
        		"  ?sncf onto:hasName ?station .\r\n" + 
        		"  ?sncf onto:hasID ?id.\r\n" + 
        		"  ?sncf onto:hasTrain [].\r\n" + 
        		"  [] onto:hasArrival ?at.\r\n" + 
        		"  [] onto:hasDeprature ?dt.\r\n" + 
        		"  \r\n" + 
        		"}";


        Query qu = QueryFactory.create(query);
        QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
        ResultSet results = q.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource stationIRI = (Resource) qs.get("station");
            RDFNode id = qs.get("id");
            RDFNode name = qs.get("station");
            RDFNode lat = qs.get("lat");
            RDFNode lon = qs.get("lon");
            RDFNode arrivalT = qs.get("at");
            RDFNode departureT = qs.get("dt");
            RDFNode ascenseurRDF = qs.get("y");
            RDFNode escalatorRDF = qs.get("x");

            String lIRI = stationIRI.getURI();
            System.out.println(lIRI);
            String ID = id.asLiteral().getString();//TODO modifications
            String nam = name.asLiteral().getString();
            double latitude = lat.asLiteral().getDouble();
            double longitude = lon.asLiteral().getDouble();
            String arrival = arrivalT.asLiteral().getString();//TODO modifications
            String depart = departureT.asLiteral().getString();//TODO modifications
            boolean escalator = escalatorRDF.asLiteral().getBoolean();//TODO modifications
            boolean ascenseur = ascenseurRDF.asLiteral().getBoolean(); //TODO modifications
            
            sncfList.add(new SNCFStations( ID, nam, latitude,  longitude, arrival, depart, escalator, ascenseur));
        }
        return sncfList;
    }
}
