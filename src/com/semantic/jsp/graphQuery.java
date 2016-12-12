package com.semantic.jsp;

import java.util.ArrayList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

public class graphQuery {
	
	static ArrayList<String> list = new ArrayList<String>();
	static String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";
	
	public static void main(String[] args) throws Exception {
		String param = null;
		String query = "prefix " + uri +
				"select distinct ?player where { " + 
				"?ball demo:ballBatsman demo:Sarfraz . "+
				"?ball demo:ballBowler ?player . } ";
		//param = "?team";
		param = "?player";
		ArrayList<String> lists = getParam(query,param);
		System.out.println(lists);
	}
	
	public static int getSum(String queryString) {
		
		String endpoint = "http://localhost:7200/repositories/cricket";
		
		Query query = QueryFactory.create(queryString);
		QueryExecution q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		ResultSet rs = q.execSelect();

		//ResultSetFormatter.out(System.out, results);
		int i=0;
		while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			RDFNode node = soln.get("count");
			String s = node.toString();
			s = s.replaceAll("..http(.*)", "");
			i = Integer.parseInt(s); 
			//System.out.print(i);
			//list.add(x.toString());
		
		}

		return i;
	}
	
public static ArrayList<String> getParam(String queryString, String param) {
		
		String endpoint = "http://localhost:7200/repositories/cricket";
		
		Query query = QueryFactory.create(queryString);
		QueryExecution q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		ResultSet rs = q.execSelect();

		//ResultSetFormatter.out(System.out, results);
		list.clear();
		while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			RDFNode node = soln.get(param);
			String s = node.toString();
			s = s.replaceAll("http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#", "");
			//System.out.print(i);
			list.add(s.toString());
		
		}

		return list;
	}
}
