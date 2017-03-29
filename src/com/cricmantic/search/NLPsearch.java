package com.cricmantic.search;

import java.io.FileWriter;
import java.io.IOException;
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
public class NLPsearch {
	private	static int flag;
	private	static List<ResultTable> resultList = new ArrayList<ResultTable>();
	private	static ArrayList<String> tokenList = new ArrayList<String>();
	private static ArrayList<String> queryList = new ArrayList<String>();
	private static String sentence = null;
	private static String teamInstance[] = new String[5];
	private static String playerInstance[] = new String[5];
	private static String matchInstance[] = new String[5];
	private static String ns="http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
	private static String uri = "demo:<http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";
	private static String rdf="rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
	private static String QueryString=null;
	
	static private Map<String, String> mapObject = new HashMap<String, String>();
	static private Map<String, String> mapClass = new HashMap<String, String>();
	static private Map<String, String> mapQuery = new HashMap<String, String>();
	static private Map<String, String> mapVoc = new HashMap<String, String>();

	public static ArrayList<String> getQueryList() {
		return queryList;
	}

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
		InitializeMap();//initialize for mapping teams and player data from ontology
		/// For adding vocabulary
		mapVoc.put("score", "score"); mapVoc.put("scores", "score"); mapVoc.put("teams", "totalteam");mapVoc.put("team", "totalteam");
		mapVoc.put("match", "match");mapVoc.put("matches", "match");
		mapVoc.put("runs", "score");mapVoc.put("vs", "vs");mapVoc.put("status", "status");
		mapVoc.put("players", "totalplayer");mapVoc.put("player", "totalplayer");
		mapVoc.put("runs", "score");
		mapVoc.put("win", "win");mapVoc.put("winner", "winner");mapVoc.put("wins", "win");mapVoc.put("won", "win");
		mapVoc.put("loss", "loss");mapVoc.put("losser", "loss");mapVoc.put("lost", "loss");
		mapVoc.put("summary", "summary");mapVoc.put("sixes", "six");mapVoc.put("six", "six");
		mapVoc.put("four", "four");mapVoc.put("fours", "four");mapVoc.put("team1", "team1");mapVoc.put("team2", "team2");
		mapVoc.put("ball", "balls");mapVoc.put("balls", "balls");mapVoc.put("detail", "summary");mapVoc.put("details", "summary");
	
		/////////for handling similar queries
		mapObject.put("match score", "match score totalteam");
		mapObject.put("match win", "match status");
		mapObject.put("team win match", "team win");	
		mapObject.put("team match win score", "team win score");
		mapObject.put("team score", "team match score");
		mapObject.put("team team score", "team match team score");
		mapObject.put("player score vs player", "player vs player");
		mapObject.put("player score vs player score", "player vs player");
		mapObject.put("player score six", "player summary");mapObject.put("player score four", "player summary");
		mapObject.put("player score six four", "player summary");mapObject.put("player six four", "player summary");
		mapObject.put("player summary match", "player score match");mapObject.put("team summary", "team totalplayer summary");
		mapObject.put("player four balls match", "player four balls");mapObject.put("player six balls match", "player six balls");
		mapObject.put("player score balls match", "player score balls");mapObject.put("matchInstance score", "matchInstance totalteam score");
		mapObject.put("matchInstance score balls", "matchInstance totalteam score balls");mapObject.put("matchInstance team1 score balls", "matchInstance totalteam score balls");
		mapObject.put("matchInstance score team2 balls totalplayer", "matchInstance score team2 balls");mapObject.put("matchInstance score team1 balls totalplayer", "matchInstance score team1 balls");
		mapObject.put("matchInstance score balls totalplayer", "matchInstance score team1 balls");
		mapObject.put("player score team", "player score match team");
	}

	private void InitializeMap() {
		QueryString="PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?s ?o where { \n"+
				"?s rdf:type demo:Team."+	
				"?s demo:teamName ?o"+
				" } ";
		Query query = QueryFactory.create(QueryString);
		String endpoint = "http://localhost:7200/repositories/cricket";
		QueryExecution q = QueryExecutionFactory.sparqlService(endpoint,query);
		ResultSet results = q.execSelect();
		String instances=null;
		while (results.hasNext()) //For classification of teams
		{
			QuerySolution soln = results.nextSolution();

			instances=(soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			mapObject.put(instances.toLowerCase(),"demo:"+instances);
			mapClass.put("demo:"+instances, "team");
			mapObject.put(soln.get("?o").toString().replaceAll(ns, "").replaceAll("..http(.*)", "").toLowerCase(),"demo:"+instances);

		}
		QueryString="PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?s where { \n"+
				"?s rdf:type demo:Player."+	
				" } ";
		query = QueryFactory.create(QueryString);
		endpoint = "http://localhost:7200/repositories/cricket";
		q = QueryExecutionFactory.sparqlService(endpoint,query);
		results = q.execSelect();
		instances=null;
		while (results.hasNext()) //for classification of players
		{
			QuerySolution soln = results.nextSolution();
			instances=(soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			mapObject.put(instances.toLowerCase(),"demo:"+instances);
			mapClass.put("demo:"+instances, "player");
		}
		QueryString="PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?s where { \n"+
				"?s rdf:type demo:Match."+	
				" } ";
		query = QueryFactory.create(QueryString);
		endpoint = "http://localhost:7200/repositories/cricket";
		q = QueryExecutionFactory.sparqlService(endpoint,query);
		results = q.execSelect();
		instances=null;
		while (results.hasNext()) //for classification of players
		{
			QuerySolution soln = results.nextSolution();
			instances=(soln.get("?s").toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			mapObject.put(instances.toLowerCase(),"demo:"+instances);
			mapClass.put("demo:"+instances, "matchInstance");
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
				resultObj.setAllFields2(i, soln,queryList);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			resultList.add(resultObj);
		}
		return resultList;
	}
	public static int factorial(int n) {
		int fact = 1; // this  will be the result
		for (int i = 1; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}
	public static void Permutation(String[] s)
	{
		// Find length of string and factorial of length
		int n = s.length;
		int fc = factorial(n);
		// Point j to the 2nd position
		int j = 1;
		int flag1=0;
		String sen="";
		// To store position of character to be fixed next.
		// m is used as in index in s[].
		int m = 0;
		// Iterate while permutation count is
		// smaller than n! which fc
		for (int perm_c = 0; perm_c < fc; )
		{
			// Store perm as current permutation
			String[] perm = s;
			// Fix the first position and iterate (n-1)
			// characters upto (n-1)!
			// k is number of iterations for current first
			// character.
			int k = 0;
			while (k != fc/n)
			{
				// Swap jth value till it reaches the end position
				while (j != n-1)
				{
					// Print current permutation
					for(int i=0;i<perm.length;i++)
					{
						sen=sen+" "+perm[i];
					}
					sen=sen.substring(1, sen.length());
					if(mapQuery.get(sen)!=null)
					{
						sentence=sen;
						flag1=1;
						break;
					}
					if(mapObject.get(sen)!=null)
					{
						sentence=mapObject.get(sen);
						flag1=1;
						break;
					}            	
					sen="";
					// Swap perm[j] with next character
					perm= swap(perm,j,j+1);
					// Increment count of permutations for this
					// cycle.
					k++;
					// Increment permutation count
					perm_c++;
					// Increment 'j' to swap with next character
					j++;
				}
				if(flag1==1)
					break;
				// Again point j to the 2nd position
				j = 1;
			}
			if(flag1==1)
				break;
			// Move to next character to be fixed in s[]
			m++;
			// If all characters have been placed at
			if (m == n)
				break;
			// Move next character to first position
			s= swap(s,0,m);
		}
	}
		private static void permute(String[] str, int l, int r)
	{

		if (l == r)
		{
			String sen="";
			for(int i=0;i<str.length;i++)
			{
				sen=sen+" "+str[i];
			}
			sen=sen.substring(1, sen.length());
			if(mapQuery.get(sen)!=null)
			{
				sentence=sen;

			}
			if(mapObject.get(sen)!=null)
			{
				sentence=mapObject.get(sen);
			}
		}
		else
		{
			for (int i = l; i <= r; i++)
			{
				str = swap(str,l,i);
				permute(str, l+1, r);
				str = swap(str,l,i);
			}
		}

	}
	public static String[] swap(String[] a, int i, int j)
	{
		String temp;
		temp = a[i] ;
		a[i] = a[j];
		a[j] = temp;
		return a;
	}
	private void queryHandler() {
		String[] words=sentence.split(" ");
		int n=words.length;
		if(n>2&&n<7)
			Permutation(words);
		else if(n==2)
		{
			permute(words, 0, 1);
		}
	}
	public ArrayList<String> getTokenList(String query) throws IOException
	{	//make log file for user input
		
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
		int l=0;
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
				else if(getMapClass().get(getMapObject().get(tokenList.get(i).toString().toLowerCase()))=="matchInstance")
				{
					matchInstance[l] = getMapObject().get(tokenList.get(i).toString().toLowerCase());
					sentence = sentence+" "+getMapClass().get(matchInstance[l]);
					l++;
				}else
				{
					teamInstance[j] = getMapObject().get(tokenList.get(i).toString().toLowerCase());
					sentence = sentence+" "+getMapClass().get(teamInstance[j]);
					j++;
				}
			}
		}
		if(!sentence.isEmpty())
			sentence=sentence.substring(1, sentence.length());
		
	}
	public void initializeQuery() {
		mapQuery.put("team win", "PREFIX "+uri+"\n"+
				"select ?Team ?Match ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:win "+teamInstance[0]+" ."+
				"?Match demo:loss ?t."+
				"?t demo:teamName ?Team. "+
				"?Match demo:matchStatus ?Status"+
				" } ");
		mapQuery.put("team win team", "PREFIX "+uri+"\n"+
				"select ?Team ?Match ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:win "+teamInstance[0]+" ."+
				"?Match demo:loss "+teamInstance[1]+" ."+
				teamInstance[1]+" demo:teamName ?Team. "+
				"?Match demo:matchStatus ?Status"+
				" } ");
		mapQuery.put("team loss", "PREFIX "+uri+"\n"+
				"select ?Team ?Match ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:loss "+teamInstance[0]+" ."+
				"?Match demo:win ?t."+
				"?t demo:teamName ?Team."+
				"?Match demo:matchStatus ?Status"+
				" } ");
		mapQuery.put("team loss team", "PREFIX "+uri+"\n"+
				"select ?Team ?Match ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:loss "+teamInstance[0]+" ."+
				"?Match demo:win "+teamInstance[1]+" ."+
				teamInstance[1]+" demo:teamName ?Team."+
				"?Match demo:matchStatus ?Status"+
				" } ");
		mapQuery.put("team win score", "PREFIX "+uri+"\n"+
				"select ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:win "+teamInstance[0]+" ."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+
				"?Match demo:matchStatus ?Status"+
				" }");
		mapQuery.put("team win team score", "PREFIX "+uri+"\n"+
				"select ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:win "+teamInstance[0]+" ."+
				"?Match demo:loss "+teamInstance[1]+" ."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+
				"?Match demo:matchStatus ?Status"+
				" }");
		mapQuery.put("team loss score", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:loss "+teamInstance[0]+" ."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+
				"?Match demo:matchStatus ?Status"+
				" }");
		mapQuery.put("team loss team score", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score ?Status where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match ."+
				"?Match demo:loss "+teamInstance[0]+" ."+
				"?Match demo:win "+teamInstance[1]+" ."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+
				"?Match demo:matchStatus ?Status"+
				" }");
		mapQuery.put("team match team", "PREFIX "+uri+"\n"+
				"SELECT ?Match where { \n"+
				teamInstance[0]+" demo:playedMatch ?Match."+
				teamInstance[1]+" demo:playedMatch ?Match"+
				" } ");
		mapQuery.put("team match team score", "PREFIX "+uri+"\n"+
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
		mapQuery.put("team totalplayer", "PREFIX "+uri+"\n"+
				"SELECT ?Players where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players ."+
				" } order by ?Players");
		
		mapQuery.put("player score", "PREFIX "+uri+"\n"+
				"SELECT (sum(?s) as ?Score ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:playerScore ?s. "+
				" } ");
		mapQuery.put("player score balls", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Over ?Score where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:playerScore ?Score. "+
				"?balls demo:over ?Over."+
				"?balls demo:instanceHasMatch ?Match"+
				" } ");
		mapQuery.put("player six", "PREFIX "+uri+"\n"+
				"SELECT (count(?s) as ?Sixes ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:playerScore ?s FILTER(?s=6)"+
				" } ");
		mapQuery.put("player six balls", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Over ?Sixes where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:over ?Over."+
				"?balls demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?Sixes FILTER(?Sixes=6)"+
				" } ");
		mapQuery.put("player four", "PREFIX "+uri+"\n"+
				"SELECT (count(?s) as ?Fours ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:playerScore ?s FILTER(?s=4)"+
				" } ");
		mapQuery.put("player four balls", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Over ?Fours where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:over ?Over."+
				"?balls demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?Fours FILTER(?Fours=4)"+
				" } ");
		mapQuery.put("player score match", "PREFIX "+uri+"\n"+
				"SELECT ?Match (sum(?s) as ?Score ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:playerScore ?s."+
				"?balls demo:instanceHasMatch ?Match"+
				" }group by ?Match ");
		mapQuery.put("player six match", "PREFIX "+uri+"\n"+
				"SELECT ?Match (count(?s) as ?Sixes ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?s FILTER(?s=6)"+
				" }group by ?Match ");
		mapQuery.put("player four match", "PREFIX "+uri+"\n"+
				"SELECT ?Match (count(?s) as ?Fours ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+"."+
				"?balls demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?s FILTER(?s=4)"+
				" }group by ?Match ");
		mapQuery.put("player score match team", "PREFIX "+uri+"\n"+
				"SELECT ?Match (sum(?s) as ?Score ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+" . "+
				teamInstance[0]+" demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?s."+
				"?balls demo:instanceHasMatch ?Match"+
				" }group by ?Match ");
		mapQuery.put("player six match team", "PREFIX "+uri+"\n"+
				"SELECT ?Match (count(?s) as ?Sixes ) where { \n"+
				"?balls demo:ballBatsman "+playerInstance[0]+" . "+
				teamInstance[0]+" demo:instanceHasMatch ?Match."+
				"?balls demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?s FILTER(?s=6)"+
				" }group by ?Match ");
		mapQuery.put("player totalteam", "PREFIX "+uri+"\n"+
				"SELECT ?Team where { \n"+
				playerInstance[0]+" demo:isPlayerOf ?t."+
				"?t demo:teamName ?Team"+
				" }");
		mapQuery.put("player match", "PREFIX "+uri+"\n"+
				"SELECT ?Match where { \n"+
				playerInstance[0]+" demo:instanceHasMatch ?Match."+
				" }");
		mapQuery.put("totalplayer","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all players
				"SELECT ?Player where { \n"+
				"?Player rdf:type demo:Player"+
				" } order by ?Players");
		mapQuery.put("totalplayer totalteam","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?Player ?PlayerTeam where { \n"+
				"?Player rdf:type demo:Player."+
				"?Player demo:isPlayerOf ?t."+
				"?t demo:teamName ?PlayerTeam."+
				" } ");
		mapQuery.put("totalteam","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all teams
				"SELECT ?Teams ?TeamName where { \n"+
				"?Teams rdf:type demo:Team."+
				"?Teams demo:teamName ?TeamName"+
				" } ");
		mapQuery.put("totalteam score", "PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score where { \n"+
				"?Match rdf:type demo:Match."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+
				" }");
		mapQuery.put("totalplayer score", "PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all player score
				"SELECT ?Players (sum(?s) as ?Score ) where { \n"+
				"?ball demo:ballBatsman ?Players."+
				"?ball demo:playerScore ?s."+
				" } group by ?Players");
		mapQuery.put("totalplayer six", "PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all player score
				"SELECT ?Players (count(?s) as ?Sixes ) where { \n"+
				"?ball demo:ballBatsman ?Players."+
				"?ball demo:playerScore ?s FILTER(?s=6)"+
				" } group by ?Players");
		mapQuery.put("totalplayer four", "PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all player score
				"SELECT ?Players (count(?s) as ?Score ) where { \n"+
				"?ball demo:ballBatsman ?Players."+
				"?ball demo:playerScore ?s FILTER(?s=4)"+
				" } group by ?Players");
		
		mapQuery.put("totalplayer match", "PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all player score
				"SELECT ?Players ?Match where { \n"+
				"?Players rdf:type demo:Player."+
				"?Players demo:instanceHasMatch ?Match"+
				" } order by ?Players");
		mapQuery.put("totalplayer match score", "PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all player score
				"SELECT ?Players ?Match (sum(?s) as ?Score ) where { \n"+
				"?Players rdf:type demo:Player."+
				"?Players demo:instanceHasMatch ?Match."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:playerScore ?s."+
				"?balls demo:instanceHasMatch ?Match."+
				" } group by ?Players ?Match");
		mapQuery.put("totalplayer match six", "PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all player score
				"SELECT ?Players ?Match (count(?s) as ?Sixes ) where { \n"+
				"?Players rdf:type demo:Player."+
				"?Players demo:instanceHasMatch ?Match."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?s FILTER(?s=6)"+
				" } group by ?Players ?Match");
		mapQuery.put("totalplayer match four", "PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all player score
				"SELECT ?Players ?Match (sum(?s) as ?Score ) where { \n"+
				"?Players rdf:type demo:Player."+
				"?Players demo:instanceHasMatch ?Match."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?s FILTER(?s=4)"+
				" } group by ?Players ?Match");
		mapQuery.put("match","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+//all matches
				"SELECT ?Match where { \n"+
				"?Match rdf:type demo:Match"+
				" }order by ?Match ");
		mapQuery.put("team match score", "PREFIX "+uri+"\n"+
				"SELECT ?Match (sum(?s) as ?Score ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?pl."+
				"?balls demo:ballBatsman ?pl."+
				"?balls demo:teamScore ?s."+
				"?balls demo:instanceHasMatch ?Match"+
				" } group by ?Match ");
		mapQuery.put("team match score totalteam", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score where { \n"+
				teamInstance[0]+" demo:playedMatch ?Match."+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+			
				" }");
		mapQuery.put("match score totalteam", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score where { \n"+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+			
				" }");
		mapQuery.put("match score win", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team1Score ?Team2 ?Team2Score ?Status where { \n"+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?Match demo:matchTeam1Score ?Team1Score."+
				"?Match demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+	
				"?Match demo:matchStatus ?Status"+
				" }");
		mapQuery.put("match totalteam", "PREFIX "+uri+"\n"+
				"SELECT ?Match ?Team1 ?Team2 where { \n"+
				"?Match demo:team1 ?t1."+
				"?Match demo:team2 ?t2."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+			
				" }");
		mapQuery.put("team totalplayer score", "PREFIX "+uri+"\n"+
				"SELECT ?Players (sum(?s) as ?Score ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:playerScore ?s."+
				" } group by ?Players order by ?Players");
		mapQuery.put("team totalplayer six", "PREFIX "+uri+"\n"+
				"SELECT ?Players (count(?s) as ?Sixes ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:playerScore ?s FILTER(?s=6)"+
				" } group by ?Players order by ?Players");
		mapQuery.put("team totalplayer four", "PREFIX "+uri+"\n"+
				"SELECT ?Players (count(?s) as ?Score ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:playerScore ?s FILTER(?s=4)"+
				" } group by ?Players order by ?Players");
		
		mapQuery.put("team totalplayer score match", "PREFIX "+uri+"\n"+
				"SELECT ?Players ?Match (sum(?s) as ?Score ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:playerScore ?s."+
				"?balls demo:instanceHasMatch ?Match"+
				" } group by ?Match ?Players order by ?Players");
		mapQuery.put("team totalplayer six match", "PREFIX "+uri+"\n"+
				"SELECT ?Players ?Match (count(?s) as ?Sixes ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?s FILTER(?s=6)"+
				" } group by ?Match ?Players order by ?Players");
		mapQuery.put("team totalplayer four match", "PREFIX "+uri+"\n"+
				"SELECT ?Players ?Match (count(?s) as ?Score ) where { \n"+
				teamInstance[0]+" demo:hasPlayer ?Players."+
				"?balls demo:ballBatsman ?Players."+
				"?balls demo:instanceHasMatch ?Match."+
				"?balls demo:playerScore ?s FILTER(?s=4)"+
				" } group by ?Match ?Players order by ?Players");
		mapQuery.put("team match","PREFIX "+uri+"\n"+
				"SELECT ?Match where { \n"+
				teamInstance[0]+" demo:instanceHasMatch ?Match"+
				" } ");
		mapQuery.put("match status","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?Match ?Status where { \n"+
				"?Match rdf:type demo:Match."+
				"?Match demo:matchStatus ?Status"+
				" } ");
		mapQuery.put("match summary","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?Match ?MatchLocation ?MatchDate ?Team1 ?Team1Score ?Team1LossWickets ?Team2 ?Team2Score ?Team2LossWickets ?Status where { \n"+//?MatchLocation ?MatchDate ?Team1 ?Team1Score ?Team1LossWickets ?Team2 ?Team2Score ?Team2LossWickets ?Status
				"?Match rdf:type demo:Match."+
				"?Match demo:matchVenue ?MatchLocation."+
				"?Match demo:matchDate ?MatchDate."+
				"?Match demo:team1  ?t1."+
				"?t1 demo:teamName ?Team1."+
				"?Match demo:team2  ?t2."+
				"?t2 demo:teamName ?Team2."+
				"?Match demo:matchTeam1Score  ?Team1Score."+
				"?Match demo:matchTeam1Wickets  ?Team1LossWickets."+
				"?Match demo:matchTeam2Score  ?Team2Score."+
				"?Match demo:matchTeam2Wickets  ?Team2LossWickets."+
				"?Match demo:matchStatus ?Status"+
				" } ");
		mapQuery.put("player vs player","PREFIX "+uri+"\n"+
				"select ?Player1Score ?Player1Fours ?Player1Sixes ?Player2Score ?Player2Sixes ?Player2Fours where { \n"+
				"{ select (sum(?s) as ?Player1Score ) where {"+
				"?b demo:ballBatsman "+playerInstance[0] +"."+
				"?b demo:playerScore ?s }"+
				"}"+
				"{ select (count(?s) as ?Player1Fours ) where {"+
				"?b demo:ballBatsman "+playerInstance[0] +"."+
				"?b demo:playerScore ?s FILTER(?s=4)}"+
				"}"+
				"{ select (count(?s) as ?Player1Sixes ) where {"+
				"?b demo:ballBatsman "+playerInstance[0] +"."+
				"?b demo:playerScore ?s FILTER(?s=6)}"+
				"}"+
				"{ select (sum(?s) as ?Player2Score ) where {"+
				"?b demo:ballBatsman "+playerInstance[1] +"."+
				"?b demo:playerScore ?s}"+
				"}"+
				"{ select (count(?s) as ?Player2Fours ) where {"+
				"?b demo:ballBatsman "+playerInstance[1] +"."+
				"?b demo:playerScore ?s FILTER(?s=4)}"+
				"}"+
				"{ select (count(?s) as ?Player2Sixes ) where {"+
				"?b demo:ballBatsman "+playerInstance[1] +"."+
				"?b demo:playerScore ?s FILTER(?s=6)}"+
				"}	}");
		mapQuery.put("player summary","PREFIX "+uri+"\n"+
				"select ?PlayerScore ?PlayerFours ?PlayerSixes where { \n"+
				"{ select (sum(?s) as ?PlayerScore ) where {"+
				"?b demo:ballBatsman "+playerInstance[0] +"."+
				"?b demo:playerScore ?s }"+
				"}"+
				"{ select (count(?s) as ?PlayerFours ) where {"+
				"?b demo:ballBatsman "+playerInstance[0] +"."+
				"?b demo:playerScore ?s FILTER(?s=4)}"+
				"}"+
				"{ select (count(?s) as ?PlayerSixes ) where {"+
				"?b demo:ballBatsman "+playerInstance[0] +"."+
				"?b demo:playerScore ?s FILTER(?s=6)}"+
				"}"+
				"}");
		mapQuery.put("totalplayer summary","PREFIX "+uri+"\n"+
				"select ?Player ?PlayerScore ?PlayerFours ?PlayerSixes where { \n"+
				"{ select ?Player (sum(?s) as ?PlayerScore ) where {"+
				"?b demo:ballBatsman ?Player."+
				"?b demo:playerScore ?s }group by ?Player"+
				"}"+
				"{ select ?Player (count(?s) as ?PlayerFours ) where {"+
				"?b demo:ballBatsman ?Player."+
				"?b demo:playerScore ?s FILTER(?s=4)}group by ?Player"+
				"}"+
				"{ select ?Player (count(?s) as ?PlayerSixes ) where {"+
				"?b demo:ballBatsman ?Player."+
				"?b demo:playerScore ?s FILTER(?s=6)}group by ?Player"+
				"}"+
				"}");
		mapQuery.put("team totalplayer summary","PREFIX "+uri+"\n"+
				"select ?Player ?PlayerScore ?PlayerFours ?PlayerSixes where { \n"+
				"?Player demo:isPlayerOf "+teamInstance[0]+"."+
				"{ select ?Player (sum(?s) as ?PlayerScore ) where {"+
				"?b demo:ballBatsman ?Player."+
				"?b demo:playerScore ?s }group by ?Player"+
				"}"+
				"{ select ?Player (count(?s) as ?PlayerFours ) where {"+
				"?b demo:ballBatsman ?Player."+
				"?b demo:playerScore ?s FILTER(?s=4)}group by ?Player"+
				"}"+
				"{ select ?Player (count(?s) as ?PlayerSixes ) where {"+
				"?b demo:ballBatsman ?Player."+
				"?b demo:playerScore ?s FILTER(?s=6)}group by ?Player"+
				"}"+
				"}");
		mapQuery.put("matchInstance status","PREFIX "+rdf+"\n"+"PREFIX "+uri+"\n"+
				"SELECT ?Status where { \n"+
				matchInstance[0]+" demo:matchStatus ?Status"+
				" } ");
		mapQuery.put("matchInstance totalteam","PREFIX "+uri+"\n"+
				"SELECT ?Team1 ?Team2 where { \n"+
				matchInstance[0]+" demo:team1 ?t1."+
				"?t1 demo:teamName ?Team1."+
				matchInstance[0]+" demo:team2 ?t2."+
				"?t2 demo:teamName ?Team2"+
				" } ");
		mapQuery.put("matchInstance totalteam score balls","PREFIX "+uri+"\n"+
				"SELECT ?Team1 ?Team1Over ?Batsman ?Bowler ?Team1Score where { \n"+
				matchInstance[0]+" demo:team1 ?t1."+
				"?t1 demo:teamName ?Team1."+
				"?t1 demo:hasPlayer ?Batsman."+
				"?balls demo:ballBatsman ?Batsman."+
				"?balls demo:ballBowler ?Bowler."+
				"?balls demo:instanceHasMatch "+matchInstance[0]+"."+
				"?balls demo:over ?Team1Over."+
				"?balls demo:teamScore ?Team1Score."+
				" } order by ?Team1Over");
		mapQuery.put("matchInstance team2 score balls","PREFIX "+uri+"\n"+
				"SELECT ?Team2 ?Team2Over ?Batsman ?Bowler ?Team2Score where { \n"+
				matchInstance[0]+" demo:team2 ?t1."+
				"?t1 demo:teamName ?Team2."+
				"?t1 demo:hasPlayer ?Batsman."+
				"?balls demo:ballBatsman ?Batsman."+
				"?balls demo:ballBowler ?Bowler."+
				"?balls demo:instanceHasMatch "+matchInstance[0]+"."+
				"?balls demo:over ?Team2Over."+
				"?balls demo:teamScore ?Team2Score."+
				" } order by ?Team2Over");
		mapQuery.put("matchInstance totalteam score","PREFIX "+uri+"\n"+
				"SELECT ?Team1 ?Team1Score ?Team2 ?Team2Score where { \n"+
				matchInstance[0]+" demo:team1 ?t1."+
				matchInstance[0]+" demo:matchTeam1Score ?Team1Score."+
				"?t1 demo:teamName ?Team1."+
				matchInstance[0]+" demo:team2 ?t2."+
				matchInstance[0]+" demo:matchTeam2Score ?Team2Score."+
				"?t2 demo:teamName ?Team2"+
				" } ");
		
		mapQuery.put("matchInstance team1","PREFIX "+uri+"\n"+
				"SELECT ?Team1 where { \n"+
				matchInstance[0]+" demo:team1 ?t1."+
				"?t1 demo:teamName ?Team1."+
				" } ");
		mapQuery.put("matchInstance team2","PREFIX "+uri+"\n"+
				"SELECT ?Team2 where { \n"+
				matchInstance[0]+" demo:team2 ?t1."+
				"?t1 demo:teamName ?Team2."+
				" } ");
		mapQuery.put("matchInstance team2 score","PREFIX "+uri+"\n"+
				"SELECT ?Team2 ?Team2Score where { \n"+
				matchInstance[0]+" demo:team2 ?t1."+
				matchInstance[0]+" demo:matchTeam2Score ?Team2Score."+
				"?t1 demo:teamName ?Team2."+
				" } ");
		mapQuery.put("matchInstance team1 score","PREFIX "+uri+"\n"+
				"SELECT ?Team1 ?Team1Score where { \n"+
				matchInstance[0]+" demo:team1 ?t1."+
				matchInstance[0]+" demo:matchTeam1Score ?Team1Score."+
				"?t1 demo:teamName ?Team1."+
				" } ");
		mapQuery.put("matchInstance team1 totalplayer","PREFIX "+uri+"\n"+
				"SELECT ?Team1 ?Team1Players where { \n"+
				matchInstance[0]+" demo:team1 ?t1."+
				"?t1 demo:hasPlayer ?Team1Players."+
				"?Team1Players demo:instanceHasMatch "+matchInstance[0]+"."+
				"?t1 demo:teamName ?Team1."+
				" } ");
		mapQuery.put("matchInstance team2 totalplayer","PREFIX "+uri+"\n"+
				"SELECT ?Team2 ?Team2Players where { \n"+
				matchInstance[0]+" demo:team2 ?t1."+
				"?t1 demo:hasPlayer ?Team2Players."+
				"?Team2Players demo:instanceHasMatch "+matchInstance[0]+"."+
				"?t1 demo:teamName ?Team2."+
				" } ");
		mapQuery.put("matchInstance team2 totalplayer score","PREFIX "+uri+"\n"+
				"SELECT ?Team2 ?Team2Players (sum(?s) as ?PlayerScore ) where { \n"+
				matchInstance[0]+" demo:team2 ?t1."+
				"?t1 demo:hasPlayer ?Team2Players."+
				"?Team2Players demo:instanceHasMatch "+matchInstance[0]+"."+
				"?t1 demo:teamName ?Team2."+
				"?balls demo:ballBatsman ?Team2Players."+
				"?balls demo:instanceHasMatch "+matchInstance[0]+"."+
				"?balls demo:playerScore ?s."+
				" } group by ?Team2 ?Team2Players");
				
		mapQuery.put("matchInstance team1 totalplayer score","PREFIX "+uri+"\n"+
				"SELECT ?Team1 ?Team1Players (sum(?s) as ?PlayerScore ) where { \n"+
				matchInstance[0]+" demo:team1 ?t1."+
				"?t1 demo:hasPlayer ?Team1Players."+
				"?Team1Players demo:instanceHasMatch "+matchInstance[0]+"."+
				"?t1 demo:teamName ?Team1."+
				"?balls demo:ballBatsman ?Team1Players."+
				"?balls demo:instanceHasMatch "+matchInstance[0]+"."+
				"?balls demo:playerScore ?s."+
				" } group by ?Team1 ?Team1Players");
		mapQuery.put("matchInstance win","PREFIX "+uri+"\n"+
				"SELECT ?Status where { \n"+
				matchInstance[0]+" demo:matchStatus ?Status."+
				" }");
		mapQuery.put("matchInstance summary","PREFIX "+uri+"\n"+
				"SELECT ?Date ?Location ?Team1 ?Team1Score ?Team1LossWickets ?Team2 ?Team2Score  ?Team2LossWickets ?Status where { \n"+
				matchInstance[0]+" demo:team1 ?t1."+
				matchInstance[0]+" demo:team2 ?t2."+
				"?t1 demo:teamName ?Team1."+
				"?t2 demo:teamName ?Team2."+
				matchInstance[0]+" demo:matchTeam1Score ?Team1Score."+
				matchInstance[0]+" demo:matchStatus ?Status."+
				matchInstance[0]+" demo:matchTeam2Score ?Team2Score."+
				matchInstance[0]+" demo:matchDate ?Date."+
				matchInstance[0]+" demo:matchVenue ?Location."+
				matchInstance[0]+" demo:matchTeam1Wickets  ?Team1LossWickets."+
				matchInstance[0]+" demo:matchTeam2Wickets  ?Team2LossWickets."+
				" }");
				
	}
}
