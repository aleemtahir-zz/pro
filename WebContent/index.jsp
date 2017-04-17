<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Cricmantic</title>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="css/font.css" rel="stylesheet" type="text/css">
<link href="css/normalize.css" rel="stylesheet" type="text/css" />
<link href="css/component.css" rel="stylesheet" type="text/css" />
<style>
body{
  background: linear-gradient( rgba(0,0,0,.5), rgba(0,0,0,.5) ), 
  url('pics/c.jpg') no-repeat center center fixed; 
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  
  height: 100%;
  background-color: #000000;
  color: #fff;
  text-align: center;
  text-shadow: 0 1px 3px rgba(0,0,0,.5);
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
        <li><a href="http://localhost:8080/Pro/matchServlet">Match</a></li>
        <li><a href="http://localhost:8080/Pro/teamServlet">Team</a></li>
        <li><a href="http://localhost:8080/Pro/comparisonServlet">Comaprison</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
      
      <%
                String username= (String) session.getAttribute("user");                     
                if (username == null) {
            %>
      		<li><a href="http://localhost:8080/Pro/login.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>

        <% } else {
         %>
         	<li><a href="http://localhost:8080/Pro/ParseServlet">Add Data</a></li>
            <li><a href="http://localhost:8080/Pro/logout.jsp"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
        <% }%>
    </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>
<br/><br/>
<br/><br/>
<br/><br/>
<div class="container" style="margin-top: 5%;">
    <div class="col-md-6 col-md-offset-3">

        <!-- Search Form -->
        <form action="NLPservlet" method="POST">
        
        <!-- Search Field -->
            <div class="row">
                <h1 class="text-center" style="font-size:50px">Cricmantic</h1>
                <br>
                <div class="form-group">
                    <div class="input-group">
                        <input class="form-control" type="text" name="inbox" placeholder="Search" required/>
                        <span class="input-group-btn">
                            <button class="btn btn-success" type="submit"><span class="glyphicon glyphicon-search" aria-hidden="true"><span style="margin-left:10px; font-family: Calibri,Candara,Segoe,Segoe UI,Optima,Arial,sans-serif;">Search</span></button>
                        </span>
                        </span>
                    </div>
                </div>
            </div>
            
        </form>
        <!-- End of Search Form -->
            
    </div>
</div>
</body>

</html>