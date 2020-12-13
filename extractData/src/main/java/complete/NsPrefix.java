package complete;

public class NsPrefix {

	private static String exNS = "http://www.example.org/";
	private static String geoNS = "https://www.w3.org/2003/01/geo/wgs84_pos#";
	private static String ontoNS = "http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#";
	private static String schemaNS = "http://schema.org/";
	private static String cityURIPrefix = "https://schema.org/City";
	private static String hospitalURIPrefix = "https://schema.org/Hospital";
	private static String stationsURIPrefix = "https://schema.org/Station";
	;

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

	public static String getCityURIPrefix() {
		return cityURIPrefix;
	}

	public static String getHospitalURIPrefix() {
		return hospitalURIPrefix;
	}

	public static String getStationsURIPrefix() {
		return stationsURIPrefix;
	}

}
