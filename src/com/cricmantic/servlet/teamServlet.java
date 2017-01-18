package com.cricmantic.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cricmantic.functions.RowObject;

/**
 * Servlet implementation class teamServlet
 */
@WebServlet("/teamServlet")
public class teamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";   

    public teamServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		try{
		String TeamName = request.getParameter("param");
		if(!(TeamName.equals(null))){
			ArrayList<String> list1 = new ArrayList<String>();
			String param1 = "?player";
			String query = "prefix " + uri +
					"select distinct "+param1+" where { " + 
					"demo:"+TeamName+" demo:hasPlayer ?player .}";
					
			list1 = com.cricmantic.functions.graphQuery.getOneList(query, param1);
			request.setAttribute("playerList", list1);
			
			int i;
			query = "prefix " + uri +
	                "select (sum(?score) as ?count) where { " + 
					"demo:"+TeamName+" demo:hasPlayer ?player." +
					"?ball demo:ballBatsman ?player."  +
	                "?ball demo:playerScore ?score. } ";
					
			i = com.cricmantic.functions.graphQuery.getSum(query);
			request.setAttribute("runs", i);
			
			query = "prefix " + uri +
	                "select (count(?ball) as ?count) where { " + 
					"demo:"+TeamName+" demo:hasPlayer ?player." +
					"?ball demo:ballBatsman ?player."  +
	                "?ball demo:event ?event."+
	                "FILTER(regex(str(?event), 'SIX')) } ";
					
			i = com.cricmantic.functions.graphQuery.getSum(query);
			request.setAttribute("sixes", i);
			
			query = "prefix " + uri +
	                "select (count(?ball) as ?count) where { " + 
					"demo:"+TeamName+" demo:hasPlayer ?player." +
					"?ball demo:ballBatsman ?player."  +
	                "?ball demo:event ?event."+
	                "FILTER(regex(str(?event), 'FOUR')) } ";
					
			i = com.cricmantic.functions.graphQuery.getSum(query);
			request.setAttribute("fours", i);
			
			query = "prefix " + uri +
	                "select (count(?ball) as ?count) where { " + 
					"demo:"+TeamName+" demo:hasPlayer ?player." +
					"?ball demo:ballBowler ?player."  +
	                "?ball demo:event ?event."+
	                "FILTER(regex(str(?event), 'OUT')) } ";
					
			i = com.cricmantic.functions.graphQuery.getSum(query);
			request.setAttribute("wickets", i);

			request.setAttribute("teamName", TeamName);
			request.getRequestDispatcher("team.jsp").forward(request, response);
		}
		else{
			loadPage(request, response);}
		}
		catch(Exception e){
			loadPage(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		//PlayerName = request.getParameter("textbox");
		//request.setAttribute("playerName",PlayerName);
		//request.getRequestDispatcher("team.jsp").forward(request, response);
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
		
		request.setAttribute("teamList", list1);
		request.getRequestDispatcher("allTeam.jsp").forward(request, response);
	}
	

}
