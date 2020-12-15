package complete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadHospitalData {
	
	
	InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("les_etablissements_hospitaliers_franciliens.json"));
	static List<Hospital> hospitalList = new ArrayList<Hospital>();

	public List<Hospital> processData() throws JSONException, IOException {
		JSONArray stations = readJsonFromUrl(reader);
		processStations(stations);
		return hospitalList;		
	}
	
	public static JSONArray readJsonFromUrl(InputStreamReader reader) throws IOException, JSONException {
		
		try {
			BufferedReader rd = new BufferedReader(reader);
			String jsonText = readAll(rd);
			JSONArray stations = new JSONArray(jsonText);
			return stations;
		} finally {
			//rd.close();
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
		for (Object station : stations) {
			
			String catagorie;
			String tel_number;
			String adresse;
			
			JSONObject hospitalJson = (JSONObject) station;
			
			
			JSONObject fields = (JSONObject) hospitalJson.getJSONObject("fields");
			
			//System.out.println(stationJson);
			if(fields.has("adresse_complete")){
				 adresse = fields.getString("adresse_complete");
			  }
			else {
				 adresse = "";
			}
			
			if(fields.has("num_tel")){
				 tel_number =  fields.getString("num_tel");;
			  }
			else {
				 tel_number = "";
			}
			
			if(fields.has("type_etablissement")){
				 catagorie = (String) fields.get("type_etablissement");
			  }
			else {
				 catagorie = "";
			}
			
		
			JSONObject geometry = (JSONObject) hospitalJson.getJSONObject("geometry");
			JSONArray coordinates = geometry.getJSONArray("coordinates");
			double lat = coordinates.getDouble(0);
			double lon = coordinates.getDouble(1);
			
			//System.out.println(catagorie);
			

			hospitalList.add(new Hospital(adresse,  tel_number,  catagorie,  lat,  lon));
						
		}
	}

}
