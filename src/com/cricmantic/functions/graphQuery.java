package com.cricmantic.functions;

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
		/*String param = null;
		String query = "prefix " + uri +
				"select distinct ?player where { " + 
				"?ball demo:ballBatsman demo:Sarfraz . "+
				"?ball demo:ballBowler ?player . } ";
		//param = "?team";
		param = "?player";
		ArrayList<String> lists = getParam(query,param);
		System.out.println(lists);*/
		
		String param1 = "?player";
		String param2 = "?score";
		
		String query = "prefix " + uri +
				"select ?player (sum(?s) as ?score) where { " + 
				"?ball demo:ballBatsman demo:Sarfraz . "+
				"?ball demo:ballBowler ?player . "+
				"?ball demo:playerScore ?s . } "+
				"GROUP BY ?player";
		RowObject obj = getObject(query, param1, param2);
		System.out.println(obj.getList1());
		System.out.println(obj.getList2());
		
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
	
public static ArrayList<String> getOneList(String queryString, String param) {
		
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


public static RowObject getObject(String queryString, String param1, String param2) throws Exception {
	
	String endpoint = "http://localhost:7200/repositories/cricket";
	
	Query query = QueryFactory.create(queryString);
	QueryExecution q = QueryExecutionFactory.sparqlService(endpoint,
			query);
	ResultSet rs = q.execSelect();
	//ResultSetFormatter.out(System.out, results);
	list.clear();
	
	RowObject playerScore = new RowObject();
	ArrayList<String> list1 = new ArrayList<String>();
	ArrayList<Integer> list2 = new ArrayList<Integer>();
	int i=0;
	while (rs.hasNext()) {
		
		 
		QuerySolution binding = rs.nextSolution();
	    String x = binding.get(param1).toString();
	    x = x.replaceAll("http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#", "");
	    list1.add(x);
		
	    String y = binding.get(param2).toString();
	    y = y.replaceAll("..http(.*)", "");
	    int score = Integer.parseInt(y);
	    list2.add(score);
	    i++; 
			 
		
	}
	
	playerScore.setList1(list1);
	playerScore.setList2(list2);
	
	return playerScore;
}

}
