package complete;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

	public class wikiRead {
	    
	    private static final String sparqlEndPoint = "https://query.wikidata.org/sparql";
	    public static void main(String args[]) {
	        // Do not forget Prefixes => IMPORTANT !!
	        String wikidataPrefixes = "PREFIX bd: <http://www.bigdata.com/rdf#> PREFIX wikibase: <http://wikiba.se/ontology#>  PREFIX wdt: <http://www.wikidata.org/prop/direct/>  PREFIX wd: <http://www.wikidata.org/entity/> ";
	        // NB : Adapt this query for what you want, this one is an ex query about cat I took on wikidata query service
	        String queryString = wikidataPrefixes + "SELECT DISTINCT ?museumLabel ?museumDescription ?villeId ?villeIdLabel (?villeIdLabel AS ?ville) ?coord ?lat ?lon\r\n"
	        		+ "WHERE\r\n"
	        		+ "{\r\n"
	        		+ "  ?museum wdt:P539 ?museofile.  \r\n"
	        		+ "  ?museum wdt:P131* wd:Q456.\r\n"
	        		+ "  ?museum wdt:P131 ?villeId. \r\n"
	        		+ "  OPTIONAL {?museum wdt:P856 ?link.}   \r\n"
	        		+ "  OPTIONAL {?museum wdt:P625 ?coord .}\r\n"
	        		+ " \r\n"
	        		+ "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"fr\". } \r\n"
	        		+ "}\r\n"
	        		+ "ORDER BY  ?villeIdLabel";
	        QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlEndPoint, queryString);
	        try {
	            ResultSet results = qexec.execSelect();
	            
	            
	            // NB : 
	            // You can use Outputstream (ex ResponseOutputStream of your web service) instead of System.out 
	            ResultSetFormatter.outputAsJSON(System.out, results);


	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	        } finally {
	            qexec.close();
	        }
	    }
	

}
