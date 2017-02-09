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


@WebServlet("/matchServlet")
public class matchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";
	
    public matchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		try{
		String MatchType = request.getParameter("matchType");
		if(!(MatchType.equals(null))){
			ArrayList<String> list1 = new ArrayList<String>();
			String param1 = "?match";
			String query = "prefix " + uri +
					" PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
					"select distinct "+param1+" where { " + 
					param1 + " rdf:type demo:Match ."+
					"FILTER(regex(str("+ param1 +"), '"+ MatchType +"')). }";
					
			list1 = com.cricmantic.functions.graphQuery.getOneList(query, param1);
			
			JsonObject json = new JsonObject();
			JsonArray arraylist = new JsonArray();
			
			for(int i=0; i<list1.size(); i++)
			{
				arraylist.add(list1.get(i));
			}
			
			json.put("list", arraylist);
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
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		//PlayerName = request.getParameter("textbox");
		//request.setAttribute("playerName",PlayerName);
		//request.getRequestDispatcher("team.jsp").forward(request, response);
	}
	
	private void loadPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> list1 = new ArrayList<String>();
		
		/*String param1 = "?team";
		String query = "prefix " + uri +
				"select distinct ?team where { " + 
				"?team demo:hasPlayer ?player .}";
				
		try {
			list1 = com.cricmantic.functions.graphQuery.getOneList(query, param1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//request.setAttribute("teamList", list1);
		request.getRequestDispatcher("match.jsp").forward(request, response);
	}

}
