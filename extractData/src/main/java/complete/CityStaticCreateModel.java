package complete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.json.JSONException;

import complete.NsPrefix;

public class CityStaticCreateModel {

	private static Model model;
	private static final String FUESKI_LOCAL_ENDPOINT = "http://localhost:3030/city";

	static String cityURIPrefix;
	static String hasaURIPrefix;
	static String hospitalURIPrefix;
	static String stationsURIPrefix;

	public static void main(String[] args) throws JSONException, IOException {
		sslResolve();
		initializeModel();
		//
		Resource city = model.createResource(NsPrefix.getCityURIPrefix() + "Lyon");
		// Property station = model.createProperty(NsPrefix.getOntoNS() + "Station");
		Property hospital = model.createProperty(NsPrefix.getHospitalURIPrefix() + "Hospital");
		Property hasa = model.createProperty(NsPrefix.getOntoNS() + "hasa");
		Property hasHospital = model.createProperty(NsPrefix.getOntoNS() + "hasHospital");
		Property hasMetrostation = model.createProperty(NsPrefix.getOntoNS() + "hasmetrostation");
		Property hasBicyclestation = model.createProperty(NsPrefix.getOntoNS() + "hasBicyclestation");
		Property hasbusStation = model.createProperty(NsPrefix.getOntoNS() + "hasbusStation");
		Property hasTramStation = model.createProperty(NsPrefix.getOntoNS() + "hasTramStation");

		// create
		city.addProperty(RDF.type, "City");
		Resource bStation = model.createResource();
		city.addProperty(hasa, bStation);
		/*
		 * city.addProperty(hasa, model.createResource().addProperty(hasTramStation,
		 * NsPrefix.getExNS() + "TramStation") .addProperty(hasMetrostation,
		 * NsPrefix.getExNS() + "Metrostation") .addProperty(hasBicyclestation,
		 * NsPrefix.getExNS() + "Bicyclestation") .addProperty(hasSNFstation,
		 * NsPrefix.getExNS() + "SNFstation") .addProperty(hasbusStation,
		 * NsPrefix.getExNS() + "BusStation"));
		 */
		city.addProperty(hasHospital, hospital);

		/*
		 * ReadStaticSNCFData sncfData = new ReadStaticSNCFData(); List<SNCFStation>
		 * stations = sncfData.processData(); appendSNCFtoModel(bStation, stations);
		 * 
		 * ReadStaticBusTramMetroData btmData = new ReadStaticBusTramMetroData();
		 * List<BTMStations> btmstations = btmData.processData();
		 * appendBTMStoModel(bStation, btmstations);
		 * 
		 * ReadStaticBicycleData bicycleData = new ReadStaticBicycleData();
		 * List<BicycleStation> bicylestations = bicycleData.processData(); for
		 * (BicycleStation station : bicylestations) {
		 * System.out.println(station.getID()); }
		 * 
		 * appendBicycletoModel(bStation, bicylestations);
		 * 
		 * saveToFueski();
		 */
		ReadHospitalData hospitalData = new ReadHospitalData();
		List<Hospital> hospitalList = hospitalData.processData();
		appendHospitalstoModel(city, hospitalList);
		// model.write(System.out, "turtle");

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

	private static void initializeModel() {
		// TODO Auto-generated method stub
		model = ModelFactory.createDefaultModel();
		model.setNsPrefix("city", NsPrefix.getCityURIPrefix());
		model.setNsPrefix("hospital", NsPrefix.getHospitalURIPrefix());
		model.setNsPrefix("station", NsPrefix.getStationsURIPrefix());
		model.setNsPrefix("onto", NsPrefix.getOntoNS());

	}

	private static void appendSNCFtoModel(Resource blank, List<SNCFStation> stations) {

		Property hasSNFstation = model.createProperty(NsPrefix.getOntoNS() + "hasSNFstation");

		Property hasId = model.createProperty(NsPrefix.getOntoNS() + "hasID");
		Property hasNAme = model.createProperty(NsPrefix.getOntoNS() + "hasNAme");
		Property hasLatitude = model.createProperty(NsPrefix.getOntoNS() + "hasLatitude");
		Property hasLongtiude = model.createProperty(NsPrefix.getOntoNS() + "hasLongtiude");
		Property hasEscalator = model.createProperty(NsPrefix.getOntoNS() + "hasEscalator");
		Property hasAscenseur = model.createProperty(NsPrefix.getOntoNS() + "hasAscenseur");
		Property hasTrain = model.createProperty(NsPrefix.getOntoNS() + "hasTrain");
		Property hasArrival = model.createProperty(NsPrefix.getOntoNS() + "hasArrival");
		Property hasDeprature = model.createProperty(NsPrefix.getOntoNS() + "hasDeprature");

		for (SNCFStation station : stations) {
			Resource trainstation = model
					.createResource(NsPrefix.getOntoNS() + "SNCFstation/" + station.getID().replaceAll(" ", "_"));
			Resource train = model.createResource();
			blank.addProperty(hasSNFstation, trainstation);

			trainstation.addProperty(hasId, station.getID());
			trainstation.addProperty(hasNAme, station.getName());
			trainstation.addProperty(hasLatitude, String.valueOf(station.getLat()), XSDDatatype.XSDdouble);
			trainstation.addProperty(hasLongtiude, String.valueOf(station.getLon()), XSDDatatype.XSDdouble);
			trainstation.addProperty(hasEscalator, String.valueOf(station.isEscalator()), XSDDatatype.XSDboolean);
			trainstation.addProperty(hasAscenseur, String.valueOf(station.isAscenseur()), XSDDatatype.XSDboolean);
			trainstation.addProperty(hasTrain, train);

			train.addProperty(hasArrival, String.valueOf(station.getArrival()));
			train.addProperty(hasDeprature, String.valueOf(station.getDepart()));

		}

	}

	private static void appendBTMStoModel(Resource blank, List<BTMStations> stations) {

		Property hasBusStation = model.createProperty(NsPrefix.getOntoNS() + "hasBusStation");
		Property hasTramStation = model.createProperty(NsPrefix.getOntoNS() + "hasTramsSation");
		Property hasMetroStation = model.createProperty(NsPrefix.getOntoNS() + "hasMetroStation");

		Property hasId = model.createProperty(NsPrefix.getOntoNS() + "hasID");
		Property hasNAme = model.createProperty(NsPrefix.getOntoNS() + "hasNAme");
		Property hasLatitude = model.createProperty(NsPrefix.getOntoNS() + "hasLatitude");
		Property hasLongtiude = model.createProperty(NsPrefix.getOntoNS() + "hasLongtiude");
		Property hasBusNumber = model.createProperty(NsPrefix.getOntoNS() + "hasBusNumber");
		Property hasTramNumber = model.createProperty(NsPrefix.getOntoNS() + "hasTramNumber");
		Property hasMetroNumber = model.createProperty(NsPrefix.getOntoNS() + "hasMetroNumber");
		Property hasUpdatedTime = model.createProperty(NsPrefix.getOntoNS() + "hasUpdatedTime");

		for (BTMStations station : stations) {
			Resource busStation = model
					.createResource(NsPrefix.getOntoNS() + "busStation/" + station.getID().replaceAll(" ", "_"));
			Resource tramStation = model
					.createResource(NsPrefix.getOntoNS() + "tramStation/" + station.getID().replaceAll(" ", "_"));
			Resource metroStation = model
					.createResource(NsPrefix.getOntoNS() + "metroStation/" + station.getID().replaceAll(" ", "_"));

			blank.addProperty(hasBusStation, busStation);
			blank.addProperty(hasTramStation, tramStation);
			blank.addProperty(hasMetroStation, metroStation);

			busStation.addProperty(hasId, station.getID());
			busStation.addProperty(hasNAme, station.getName());
			busStation.addProperty(hasLatitude, String.valueOf(station.getLat()), XSDDatatype.XSDdouble);
			busStation.addProperty(hasLongtiude, String.valueOf(station.getLon()), XSDDatatype.XSDdouble);
			busStation.addProperty(hasBusNumber, String.valueOf(station.getBusNumber()));
			busStation.addProperty(hasUpdatedTime, String.valueOf(station.getUpdatedtime()));

			tramStation.addProperty(hasId, station.getID());
			tramStation.addProperty(hasNAme, station.getName());
			tramStation.addProperty(hasLatitude, String.valueOf(station.getLat()), XSDDatatype.XSDdouble);
			tramStation.addProperty(hasLongtiude, String.valueOf(station.getLon()), XSDDatatype.XSDdouble);
			tramStation.addProperty(hasTramNumber, String.valueOf(station.getTramNumber()));
			tramStation.addProperty(hasUpdatedTime, String.valueOf(station.getUpdatedtime()));

			metroStation.addProperty(hasId, station.getID());
			metroStation.addProperty(hasNAme, station.getName());
			metroStation.addProperty(hasLatitude, String.valueOf(station.getLat()), XSDDatatype.XSDdouble);
			metroStation.addProperty(hasLongtiude, String.valueOf(station.getLon()), XSDDatatype.XSDdouble);
			metroStation.addProperty(hasMetroNumber, String.valueOf(station.getMetroNumber()));
			metroStation.addProperty(hasUpdatedTime, String.valueOf(station.getUpdatedtime()));

		}

	}

	public static void appendBicycletoModel(Resource blank, List<BicycleStation> stations) {

		Property hasBicycleStation = model.createProperty(NsPrefix.getOntoNS() + "hasBicycleStation");
		Property hasId = model.createProperty(NsPrefix.getOntoNS() + "hasId");
		Property hasNAme = model.createProperty(NsPrefix.getOntoNS() + "hasNAme");
		Property hascapacity = model.createProperty(NsPrefix.getOntoNS() + "hascapacity");
		Property hasStation = model.createProperty(NsPrefix.getOntoNS() + "hasStation");
		Property hasLatitude = model.createProperty(NsPrefix.getOntoNS() + "hasLatitude");
		Property hasLongitude = model.createProperty(NsPrefix.getOntoNS() + "hasLongitude");

		for (BicycleStation station : stations) {
			Resource bicyclestation = model
					.createResource(NsPrefix.getOntoNS() + "BicycleStation/" + station.getID().replaceAll(" ", "_"));
			blank.addProperty(hasBicycleStation, bicyclestation);
			bicyclestation.addProperty(hasNAme, station.getName(), "En");
			Statement statement_pcapacity = model.createLiteralStatement(bicyclestation, hascapacity,
					station.getCapacity());
			model.add(statement_pcapacity);
			bicyclestation.addLiteral(model.createProperty(NsPrefix.getGeoNS() + "hasLatitude"), station.getLat());
			bicyclestation.addLiteral(model.createProperty(NsPrefix.getGeoNS() + "hasLongitude"), station.getLon());
			System.out.println(bicyclestation);

		}
	}

	public static void appendHospitalstoModel(Resource city, List<Hospital> hospitalList) {

		Property hashospital = model.createProperty(NsPrefix.getOntoNS() + "hashospital");
		Property hasId = model.createProperty(NsPrefix.getOntoNS() + "hasId");
		Property hasNAme = model.createProperty(NsPrefix.getOntoNS() + "hasNAme");
		Property hascapacity = model.createProperty(NsPrefix.getOntoNS() + "hascapacity");
		Property hasStation = model.createProperty(NsPrefix.getOntoNS() + "hasStation");
		Property hasLatitude = model.createProperty(NsPrefix.getOntoNS() + "hasLatitude");
		Property hasLongitude = model.createProperty(NsPrefix.getOntoNS() + "hasLongitude");

		for (Hospital station : hospitalList) {
			//Resource bicyclestation = model
			//		.createResource(NsPrefix.getOntoNS() + "BicycleStation/" + station.getID().replaceAll(" ", "_"));
			//blank.addProperty(hasBicycleStation, bicyclestation);
			//bicyclestation.addProperty(hasNAme, station.getName(), "En");
			//Statement statement_pcapacity = model.createLiteralStatement(bicyclestation, hascapacity,
			//		station.getCapacity());
			//model.add(statement_pcapacity);
			//bicyclestation.addLiteral(model.createProperty(NsPrefix.getGeoNS() + "hasLatitude"), station.getLat());
			//bicyclestation.addLiteral(model.createProperty(NsPrefix.getGeoNS() + "hasLongitude"), station.getLon());
			//System.out.println(bicyclestation);

		}
	}

	public static void saveToFueski() {
		try {
			DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(FUESKI_LOCAL_ENDPOINT);
			accessor.putModel(model);
			String url = "C:\\Users\\redig\\Downloads\\Project\\creatmodel.ttl";

			try {
				model.write(new FileOutputStream(new File(url)), "TURTLE");
				System.out.println("Finished!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}