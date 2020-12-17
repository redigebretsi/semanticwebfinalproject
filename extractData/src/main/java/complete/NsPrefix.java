package complete;

public class NsPrefix {

	private static String exNS = "http://www.example.org/";
	private static String geoNS = "https://www.w3.org/2003/01/geo/wgs84_pos#";
	private static String ontoNS = "http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#";
	private static String schemaNS = "http://schema.org/";
	
	private static String rdf = "http://www.w3.org/2000/01/rdf-schema/";

	public static String getExNS() {
		return exNS;
	}

	public static String getGeoNS() {
		return geoNS;
	}

	public static String getOntoNS() {
		return ontoNS;
	}

	public static String getSchemaNS() {
		return schemaNS;
	}

	
	public static String getrdf() {
		return rdf;
	}

}
