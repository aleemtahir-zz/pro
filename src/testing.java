

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import com.semantic.jsp.RowObject;

/**
 * Servlet implementation class testing
 */
@WebServlet("/testing")
public class testing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
    public testing() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		makeGraph(request,response);
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		//String name = request.getParameter("textbox").toString();
		
		/*List<RowObject> results = com.semantic.jsp.graph.execSelectAndPrint();
		for(RowObject r: results){
			out.println(r.getSubject());
			out.println(r.getObject());
		}*/
		
		//doGet(request,response);
		/*int integer = 0; 
		String query= null;
		String player = "Sarfraz";	
		String uri = "demo: <http://www.semanticweb.org/tayyab/ontologies/2016/7/untitled-ontology-2#> ";
		String data = request.getParameter("input");
		query = "prefix " + uri +
                "select (sum(?score) as ?count) where { " + 
				"demo:Ind demo:hasPlayer ?bowler." +
				"?ball demo:ballBatsman demo:"+ player +"." +
				"?ball demo:ballBowler ?bowler." +
                " ?ball demo:teamScoreIn1stInnings ?score. } ";
		integer = com.semantic.jsp.graph.execSelectAndPrint(query);
		request.setAttribute("results", integer);*/
		request.getRequestDispatcher("page.jsp").forward(request, response);
	}

	public void makeGraph(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		int integer = 0; 
		String query= null;
		String player = "Sarfraz";	
		String uri = "demo: <http://www.semanticweb.org/tayyab/ontologies/2016/7/untitled-ontology-2#> ";
		String data = request.getParameter("input");
		if (data.equals("Runs")){
			query = "prefix " + uri +
                "select (sum(?score) as ?count) where { " + 
				"demo:Ind demo:hasPlayer ?bowler." +
				"?ball demo:ballBatsman demo:"+ player +"." +
				"?ball demo:ballBowler ?bowler." +
                " ?ball demo:teamScoreIn1stInnings ?score. } ";
		}
		else if(data.equals("Highest")){
			query = "prefix " + uri +
	                "select (sum(?score) as ?count) where { " + 
					"demo:Ind demo:hasPlayer ?bowler." +
					"?ball demo:ballBatsman demo:Sami." +
					"?ball demo:ballBowler ?bowler." +
	                " ?ball demo:teamScoreIn1stInnings ?score. } ";
		}
		else if(data.equals("100s")){
			query = "prefix " + uri +
	                "select (sum(?score) as ?count) where { " + 
					"demo:Ind demo:hasPlayer ?bowler." +
					"?ball demo:ballBatsman demo:Sami." +
					"?ball demo:ballBowler ?bowler." +
	                " ?ball demo:teamScoreIn1stInnings ?score. } ";
		}
		else if(data.equals("50s")){
			query = "prefix " + uri +
	                "select (sum(?score) as ?count) where { " + 
					"demo:Ind demo:hasPlayer ?bowler." +
					"?ball demo:ballBatsman demo:Sami." +
					"?ball demo:ballBowler ?bowler." +
	                " ?ball demo:teamScoreIn1stInnings ?score. } ";
		}
		else if(data.equals("Wickets")){
			query = "prefix " + uri +
	                "select (sum(?score) as ?count) where { " + 
					"demo:Ind demo:hasPlayer ?bowler." +
					"?ball demo:ballBatsman demo:Sami." +
					"?ball demo:ballBowler ?bowler." +
	                " ?ball demo:teamScoreIn1stInnings ?score. } ";
		}
		integer = com.semantic.jsp.graph.execSelectAndPrint(query);
		
		JsonObject json = new JsonObject();
		JsonArray yAxis = new JsonArray();
		yAxis.add("India");
		yAxis.add("Pakistan");
		yAxis.add("Australia");
		yAxis.add("England");
		yAxis.add("Bangladesh");
		json.put("yAxis", yAxis);
		JsonArray xAxis = new JsonArray();
		xAxis.add(integer);
		json.put("yAxis", yAxis);
		json.put("xAxis", xAxis);
		out.print(json);
	}
}
