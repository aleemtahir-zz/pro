package com.cricmantic.search;
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

@WebServlet("/NLPservlet")
public class NLPservlet extends HttpServlet {
	private NLPsearch N=new NLPsearch();
	String query = null;
	private static final long serialVersionUID = 1L;

	public NLPservlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		if(query!=null)
		{	
			N.clear();
			N.getTokenList(query);
			N.queryConvert();

			ArrayList<Integer> runsList1 = new ArrayList<Integer>();
			ArrayList<Integer> runsList2 = new ArrayList<Integer>();
			ArrayList<Integer> sixList1 = new ArrayList<Integer>();
			ArrayList<Integer> sixList2 = new ArrayList<Integer>();
			ArrayList<Integer> fourList1 = new ArrayList<Integer>();
			ArrayList<Integer> fourList2 = new ArrayList<Integer>();
			try{
				for(ResultTable rt: N.getResult()){
					runsList1.add(Integer.parseInt(rt.getField()[0]));
					runsList1.add(Integer.parseInt(rt.getField()[1]));
					runsList1.add(Integer.parseInt(rt.getField()[2]));
					runsList2.add(Integer.parseInt(rt.getField()[3]));
					runsList2.add(Integer.parseInt(rt.getField()[4]));
					runsList2.add(Integer.parseInt(rt.getField()[5]));
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}

			makeJSON(request, response, runsList1, runsList2);
		}
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		query=request.getParameter("inbox");
		if(query!=null)
		{	
			N.clear();
			N.getTokenList(query);
			N.queryConvert();
			String[] fieldHeadings=new String[NLPsearch.getQueryList().size()];

			for (int i = 0; i < NLPsearch.getQueryList().size(); i++) {
				//request.setAttribute("field" + i, NLPsearch.getQueryList().get(i).replaceAll("\\?", ""));
				fieldHeadings[i]=NLPsearch.getQueryList().get(i).replaceAll("\\?", "");
			}

			//check query for vs token
			String tokenList[] = query.split(" ");
			for(String token: tokenList){
				if(token.equals("vs")){
					request.setAttribute("flag", "true");
					break;
				}
				else
					request.setAttribute("flag", "false");
			}

			////code here but results list has data of players
			request.setAttribute("field", fieldHeadings);
			request.setAttribute("query", query);
			request.setAttribute("count", N.getResult().size());
			request.setAttribute("resultList1", N.getResult());
			request.getRequestDispatcher("searchResult.jsp").forward(request, response);
		}
		return;
	}

	public void makeJSON(HttpServletRequest request, HttpServletResponse response, ArrayList<Integer> list1, ArrayList<Integer> list2) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		JsonObject json = new JsonObject();
		JsonArray runsList1 = new JsonArray();
		JsonArray labelList1 = new JsonArray();
		JsonArray runsList2 = new JsonArray();
		JsonArray labelList2 = new JsonArray();


		for(int i=0; i<list1.size(); i++)
		{
			runsList1.add(list1.get(i));
			/*sixList1.add(list2.get(i));
			fourList1.add(list3.get(i));*/
			runsList2.add(list2.get(i));
			/*sixList2.add(list5.get(i));
			fourList2.add(list6.get(i));*/

		}
		//labelList1.add("player1");
		labelList1.add("Runs");
		labelList1.add("Fours");
		labelList1.add("Sixes");
		/*labelList2.add("player2");
		labelList2.add("Runs");
		labelList2.add("Fours");
		labelList2.add("Sixes");*/


		json.put("list1", runsList1);
		/*json.put("sixList1", sixList1);
		json.put("fourList1", fourList1)*/;
		json.put("list2", runsList2);
		json.put("labels", labelList1);
		/*json.put("sixList2", sixList2);
		json.put("fourList2", fourList2);*/

		out.print(json);
	}
}