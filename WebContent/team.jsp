<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">	
<link rel="stylesheet" href="style.css">
<title>Cricmantic</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<link href="dist/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script src="dist/jquery-3.1.0.js"></script>
<script src="dist/bootstrap.min.js"></script>
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
        <li><a href="http://localhost:8080/Pro/comparisonServlet">Comaprison</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>
<br/><br/>
<div class="teamPage">
<h1>${teamName}</h1>
<br/>

<h3>Players</h3>
<div class="teamTable">
<ul class="list-group row">
<c:forEach items="${playerList}" var="list">
	<strong>
     <li class="list-group-item col-md-4"><a href="playerServlet?name=${list}">${list}</a></li>
     </strong>
</c:forEach>
</ul>
</div>
</div>
</body>
</html>