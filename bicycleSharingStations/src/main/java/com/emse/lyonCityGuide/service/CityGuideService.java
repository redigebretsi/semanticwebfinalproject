package com.emse.lyonCityGuide.service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.emse.lyonCityGuide.model.BTMStations;
import com.emse.lyonCityGuide.model.BicycleStation;
import com.emse.lyonCityGuide.model.DynamicData;
import com.emse.lyonCityGuide.model.Hospital;
import com.emse.lyonCityGuide.model.SNCFStations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Service
public class CityGuideService {
	private List<BicycleStation> bicycleStations;
	String FUESKI_LOCAL_ENDPOINT = "http://localhost:3030/city";

	public List<BicycleStation> findBicycleStation() {

		List<BicycleStation> stationsList = new ArrayList<BicycleStation>();

		String query = "PREFIX schema: <http://schema.org/> \r\n"
				+ "PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n"
				+ "PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n"
				+ "PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n"
				+ "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + "\r\n"
				+ "SELECT ?stopname  ?lat ?lon ?capacity \r\n" + "WHERE{\r\n" + " \r\n"
				+ "  ?b onto:hasName ?stopname.\r\n" + "  ?b geo:Lat ?lat .\r\n" + "  ?b geo:Long ?lon .\r\n"
				+ "  ?b onto:hascapacity ?capacity .\r\n" + "  \r\n" + "}";

		Query qu = QueryFactory.create(query);
		QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
		ResultSet results = q.execSelect();

		while (results.hasNext()) {
			QuerySolution qs = results.next();
			RDFNode name = qs.get("stopname");
			RDFNode lat = qs.get("lat");
			RDFNode lon = qs.get("lon");
			RDFNode capacity = qs.get("capacity");
			String lName = name.asLiteral().getString();
			String lLat = lat.asLiteral().getString();
			String lLon = lon.asLiteral().getString();
			String lCapacity = capacity.asLiteral().getString();
			stationsList.add(new BicycleStation(lName, lLat, lLon, lCapacity));
		}
		return stationsList;
	}

	public List<Hospital> findHospitals() {

		List<Hospital> hospitalList = new ArrayList<Hospital>();

		String query = "PREFIX schema: <http://schema.org/> \r\n"
				+ "PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n"
				+ "PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n"
				+ "PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n"
				+ "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + "\r\n"
				+ "SELECT ?category  ?lat ?lon ?address ?telephone ?hospital\r\n" + "WHERE{\r\n"
				+ "  ?hospital onto:catagorie ?category.\r\n" + "  ?hospital geo:Lat ?lat .\r\n"
				+ "  ?hospital geo:Long ?lon .\r\n" + "  ?hospital onto:tel_number ?telephone .\r\n"
				+ "  ?hospital onto:adresse ?address.\r\n" + "}";

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

			String lIRI = hospitalIRI.getURI();
			System.out.println(lIRI);
			String adresse = address.asLiteral().getString();
			double latitude = lat.asLiteral().getDouble();
			double longitude = lon.asLiteral().getDouble();
			String tel_number = telephone.asLiteral().getString();
			String catagorie = category.asLiteral().getString();

			hospitalList.add(new Hospital(adresse, tel_number, catagorie, latitude, longitude));
		}
		return hospitalList;
	}

	public List<BTMStations> findBTMmodel(String type) {
		List<BTMStations> btmList = new ArrayList<BTMStations>();
		String query = "";
		if (type.equals("BUSSTATIONS")) {
			query = "PREFIX schema: <http://schema.org/> \r\n"
					+ "PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n"
					+ "PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n"
					+ "PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n"
					+ "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + "\r\n"
					+ "SELECT ?stopName ?id ?lat ?lon ?busNumber \r\n" + "WHERE{\r\n" + "  ?bus onto:hasID ?id .\r\n"
					+ "  ?bus onto:hasName ?stopName.\r\n" + "  ?bus geo:Lat ?lat .\r\n" + "  ?bus geo:Long ?lon .\r\n"
					+ "  ?bus onto:hasBusNumber ?busNumber .\r\n" + "  \r\n" + "}";

		} else if (type.equals("TRAMSTATIONS")) {
			query = "PREFIX schema: <http://schema.org/> \r\n"
					+ "PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n"
					+ "PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n"
					+ "PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n"
					+ "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + "\r\n"
					+ "SELECT ?stopName ?id ?lat ?lon ?tramNumber \r\n" + "WHERE{\r\n" + "  ?t onto:hasID ?id .\r\n"
					+ "  ?t onto:hasName ?stopName.\r\n" + "  ?t geo:Lat ?lat .\r\n" + "  ?t geo:Long ?lon .\r\n"
					+ "  ?t onto:hasTramNumber ?tramNumber .\r\n" + "  \r\n" + "}";

		} else if (type.equals("METROSTATIONS")) {
			query = "PREFIX schema: <http://schema.org/> \r\n"
					+ "PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n"
					+ "PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n"
					+ "PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n"
					+ "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + "\r\n"
					+ "SELECT ?stopName ?id ?lat ?lon ?metroNumber \r\n" + "WHERE{\r\n"
					+ "  ?metro onto:hasID ?id .\r\n" + "  ?metro onto:hasName ?stopName.\r\n"
					+ "  ?metro geo:Lat ?lat .\r\n" + "  ?metro geo:Long ?lon .\r\n"
					+ "  ?metro onto:hasMetroNumber ?metroNumber .\r\n" + "  \r\n" + "}";

		}

		Query qu = QueryFactory.create(query);
		QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
		ResultSet results = q.execSelect();

		while (results.hasNext()) {
			QuerySolution qs = results.next();
			RDFNode id = qs.get("id");
			RDFNode name = qs.get("stopName");
			RDFNode lat = qs.get("lat");
			RDFNode lon = qs.get("lon");
			String numb = "";
			if (type.equals("BUSSTATIONS")) {
				RDFNode transportNumber = qs.get("busNumber");
				numb = transportNumber.asLiteral().getString();
			}
			if (type.equals("TRAMSTATIONS")) {
				RDFNode transportNumber = qs.get("tramNumber");
				numb = transportNumber.asLiteral().getString();
			}
			if (type.equals("METROSTATIONS")) {
				RDFNode transportNumber = qs.get("metroNumber");
				numb = transportNumber.asLiteral().getString();
			}
			String ID = id.asLiteral().getString();
			String nam = name.asLiteral().getString();
			double latitude = lat.asLiteral().getDouble();
			double longitude = lon.asLiteral().getDouble();

			btmList.add(new BTMStations(ID, nam, latitude, longitude, numb));
		}
		return btmList;
	}

	public List<DynamicData> findDynamicData() throws JSONException, IOException {

		sslResolve();
		List<DynamicData> dynamicList = new ArrayList<DynamicData>();

		String bicycleURL = "https://download.data.grandlyon.com/wfs/rdata?SERVICE=WFS&VERSION=1.1.0&outputformat=GEOJSON&request=GetFeature&typename=jcd_jcdecaux.jcdvelov&SRSNAME=urn:ogc:def:crs:EPSG::4171";
		String weatherURLPart = "http://api.openweathermap.org/data/2.5/weather?APPID=6eaa88893a7b68dde346b5c0ed4c980f";

		JSONObject stations = readJsonFromUrl(bicycleURL);
		JSONArray fstations = (JSONArray) stations.get("features");

		for (int i = 0; i < 10; i++) {
			Object station = fstations.get(i);

			JSONObject stationJson = (JSONObject) station;
			JSONObject properties = (JSONObject) stationJson.get("properties");
			String nava = (String) properties.get("available_bikes");
			String name = (String) properties.get("name");
			String lat = (String) properties.get("lat");
			String lng = (String) properties.get("lng");

			String weatherURL = weatherURLPart + "&lat=" + lat + "&lon=" + lng;

			JSONObject weatherJson = readJsonFromUrl(weatherURL);

			JSONArray weatherArray = weatherJson.getJSONArray("weather");
			JSONObject mainObject = weatherJson.getJSONObject("main");
			String temperature = mainObject.get("temp").toString();
			String pressure = mainObject.get("pressure").toString();
			String humidity = mainObject.get("humidity").toString();
			JSONObject windObject = weatherJson.getJSONObject("wind");
			String windSpeed = windObject.get("speed").toString();

			dynamicList.add(new DynamicData(name, nava, temperature, humidity, windSpeed, pressure));
		}
		return dynamicList;
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject stations = new JSONObject(jsonText);
			return stations;
		} finally {
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static void sslResolve() {

		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (GeneralSecurityException e) {
		}

	}

	public List<SNCFStations> findSNCFmodel() {

		List<SNCFStations> sncfList = new ArrayList<SNCFStations>();

		String query = "PREFIX schema: <http://schema.org/> \r\n"
				+ "PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n"
				+ "PREFIX rdf:   <http://www.w3.org/2000/01/rdf-schema/> \r\n"
				+ "PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n"
				+ "PREFIX ont: <http://purl.org/net/ns/ontology-annot#>\r\n" + "\r\n"
				+ "SELECT ?y  ?x ?lon ?lat ?station ?at ?dt ?id \r\n" + "WHERE{\r\n" + " \r\n"
				+ "  ?sncf onto:hasAscenseur ?y.\r\n" + "  ?sncf onto:hasEscalator ?x.\r\n"
				+ "  ?sncf geo:Lat ?lat .\r\n" + "  ?sncf geo:Long ?lon .\r\n" + "  ?sncf onto:hasName ?station .\r\n"
				+ "  ?sncf onto:hasID ?id.\r\n" + "  ?sncf onto:hasTrain [].\r\n" + "  [] onto:hasArrival ?at.\r\n"
				+ "  [] onto:hasDeprature ?dt.\r\n" + "  \r\n" + "}";

		Query qu = QueryFactory.create(query);
		QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT, qu);
		ResultSet results = q.execSelect();

		while (results.hasNext()) {
			QuerySolution qs = results.next();
			RDFNode id = qs.get("id");
			RDFNode name = qs.get("station");
			RDFNode lat = qs.get("lat");
			RDFNode lon = qs.get("lon");
			RDFNode arrivalT = qs.get("at");
			RDFNode departureT = qs.get("dt");
			RDFNode ascenseurRDF = qs.get("y");
			RDFNode escalatorRDF = qs.get("x");

			String ID = id.asLiteral().getString();
			String nam = name.asLiteral().getString();
			double latitude = lat.asLiteral().getDouble();
			double longitude = lon.asLiteral().getDouble();
			String arrival = arrivalT.asLiteral().getString();
			String depart = departureT.asLiteral().getString();
			boolean escalator = escalatorRDF.asLiteral().getBoolean();
			boolean ascenseur = ascenseurRDF.asLiteral().getBoolean(); 

			sncfList.add(new SNCFStations(ID, nam, latitude, longitude, arrival, depart, escalator, ascenseur));
		}
		return sncfList;
	}
}
