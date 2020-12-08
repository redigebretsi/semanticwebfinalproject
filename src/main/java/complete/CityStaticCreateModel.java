package complete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import complete.NsPrefix;


public class CityStaticCreateModel {
	
	private static Model model;
	private static final String FUESKI_LOCAL_ENDPOINT = "http://localhost:3030/city";

	static String cityURIPrefix;
	static String hasaURIPrefix;
	static String hospitalURIPrefix;
	static String stationsURIPrefix;
	
	
	public static void main(String[] args) {
		initializeModel();
		//
		Resource city = model.createResource(NsPrefix.getCityURIPrefix() + "Lyon");
		Property hasa= model.createProperty(NsPrefix.getExNS() + "hasa");
		Property station = model.createProperty(NsPrefix.getOntoNS() + "Station");
		Property hospital = model.createProperty(NsPrefix.getHospitalURIPrefix() + "Hospital");
        //create
        city.addProperty(RDF.type,"City");
        city.addProperty(hasa,station);
        city.addProperty(hasa,hospital);
        //saveToFueski();
        model.write(System.out, "turtle");
        
	}
	private static void initializeModel() {
		// TODO Auto-generated method stub
		model = ModelFactory.createDefaultModel();
		model.setNsPrefix("city", NsPrefix.getCityURIPrefix());
		model.setNsPrefix("hospital",NsPrefix.getHospitalURIPrefix());
		model.setNsPrefix("station", NsPrefix.getStationsURIPrefix());
		
	}
	public static void saveToFueski() {
		try {
			DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(FUESKI_LOCAL_ENDPOINT);
			accessor.putModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
}
}
