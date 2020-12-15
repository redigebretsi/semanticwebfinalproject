package complete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public class ReadStaticBusTramMetroData {
	static List<BTMStations> stationsList = new ArrayList<BTMStations>();

	public List<BTMStations> processData() throws JSONException, IOException {
		List<BTMStations> stations = readCSV();
		return stations;
	}

	public List<BTMStations> readCSV() throws IOException, JSONException {
		
		InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("tclresult.csv"));
		List<BTMStations> stations = new ArrayList<BTMStations>();
		try {
			BufferedReader br = new BufferedReader(reader);
			

			String line = null;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");

				String id = data[0];
				String stationName = data[1];
				double lat = (double) (data[12] == "" ? 0.0 : Double.parseDouble(data[12]));
				double lon = (double) (data[13] == "" ? 0.0 : Double.parseDouble(data[13]));
				String busNumber = data[2];
				String tramNumbrer = data[3];
				String metroNumber = data[4];
				String updatedtime = data[9];

				BTMStations bts = new BTMStations(id, stationName, lat, lon, busNumber, tramNumbrer, metroNumber,
						updatedtime);
				stations.add(bts);
			}
			br.close();
			return stations;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stations;

	}

}
