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

#result li {
	font-weight: bold;
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
        <li><a href="http://localhost:8080/Pro/compare.jsp">Comaprison</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>
<br/><br/>
<div class="teamPage">
<h1>Matches</h1>
<br/>
<h3>Types</h3>
<div class="teamTable">
<ul class="list-group row cl-effect-1">
	<strong>
     <li class="list-group-item col-md-4"><a href="#">ODI</a></li>
     </strong>
     <strong>
     <li class="list-group-item col-md-4"><a href="#">T20</a></li>
     </strong>
     <strong>
     <li class="list-group-item col-md-4"><a href="#">AsiaCup</a></li>
     </strong>
</ul>
</div>
<br><br>
<div class="teamTable">
<ul id="result" class="list-group row cl-effect-1">	
</ul>
</div>
</div>
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-submenu.min.js" defer></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<script src="js/jquery.spincrement.min.js"></script>
<script src="js/modernizr.custom.js"></script>
<script type="text/javascript">

function onClickType(param){
	$.ajax({
	    url: 'matchServlet', // the url you want to send it to
	    data: {
	    	matchType: param
	    },
	    method: 'GET',
        dataType: 'json',
        async:false,
	    success: function(data) {
	    	$('ul#result').html('');
	    	data.list.forEach(function(current) {
	    		$('ul#result').append('<li class="list-group-item col-md-6"><a href="#">' + current + '</li>');	
	    	}); 
	    }
	  });
};



$(document).ready(function () {
	$("li a").on('click', function(e) {
		var match = $(this).html();
		  console.log(match);
		  onClickType(match);
		});
});
</script>
</body>
</html>