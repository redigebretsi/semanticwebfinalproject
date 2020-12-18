package complete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadDynamicLyon {
	static String url = "https://download.data.grandlyon.com/wfs/rdata?SERVICE=WFS&VERSION=1.1.0&outputformat=GEOJSON&request=GetFeature&typename=jcd_jcdecaux.jcdvelov&SRSNAME=urn:ogc:def:crs:EPSG::4171";
	static List<Dbicycle> stationsList = new ArrayList<Dbicycle>();
	
	public List<Dbicycle> processData() throws JSONException, IOException {
		JSONObject stations = readJsonFromUrl(url);
		JSONArray fstations = (JSONArray) stations.get("features");
		processStations(fstations);
		return stationsList;		
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

	private static void processStations(JSONArray stations) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();
		String todayDate = formatter.format(today);
		int i = 0;
		for (Object station : stations) {
			JSONObject stationJson = (JSONObject) station;
			JSONObject properties=(JSONObject) stationJson.get("properties");
			String ID = (String) properties.get("number");
			String nava = (String) properties.get("available_bikes");
			String iri = NsPrefix.getOntoNS() + "BicycleStation/" + ID;

			stationsList.add(new Dbicycle(iri, nava));
		}
	}

}
