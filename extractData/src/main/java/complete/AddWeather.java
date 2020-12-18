package complete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Weather data added for each station 
 */
public class AddWeather {
	private static final String FUESKI_LOCAL_ENDPOINT_GET = "http://localhost:3030/city";
	private static final String FUESKI_LOCAL_ENDPOINT_UPDATE = "http://localhost:3030/city/update";
	private static String url_part = "http://api.openweathermap.org/data/2.5/weather?APPID=6eaa88893a7b68dde346b5c0ed4c980f";

	static List<Location> locationsList;
	
	public static void main(String args[]) throws IOException, JSONException {
		sslResolve();
		locationsList = getAllStationsWithLocations();
		System.out.println(locationsList);
		//processStationWeather(locationsList);
	}

	// Get all stations
	public static List<Location> getAllStationsWithLocations() {
        List<Location> locationsList = new ArrayList<Location>();
        String query = "PREFIX schema: <http://schema.org/> \r\n"
        		+ "PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n"
        		+ "PREFIX xsd:   <http://www.w3.org/2000/01/rdf-schema/> \r\n"
        		+ "PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#> \r\n"
        		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
        		+ "\r\n"
        		+ "SELECT ?station ?lat ?long ?hasName \r\n"
        		+ "WHERE{\r\n"
        		+ " ?station a schema:bicycleStation.\r\n"
        		+ " ?station geo:Lat ?lat.\r\n"
        		+ "  ?station geo:Long ?long. \r\n"
        		+ "  ?station onto:hasName ?hasName.\r\n"
        		+ "}";

	        Query qu = QueryFactory.create(query);
	        QueryExecution q = QueryExecutionFactory.sparqlService(FUESKI_LOCAL_ENDPOINT_GET, qu);
	        ResultSet results = q.execSelect();

	        while (results.hasNext()) {
	            QuerySolution qs = results.next();
	            Resource stationIRI = (Resource) qs.get("station");
	            RDFNode lat = qs.get("lat");
	            RDFNode lon = qs.get("long");
	            RDFNode name = qs.get("hasName");

	            String lIRI = stationIRI.getURI();
	            Double lLat = lat.asLiteral().getDouble();
	            Double lLon = lon.asLiteral().getDouble();
	            String lname = name.toString();
	            
	            locationsList.add(new Location(lIRI, lname, lLat, lLon));
	        }
	        System.out.println(query);
	        return locationsList;
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

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
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

	private static void processStationWeather(List<Location> locations) throws JSONException, IOException {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();
		String todayDate = formatter.format(today);

		for (Location location : locations) {

			String Id = location.getID();
//			String name = location.getName();
			Double lat = location.getLat();
			Double lon = location.getLon();

			String url = url_part+"&lat="+lat.toString()+"&lon="+lon.toString();
			System.out.println(url);

			JSONObject weatherJson = readJsonFromUrl(url);
			System.out.println(weatherJson);
			
			JSONArray weatherArray = weatherJson.getJSONArray("weather");
			JSONObject weatherObject = (JSONObject) weatherArray.get(0);
			String weatherDescription = (String) weatherObject.get("description");
			
			JSONObject mainObject = weatherJson.getJSONObject("main");
			String temperature = mainObject.get("temp").toString();
			int pressure =  (Integer)mainObject.get("pressure");
			int humadity =  (Integer)mainObject.get("humidity");
			
			JSONObject windObject = weatherJson.getJSONObject("wind");
			Double windSpeed =  (Double) windObject.get("speed");	


			String query = "PREFIX schema: <http://schema.org/> \r\n"
					+ "PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n"
					+ "PREFIX xsd:   <http://www.w3.org/2000/01/rdf-schema/> \r\n"
					+ "PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#>\r\n "
					+ "INSERT DATA { <" + Id + "> "
					+ "onto:hasWeather [\r\n"
					+ " a                                 onto:Weather; \r\n"
					+ "	onto:weatherUpdatedDatetime \"" + todayDate	+ "\"^^xsd:dateTime;\r\n" 
					+ " onto:temperature \"" + temperature + "\";"
					+ " onto:humadity \"" + humadity + "\";"
					+ " onto:windSpeed \"" + windSpeed + "\";"
					+ " onto:weatherDescription \"" + weatherDescription + "\";"
					+ " onto:pressure \"" + pressure + "\"; ] .}";

			UpdateRequest update = UpdateFactory.create(query);
			UpdateProcessor qexec = UpdateExecutionFactory.createRemote(update, FUESKI_LOCAL_ENDPOINT_UPDATE);
			qexec.execute();
			System.out.println(query);

		}
		System.out.println("DONE: Weather data added successfully");
	}

}

