package complete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import complete.NsPrefix;

/**
 * Extract the dynamic data of Lyon and save it in Fueski. 
 * 
 */

public class DynamicLyon {
	private static final String FUESKI_LOCAL_ENDPOINT = "http://localhost:3030/city/update";
	
	public static void main(String args[]) throws IOException, JSONException {
		sslResolve();
		String url = "https://download.data.grandlyon.com/wfs/rdata?SERVICE=WFS&VERSION=1.1.0&outputformat=GEOJSON&request=GetFeature&typename=jcd_jcdecaux.jcdvelov&SRSNAME=urn:ogc:def:crs:EPSG::4171";
		JSONObject json = readJsonFromUrl(url);		
		processStationDyna(json);
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
			System.out.println(jsonText);
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

	private static void processStationDyna(JSONObject json) {

		String stationURIPrefix = NsPrefix.getSchemaNS() + "Station";	
		//System.out.println(stationURIPrefix);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();
		String todayDate = formatter.format(today);
		JSONArray fstations = (JSONArray) json.get("features");
		for (Object station : fstations) {
			JSONObject stationJson = (JSONObject) station;
			JSONObject properties=(JSONObject) stationJson.get("properties");
			String ID = (String) properties.get("number");
			String nava = (String) properties.get("available_bikes");
//			String ndocava = (String) properties.get("available_bike_stands");
//		    String nupdatetime = (String) properties.get("last_update");
//		    String nlat = (String) properties.get("lat");
//		    String nlong = (String) properties.get("lng");

			String iri = stationURIPrefix + ":LYON:" + ID;

			String query = "PREFIX schema: <http://schema.org/> \r\n"
					+ "PREFIX geo:   <https://www.w3.org/2003/01/geo/wgs84_pos#> \r\n"
					+ "PREFIX xsd:   <http://www.w3.org/2000/01/rdf-schema/> \r\n"
					+ "PREFIX onto:  <http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#> "
					+"PREFIX schema: <http://schema.org/>\r\n"
					+ "INSERT DATA { <" +  iri + "> onto:hasAvailability [\r\n" 
					+ "                                            a           onto:Availability; \r\n"
					+ "					                           onto:updatedDatetime \"" + todayDate + "\"^^xsd:dateTime;\r\n"
					+ "					                           onto:availableBikes \""  + nava + "\";\r\n" 
					+ "                                            ] .\r\n" 
					+ "		}";
			
			
			UpdateRequest update  = UpdateFactory.create(query);
	        UpdateProcessor qexec = UpdateExecutionFactory.createRemote(update, FUESKI_LOCAL_ENDPOINT);
	        qexec.execute();	
	        //model.write(new FileOutputStream(new File(url)), "TURTLE");
		}
		System.out.println("DONE: Added Lyon dynamic data");
	}
}
