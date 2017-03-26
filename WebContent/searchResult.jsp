<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html xmlns="http://www.w3.org/1999/xhtml"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cricmantic</title>
<meta charset="utf-8">
<link href="css/style.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="css/font.css" rel="stylesheet" type="text/css">
<link href="css/normalize.css" rel="stylesheet" type="text/css" />
<link href="css/component.css" rel="stylesheet" type="text/css" />
</script>
</head>

<style>
#usr
{
	margin-top:20px;
	
	width: 880px;
	height: 48px;
	margin-left: 200px;	

}
placeholder{
	font-family="times new roman";
	font-size: 14px;}

#buton
{
	margin-top:20px;
	margin-left:0px;
	margin-right:250px;
}

.table 
{
	border:2px;
	
	
}


</style>


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
    </div><!--/.nav-collapse -->
  </div>
</div>
<br/><br/>
<br/><br/>
<br/><br/>

<form action="NLPservlet" method="POST">
<div class="input-group">
   <input type="text" id="usr" name="inbox" value='${query}' class="form-control" placeholder= "PLEASE ENTER CRICKET RELATED Q/A FOR SEARCH";>
   <span class="input-group-btn">
        <button id="buton" class="btn btn-success btn-lg" type="submit">Go!</button>
   </span>
</div>     
</form>
<div class="container">
<h2>Count:</h2><p style="display:inline">${count}</p>
  <h2>Results</h2>
  <table class="table table-striped fixed-cell-width table-bordered table-hover">
    <thead>
      <tr class="fixed-cell-width table-bordered">
        <th>${field0}</th>
        <th>${field1}</th>
        <th>${field2}</th>
        <th>${field3}</th>
        <th>${field4}</th>
        <th>${field5}</th>
      </tr>
    </thead>
    <tbody>
    
<c:forEach items="${resultList1}" var="list1">
<tr>
   	   <td>${list1.field1}</td>
   	   <td>${list1.field2}</td>
   	   <td>${list1.field3}</td>
   	   <td>${list1.field4}</td>
   	   <td>${list1.field5}</td>
   	   <td>${list1.field6}</td>
    	</tr> 
    </c:forEach>
    	
	    </tbody>
  </table>
  <br><br>
  <div class="row">
    <div  style="height:300px; width:300px; float:left">
      <canvas id="myChart1"></canvas>
    </div>
    <div  style="height:300px; width:300px; float:right">
      <canvas id="myChart2"></canvas>
    </div>
  </div>
</div>
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/progressbar.min.js"></script>
<script src="js/Chart.min.js"></script>
	<script type="text/javascript">
	var chartData1 = {};
	var chartData2 = {};
	var ctx1 = document.getElementById("myChart1").getContext("2d");
	var ctx2 = document.getElementById("myChart2").getContext("2d");
	
	
	function respondCanvas(ctx, chartData){
		
		var myPieChart = new Chart(ctx,{
		    type: 'doughnut',
		    data: chartData,
		    options: {
		        animation:{
		            animateScale:true,
		            animateRotate:true
		        }
		    }
		});
	}
	var GetChartData = function (label, dataList, ctx) {
		var data = {
    		    labels: label,
    		    datasets: [
    		        {
    		            data: dataList,
    		            backgroundColor: [
    		                "#FF6384",
    		                "#36A2EB",
    		                "#FFCE56"
    		            ],
    		            hoverBackgroundColor: [
    		                "#FF6384",
    		                "#36A2EB",
    		                "#FFCE56"
    		            ]
    		        }]
    		};
           respondCanvas(ctx, data);
	}
	
	$(document).ready(function () {
		
		/* $.get("NLPservlet", function(data){
	        console.log(data);
	    }); */
	    $.ajax({
	        url: 'NLPservlet',
	        method: 'GET',
	        dataType: 'json',
	        async:false,
	        success: function (d) {
	           console.log(d);
	           GetChartData(d.labels, d.list1, ctx1);
	   		   GetChartData(d.labels, d.list2, ctx2);
	        },
	        error:function(){
		           alert('Chart error');
		         }
	    });
		
	}); 
	</script>
</body>
</html>