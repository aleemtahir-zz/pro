package com.cricmantic.search;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.opencsv.CSVReader;


public class NLPsearch {

	private	static int flag;
	private	static List<ResultTable> resultList = new ArrayList<ResultTable>();
	private	static ArrayList<String> tokenList = new ArrayList<String>();
	private static ArrayList<String> queryList = new ArrayList<String>();


	public static ArrayList<String> getQueryList() {
		return queryList;
	}



	static String sentence = null;
	static String teamInstance[] = new String[10];
	static String playerInstance[] = new String[10];
	static String ns="http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
	static String uri = "demo:<http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";
	static String rdf="rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
	static String QueryString=null;

	static private Map<String, String> mapObject = new HashMap<String, String>();
	static private Map<String, String> mapClass = new HashMap<String, String>();
	static private Map<String, String> mapQuery = new HashMap<String, String>();
	static private Map<String, String> mapVoc = new HashMap<String, String>();

	public static void main(String[] args) throws  IOException 
	{

		String query="afridi score";
		NLPsearch N=new NLPsearch();
		N.clear();
		N.getTokenList(query);
		N.queryConvert();

	}



	NLPsearch() {
		queryList.clear();
		tokenList.clear();
		flag = 0;
		InitializeMap();
		
	/// For adding vocabulary
		mapVoc.put("score", "score"); mapVoc.put("scores", "score"); mapVoc.put("teams", "totalteam");mapVoc.put("team", "totalteam");
		mapVoc.put("match", "match");mapVoc.put("matches", "match");
		mapVoc.put("runs", "score");mapVoc.put("vs", "vs");
		mapVoc.put("players", "totalplayer");mapVoc.put("player", "totalplayer");
		mapVoc.put("total", "total");mapVoc.put("runs", "score");
		mapVoc.put("win", "win");mapVoc.put("winner", "winner");mapVoc.put("wins", "win");mapVoc.put("won", "win");
		mapVoc.put("loss", "loss");mapVoc.put("losser", "loss");mapVoc.put("lost", "loss");
		//For classification of individuals
		mapObject.put("pakistan", "demo:Pak");
		mapObject.put("india", "demo:Ind");mapObject.put("australia", "demo:Aus");mapObject.put("england", "demo:Eng");mapObject.put("ireland", "demo:IRE");
		mapObject.put("newzealand", "demo:NZ");mapObject.put("srilanka", "demo:SL");
		mapObject.put("southafrica", "demo:RSA");mapObject.put("westindies", "demo:WI");
	}

	private void InitializeMap() {
		// TODO Auto-generated method stub
		 QueryString="PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
					"SELECT ?s where { \n"+
					"?s rdf:type demo:Team."+	
					" } ";
			Query query = QueryFactory.create(QueryString);
			String endpoint = "http://localhost:7200/repositories/cricket";

			QueryExecution q = QueryExecutionFactory.sparqlService(endpoint,
					query);
			ResultSet results = q.execSelect();
			String instances=null;
			
			while (results.hasNext()) 
			{
				QuerySolution soln = results.nextSolution();
				
				instances=(soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
				mapObject.put(instances.toLowerCase(),"demo:"+instances);
				mapClass.put("demo:"+instances, "team");
				
				
			}
			QueryString="PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
					"SELECT ?s where { \n"+
					"?s rdf:type demo:Player."+	
					" } ";
			query = QueryFactory.create(QueryString);
			endpoint = "http://localhost:7200/repositories/cricket";

			q = QueryExecutionFactory.sparqlService(endpoint,
					query);
			results = q.execSelect();
			instances=null;
			
			while (results.hasNext()) 
			{
				QuerySolution soln = results.nextSolution();
				
				instances=(soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
				mapObject.put(instances.toLowerCase(),"demo:"+instances);
				mapClass.put("demo:"+instances, "player");
				
			}


	}



	public static Map<String, String> getMapClass() {
		return mapClass;
	}


	public static Map<String, String> getMapObject() {
		return mapObject;
	}

	public static Map<String, String> getMapVoc() {
		return mapVoc;
	}

	public List<ResultTable> getResult() {
		return resultList;
	}

	public void clear() throws IOException {
		queryList.clear();
		tokenList.clear();
		resultList.clear();
		QueryString = null;
		sentence = "";
		flag = 0;
	}

	private static void setFlag(String query) {

		String pattern = "\\?[A-z0-9]*\\s";
		Pattern r = Pattern.compile(pattern);
		String S3="";
		int k=0;
		int count=0;
		Matcher m=null;
		while(query.charAt(k)!='w'||query.charAt(k+1)!='h'||query.charAt(k+2)!='e'||query.charAt(k+3)!='r')
		{
			S3=S3+query.charAt(k);
			k++;
		}
		
		m = r.matcher(S3);
		
		while (m.find())
		{
			queryList.add(m.group(0).replaceAll(" ", ""));
			count++;
		}

		flag=count;       
	}


	public List<ResultTable> queryConvert() {

		parseSentence();

		initializeQuery();	//Set all queries in hash table	
		if(mapQuery.get(sentence)==null)
			queryHandler();

		QueryString = mapQuery.get(sentence);  //get specific query from hash table

		if(QueryString == null){

			ResultTable resultObj = new ResultTable();
			resultObj.setField1(sentence);
			resultList.add(resultObj);
			queryList.add("?Result");
			return resultList;
		}
		System.out.println(QueryString);
		setFlag(QueryString);

		Query query = QueryFactory.create(QueryString);
		String endpoint = "http://localhost:7200/repositories/cricket";

		QueryExecution q = QueryExecutionFactory.sparqlService(endpoint,
				query);
		ResultSet results = q.execSelect();
		int i=0;
		while (results.hasNext()) {
			i=flag;
			ResultTable resultObj = new ResultTable();
			QuerySolution soln = results.nextSolution();
			try {
				resultObj=resultObj.setAllFields(i, soln,queryList);
				resultList.add(resultObj);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultList;
	}

	private void queryHandler() {
		// TODO Auto-generated method stub

		String[] words=sentence.split(" ");
		int j=words.length;
		if(j==3)
		{
			sentence=" "+words[2]+" "+words[1];

		}
		if(j==4)
		{
			sentence=" "+words[3]+" "+words[2]+" "+words[1];
			if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[3]+" "+words[1];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[3]+" "+words[2];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[1]+" "+words[2];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[2]+" "+words[3];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[1]+" "+words[3];
		}
		if(j==5)
		{
			System.out.println(sentence);
			sentence=" "+words[4]+words[3]+" "+words[2]+" "+words[1];
			if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[4]+" "+words[2]+" "+words[1];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[4]+" "+words[3]+" "+words[1];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[3]+" "+words[4]+" "+words[1];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[2]+" "+words[4]+" "+words[1];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[1]+" "+words[4]+" "+words[2];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[4]+" "+words[1]+" "+words[2];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[4]+" "+words[3]+" "+words[2];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[3]+" "+words[4]+" "+words[2];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[1]+" "+words[4]+" "+words[2];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[1]+" "+words[4]+" "+words[3];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[4]+" "+words[1]+" "+words[3];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[4]+" "+words[2]+" "+words[3];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[2]+" "+words[4]+" "+words[3];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[2]+" "+words[4]+" "+words[3];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[1]+" "+words[2]+" "+words[4];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[2]+" "+words[1]+" "+words[4];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[2]+" "+words[3]+" "+words[4];	
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[3]+" "+words[2]+" "+words[4];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[2]+" "+words[1]+" "+words[4];	


		}
		if(j==6)
		{
			sentence=" "+words[5]+" "+words[4]+" "+words[3]+" "+words[2]+" "+words[1];
			if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[5]+" "+words[3]+" "+words[2]+" "+words[1];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[5]+" "+words[4]+" "+words[2]+" "+words[1];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[5]+" "+words[3]+" "+words[4]+" "+words[1];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[5]+" "+words[3]+" "+words[4]+" "+words[2]+" "+words[1];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[5]+" "+words[3]+" "+words[2]+" "+words[4]+" "+words[1];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[5]+" "+words[2]+" "+words[3]+" "+words[1];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[3]+" "+words[5]+" "+words[2]+" "+words[1];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[2]+" "+words[3]+" "+words[5]+" "+words[1];
			////////////
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[5]+" "+words[3]+" "+words[1]+" "+words[2];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[5]+" "+words[4]+" "+words[1]+" "+words[2];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[5]+" "+words[3]+" "+words[4]+" "+words[2];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[5]+" "+words[3]+" "+words[4]+" "+words[1]+" "+words[2];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[5]+" "+words[3]+" "+words[1]+" "+words[4]+" "+words[2];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[5]+" "+words[1]+" "+words[3]+" "+words[2];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[3]+" "+words[5]+" "+words[1]+" "+words[2];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[1]+" "+words[3]+" "+words[5]+" "+words[2];
			//////////
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[5]+" "+words[1]+" "+words[2]+" "+words[3];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[5]+" "+words[4]+" "+words[2]+" "+words[3];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[5]+" "+words[1]+" "+words[4]+" "+words[3];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[5]+" "+words[1]+" "+words[4]+" "+words[2]+" "+words[3];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[5]+" "+words[1]+" "+words[2]+" "+words[4]+" "+words[3];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[5]+" "+words[2]+" "+words[1]+" "+words[3];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[1]+" "+words[5]+" "+words[2]+" "+words[3];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[2]+" "+words[1]+" "+words[5]+" "+words[3];

			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[5]+" "+words[3]+" "+words[2]+" "+words[4];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[5]+" "+words[1]+" "+words[2]+" "+words[4];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[5]+" "+words[3]+" "+words[1]+" "+words[4];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[5]+" "+words[3]+" "+words[1]+" "+words[2]+" "+words[4];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[5]+" "+words[3]+" "+words[2]+" "+words[1]+" "+words[4];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[5]+" "+words[2]+" "+words[3]+" "+words[4];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[3]+" "+words[5]+" "+words[2]+" "+words[4];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[2]+" "+words[3]+" "+words[5]+" "+words[4];

			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[5]+" "+words[3]+" "+words[2]+" "+words[5];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[3]+" "+words[1]+" "+words[4]+" "+words[2]+" "+words[5];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[2]+" "+words[1]+" "+words[3]+" "+words[4]+" "+words[5];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[3]+" "+words[4]+" "+words[2]+" "+words[5];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[1]+" "+words[3]+" "+words[2]+" "+words[4]+" "+words[5];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[1]+" "+words[2]+" "+words[3]+" "+words[5];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[3]+" "+words[1]+" "+words[2]+" "+words[5];
			 if(mapQuery.get(sentence)==null)
				sentence=" "+words[4]+" "+words[2]+" "+words[3]+" "+words[1]+" "+words[5];

		}
		
	}



	public ArrayList<String> getTokenList(String query) throws IOException
	{
		//make log file for user input
		FileWriter out = new FileWriter("E:\\workspace\\Pro\\log.txt",true);
		String s = System.lineSeparator() + "\n";
		out.write(query+s);
		out.close();	

		String[] words=query.split(" ");//splits the string based on string
		for(String w:words){
			tokenList.add(w);
		}
		return tokenList;
	}

	public void parseSentence() {

		sentence = "";
		int j=0;
		int k=0;
		for(int i=0;i<tokenList.size();i++){

			if(getMapVoc().get(tokenList.get(i).toString().toLowerCase()) != null)
				sentence = sentence+" "+getMapVoc().get(tokenList.get(i).toString().toLowerCase());

			if(getMapObject().get(tokenList.get(i).toString().toLowerCase()) != null){
					if(getMapClass().get(getMapObject().get(tokenList.get(i).toString().toLowerCase()))=="player")
						{
						playerInstance[k]=getMapObject().get(tokenList.get(i).toString().toLowerCase());
						sentence = sentence+" "+getMapClass().get(playerInstance[k]);
						k++;
						}
					else
						{
						teamInstance[j] = getMapObject().get(tokenList.get(i).toString().toLowerCase());
						sentence = sentence+" "+getMapClass().get(teamInstance[j]);
						j++;
						}
				
				
				
			}
		}
	}



	public void initializeQuery() {


		mapQuery.put(" team win", "PREFIX "+uri+"\n"+
				"select ?Team ?Match ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:win "+teamInstance[0]+" ."+
				"?Match demo:loss ?t."+
				"?t demo:teamName ?Team. "+
				"?Match demo:matchStatus ?Status"+
				" } ");

		mapQuery.put(" team loss", "PREFIX "+uri+"\n"+
				"select ?Team ?Match ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:loss "+teamInstance[0]+" ."+
				"?Match demo:win ?t."+
				"?t demo:teamName ?Team."+
				"?Match demo:matchStatus ?Status"+
				" } ");
		mapQuery.put(" team win score", "PREFIX "+uri+"\n"+
				"select ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:win "+teamInstance[0]+" ."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+
				" }");
		mapQuery.put(" team loss score", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:loss "+teamInstance[0]+" ."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+
				" }");
		mapQuery.put(" team match team", "PREFIX "+uri+"\n"+
				"SELECT ?Match where { \n"+
				teamInstance[0]+" demo:playedMatch ?Match."+
				teamInstance[1]+" demo:playedMatch ?Match"+
				" } ");
		mapQuery.put(" team match team score", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score where { \n"+
				teamInstance[0]+" demo:playedMatch ?Match."+
				teamInstance[1]+" demo:playedMatch ?Match."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+			
				" }");
		mapQuery.put(" team totalplayer", "PREFIX "+uri+"\n"+
				"SELECT ?Players where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players ."+
				" } ");

		mapQuery.put(" team score", "PREFIX "+uri+"\n"+
				"SELECT (sum(?s) as ?Score ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?o."+
				"?balls demo:ballBatsman ?o."+
				"?balls demo:teamScore ?s."+
				" } ");
		mapQuery.put(" player score", "PREFIX "+uri+"\n"+
				"SELECT (sum(?s) as ?Score ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:playerScore ?s."+
				" } ");
		mapQuery.put(" player score match", "PREFIX "+uri+"\n"+
				"SELECT ?Match (sum(?s) as ?Score ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:playerScore ?s."+
				"?balls demo:instanceHasMatch ?Match"+
				" }group by ?Match ");
		mapQuery.put(" player score match team", "PREFIX "+uri+"\n"+
				"SELECT ?Match (sum(?s) as ?Score ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+" . "+
				teamInstance[0]+" demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?s."+
				"?balls demo:instanceHasMatch ?Match"+
				" }group by ?Match ");
		
		mapQuery.put(" player totalteam", "PREFIX "+uri+"\n"+
				"SELECT ?Team where { \n"+
				playerInstance[0]+" demo:isPlayerOf ?t."+
				"?t demo:teamName ?Team"+
				" }");
		mapQuery.put(" player match", "PREFIX "+uri+"\n"+
				"SELECT ?Match where { \n"+
				playerInstance[0]+" demo:instanceHasMatch ?Match."+
				" }");
		
		
		mapQuery.put(" totalplayer","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?Player where { \n"+
				"?Player rdf:type demo:Player"+
				" } ");
		mapQuery.put(" totalteam","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?Teams where { \n"+
				"?o rdf:type demo:Team."+
				"?o demo:teamName ?Teams"+
				" } ");
		mapQuery.put(" totalteam score", "PREFIX "+uri+"\n"+
				"SELECT ?Teams (sum(?s) as ?Score ) where { \n"+
				"?ball demo:ballBatsman ?player."+
				"?ball demo:teamScore ?s."+
				"?player demo:isPlayerOf ?t."+
				"?t demo:teamName ?Teams"+
				" } group by ?Teams");
		mapQuery.put(" totalplayer score", "PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?Players (sum(?s) as ?Score ) where { \n"+
				"?ball demo:ballBatsman ?Players."+
				"?ball demo:playerScore ?s."+
				" } group by ?Players");

		mapQuery.put(" match","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?Match where { \n"+
				"?Match rdf:type demo:Match"+
				" } ");
		mapQuery.put(" team match score", "PREFIX "+uri+"\n"+
				"SELECT ?Match (sum(?s) as ?Score ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?pl."+
				"?balls demo:ballBatsman ?pl."+
				"?balls demo:teamScore ?s."+
				"?balls demo:instanceHasMatch ?Match"+
				" } group by ?Match ");
		mapQuery.put(" team match score totalteam", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score where { \n"+
				teamInstance[0]+" demo:playedMatch ?Match."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+			
				" }");
		mapQuery.put(" match score totalteam", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score where { \n"+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+			
				" }");
		mapQuery.put(" match totalteam", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team2 where { \n"+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+			
				" }");
		mapQuery.put(" team totalplayer score", "PREFIX "+uri+"\n"+
				"SELECT ?Players (sum(?s) as ?Score ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:playerScore ?s."+
				" } group by ?Players ");
		mapQuery.put(" team totalplayer score match", "PREFIX "+uri+"\n"+
				"SELECT ?Players ?Match (sum(?s) as ?Score ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:playerScore ?s."+
				"?balls demo:instanceHasMatch ?Match"+
				" } group by ?Match ?Players ");
		mapQuery.put(" team match","PREFIX "+uri+"\n"+
				"SELECT ?Match where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match"+
				" } ");
		mapQuery.put(" player vs player","PREFIX "+uri+"\n"+
				"select ?Player1Score ?Player1fours ?Player1sixes ?Player2Score ?Player2sixes ?Player2fours where { \n"+
				"{ select (sum(?s) as ?Player1Score ) where {"+
				"?b demo:ballBatsman "+playerInstance[0] +"."+
				"?b demo:playerScore ?s }"+
				"}"+
				"{ select (count(?s) as ?Player1fours ) where {"+
				"?b demo:ballBatsman "+playerInstance[0] +"."+
				"?b demo:playerScore ?s FILTER(?s=4)}"+
				"}"+
				"{ select (count(?s) as ?Player1sixes ) where {"+
				"?b demo:ballBatsman "+playerInstance[0] +"."+
				"?b demo:playerScore ?s FILTER(?s=6)}"+
				"}"+
				"{ select (sum(?s) as ?Player2Score ) where {"+
				"?b demo:ballBatsman "+playerInstance[1] +"."+
				"?b demo:playerScore ?s}"+
				"}"+
				"{ select (count(?s) as ?Player2fours ) where {"+
				"?b demo:ballBatsman "+playerInstance[1] +"."+
				"?b demo:playerScore ?s FILTER(?s=4)}"+
				"}"+
				"{ select (count(?s) as ?Player2sixes ) where {"+
				"?b demo:ballBatsman "+playerInstance[1] +"."+
				"?b demo:playerScore ?s FILTER(?s=6)}"+
				"}	}");

	}



}
