package com.cricmantic.functions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.Update;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
public class InsertRDF {

	public static void main(String[] args) throws IOException {

	}
	public void insertParsedRDF() throws IOException
	{
		Repository repo=initRepository();
		int no=getFileNo();
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\Hamza\\workspace\\pro-master\\pro-master\\Archive\\cricmantic"+ Integer.toString(no) + ".owl");
		String baseURI = "http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
		try {
			RepositoryConnection con = repo.getConnection();
			try {
				con.add(file, baseURI, RDFFormat.RDFXML);
			}
			finally {
				con.close();
				con=null;
			}
		}
		catch (RDF4JException e) {
			// handle exception
		}
		catch (IOException e) {
			// handle io exception
		}
		writeFileNo();
		repo=null;

	}
	public void insertCommentaryRDF() throws IOException
	{
		Repository repo=initRepository();
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\Hamza\\workspace\\pro-master\\pro-master\\Archive\\commentary.rdf");
		String baseURI = "http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
		try {
			RepositoryConnection con = repo.getConnection();
			try {
				con.add(file, baseURI, RDFFormat.RDFXML);
				//      URL url = new URL("http://example.org/example/remote.rdf");
				//     con.add(url, url.toString(), RDFFormat.RDFXML);
			}
			finally {
				con.close();
				con=null;
			}
		}
		catch (RDF4JException e) {
			// handle exception
		}
		catch (IOException e) {
			// handle io exception
		}
		repo=null;
	}
	private static Repository initRepository()
	{
		String rdf4jServer = "http://localhost:7200/";
		String repositoryID = "cricket";
		Repository repo = new HTTPRepository(rdf4jServer, repositoryID);
		repo.initialize();
		return repo;
	}
	private static int getFileNo() throws IOException
	{
		FileWriter out;
		String fileName = "C:\\Users\\Hamza\\workspace\\pro-master\\pro-master\\Archive\\FileNo.txt";
		String content = null;
		FileInputStream fstream = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		// Read File Line By Line
		content = br.readLine();
		int no = Integer.parseInt(content);
		br.close();
		return no;
	}
	private static void writeFileNo() throws IOException
	{
		FileWriter out;
		String fileName = "C:\\Users\\Hamza\\workspace\\pro-master\\pro-master\\Archive\\FileNo.txt";
		String content = null;
		fileName = "C:\\Users\\Hamza\\workspace\\pro-master\\pro-master\\Archive\\FileNo.txt";
		out = new FileWriter(fileName);
		int fileNo=getFileNo();
		fileNo = fileNo + 1;
		System.out.println(fileNo);
		out.write(Integer.toString(fileNo));
		out.close();

	}
	public void deleteData() {
		// TODO Auto-generated method stub
		String endpoint = "http://localhost:7200/repositories/cricket";
		String baseURI = "http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
		String update="PREFIX demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> DELETE DATA{  demo:14960 demo:over 33}"; 

		Repository repo=initRepository();
		try( RepositoryConnection con = repo.getConnection()){
			Update preparedUpdate = con.prepareUpdate(update);
			preparedUpdate.execute();
			con.close();
		}

	}
	public void updateData(String match) {
		// TODO Auto-generated method stub
		String endpoint = "http://localhost:7200/repositories/cricket";
		String rdf = "rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
		String uri="demo:<http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#>";
		String QueryString =null;
		String score1 = null;
		String score2 = null;
		String team1 = null;
		String team2 = null;
		String wik1 = null;
		String wik2 = null;
		String ns = "http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" +
				"SELECT * where { \n" + "demo:"
				+ match + " demo:team1 ?s." +

				" } ";
		Query query = QueryFactory.create(QueryString);
		QueryExecution q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		ResultSet results = q.execSelect();
		QuerySolution soln=null;
		while (results.hasNext()) {
			soln = results.nextSolution();
			team1 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));

		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" +
				"SELECT * where { \n" + "demo:"
				+ match + " demo:team2 ?s." +

				" } ";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			team2 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));

		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"SELECT (sum(?score)as ?s) where { \n"
				+ "demo:" + match + " demo:team1 ?t." + "?t demo:hasPlayer ?player."
				+ "?ball demo:ballBatsman ?player." + "?ball demo:instanceHasMatch demo:" + match + "."
				+ "?ball demo:teamScore ?score." + " } ";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			score1 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));

		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"SELECT (sum(?score)as ?s) where { \n"
				+ "demo:" + match + " demo:team2 ?t." + "?t demo:hasPlayer ?player."
				+ "?ball demo:ballBatsman ?player." + "?ball demo:instanceHasMatch demo:" + match + "."
				+ "?ball demo:teamScore ?score." + " } ";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			score2 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));	
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + "select ( count(  ?e) as ?s ) where {"
				+ "demo:" + match + " demo:team1 ?t." + "?t demo:hasPlayer ?player."
				+ "?ball demo:ballBatsman ?player." + "?ball demo:instanceHasMatch demo:" + match + "."
				+ "?ball demo:event ?e." + " FILTER(?e=\"OUT!\")" + " } ";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			wik1 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));	
		}

		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + "select ( count(  ?e) as ?s ) where {"
				+ "demo:" + match + " demo:team2 ?t." + "?t demo:hasPlayer ?player."
				+ "?ball demo:ballBatsman ?player." + "?ball demo:instanceHasMatch demo:" + match + "."
				+ "?ball demo:event ?e." + " FILTER(?e=\"OUT!\")" + " } ";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			wik2 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));	
		}

		String oldScore1=null;
		String oldScore2=null;
		String oldWiks1=null;
		String oldWiks2=null;
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + "select * where {"
				+ "demo:" + match + " demo:matchTeam1Wickets ?s." +
				"}";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			oldWiks1 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));	
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + "select * where {"
				+ "demo:" + match + " demo:matchTeam2Wickets ?s." +
				"}";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			oldWiks2 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));	
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + "select * where {"
				+ "demo:" + match + " demo:matchTeam1Score ?s." +
				"}";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			oldScore1 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));	
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + "select * where {"
				+ "demo:" + match + " demo:matchTeam2Score ?s." +
				"}";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			oldScore2 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));	
		}

		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + "select ( count(  ?e) as ?s ) where {"
				+ "demo:" + match + " demo:team2 ?t." + "?t demo:hasPlayer ?player."
				+ "?ball demo:ballBatsman ?player." + "?ball demo:instanceHasMatch demo:" + match + "."
				+ "?ball demo:event ?e." + " FILTER(?e=\"OUT!\")" + " } ";
		query = QueryFactory.create(QueryString);
		q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		results = q.execSelect();
		while (results.hasNext()) {
			soln = results.nextSolution();
			wik2 = (soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));	
		}


		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"DELETE DATA {"+
				"demo:"+ match +" demo:matchTeam1Score "+ oldScore1+
				"}";
		Repository repo=initRepository();
		try( RepositoryConnection con = repo.getConnection()){
			Update preparedUpdate = con.prepareUpdate(QueryString);
			preparedUpdate.execute();
			con.close();
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"DELETE DATA {"+
				"demo:"+ match +" demo:matchTeam2Score "+ oldScore2+
				"}";
		try( RepositoryConnection con = repo.getConnection()){
			Update preparedUpdate = con.prepareUpdate(QueryString);
			preparedUpdate.execute();
			con.close();
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"DELETE DATA {"+
				"demo:"+ match +" demo:matchTeam1Wickets "+ oldWiks1+
				"}";
		try( RepositoryConnection con = repo.getConnection()){
			Update preparedUpdate = con.prepareUpdate(QueryString);
			preparedUpdate.execute();
			con.close();
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"DELETE DATA {"+
				"demo:"+ match +" demo:matchTeam2Wickets "+ oldWiks2+
				"}";
		try( RepositoryConnection con = repo.getConnection()){
			Update preparedUpdate = con.prepareUpdate(QueryString);
			preparedUpdate.execute();
			con.close();
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"INSERT DATA {"+
				"demo:"+ match +" demo:matchTeam1Score "+ score1+
				"}";
		try( RepositoryConnection con = repo.getConnection()){
			Update preparedUpdate = con.prepareUpdate(QueryString);
			preparedUpdate.execute();
			con.close();
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"INSERT DATA {"+
				"demo:"+ match +" demo:matchTeam2Score "+ score2+
				"}";
		try( RepositoryConnection con = repo.getConnection()){
			Update preparedUpdate = con.prepareUpdate(QueryString);
			preparedUpdate.execute();
			con.close();
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"INSERT DATA {"+
				"demo:"+ match +" demo:matchTeam1Wickets "+ wik1+
				"}";
		try( RepositoryConnection con = repo.getConnection()){
			Update preparedUpdate = con.prepareUpdate(QueryString);
			preparedUpdate.execute();
			con.close();
		}
		QueryString = "PREFIX " + rdf + "\n" + "PREFIX " + uri + "\n" + 
				"INSERT DATA {"+
				"demo:"+ match +" demo:matchTeam2Wickets "+ wik2+
				"}";
		try( RepositoryConnection con = repo.getConnection()){
			Update preparedUpdate = con.prepareUpdate(QueryString);
			preparedUpdate.execute();
			con.close();
		}


	}

}
