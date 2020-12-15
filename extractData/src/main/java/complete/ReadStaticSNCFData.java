package complete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadStaticSNCFData {
	static List<SNCFStation> stationsList = new ArrayList<SNCFStation>();

	public List<SNCFStation> processData() throws JSONException, IOException {
		List<SNCFStation> stations = readCSV();
		return stations;
	}

	public List<SNCFStation> readCSV() throws IOException, JSONException {
		InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("outTRUE.csv"));
		List<SNCFStation> stations = new ArrayList<SNCFStation>();
		try {
			BufferedReader br = new BufferedReader(reader);
			

			String line = null;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");

				String id = data[0];
				String stationName = data[1];
				double lat =  (double) (data[3] == "" ? 0.0 :Double.parseDouble(data[3]));
				double lon =  (double) (data[4] == "" ? 0.0 :Double.parseDouble(data[4]));
				String arrivalTime = data[10];
				String departureTime = data[11];
				Boolean ascenseur = Boolean.parseBoolean(data[17]);
				Boolean escalator = Boolean.parseBoolean(data[18]);

				SNCFStation sncf = new SNCFStation(id, stationName, lat, lon, arrivalTime, departureTime, ascenseur,
						escalator);
				stations.add(sncf);
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
