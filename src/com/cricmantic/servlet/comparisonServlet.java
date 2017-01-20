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

@WebServlet("/comparisonServlet")
public class comparisonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<String> list = new ArrayList<String>();  
	String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";  
	
    public comparisonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		try{
			String player = request.getParameter("name");
			String flag = request.getParameter("dropdown");
			if(!(player.equals(null))){
				
				JsonObject json = new JsonObject();
				JsonArray list = new JsonArray();

				int i;
				String query;
				
				query = "prefix " + uri +
		                "select (sum(?score) as ?count) where { " + 
						"?ball demo:ballBatsman "+"demo:"+ player + "."  +
		                "?ball demo:playerScore ?score. } ";
						
				i = com.cricmantic.functions.graphQuery.getSum(query);
				list.add(i);
					
				query = "prefix " + uri +
		                "select (count(?ball) as ?count)  where { " +
						"?ball demo:ballBowler ?player."+
						"?ball demo:ballBatsman "+"demo:"+ player + "."  +
						"?ball demo:event ?event."+
		                "FILTER(regex(str(?event), 'SIX')) } ";
				
				i = com.cricmantic.functions.graphQuery.getSum(query);
				list.add(i);
				
				query = "prefix " + uri +
		                "select (count(?ball) as ?count)  where { " +
						"?ball demo:ballBowler ?player."+
						"?ball demo:ballBatsman "+"demo:"+ player + "."  +
						"?ball demo:event ?event."+
		                "FILTER(regex(str(?event), 'FOUR')) } ";
				
				i = com.cricmantic.functions.graphQuery.getSum(query);
				list.add(i);
				
				query = "prefix " + uri +
		                "select (count(?ball) as ?count)  where { " +
						"?ball demo:ballBowler ?player."+
						"?ball demo:ballBowler "+"demo:"+ player + "."  +
						"?ball demo:event ?event."+
		                "FILTER(regex(str(?event), 'OUT')) } ";
				
				i = com.cricmantic.functions.graphQuery.getSum(query);
				list.add(i);
				
				json.put("playerRecord", list);
				json.put("flag", flag);
				out.print(json);
			}
			else{
				loadPage(request, response);}
			}
			catch(Exception e){
				loadPage(request, response);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void loadPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> list1 = new ArrayList<String>();
		
		String param1 = "?team";
		String query = "prefix " + uri +
				"select distinct ?team where { " + 
				"?team demo:hasPlayer ?player .}";
				
		try {
			list1 = com.cricmantic.functions.graphQuery.getOneList(query, param1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("playerList", list1);
		request.getRequestDispatcher("compare.jsp").forward(request, response);
	}

}
