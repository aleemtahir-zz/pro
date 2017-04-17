<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<%
      session.invalidate();
      response.sendRedirect("http://localhost:8080/Pro/");
            %>

</html>