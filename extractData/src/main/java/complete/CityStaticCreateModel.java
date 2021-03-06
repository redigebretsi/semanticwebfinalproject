package complete;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.apache.jena.vocabulary.RDF;
import org.json.JSONException;

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
		// City node
		Resource city = model.createResource(NsPrefix.getOntoNS() + "city");
		Property hasa = model.createProperty(NsPrefix.getOntoNS() + "hasa");
		Property hasName = model.createProperty(NsPrefix.getOntoNS() + "hasName");

		Resource cityClass = model.createResource(NsPrefix.getSchemaNS() + "City");
		city.addProperty(RDF.type, cityClass);
		city.addProperty(hasName, "Lyon");
		Resource bStation = model.createResource();
		city.addProperty(hasa, bStation);

		// create model for SNCF
		ReadStaticSNCFData sncfData = new ReadStaticSNCFData();
		List<SNCFStation> stations = sncfData.processData();
		appendSNCFtoModel(bStation, stations);

		// create model for BusTramMetroData
		ReadStaticBusTramMetroData btmData = new ReadStaticBusTramMetroData();
		List<BTMStations> btmstations = btmData.processData();
		appendBTMStoModel(bStation, btmstations);

		// create model for BusTramMetroData
		ReadStaticBicycleData bicycleData = new ReadStaticBicycleData();
		List<BicycleStation> bicylestations = bicycleData.processData();
		appendBicycletoModel(bStation, bicylestations);

		// create model for HospitalData
		ReadHospitalData hospitalData = new ReadHospitalData();
		List<Hospital> hospitalList = hospitalData.processData();
		appendHospitalstoModel(city, hospitalList);
		saveToFueski();
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
		model.setNsPrefix("schema", NsPrefix.getSchemaNS());
		model.setNsPrefix("onto", NsPrefix.getOntoNS());
		model.setNsPrefix("rdf", NsPrefix.getrdf());
		model.setNsPrefix("geo", NsPrefix.getGeoNS());

	}

	private static void appendSNCFtoModel(Resource blank, List<SNCFStation> stations) {

		Property hasSNFstation = model.createProperty(NsPrefix.getOntoNS() + "hasSNFstation");

		Property hasId = model.createProperty(NsPrefix.getOntoNS() + "hasID");
		Property hasName = model.createProperty(NsPrefix.getOntoNS() + "hasName");
		Property hasLatitude = model.createProperty(NsPrefix.getGeoNS() + "Lat");
		Property hasLongtiude = model.createProperty(NsPrefix.getGeoNS() + "Long");
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
			Resource trainClass = model.createResource(NsPrefix.getSchemaNS() + "SNCFStation");
			trainstation.addProperty(RDF.type, trainClass);
			trainstation.addProperty(hasId, station.getID());
			trainstation.addProperty(hasName, station.getName());
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
		Property hasName = model.createProperty(NsPrefix.getOntoNS() + "hasName");
		Property hasLatitude = model.createProperty(NsPrefix.getGeoNS() + "Lat");
		Property hasLongtiude = model.createProperty(NsPrefix.getGeoNS() + "Long");
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

			Resource busClass = model.createResource(NsPrefix.getSchemaNS() + "busStation");

			busStation.addProperty(RDF.type, busClass);
			busStation.addProperty(hasId, station.getID());
			busStation.addProperty(hasName, station.getName());
			busStation.addProperty(hasLatitude, String.valueOf(station.getLat()), XSDDatatype.XSDdouble);
			busStation.addProperty(hasLongtiude, String.valueOf(station.getLon()), XSDDatatype.XSDdouble);
			busStation.addProperty(hasBusNumber, String.valueOf(station.getBusNumber()));
			busStation.addProperty(hasUpdatedTime, String.valueOf(station.getUpdatedtime()));

			Resource tramClass = model.createResource(NsPrefix.getSchemaNS() + "tramStation");
			tramStation.addProperty(RDF.type, tramClass);
			tramStation.addProperty(hasId, station.getID());
			tramStation.addProperty(hasName, station.getName());
			tramStation.addProperty(hasLatitude, String.valueOf(station.getLat()), XSDDatatype.XSDdouble);
			tramStation.addProperty(hasLongtiude, String.valueOf(station.getLon()), XSDDatatype.XSDdouble);
			tramStation.addProperty(hasTramNumber, String.valueOf(station.getTramNumber()));
			tramStation.addProperty(hasUpdatedTime, String.valueOf(station.getUpdatedtime()));

			Resource metroClass = model.createResource(NsPrefix.getSchemaNS() + "metroStation");
			metroStation.addProperty(RDF.type, metroClass);
			metroStation.addProperty(hasId, station.getID());
			metroStation.addProperty(hasName, station.getName());
			metroStation.addProperty(hasLatitude, String.valueOf(station.getLat()), XSDDatatype.XSDdouble);
			metroStation.addProperty(hasLongtiude, String.valueOf(station.getLon()), XSDDatatype.XSDdouble);
			metroStation.addProperty(hasMetroNumber, String.valueOf(station.getMetroNumber()));
			metroStation.addProperty(hasUpdatedTime, String.valueOf(station.getUpdatedtime()));

		}
	}

	public static void appendBicycletoModel(Resource blank, List<BicycleStation> stations) {

		Property hasBicycleStation = model.createProperty(NsPrefix.getOntoNS() + "hasBicycleStation");
		Property hasName = model.createProperty(NsPrefix.getOntoNS() + "hasName");
		Property hascapacity = model.createProperty(NsPrefix.getOntoNS() + "hascapacity");
		int i = 0;
		for (BicycleStation station : stations) {
			i++;
			Resource bicyclestation = model.createResource(NsPrefix.getOntoNS() + "BicycleStation/" + station.getID());
			Resource bicycleClass = model.createResource(NsPrefix.getSchemaNS() + "bicyclestation");

			bicyclestation.addProperty(RDF.type, bicycleClass);
			blank.addProperty(hasBicycleStation, bicyclestation);
			bicyclestation.addProperty(hasName, station.getName(), "En");
			Statement statement_pcapacity = model.createLiteralStatement(bicyclestation, hascapacity,
					station.getCapacity());
			model.add(statement_pcapacity);
			bicyclestation.addLiteral(model.createProperty(NsPrefix.getGeoNS() + "Lat"), station.getLat());
			bicyclestation.addLiteral(model.createProperty(NsPrefix.getGeoNS() + "Long"), station.getLon());

		}
	}

	public static void appendHospitalstoModel(Resource city, List<Hospital> hospitalList) {
		Resource bHospital = model.createResource();
		Property hashospital = model.createProperty(NsPrefix.getOntoNS() + "hashospital");
		Property hasadresse = model.createProperty(NsPrefix.getOntoNS() + "adresse");
		Property hastel_number = model.createProperty(NsPrefix.getOntoNS() + "tel_number");
		Property hascatagorie = model.createProperty(NsPrefix.getOntoNS() + "catagorie");
		Property hasLatitude = model.createProperty(NsPrefix.getGeoNS() + "Lat");
		Property hasLongitude = model.createProperty(NsPrefix.getGeoNS() + "Long");
		Property instanceofhospital = model.createProperty(NsPrefix.getOntoNS() + "instanceofhospital");
		city.addProperty(hashospital, bHospital);
		int i = 0;
		for (Hospital hos : hospitalList) {
			i++;
			Resource Hospital = model.createResource(NsPrefix.getOntoNS() + "hospital" + i);
			Resource hospClass = model.createResource(NsPrefix.getSchemaNS() + "Hospital");
			Hospital.addProperty(RDF.type, hospClass);
			bHospital.addProperty(instanceofhospital, Hospital);
			Hospital.addProperty(hasadresse, hos.getAdresse());
			Hospital.addProperty(hastel_number, String.valueOf(hos.getTel_number()));
			Hospital.addProperty(hasLatitude, String.valueOf(hos.getLat()), XSDDatatype.XSDdouble);
			Hospital.addProperty(hasLongitude, String.valueOf(hos.getLon()), XSDDatatype.XSDdouble);
			Hospital.addProperty(hascatagorie, String.valueOf(hos.getCatagorie()));
		}
	}

	public static void saveToFueski() {
		try {
			DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(FUESKI_LOCAL_ENDPOINT);
			accessor.putModel(model);
			// OutputStreamWriter writer = new OutputStreamWriter(null, "outTRUE.csv");
			String url = "creatmodelFull.ttl";

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