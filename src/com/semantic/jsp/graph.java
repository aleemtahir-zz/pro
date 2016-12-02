package com.semantic.jsp;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFFormat;


public class graph {
	private RepositoryConnection connection;
	static ArrayList list = new ArrayList();
	static String defaultNameSpace = "http://www.semanticweb.org/tayyab/ontologies/2016/7/untitled-ontology-2#";
	public static void main(String[] args) throws Exception {
		
		//loadQuery();
		getConnectandLoad();
		
        
	}
	
	public graph(RepositoryConnection connection) {
        this.connection = connection;
    }
	
	public static void getConnectandLoad() throws Exception{
		
		HTTPRepository repository = new HTTPRepository("http://localhost:7200/repositories/cricket");
        RepositoryConnection connection = repository.getConnection();
        connection.clear();
        graph test = new graph(connection);
        
        test.loadData();
        connection.close();
	}
	
	public void loadData() throws Exception {
		
        //System.out.println("# Loading ontology and data");
        connection.begin();
        connection.add(graph.class.getResourceAsStream("/cric.rdf"),"urn:base", RDFFormat.RDFXML);
        //connection.add(FamilyRelationsApp.class.getResourceAsStream("/family-data.ttl"), "urn:base", RDFFormat.TURTLE);
        connection.commit();
        System.out.println("# Loading ontology and data");
    }
	
	public static ResultSet execSelectAndPrint(String queryString) {
		List<RowObject> results = new ArrayList<RowObject>();
		
		String endpoint = "http://localhost:7200/repositories/cricket";
		
		Query query = QueryFactory.create(queryString);
		QueryExecution q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		ResultSet rs = q.execSelect();
		
		return rs;
		//ResultSetFormatter.out(System.out, results);
		//int i=0;
		/*while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			RDFNode node = soln.get("count");
			String s = node.toString();
			s = s.replaceAll("..http(.*)", "");
			i = Integer.parseInt(s); 
			System.out.print(i);
			//list.add(x.toString());
		
		}*/
		
		/*while (rs.hasNext()) {
		    RowObject result = new RowObject();
		    QuerySolution binding = rs.nextSolution();
		    String x = binding.get("x").toString();
		    x = x.replaceAll("http://www.semanticweb.org/tayyab/ontologies/2016/7/untitled-ontology-2#", "");
		    String y = binding.get("y").toString();
		    y = y.replaceAll("http://www.semanticweb.org/tayyab/ontologies/2016/7/untitled-ontology-2#", "");
		    result.setSubject(binding.get("x"));
		    result.setObject(binding.get("y"));
		    // ...
		    results.add(result);
		}
		*/
		//return results;
		//return i;
	}
	
}
