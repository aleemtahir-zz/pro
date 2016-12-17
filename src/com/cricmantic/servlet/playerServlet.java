package com.cricmantic.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import com.cricmantic.functions.RowObject;

/**
 * Servlet implementation class playerServlet
 */
@WebServlet("/playerServlet")
public class playerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";
	ArrayList<String> list1 = new ArrayList<String>();
	ArrayList<Integer> list2 = new ArrayList<Integer>();
	static String PlayerName = null;   
	static String PlayerTeam = null;

    public playerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String player = null;
		
		try{
			player = request.getParameter("name");
			if(!(player.equals(null))){
				PlayerName = player;
				getTeam();
				doPost(request, response);
			}
			else{
				getTeam();
				loadPage(request, response);
				
				}
			}
			catch(Exception e){
				getTeam();
				loadPage(request, response);
			}

		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		request.setAttribute("playerName",PlayerName);
		request.setAttribute("playerTeam",PlayerTeam);
		request.getRequestDispatcher("player.jsp").forward(request, response);
	}

	private void loadPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String method = request.getParameter("method");
		if(method.equals("makegraph")){
			makeGraph(request,response);
			}
	    else if (method.equals("makebar")){
		    makeProgressBar(request,response);
	        }
	}

	public void makeProgressBar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String name = request.getParameter("bar");
		String event = request.getParameter("param");
		if(event.equals("runs")){
		String query= null;	
		query = "prefix " + uri +
                "select (sum(?score) as ?count) where { " + 
				"?ball demo:ballBatsman "+"demo:"+ name + "."  +
                "?ball demo:playerScore ?score. } ";
		
		int i = com.cricmantic.functions.graphQuery.getSum(query);
		out.print(i);
		}
		else if(event.equals("4s")){
			String query= null;	
			query = "prefix " + uri +
	                "select (count(?ball) as ?count)  where { " +
					"?ball demo:ballBowler ?player."+
					"?ball demo:ballBatsman "+"demo:"+ name + "."  +
					"?ball demo:event ?event."+
	                "FILTER(regex(str(?event), 'FOUR')) } ";
			
			int i = com.cricmantic.functions.graphQuery.getSum(query);
			out.print(i);
			}
		else if(event.equals("6s")){
			String query= null;	
			query = "prefix " + uri +
	                "select (count(?ball) as ?count)  where { " +
					"?ball demo:ballBowler ?player."+
					"?ball demo:ballBatsman "+"demo:"+ name + "."  +
					"?ball demo:event ?event."+
	                "FILTER(regex(str(?event), 'SIX')) } ";
			
			int i = com.cricmantic.functions.graphQuery.getSum(query);
			out.print(i);
			}
	}
	
	public void makeGraph(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		
		String query= null;
		String param1 = "?team";
		String param2 = "?score";		
		String data = request.getParameter("input");
		if (data.equals("RunsP")){
			param1 = "?player";
			param2 = "?score";
			
			
			query = "prefix " + uri +
					"select ?player (sum(?s) as ?score) where { " + 
					"?ball demo:ballBatsman demo:"+PlayerName +". "+
					"?ball demo:ballBowler ?player . "+
					"?ball demo:playerScore ?s . } "+
					"GROUP BY ?player";
			
		}

		else if(data.equals("WicketsP")){
			param1 = "?player";
			param2 = "?wickets";
			query = "prefix " + uri +
					"select ?player (count(?ball) as ?wickets) where { " + 
					"?ball demo:ballBowler demo:"+PlayerName +". "+
					"?ball demo:ballBatsman ?player. "+
					"?ball demo:event ?event."+
					"FILTER(regex(str(?event), 'OUT')).} "+
					"GROUP BY ?player";
		}
		else if (data.equals("RunsT")){
			param1 = "?team";
			param2 = "?score";
			query = "prefix " + uri +
					"select ?team (sum(?s) as ?score) where { " + 
					"?team demo:hasPlayer ?bowler ."+
					"?ball demo:ballBatsman demo:"+PlayerName +". "+
					"?ball demo:ballBowler ?bowler . "+
					"?ball demo:playerScore ?s . } "+
					"GROUP BY ?team";
			
			}
		
		else if(data.equals("WicketsT")){
			param1 = "?team";
			param2 = "?wickets";
			query = "prefix " + uri +
					"select ?team (count(?ball) as ?wickets) where { " + 
					"?team demo:hasPlayer ?player."+
					"?ball demo:ballBowler demo:"+PlayerName +". "+
					"?ball demo:ballBatsman ?player. "+
					"?ball demo:event ?event."+
					"FILTER(regex(str(?event), 'OUT')).} "+
					"GROUP BY ?team";
		}
		
		try {
			RowObject obj = com.cricmantic.functions.graphQuery.getObject(query, param1, param2);
			list1 = obj.getList1();
			list2 = obj.getList2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		makeJSON(request, response, list1, list2);
	}
	
	public void makeJSON(HttpServletRequest request, HttpServletResponse response, ArrayList<String> list1, ArrayList<Integer> list2) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		JsonObject json = new JsonObject();
		JsonArray xAxis = new JsonArray();
		JsonArray yAxis = new JsonArray();
		
		for(int i=0; i<list1.size(); i++)
		{
			xAxis.add(list2.get(i));
			yAxis.add(list1.get(i));
			
		}
		
		json.put("xAxis", xAxis);
		json.put("yAxis", yAxis);
		out.print(json);
	}
	
	private void getTeam() throws ServletException, IOException
	{
		
		String param1 = "?team";
		String query = "prefix " + uri +
				"select ?team where { " + 
				"?team demo:hasPlayer demo:"+PlayerName +". }";
		try {
			list1 = com.cricmantic.functions.graphQuery.getOneList(query, param1);
			//list1 = obj.getList1();
			PlayerTeam = list1.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(PlayerTeam);
	}
	
	/*public void makeYAxisC2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		ArrayList<String> listT = new ArrayList<String>();
		String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";

		String paramT = null;
		String paramName = "Y2";
		paramT = "?team";
		String queryT = "prefix " + uri +
				"select distinct "+ paramT +" where { " + 
				paramT + " demo:hasPlayer ?bowler ."+
				"?ball demo:ballBatsman demo:"+ testing.PlayerName +" . "+
				"?ball demo:ballBowler ?bowler . } ";
		
		listT = com.semantic.jsp.graphQuery.getParam(queryT, paramT);
		makeJSONData(request,response,listT,paramName);
	}
	
	public void makeYAxisC1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		ArrayList<String> listP = new ArrayList<String>();
		String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";
		String paramP = null;
		String paramName = "Y1";
		paramP = "?player";

		String queryP = "prefix " + uri +
	            "select distinct "+ paramP +" where { " + 
				"?ball demo:ballBatsman demo:"+ testing.PlayerName +" . "+
				"?ball demo:ballBowler "+ paramP +" . } ";

		listP = com.semantic.jsp.graphQuery.getParam(queryP, paramP);
		//ArrayList<String> list = new ArrayList<String>(listP.subList(0, 5));
		makeJSONData(request,response,listP,paramName);
	}
	
	public void makeJSONData(HttpServletRequest request, HttpServletResponse response, ArrayList<String> list, String param) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		
		JsonObject json = new JsonObject();
		JsonArray yAxis = new JsonArray();
		
		for(int i=0; i<list.size(); i++)
		{
			yAxis.add(list.get(i));
		}
		
		json.put(param, yAxis);
		out.print(json);
	}*/

}
