package com.cricmantic.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jena.atlas.json.JsonObject;

import com.cricmantic.functions.InsertRDF;
import com.cricmantic.parsing.parse;
import com.cricmantic.search.NLPsearch;

@WebServlet("/ParseServlet")
public class ParseServlet extends HttpServlet {
	private String match=null;
	private static final long serialVersionUID = 1L;
	ArrayList<String> list = new ArrayList<String>();
	HttpSession session = null;
	String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";

	public ParseServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		match = request.getParameter("match");
		try{
			
			if(!match.equals(null)){
				session  = request.getSession(true);
				session.setAttribute("match", match);
				
				JsonObject json = new JsonObject();
				json.put("MatchName", match);
				out.print(json);
			}
			else
				loadPage(request, response);
		}
		catch(NullPointerException e){
			loadPage(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");


		PrintWriter out = response.getWriter();

		String query=request.getParameter("inbox");
		if(query != null && match!=null)	
		{	
			parse p=new parse();
			p.addMatch(match);
			p.parseCommentary(query, 3);
			InsertRDF rdf=new InsertRDF();
			rdf.insertCommentaryRDF();
			p=null;
			//rdf.updateData(match);
			rdf=null;

		}
		request.getRequestDispatcher("addData.jsp").forward(request, response);
		return;
	}

	private void loadPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		ArrayList<String> list1 = new ArrayList<String>();

		String param1 = "?match";
		String query = "prefix " + uri + " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "select distinct " + param1 + " where { " + param1 + " rdf:type demo:Match ."
				+ " }";

		try {
			list1 = com.cricmantic.functions.graphQuery.getOneList(query, param1);

		} catch (Exception e) {

			e.printStackTrace();
		}
		//out.println(list1);
		request.setAttribute("MatchList", list1);
		request.getRequestDispatcher("addData.jsp").forward(request, response);
	}
}
