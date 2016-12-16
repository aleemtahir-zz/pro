package com.cricmantic.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.http.HTTPRepository;

@WebServlet("/commentaryServlet")
public class commentaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String NameSpace = "http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
    public commentaryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String subject = request.getParameter("subject");
		String predicate = request.getParameter("predicate");
		String object = request.getParameter("object");

		

		String update = String.format(
                "PREFIX demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#>" +
                        "INSERT DATA { " +
                        "demo:"+subject+" demo:"+predicate+" demo:"+object +
                        " }");
		com.cricmantic.functions.graph.executeUpdate(update);
		//out.print(update+"      Data is inserted!");
		request.getRequestDispatcher("index.html").forward(request, response);
	}

}
