package com.cricmantic.parsing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

public class DynamicRules {
	static String QueryString = null;
	static int fileNo = 0;
	static ArrayList<String> tokenList = new ArrayList<String>();
//	static Model model = ModelFactory.createOntologyModel();
	static String ns = "http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
	static String uri = "demo:<http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";
	static String rdf = "rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DynamicRules r = new DynamicRules();
	//	r.read();
		r.inferRules();

	}

	public void read() throws IOException {
		//tokenList.clear();
	
		//String fileName="C:\\Users\\Hamza\\workspace\\pro-master\\pro-master\\Archive\\cricket.owl";
		//model.read(fileName);
	}

	public void inferRules() throws IOException {
		Model model = ModelFactory.createOntologyModel();
		String content = null;
		String fileName = System.getProperty("user.dir")+"\\Archive\\FileNo.txt";
		FileInputStream fstream = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		// Read File Line By Line
		content = br.readLine();
		fileNo = Integer.parseInt(content);
			fileName = System.getProperty("user.dir")+"\\Archive\\cricmantic" + Integer.toString(fileNo)
					+ ".owl";
	
		model.read(fileName);
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" +
				"SELECT * where "
				+ "{ ?s rdf:type demo:Match"
				+ " } ";
		Query query = QueryFactory.create(QueryString);
		
		QueryExecution q = QueryExecutionFactory.create(query, model);
		ResultSet results = q.execSelect();
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			tokenList.add(soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
		}
		String score1 = null;
		String score2 = null;
		String team1 = null;
		String team2 = null;
	//	String team1Name=null;
	//	String team2Name=null;
		String wik1 = null;
		String wik2 = null;
		int i = 0;
		while (i < tokenList.size()) {
			QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" +
					"SELECT * where { \n" + "demo:"
					+ tokenList.get(i) + " demo:team1 ?s." +
				
					" } ";

			query = QueryFactory.create(QueryString);
			q = QueryExecutionFactory.create(query, model);
			results = q.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				team1 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			//	team1Name=(soln.get("?o").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			}
			QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
					"SELECT * where { \n" +
					"demo:"+tokenList.get(i) + " demo:team2 ?s." +
				
					" } ";
			query = QueryFactory.create(QueryString);
			q = QueryExecutionFactory.create(query, model);
			results = q.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				team2 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			//	team2Name=(soln.get("?o").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			}
			QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
					"SELECT (sum(?score)as ?s) where { \n"
					+ "demo:" + tokenList.get(i) + " demo:team1 ?t." + "?t demo:hasPlayer ?player."
					+ "?ball demo:ballBatsman ?player." + "?ball demo:instanceHasMatch demo:" + tokenList.get(i) + "."
					+ "?ball demo:teamScore ?score." + " } ";
			query = QueryFactory.create(QueryString);
			q = QueryExecutionFactory.create(query, model);
			results = q.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				score1 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			}
			QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
			"SELECT (sum(?score)as ?s) where { \n"
					+ "demo:" + tokenList.get(i) + " demo:team2 ?t." + "?t demo:hasPlayer ?player."
					+ "?ball demo:ballBatsman ?player." + "?ball demo:instanceHasMatch demo:" + tokenList.get(i) + "."
					+ "?ball demo:teamScore ?score." + " } ";
			query = QueryFactory.create(QueryString);
			q = QueryExecutionFactory.create(query, model);
			results = q.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				score2 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			}
			QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + "select ( count(  ?e) as ?s ) where {"
					+ "demo:" + tokenList.get(i) + " demo:team1 ?t." + "?t demo:hasPlayer ?player."
					+ "?ball demo:ballBatsman ?player." + "?ball demo:instanceHasMatch demo:" + tokenList.get(i) + "."
					+ "?ball demo:event ?e." + " FILTER(?e=\"OUT!\")" + " } ";
			query = QueryFactory.create(QueryString);
			q = QueryExecutionFactory.create(query, model);
			results = q.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				wik1 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			}
			QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + "select ( count(  ?e) as ?s ) where {"
					+ "demo:" + tokenList.get(i) + " demo:team2 ?t." + "?t demo:hasPlayer ?player."
					+ "?ball demo:ballBatsman ?player." + "?ball demo:instanceHasMatch demo:" + tokenList.get(i) + "."
					+ "?ball demo:event ?e" + " FILTER(?e=\"OUT!\")" + " } ";
			query = QueryFactory.create(QueryString);
			q = QueryExecutionFactory.create(query, model);
			results = q.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				wik2 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			}
			System.out.println(score1);
			System.out.println(score2);
			System.out.println(team1);
			System.out.println(team2);
			System.out.println(wik1);
			System.out.println(wik2);
			Resource class1 = model.getResource(ns + "Match");
			Resource class2 = model.getResource(ns + "Team");
			Resource Match = model.getResource(ns + tokenList.get(i));

			Resource Team1 = model.getResource(ns + team1);
			Resource Team2 = model.getResource(ns + team2);
			Property pr1 = model.getProperty(ns + "matchTeam1Score");
			Property pr2 = model.getProperty(ns + "matchTeam2Score");
			Property pr3 = model.getProperty(ns + "win");
			Property pr4 = model.getProperty(ns + "loss");
			Property pr5 = model.getProperty(ns + "matchTeam1Wickets");
			Property pr6 = model.getProperty(ns + "matchTeam2Wickets");
			Property pr7 = model.getProperty(ns + "matchStatus");
			model.add(Match, RDF.type, class1);
			model.add(Team1, RDF.type, class2);
			model.add(Team2, RDF.type, class2);
			Match.addLiteral(pr1, Integer.parseInt(score1));
			Match.addLiteral(pr2, Integer.parseInt(score2));
			Match.addLiteral(pr5, Integer.parseInt(wik1));
			Match.addLiteral(pr6, Integer.parseInt(wik2));

			if (Integer.parseInt(score1) > Integer.parseInt(score2)) {
				model.add(Match, pr3, Team1);
				model.add(Match, pr4, Team2);
				Match.addLiteral(pr7,
						team1 + " won by " + Integer.toString((10 - Integer.parseInt(wik1))) + " wiks" + " and "
								+ Integer.toString((Integer.parseInt(score1) - (Integer.parseInt(score2)))) + " runs");

			} else if (Integer.parseInt(score1) < Integer.parseInt(score2)) {
				model.add(Match, pr3, Team2);
				model.add(Match, pr4, Team1);
				Match.addLiteral(pr7,
						team2 + " won by " + Integer.toString((10 - Integer.parseInt(wik2))) + " wiks" + " and "
								+ Integer.toString((Integer.parseInt(score2) - (Integer.parseInt(score1)))) + " runs");
			}

			i++;
		}

		FileWriter out = null;
		fileName = System.getProperty("user.dir")+"\\Archive\\cricmantic" + Integer.toString(fileNo)
		+ ".owl";
		out = new FileWriter(fileName);
		model.write(out, "RDF/XML");
		out.close();
		
	}
}
