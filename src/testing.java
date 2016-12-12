

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.atlas.json.JsonValue;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;


@WebServlet("/testing")
public class testing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String PlayerName = null;

    public testing() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String method = request.getParameter("method");
		if(method.equals("makegraph")){
			makeGraph(request,response);
			}
	    else if (method.equals("makebar")){
		    makeProgressBar(request,response);
	        }
	    else if (method.equals("makeYAxisC2")){
	    	makeYAxisC2(request,response);
		    }
	    else if (method.equals("makeYAxisC1")){
	    	makeYAxisC1(request,response);
		    }
		/*else {
		    throw new IllegalArgumentException("'method' parameter required, must be 'methodA' or 'methodB' !");
		  }*/
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		PlayerName = request.getParameter("textbox");
		request.setAttribute("playerName",PlayerName);
		request.getRequestDispatcher("page.jsp").forward(request, response);
	}


	public void makeProgressBar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String name = request.getParameter("bar");
		String query= null;	
		String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";
		query = "prefix " + uri +
                "select (sum(?score) as ?count) where { " + 
				"?ball demo:ballBatsman "+"demo:"+ name + "."  +
                "?ball demo:playerScore ?score. } ";
		
		int i = com.semantic.jsp.graphQuery.getSum(query);
		out.print(i);
	}
	
	public void makeGraph(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String player = PlayerName;
		String query= null;
		
		String uri = "demo: <http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#> ";
		String data = request.getParameter("input");
		if (data.equals("RunsP")){
				query = "prefix " + uri +
                "select (sum(?score) as ?count) where { " + 
				"demo:Ind demo:hasPlayer ?bowler." +
				"?ball demo:ballBatsman "+"demo:"+player+"."+
				"?ball demo:ballBowler ?bowler."+
                "?ball demo:playerScore ?score. } ";
		}

		else if(data.equals("WicketsP")){
			query = "PREFIX demo:<http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#>"+
					"SELECT (count(distinct ?ball ) as ?count) WHERE {"+ 
					"?ball demo:ballBowler demo:"+ player +"." +
					"?ball demo:event ?event."+
					"FILTER(regex(str(?event), 'OUT')).}"+
					"GROUP BY ?event ";
		}
		else if (data.equals("RunsT")){
			query = "prefix " + uri +
		            "select (sum(?score) as ?count) where { " + 
					"demo:Ind demo:hasPlayer ?bowler." +
					"?ball demo:ballBatsman "+"demo:"+player+"."+
					"?ball demo:ballBowler ?bowler."+
		            "?ball demo:playerScore ?score. } ";
			}
		
		else if(data.equals("WicketsT")){
			query = "PREFIX demo:<http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#>"+
					"SELECT (count(distinct ?ball ) as ?count) WHERE {"+ 
					"?ball demo:ballBowler demo:"+ player +"." +
					"?ball demo:event ?event."+
					"FILTER(regex(str(?event), 'OUT')).}"+
					"GROUP BY ?event ";
		}
		int result = com.semantic.jsp.graphQuery.getSum(query);
		
		makeJSON(request,response,result);
	}
	
	public void makeJSON(HttpServletRequest request, HttpServletResponse response, int data) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		JsonObject json = new JsonObject();
		JsonArray xAxis = new JsonArray();
		xAxis.add(data);
		json.put("xAxis", xAxis);
		out.print(json);
	}
	
	public void makeYAxisC2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
		ArrayList<String> list = new ArrayList<String>(listP.subList(0, 5));
		makeJSONData(request,response,list,paramName);
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
	}
}
