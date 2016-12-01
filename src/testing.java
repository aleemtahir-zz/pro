

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String data = request.getParameter("input");
		int integer = com.semantic.jsp.graph.loadQuery("Sarfraz");
		request.setAttribute("results", integer);
		request.getRequestDispatcher("page.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String name = request.getParameter("textbox").toString();
		
		/*List<RowObject> results = com.semantic.jsp.graph.execSelectAndPrint();
		for(RowObject r: results){
			out.println(r.getSubject());
			out.println(r.getObject());
		}*/
		
		
		request.setAttribute("playerName", name);
		 // Will be available as ${results} in JSP
		request.getRequestDispatcher("page.jsp").forward(request, response);
		
		
	}

}
