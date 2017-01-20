<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">	
<title>Cricmantic</title>
<link href="css/style.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="css/font.css" rel="stylesheet" type="text/css">
<link href="css/normalize.css" rel="stylesheet" type="text/css" />
<link href="css/component.css" rel="stylesheet" type="text/css" />

<style>
h3{
	color: grey;
}

</style>

</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="http://localhost:8080/Pro/">Cricmantic</a>
    </div>
    <div class="collapse navbar-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="http://localhost:8080/Pro/">Home</a></li>
        <li><a href="#about">Player</a></li>
        <li><a href="http://localhost:8080/Pro/teamServlet">Team</a></li>
        <li><a href="http://localhost:8080/Pro/compare.jsp">Comaprison</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>
<br/><br/>
<div class="teamPage">
<h1>Teams</h1>
<br/>
<h3>International</h3>
<div class="teamTable">
<ul class="list-group row cl-effect-1">
<c:forEach items="${teamList}" var="list">
	<strong>
     <li class="list-group-item col-md-4"><a href="teamServlet?param=${list}">${list}</a></li>
     </strong>
</c:forEach>
</ul>

</div>
</div>

<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-submenu.min.js" defer></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<script src="js/jquery.spincrement.min.js"></script>
<script src="js/modernizr.custom.js"></script>
</body>
</html>